package waf.fisa.condition.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * waf.fisa.condition.dto.QConditionDto is a Querydsl Projection type for ConditionDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QConditionDto extends ConstructorExpression<ConditionDto> {

    private static final long serialVersionUID = -41621071L;

    public QConditionDto(com.querydsl.core.types.Expression<String> id, com.querydsl.core.types.Expression<String> location, com.querydsl.core.types.Expression<String> buildingType, com.querydsl.core.types.Expression<String> fee, com.querydsl.core.types.Expression<java.time.LocalDate> moveInDate, com.querydsl.core.types.Expression<String> hashtag, com.querydsl.core.types.Expression<String> accountId, com.querydsl.core.types.Expression<String> nickname) {
        super(ConditionDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, java.time.LocalDate.class, String.class, String.class, String.class}, id, location, buildingType, fee, moveInDate, hashtag, accountId, nickname);
    }

}

