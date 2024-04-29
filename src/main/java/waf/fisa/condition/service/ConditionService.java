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
import java.util.Optional;

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
     * Condition isRegistered
     * !list.isEmpty(): false -> 등록된 조건 없음 -> 등록 가능
     * !list.isEmpty(): true  -> 등록된 조건 있음 -> 등록 불가
     */
    public Boolean isRegistered (String accountId) {
        log.info("[in service]: " + accountId);

        Optional<ConditionDto> one = conditionRepositoryCustom.readMyConditions(accountId);

        return one.isPresent();
    }

    /*
     * Condition save
     */
    public ConditionRespDto save (ConditionReqDto conditionReqDto) {
        log.info("[in Service]: " + conditionReqDto.toString());

        Condition condition = conditionRepository.save(conditionReqDto.toEntity());

        return new ConditionRespDto(condition);
    }

    /*
     * Condition read
     */
    public ConditionDto read(String accountId) {
        log.info("[in Service]: " + accountId.toString());

        ConditionDto conditionDto = conditionRepositoryCustom.readMyConditions(accountId)
                .orElseThrow(EntityExistsException::new);

        return conditionDto;
    }

    /*
     * Condition readAll
     */
    public List<ConditionRespDto> readAll() {
        log.info("[in Service]: {}");

        List<Condition> list = conditionRepository.findAll();

        return convertToRespDtoFromCondition(list);
    }

    private static List<ConditionRespDto> convertToRespDtoFromCondition(List<Condition> list) {
        return list.stream().map(condition -> condition.toConditionRespDto(condition)
        ).toList();
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
