package com.dashboard.dashboard.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetail {

    @EmbeddedId
    private Pk pk;

    private String description;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    @Embeddable
    @Getter
    @Setter
    public static class Pk implements Serializable {

        @Column(name = "member_id")
        private Long memberId;

        private String type;
    }
}