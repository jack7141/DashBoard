package com.dashboard.dashboard.repository;

import com.dashboard.dashboard.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DataJPAMemberRepositoryTest {

    @Autowired
    private DataJPAMemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    @Commit
    void addMember() {
        Member member = memberRepository.save(Member.builder()
                .name("Test User")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build());

        Member findMember = memberRepository.findByEmail("test@example.com").get();
        Assertions.assertEquals(member, findMember);
        assertThat(member).isNotNull();
        assertThat(member.getMemberId()).isNotNull();
        assertThat(member.getName()).isEqualTo("Test User");
    }


    @Test
    void findByMemberId() {
        Member findMember = memberRepository.findByMemberId(1L).get();
        Assertions.assertEquals("test@example.com", findMember.getEmail());

    }

    @Test
    void findByEmail() {
        Member findMember = memberRepository.findByEmail("test@example.com").get();
        Assertions.assertEquals("test@example.com", findMember.getEmail());
    }

    @Test
    void findByPhoneNumber() {
        Member findMember = memberRepository.findByMemberDetail_PhoneNumber("1234567890").get();
        Assertions.assertEquals("1234567890", findMember.getPhoneNumber());
    }

    @Test
    @DisplayName("이메일과 전화번호로 회원 찾기 테스트")
    void findByEmailAndPhoneNumber() {
        // Given: 테스트용 회원 생성 및 저장
        Member member = Member.builder()
                .name("TestUser123123")
                .email("test@exampl12312331e.com")
                .phoneNumber("1234567890")
                .build();

        memberRepository.save(member);

        // When: 이메일과 전화번호로 회원 조회
        Optional<Member> optionalMember = memberRepository.findByEmailAndPhoneNumber("test@exampl12312331e.com", "1234567890");

        // Then: 조회된 회원이 존재하고, 정보가 일치하는지 확인
        assertTrue(optionalMember.isPresent(), "회원이 존재해야 합니다.");
        Member findMember = optionalMember.get();
        assertEquals("test@exampl12312331e.com", findMember.getEmail());
        assertEquals("1234567890", findMember.getPhoneNumber());
    }

    @Test
    @DisplayName("전화번호와 이메일로 회원 찾기 테스트")
    void findByPhoneNumberAndEmail() {
        Optional<Member> optionalMember = memberRepository.findByPhoneNumberAndEmail("1234567890", "test@example.com");
        assertTrue(optionalMember.isPresent(), "회원이 존재해야 합니다.");

        Member findMember = optionalMember.get();
        assertEquals("test@example.com", findMember.getEmail());
        assertEquals("1234567890", findMember.getPhoneNumber());
    }

    @Test
    void findByName() {
    }
}