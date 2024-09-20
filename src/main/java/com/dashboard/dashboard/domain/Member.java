package com.dashboard.dashboard.domain;

import com.dashboard.dashboard.dto.memberDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member", orphanRemoval = true)
    private List<MemberDetail> details = new ArrayList<>();

    @Builder
    public Member(String name, String email, String phoneNumber, LocalDateTime createDate) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate != null ? createDate : LocalDateTime.now();
    }
}