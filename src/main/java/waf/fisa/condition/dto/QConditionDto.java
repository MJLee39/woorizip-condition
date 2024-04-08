package waf.fisa.condition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class QConditionDto {

    private String registeredId;

    private String location;

    private String buildingType;

    private String fee;

    private LocalDate moveInDate;

    private String hashtag;

    private String accountId;

    private String nickname;


    @Builder
    public Condition toEntity() {
        return Condition.builder()
                .registeredId(registeredId)
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
