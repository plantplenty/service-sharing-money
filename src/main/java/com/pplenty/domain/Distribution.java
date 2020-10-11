package com.pplenty.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

/**
 * Created by yusik on 2020/10/09.
 */
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @ManyToOne(targetEntity = Sharing.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_id", foreignKey = @ForeignKey(name = "FK_DISTRIBUTION_SHARING"))
    private Sharing sharing;

    private int amount;

    @OneToOne(mappedBy = "distribution", cascade = ALL)
    private Taking taking;

    @Builder
    public Distribution(Sharing sharing, int amount, Taking taking) {
        this.sharing = sharing;
        this.amount = amount;
        this.taking = taking;
    }

    public boolean isReady() {
        return Objects.isNull(taking);
    }

    public boolean isDone() {
        return Objects.nonNull(taking);
    }

    public Long getTakenUserId() {

        if (Objects.isNull(taking)) {
            return null;
        }

        return taking.getTakenUserId();
    }

    public LocalDateTime getTakenDate() {
        if (Objects.isNull(taking)) {
            return null;
        }

        return taking.getCreatedDate();
    }
}
