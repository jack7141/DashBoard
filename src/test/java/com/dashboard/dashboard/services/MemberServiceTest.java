package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    @DisplayName("서비스 레이어 회원가입 테스트")
    void addMember() {
        Long newMember = memberService.addMember(Member.builder()
                .name("TestUser11")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build());
        Member findMember = memberService.getMemberById(newMember).get();
        Assertions.assertEquals(1L, findMember.getMemberId());
    }
}