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
}