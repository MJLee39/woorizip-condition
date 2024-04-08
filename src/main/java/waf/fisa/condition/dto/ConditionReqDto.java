package waf.fisa.condition.dto;

import lombok.*;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ConditionReqDto {

    private String registeredId;

    private String location;

    private String buildingType;

    private String fee;

    private LocalDate moveInDate;

    private String hashtag;

    private String accountId;

    private String nickname;

    @Builder
    public Condition toEntity () {
        return Condition.builder()
                .registeredId(UUID.randomUUID().toString())
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
