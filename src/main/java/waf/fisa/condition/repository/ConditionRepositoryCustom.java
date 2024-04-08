package waf.fisa.condition.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import waf.fisa.condition.dto.QConditionDto;
import waf.fisa.condition.entity.Condition;
import waf.fisa.condition.entity.QCondition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static waf.fisa.condition.entity.QCondition.condition;

@Repository
@RequiredArgsConstructor
public class ConditionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Condition> readByWhere(QConditionDto dto) {
        return queryFactory
                .select(new QConditionDto(
                        Expressions.asString(dto.getRegisteredId()),
                        Expressions.asString(dto.getLocation()),
                        Expressions.asString(dto.getBuildingType()),
                        condition.fee.as(dto.getFee().toString()),
                        condition.moveInDate.as(String.valueOf(dto.getMoveInDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))),
                        condition.hashtag.as(dto.getHashtag().toString()),
                        condition.accountId.as(dto.getAccountId().toString()),
                        Expressions.asString(dto.getNickname())
                ))
                .from(condition)
                .where(
                        idEq(dto.getRegisteredId()),
                        locationEq(dto.getLocation()),
                        buildingTypeEq(dto.getBuildingType()),
                        feeLoe(dto.getFee()),
                        moveInDateAfter(String.valueOf(dto.getMoveInDate())),
                        hashtagEq(dto.getHashtag()),
                        accountIdEq(dto.getAccountId()),
                        nicknameEq(dto.getNickname())
                )
                .fetch();

    }

    private BooleanExpression idEq(String id) {
        return hasText(id) ? condition.registeredId.eq(id) : null;
    }

    private BooleanExpression locationEq(String location) {
        return hasText(location) ? condition.location.eq(location) : null;
    }

    private BooleanExpression buildingTypeEq(String buildingType) {
        return hasText(buildingType) ? condition.buildingType.eq(buildingType) : null;
    }

    private BooleanExpression feeLoe(String fee) {
        return hasText(fee) ? condition.fee.loe(fee) : null;
    }

    private BooleanExpression moveInDateAfter(String modeInDate) {
        return hasText(modeInDate) ? condition.moveInDate.eq((Expression<? super LocalDate>) new Date(modeInDate)) : null;
    }

    private BooleanExpression hashtagEq(String hashtag) {
        return hasText(hashtag) ? condition.registeredId.eq(hashtag) : null;
    }

    private BooleanExpression accountIdEq(String accountId) {
        return hasText(accountId) ? condition.registeredId.eq(accountId) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? condition.registeredId.eq(nickname) : null;
    }

}