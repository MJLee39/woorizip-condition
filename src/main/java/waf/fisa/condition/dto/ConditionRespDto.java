package waf.fisa.condition.dto;

import lombok.*;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConditionRespDto {

    private String id;

    private String accountId;

    private String location;

    private String buildingType;

    private int fee;

    private LocalDate moveInDate;

    private String hashtag;

    public ConditionRespDto(Condition condition) {
        this.id = condition.getId();
        this.accountId = condition.getAccountId();;
        this.location = condition.getLocation();
        this.buildingType = condition.getBuildingType();
        this.fee = condition.getFee();
        this.moveInDate = condition.getMoveInDate();
        this.hashtag = condition.getHashtag();
    }

//    public ConditionRespDto toConditionRespDto(ConditionDto conditionDto) {
//        this.id = conditionDto.getId();
//        this.accountId = conditionDto.getAccountId();;
//        this.location = conditionDto.getLocation();
//        this.buildingType = conditionDto.getBuildingType();
//        this.fee = conditionDto.getFee();
//        this.moveInDate = conditionDto.getMoveInDate();
//        this.hashtag = conditionDto.getHashtag();
//
//        return new ConditionRespDto(conditionDto.getId(), conditionDto.getAccountId(), conditionDto.getLocation()
//        , conditionDto.getBuildingType(), conditionDto.getFee(), conditionDto.getMoveInDate(), conditionDto.getHashtag());
//    }

    @Override
    public String toString() {
        return String.format(
                this.id + " " +
                this.accountId + " " +
                this,location + " " +
                this.buildingType + " " +
                this.fee + " " +
                this.moveInDate + " " +
                this.hashtag + " "
        );
    }

}
