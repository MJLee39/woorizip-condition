package waf.fisa.condition.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waf.fisa.condition.dto.ConditionDto;
import waf.fisa.condition.dto.ConditionReqDto;
import waf.fisa.condition.dto.ConditionRespDto;
import waf.fisa.condition.entity.Condition;
import waf.fisa.condition.repository.ConditionRepository;
import waf.fisa.condition.repository.ConditionRepositoryCustom;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class ConditionService {

    private ConditionRepository conditionRepository;
    private ConditionRepositoryCustom conditionRepositoryCustom;

    @Autowired
    public ConditionService (ConditionRepository conditionRepository, ConditionRepositoryCustom conditionRepositoryCustom) {
        this.conditionRepository = conditionRepository;
        this.conditionRepositoryCustom = conditionRepositoryCustom;
    }

    /*
     * Condition save
     */
    public ConditionRespDto save (ConditionReqDto conditionReqDto) {
        log.info("[in Service]: " + conditionReqDto.toString());

        List<ConditionRespDto> list = readAll(conditionReqDto.getAccountId());

        if (!list.isEmpty()) {
            return null;
        } else {
            Condition condition = conditionRepository.save(conditionReqDto.toEntity());
            return new ConditionRespDto(condition);
        }
    }

    /*
     * Condition read
     */
    public ConditionRespDto read(String id) {
        log.info("[in Service]: " + id.toString());

        Condition condition = conditionRepository.findById(id)
                .orElseThrow(EntityExistsException::new);

        return new ConditionRespDto(condition);
    }

    /*
     * Condition readAll
     */
    public List<ConditionRespDto> readAll(String accountId) {
        log.info("[in Service]: {}", accountId);

        ConditionReqDto conditionReqDto = ConditionReqDto.builder()
                .accountId(accountId)
                .build();

        Condition condition = conditionReqDto.toEntity();
        log.info("condition: {}", condition.getAccountId());

        List<ConditionDto> list = conditionRepositoryCustom.readMyConditions(condition);

        return convertToRespDtoFromConditionDto(list);
    }

    /*
     * Condition readByWhere
     */
    public List<ConditionRespDto> readByWhere(ConditionReqDto conditionReqDto) {
        log.info("[in Service]: " + conditionReqDto.toString());

        Condition condition = conditionReqDto.toEntity();

        List<ConditionDto> list = conditionRepositoryCustom.readByBuilder(condition);

        return convertToRespDtoFromConditionDto(list);
    }

    private static List<ConditionRespDto> convertToRespDtoFromConditionDto(List<ConditionDto> list) {
        return list.stream().map(conditionDto -> conditionDto.toConditionRespDto(conditionDto.getId()
                ,conditionDto.getAccountId()
                ,conditionDto.getLocation()
                ,conditionDto.getBuildingType()
                ,conditionDto.getFee()
                ,conditionDto.getMoveInDate()
                ,conditionDto.getHashtag())
        ).toList();
    }

    /*
     * Condition update
     */
    @Transactional
    public ConditionRespDto update(ConditionReqDto conditionReqDto) {
        log.info("[in Service][input]: " + conditionReqDto.toString());

        Condition target = conditionRepository.findById(conditionReqDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        log.info("[in Service][target]: " + target.toString());

        if (!conditionReqDto.getLocation().equals(target.getLocation())) {
            target.updateLocation(conditionReqDto.getLocation());
        }

        if (!conditionReqDto.getBuildingType().equals(target.getBuildingType())) {
            target.updateBuildingType(conditionReqDto.getBuildingType());
        }

        if (conditionReqDto.getFee() != 0) {
            target.updateFee(conditionReqDto.getFee());
        }

        if (!conditionReqDto.getMoveInDate().isEqual(target.getMoveInDate())) {
            target.updateMoveInDate(conditionReqDto.getMoveInDate());
        }

        if (!conditionReqDto.getHashtag().equals(target.getHashtag())) {
            target.updateHashtag(conditionReqDto.getHashtag());
        }

        log.info("[in Service][target]: " + target.toString());

        return new ConditionRespDto(target);
    }

    /*
     * Condition delete
     */
    public String delete(String id) {
        log.info("[in Service]: " + id);

        if (conditionRepository.existsById(id)) {
            conditionRepository.deleteById(id);
            log.info("** in if: target was deleted.");
            return "deleted";
        } else {
            log.info("** in if: target didnt existed.");
            return "fail";
        }
    }
}
