//package waf.fisa.condition.service;
//
//import jakarta.persistence.EntityExistsException;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import waf.fisa.condition.dto.ConditionDto;
//import waf.fisa.condition.dto.ConditionReqDto;
//import waf.fisa.condition.dto.ConditionRespDto;
//import waf.fisa.condition.entity.Condition;
//import waf.fisa.condition.repository.ConditionRepository;
//import waf.fisa.condition.repository.ConditionRepositoryCustom;
//
//import java.util.List;
//
//@Slf4j
//@Service
//@NoArgsConstructor
//public class ConditionService {
//
//    private ConditionRepository conditionRepository;
//    private ConditionRepositoryCustom conditionRepositoryCustom;
//
//    @Autowired
//    public ConditionService (ConditionRepository conditionRepository) {
//        this.conditionRepository = conditionRepository;
//    }
//
//    /*
//     * Condition save
//     */
//    public ConditionRespDto save (ConditionReqDto conditionReqDto) {
//        log.info("[in Service]: " + conditionReqDto.toString());
//
//        Condition condition = conditionRepository.save(conditionReqDto.toEntity());
//
//        return new ConditionRespDto(condition);
//    }
//
//    /*
//     * Condition read
//     */
//    public ConditionRespDto read(String id) {
//        log.info("[in Service]: " + id.toString());
//
//        Condition condition = conditionRepository.findById(id)
//                .orElseThrow(EntityExistsException::new);
//
//        return new ConditionRespDto(condition);
//    }
//
//    /*
//     * Condition readAll
//     */
//    public List<Condition> readAll() {
//        log.info("[in Service]: ");
//
//        List<Condition> list = conditionRepository.findAll();
//
//        return list;
//    }
//
//    /*
//     * Condition readByWhere
//     */
//    public List<ConditionDto> readByWhere(ConditionReqDto conditionReqDto) {
//        log.info("[in Service]: " + conditionReqDto.toString());
//
//        List<ConditionDto> conditionDtos = conditionRepositoryCustom.readByWhere(conditionReqDto);
//
//        return conditionDtos;
//    }
//
//    /*
//     * Condition update
//     */
//    public ConditionRespDto update(ConditionReqDto conditionReqDto) {
//        log.info("[in Service][input]: " + conditionReqDto.toString());
//
//        Condition target = conditionRepository.findById(conditionReqDto.getId())
//                .orElseThrow(EntityNotFoundException::new);
//
//        log.info("[in Service][target]: " + target.toString());
//
//        if (!conditionReqDto.getLocation().equals(target.getLocation())) {
//            target.updateLocation(conditionReqDto.getLocation());
//        }
//
//        if (!conditionReqDto.getBuildingType().equals(target.getBuildingType())) {
//            target.updateBuildingType(conditionReqDto.getBuildingType());
//        }
//
//        if (!conditionReqDto.getFee().equals(target.getFee())) {
//            target.updateFee(conditionReqDto.getFee());
//        }
//
//        if (!conditionReqDto.getMoveInDate().isEqual(target.getMoveInDate())) {
//            target.updateMoveInDate(conditionReqDto.getMoveInDate());
//        }
//
//        if (!conditionReqDto.getHashtag().equals(target.getHashtag())) {
//            target.updateHashtag(conditionReqDto.getHashtag());
//        }
//
//        if (!conditionReqDto.getNickname().equals(target.getNickname())) {
//            target.updateNickname(conditionReqDto.getNickname());
//        }
//
//        log.info("[in Service][target]: " + target.toString());
//
//        return new ConditionRespDto(target);
//    }
//
//    /*
//     * Condition delete
//     */
//    public String delete(String id) {
//        log.info("[in Service]: " + id);
//
//        if (conditionRepository.existsById(id)) {
//            conditionRepository.deleteById(id);
//            return id;
//        } else {
//            return id;
//        }
//    }
//}
