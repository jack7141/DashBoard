package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

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
        // Given
        String randomName = UUID.randomUUID().toString();
        Member newMember = Member.builder()
                .name(randomName)
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build();

        // When
        Long newMemberId = memberService.addMember(newMember);
        Member findMember = memberService.getMemberById(newMemberId).orElse(null);


        // Then
        assertNotNull(findMember, "회원이 정상적으로 저장되어야 합니다.");
        Assertions.assertEquals(newMemberId, findMember.getMemberId(), "회원 ID가 일치해야 합니다.");

    }


    @Test
    @DisplayName("서비스 레이어 중복 회원가입 테스트")
    void DuplicateMember() {
        Member member = new Member();
        member.setName("TestUser11");
        assertThrows(IllegalStateException.class, () -> memberService.addMember(member)); // 1번 방법

    }
}