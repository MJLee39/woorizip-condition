package waf.fisa.condition.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import waf.fisa.condition.dto.ConditionRespDto;
import waf.fisa.condition.dto.QConditionDto;
import waf.fisa.condition.entity.Condition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ConditionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Condition> readByWhere(Condition condition) {
        return queryFactory
                .select(
                        new QConditionDto(
                            Expressions.asString(condition.getRegisteredId()),
                            Expressions.asString(condition.getLocation()),
                            Expressions.asString(condition.getBuildingType()),
                            condition.fee.as(condition.getFee()),
                            condition.moveInDate.as(String.valueOf(condition.getMoveInDate())),
                            condition.hashtag.as(condition.getHashtag()),
                            Expressions.asString(condition.getAccountId()),
                            Expressions.asString(condition.getNickname())
                        )
                )
                .from(condition)
                .where(
                        idEq(condition.getRegisteredId()),
                        locationEq(condition.getLocation()),
                        buildingTypeEq(condition.getBuildingType()),
                        feeLoe(condition.getFee()),
                        moveInDateAfter(String.valueOf(condition.getMoveInDate())),
                        hashtagEq(condition.getHashtag()),
                        accountIdEq(condition.getAccountId()),
                        nicknameEq(condition.getNickname())
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