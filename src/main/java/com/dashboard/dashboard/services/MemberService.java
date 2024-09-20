package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.Member;
import com.dashboard.dashboard.dto.member.memberDTO;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import com.exceptons.DuplicateMemberException;
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

    public memberDTO register(memberDTO memberDTO) {
        Member newMember = memberDTO.toEntity();
        validateDuplicateEmailAndPhoneNumber(newMember);
        Member savedMember = dataJPAMemberRepository.save(newMember);
        return memberDTO.of(savedMember);
    }

    private void validateDuplicateEmailAndPhoneNumber(Member member) {
        if (dataJPAMemberRepository.existsByEmailOrMemberDetail_PhoneNumber(
                member.getEmail(), member.getMemberDetail().getPhoneNumber())) {
            throw new DuplicateMemberException("중복된 이메일 또는 핸드폰 번호로 회원 가입을 시도했습니다.");
        }
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