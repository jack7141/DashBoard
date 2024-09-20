package com.dashboard.dashboard.repository;

import com.dashboard.dashboard.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataJPAMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);

    Optional<Member> findByMemberDetail_PhoneNumber(String phoneNumber);

    boolean existsByEmailOrMemberDetail_PhoneNumber(String email, String phoneNumber);



}
