package waf.fisa.condition.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConditionRespDto {

    private String id;

    private String location;

    private String buildingType;

    private String fee;

    private LocalDate moveInDate;

    private String hashtag;

    private String accountId;

    private String nickname;

    public ConditionRespDto(Condition condition) {
        this.id = condition.getId();
        this.location = condition.getLocation();
        this.buildingType = condition.getBuildingType();
        this.fee = condition.getFee();
        this.moveInDate = condition.getMoveInDate();
        this.hashtag = condition.getHashtag();
        this.accountId = condition.getAccountId();;
        this.nickname = condition.getNickname();
    }

    @Override
    public String toString() {
        return String.format(
                this.id + " " +
                this,location + " " +
                this.buildingType + " " +
                this.fee + " " +
                this.moveInDate + " " +
                this.hashtag + " " +
                this.accountId + " " +
                this.nickname);
    }

}
