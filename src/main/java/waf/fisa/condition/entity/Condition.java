package waf.fisa.condition.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Condition {

    @Id
    private String id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String buildingType;

    private String accountId;

    private String fee;

    private LocalDate moveInDate;

    private String hashtag;

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
