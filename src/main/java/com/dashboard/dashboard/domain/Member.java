package com.dashboard.dashboard.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String name;
    private String email;
    private String phoneNumber;

    @Column(name = "create_dt")
    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<MemberDetail> details;

    @Builder
    public Member(String name, String email, String phoneNumber, LocalDateTime createDate) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate != null ? createDate : LocalDateTime.now();
    }
}