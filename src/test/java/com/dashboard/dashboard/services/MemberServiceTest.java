package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addMember() {
        Member member = Member.builder()
                .name("Test User")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build();

        Long newMember = memberService.addMember(member);
        Member findMember = memberService.getMemberById(newMember).get();
        Assertions.assertEquals(member, findMember);

    }
}