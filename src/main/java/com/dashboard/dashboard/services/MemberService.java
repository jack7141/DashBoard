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


    public Optional<Member> getMemberById(Long memberId) {
        return dataJPAMemberRepository.findById(memberId);
    }

    public Optional<Member> getMemberByUserName(String userName) {
        return dataJPAMemberRepository.findByName(userName);
    }

    public List<memberDTO> getMembers() {
//        return dataJPAMemberRepository.findAll();
        List<Member> members = dataJPAMemberRepository.findAll();

        // Member 엔티티 리스트를 MemberDTO 리스트로 변환
        return members.stream()
                .map(member -> memberDTO.builder()
                        .memberId(member.getMemberId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .phoneNumber(member.getPhoneNumber())
                        .createAt(member.getCreateDate())  // LocalDateTime을 그대로 사용
                        .details(member.getDetails().stream()
                                .map(detail -> memberDetailDTO.builder()
                                        .description(detail.getDescription())
                                        // Pk 객체의 type 필드에 접근
                                        .type(detail.getPk().getType())
                                        .build())
                                .collect(Collectors.toList()))  // List<memberDetailDTO>로 변환
                        .build())
                .collect(Collectors.toList());
    }
}
