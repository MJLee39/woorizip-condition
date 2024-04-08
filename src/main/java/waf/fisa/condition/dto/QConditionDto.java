package waf.fisa.condition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

}
