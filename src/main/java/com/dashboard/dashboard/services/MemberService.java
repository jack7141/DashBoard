package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import com.dashboard.dashboard.dto.memberDTO;
import com.dashboard.dashboard.dto.memberDetailDTO;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import jakarta.transaction.Transactional;
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

    public memberDTO add(memberDTO memberDTO) {
        Member newMember = memberDTO.toEntity();
        validateDuplicateEmail(newMember);
        Member savedMember = dataJPAMemberRepository.save(newMember);
        return memberDTO.of(savedMember);
    }

    private void validateDuplicateEmail(Member member) {
        dataJPAMemberRepository.findByEmail(member.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("중복된 이메일 회원 가입입니다.");
        });
    }

    public Optional<memberDTO> getMemberById(Long memberId) {
        return dataJPAMemberRepository.findById(memberId).map(memberDTO::of);
    }

    public Optional<memberDTO> getMemberByUserName(String userName) {
        return dataJPAMemberRepository.findByName(userName).map(memberDTO::of);
    }

    public List<memberDTO> getMembers() {
        List<Member> members = dataJPAMemberRepository.findAll();
        return members.stream()
                .map(memberDTO::of)
                .collect(Collectors.toList());
    }

    public Optional<memberDTO> getMembersByUserPhoneNumber(String phoneNumber) {
        return dataJPAMemberRepository.findByMemberDetail_PhoneNumber(phoneNumber).map(memberDTO::of);
    }
}