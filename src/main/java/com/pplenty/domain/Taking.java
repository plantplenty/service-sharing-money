package com.pplenty.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * Created by yusik on 2020/10/09.
 */
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_TAKING_1", columnNames = {"takenUserId", "sharing_id"}),
        @UniqueConstraint(name = "UNIQUE_TAKING_2", columnNames = {"distribution_id", "sharing_id"})
})
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Taking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long takenUserId;

    @ManyToOne
    @JoinColumn(name = "sharing_id", foreignKey = @ForeignKey(name = "FK_TAKING_SHARING"))
    private Sharing sharing;

    @OneToOne
    @JoinColumn(name = "distribution_id", foreignKey = @ForeignKey(name = "FK_TAKING_DISTRIBUTION"))
    private Distribution distribution;

    private LocalDateTime createdDate;

    @Builder
    public Taking(long takenUserId, Sharing sharing, Distribution distribution, LocalDateTime createdDate) {
        this.takenUserId = takenUserId;
        this.sharing = sharing;
        this.distribution = distribution;
        this.createdDate = createdDate;
    }
}
