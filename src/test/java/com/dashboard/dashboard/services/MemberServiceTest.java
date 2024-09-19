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
        Member newMember = Member.builder()
                .name("TestUser11")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build();
        assertThrows(IllegalStateException.class, () -> memberService.addMember(newMember)); // 1번 방법
    }

    @Test
    @DisplayName("서비스 레이어 Member Id 조회 테스트")
    void getMemberById() {
        // Given
        long memberId = 1;

        // When
        Member findMember = memberService.getMemberById(memberId).orElse(null);

        // Then
        Assertions.assertEquals(findMember.getMemberId(), memberId, "회원 ID가 일치해야 합니다.");
        Assertions.assertEquals(findMember.getName(), "TestUser11", "회원 이름이 일치해야 합니다.");
        Assertions.assertEquals(findMember.getEmail(), "test@example.com", "회원 이메일이 일치해야 합니다.");
    }

    @Test
    @DisplayName("서비스 레이어 Member 이름 조회 테스트")
    void getMemberByUserName() {
        // Given
        long memberId = 1;
        String memberName = "TestUser11";
        String memberEmail = "test@example.com";

        // When
        Member findMember = memberService.getMemberByUserName(memberName).orElse(null);

        // Then
        Assertions.assertEquals(findMember.getMemberId(), memberId, "회원 ID가 일치해야 합니다.");
        Assertions.assertEquals(findMember.getName(), memberName, "회원 이름이 일치해야 합니다.");
        Assertions.assertEquals(findMember.getEmail(), memberEmail, "회원 이메일이 일치해야 합니다.");

    }
}