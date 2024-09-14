package com.dashboard.dashboard.repository;

import com.dashboard.dashboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataJPAMemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmailAndPhoneNumber(String email, String phoneNumber);

    Optional<Member> findByPhoneNumberAndEmail(String phoneNumber, String email);

    Optional<Member> findByName(String name);

}
