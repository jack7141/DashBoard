package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import com.dashboard.dashboard.dto.memberDTO;
import com.dashboard.dashboard.dto.memberDetailDTO;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    @Autowired
    private DataJPAMemberRepository dataJPAMemberRepository;

    public Long addMember(Member member) {
        validateDuplicateEmail(member);
        dataJPAMemberRepository.save(member);
        return member.getMemberId();
    }

    private void validateDuplicateUserName(Member member) {
        dataJPAMemberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("Member name is already in use");
        });
    }

    private void validateDuplicateEmail(Member member) {
        dataJPAMemberRepository.findByEmail(member.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("중복된 이메일 회원 가입입니다.");
        });
    }


    // Entity -> DTO
    public Optional<memberDTO> getMemberById(Long memberId) {
        return dataJPAMemberRepository.findById(memberId).map(memberDTO::toDTO);
    }

    // Entity -> DTO
    public Optional<memberDTO> getMemberByUserName(String userName) {
        Optional<Member> optionalMember = dataJPAMemberRepository.findByName(userName);

        if (optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            return Optional.of(memberDTO.toDTO(findMember));
        } else {
            return Optional.empty();
        }
    }

    public List<memberDTO> getMembers() {
        List<Member> members = dataJPAMemberRepository.findAll();
        // Member 엔티티 리스트를 MemberDTO 리스트로 변환
        return memberDTO.toDTO(members);
    }
}
