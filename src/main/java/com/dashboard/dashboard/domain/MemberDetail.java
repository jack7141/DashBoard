package com.dashboard.dashboard.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "member_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder
    public MemberDetail(Long memberId, String phoneNumber, String address) {
        this.memberId = memberId;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}