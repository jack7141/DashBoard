package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private DataJPAMemberRepository dataJPAMemberRepository;

    public Long addMember(Member member) {
        validateDuplicateUserName(member);
        dataJPAMemberRepository.save(member);
        return member.getMemberId();
    }

    private void validateDuplicateUserName(Member member) {
        dataJPAMemberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("Member name is already in use");
        });
    }

    public Optional<Member> getMemberById(Long memberId) {
        return dataJPAMemberRepository.findById(memberId);
    }

    public Optional<Member> getMemberByUserName(String userName) {
        return dataJPAMemberRepository.findByName(userName);
    }
}
