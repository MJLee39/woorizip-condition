package waf.fisa.condition.dto;

import lombok.*;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConditionReqDto {

    private String id;

    private String location;

    private String buildingType;

    private int fee;

    private LocalDate moveInDate;

    private String hashtag;

    private String accountId;

    private String nickname;

    @Builder
    public ConditionReqDto(String location, String buildingType,
                           int fee, LocalDate moveInDate, String hashtag, String accountId, String nickname) {
        this.location = location;
        this.buildingType = buildingType;
        this.fee = fee;
        this.moveInDate = moveInDate;
        this.hashtag = hashtag;
        this.accountId = accountId;
        this.nickname = nickname;
    }

    public Condition toEntity () {
        return Condition.builder()
                .id(UUID.randomUUID().toString())
                .location(location)
                .buildingType(buildingType)
                .fee(fee)
                .moveInDate(moveInDate)
                .hashtag(hashtag)
                .accountId(accountId)
                .nickname(nickname)
                .build();
    }

}
