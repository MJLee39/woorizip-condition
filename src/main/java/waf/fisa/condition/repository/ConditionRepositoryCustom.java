package waf.fisa.condition.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;
import waf.fisa.condition.dto.ConditionDto;
import waf.fisa.condition.dto.ConditionReqDto;
import waf.fisa.condition.dto.ConditionRespDto;
import waf.fisa.condition.dto.QConditionDto;
import waf.fisa.condition.entity.Condition;

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

    public List<ConditionDto> readByWhere(ConditionReqDto conditionDto) {

        return queryFactory
                .select(new QConditionDto(
                        condition.registeredId,
                        condition.location,
                        condition.buildingType,
                        condition.fee,
                        condition.moveInDate,
                        condition.hashtag,
                        condition.accountId,
                        condition.nickname
                ))
                .from(condition)
                .where(
                        idEq(conditionDto.getId()),
                        locationEq(conditionDto.getLocation()),
                        buildingTypeEq(conditionDto.getBuildingType()),
                        feeLoe(conditionDto.getFee()),
                        moveInDateAfter(conditionDto.getMoveInDate()),
                        hashtagEq(conditionDto.getHashtag()),
                        accountIdEq(conditionDto.getAccountId()),
                        nicknameEq(conditionDto.getNickname())
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

    private BooleanExpression moveInDateAfter(LocalDate modeInDate) {
        return modeInDate != null ? condition.moveInDate.eq(modeInDate) : null;
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