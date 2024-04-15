package waf.fisa.condition.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import waf.fisa.condition.dto.ConditionDto;
import waf.fisa.condition.dto.QConditionDto;
import waf.fisa.condition.entity.Condition;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static waf.fisa.condition.entity.QCondition.condition;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ConditionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ConditionDto> readMyConditions(Condition input) {
        BooleanBuilder builder = new BooleanBuilder();

        log.info("** in ConditionRepositoryCustom, input: accountId: {}", input.getAccountId());

        builder.and(condition.accountId.eq(input.getAccountId()));

        log.info("** in ConditionRepositoryCustom, builder: {}", builder.toString());

        return queryFactory
                .select(new QConditionDto(
                        condition.id,
                        condition.accountId,
                        condition.location,
                        condition.buildingType,
                        condition.fee,
                        condition.moveInDate,
                        condition.hashtag
                ))
                .from(condition)
                .where(builder)
                .fetch();
    }

    public List<ConditionDto> readByBuilder(Condition input) {
        BooleanBuilder builder = new BooleanBuilder();

        log.info("** in ConditionRepositoryCustom, input: \n id: {}, \n accountId: {}, \n location: {}, \n buildingType: {}, " +
                        "\n fee: {}, \n moveInDate: {}, \n hashTag: {}",
                input.getId(), input.getAccountId(), input.getLocation(), input.getBuildingType()
        , input.getFee(), input.getMoveInDate(), input.getHashtag());

        if (hasText(input.getLocation())) {
            builder.and(condition.location.eq(input.getLocation()));
        }

        if (hasText(input.getBuildingType())) {
            builder.and(condition.buildingType.eq(input.getBuildingType()));
        }

        if (input.getFee() != 0 && hasText(String.valueOf(input.getFee()))) {
            builder.and(condition.fee.loe(input.getFee()));
        }

        if (input.getMoveInDate() != null) {
            if (hasText(String.valueOf(input.getMoveInDate()))) {
                builder.and(condition.moveInDate.after(input.getMoveInDate()));
            }
        }

        if (hasText(input.getHashtag())) {
            builder.and(condition.hashtag.contains(input.getHashtag()));
        }

        log.info("** in ConditionRepositoryCustom, builder: {}", builder);

        return queryFactory
                .select(new QConditionDto(
                        condition.id,
                        condition.accountId,
                        condition.location,
                        condition.buildingType,
                        condition.fee,
                        condition.moveInDate,
                        condition.hashtag
                ))
                .from(condition)
                .where(builder)
                .fetch();
    }
}