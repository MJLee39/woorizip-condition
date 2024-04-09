package waf.fisa.condition.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;

@Getter
public class ConditionDto {

    private String id;

    private String location;

    private String buildingType;

    private String fee;

    private LocalDate moveInDate;

    private String hashtag;

    private String accountId;

    private String nickname;

    @QueryProjection
    public ConditionDto(String id, String location, String buildingType, String fee, LocalDate moveInDate, String hashtag, String accountId, String nickname) {
        this.id = id;
        this.location = location;
        this.buildingType = buildingType;
        this.fee = fee;
        this.moveInDate = moveInDate;
        this.hashtag = hashtag;
        this.accountId = accountId;
        this.nickname = nickname;
    }

    public Condition toEntity() {
        return Condition.builder()
                .id(id)
                .moveInDate(moveInDate)
                .location(location)
                .buildingType(buildingType)
                .fee(fee)
                .hashtag(hashtag)
                .accountId(accountId)
                .nickname(nickname)
                .build();
    }
}
