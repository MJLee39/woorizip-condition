package waf.fisa.condition.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import waf.fisa.grpc.condition.ConditionReq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@ToString
@Table(name = "filter")
public class Condition {

    /**
     * 무작위로 생성되는 UUID, Condition의 구분값
     */
    @Id
    private String id;

    /**
     * 기본 조건: 소재지
     */
    @Column(nullable = false)
    private String location;

    /**
     * 기본 조건: 건물 유형
     */
    @Column(nullable = false)
    private String buildingType;

    /**
     * Condition을 등록한 의뢰인
     */
    private String accountId;

    /**
     * 상세 조건: 월세 상한가
     */
    private String fee;

    /**
     * 상세 조건: 입주 가능일
     */
    private LocalDate moveInDate;

    /**
     * 상세 조건: 원하는 부가 옵션
     */
    private String hashtag;

    /**
     * 상세 조건: 의뢰인이 등록한 조건들의 별칭
     */
    private String nickname;

    @Builder
    public Condition(String id, String location, String buildingType, String fee, LocalDate moveInDate,
                     String hashtag, String accountId, String nickname) {
        this.id = id;
        this.location = location;
        this.buildingType = buildingType;
        this.accountId = accountId;
        this.fee = fee;
        this.moveInDate = moveInDate;
        this.hashtag = hashtag;
        this.nickname = nickname;
    }

    public static Condition toEntity (ConditionReq input) {
        return Condition.builder()
                .id(UUID.randomUUID().toString())
                .location(input.getLocation())
                .buildingType(input.getBuildingType())
                .accountId(input.getAccountId())
                .fee(input.getFee())
                .moveInDate(LocalDate.parse(input.getMoveInDate(), DateTimeFormatter.ISO_DATE))
                .hashtag(input.getHashtag())
                .nickname(input.getNickname())
                .build();
    }

    public void updateId(String id) {
        this.id = id;
    }

    public void updateLocation(String location) {
        this.location = location;
    }

    public void updateBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public void updateFee(String fee) {
        this.fee = fee;
    }

    public void updateMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }

    public void updateHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
