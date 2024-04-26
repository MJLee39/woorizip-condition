package waf.fisa.condition.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waf.fisa.condition.dto.ConditionReqDto;
import waf.fisa.condition.dto.ConditionRespDto;
import waf.fisa.condition.service.ConditionService;

import java.util.List;

@Slf4j
@RestController
public class ConditionController {

    private waf.fisa.condition.service.ConditionService conditionService;

    @Autowired
    public ConditionController (ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    /*
    is registered
     */
    @GetMapping("/condition/isregistered/{accountId}")
    public ResponseEntity<Boolean> isRegistered(@PathVariable String accountId) {
        log.info("[in controller]: " + accountId);

        Boolean isRegistered = conditionService.isRegistered(accountId);

        if (isRegistered) {
            // isRegistered: true  -> 등록된 조건 있음 -> 등록 불가
            return ResponseEntity.ok(true);
        } else {
            // isRegistered: false -> 등록된 조건 없음 -> 등록 가능
            return ResponseEntity.ok(false);
        }
    }

    /*
    save
     */
    @CrossOrigin("*")
    @PostMapping("/condition/save")
    public ResponseEntity<ConditionRespDto> save(@RequestBody ConditionReqDto conditionReqDto) {
        log.info("[in controller]: " + ConditionReqDto.builder().toString());

        ConditionRespDto conditionRespDto = conditionService.save(conditionReqDto);

        return ResponseEntity.ok(conditionRespDto);
    }

    /*
    read
     */
    @CrossOrigin("*")
    @GetMapping("/condition/read/{id}")
    public ResponseEntity<ConditionRespDto> read(@PathVariable String id) {
        log.info("[in controller]: " + id);

        ConditionRespDto filterRespDto = conditionService.read(id);

        return ResponseEntity.ok(filterRespDto);
    }

    /*
    readAll
     */
    @CrossOrigin("*")
    @GetMapping("/condition/readAll/{accountId}")
    public ResponseEntity<List<ConditionRespDto>> readAll(@PathVariable String accountId) {
        log.info("[in controller]: " + accountId);

        List<ConditionRespDto> conditionRespDto = conditionService.readAll(accountId);

        return ResponseEntity.ok(conditionRespDto);
    }

    /*
    readByWhere
     */
    @CrossOrigin("*")
    @PostMapping("/condition/readByWhere")
    public ResponseEntity<List<ConditionRespDto>> readByWhere(@RequestBody ConditionReqDto conditionReqDto) {
        log.info("[in controller]: " + conditionReqDto.toString());

        List<ConditionRespDto> conditionRespDtos = conditionService.readByWhere(conditionReqDto);

         return ResponseEntity.ok(conditionRespDtos);
    }

    /*
    update
     */
    @CrossOrigin("*")
    @PostMapping("/condition/update")
    public ResponseEntity<ConditionRespDto> update(@RequestBody ConditionReqDto conditionReqDto) {
        log.info("[in controller]: " + conditionReqDto.toString());

        ConditionRespDto filterRespDto = conditionService.update(conditionReqDto);

        return ResponseEntity.ok(filterRespDto);
    }

    /*
    delete
     */
    @CrossOrigin("*")
    @DeleteMapping("/condition/delete/{id}")
    public String delete(@PathVariable String id) {
//        log.info("[in controller]: " + id);

        String result = conditionService.delete(id);
        log.info("result: {}", result);

        if (result.equals("deleted")) {
            return "* target was deleted.";
        } else {
            return "* fail.";
        }
    }

}
