package com.dashboard.dashboard.dto;

import com.dashboard.dashboard.domain.Member;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class memberDTO {
    private Long memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createAt;
    private List<memberDetailDTO> details;

    // Member 엔티티를 DTO로 변환하는 정적 메서드
    public static memberDTO toDTO(Member member) {
        return memberDTO.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .createAt(member.getCreateDate())
                .details(member.getDetails().stream()
                        .map(memberDetailDTO::toDTO)  // MemberDetail -> MemberDetailDTO 변환
                        .collect(Collectors.toList()))
                .build();
    }

    // List<Member>를 List<memberDTO>로 변환하는 정적 메서드
    public static List<memberDTO> toDTO(List<Member> members) {
        return members.stream()
                .map(memberDTO::toDTO)
                .collect(Collectors.toList());
    }
}