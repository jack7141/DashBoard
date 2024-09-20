package com.dashboard.dashboard.dto;

import com.dashboard.dashboard.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/*
* @AllArgsConstructor가 없으면 빌더 패턴에서 문제가 발생하는 이유는
* @Builder 애노테이션이 @AllArgsConstructor와 함께 사용되어 모든 필드를 초기화하는 생성자를 필요로 하기 때문입니다.
* 빌더 패턴은 내부적으로 모든 필드를 초기화하는 생성자를 사용하여 객체를 생성합니다.
*  따라서 @AllArgsConstructor가 없으면 @Builder가 제대로 동작하지 않을 수 있습니다.

이유:
@Builder는 필드에 값을 설정하는 생성자를 필요로 함:
@Builder는 클래스의 모든 필드를 설정하기 위해 내부적으로 모든 필드를 포함한 생성자를 사용합니다. 이 생성자가 자동으로 만들어지지 않으면 빌더 패턴은 객체를 생성할 수 없습니다.

@AllArgsConstructor는 모든 필드를 인자로 받는 생성자를 자동 생성:
@AllArgsConstructor는 모든 필드를 인자로 받는 생성자를 만들어줍니다. 이 생성자가 있어야만 @Builder가 각 필드의 값을 초기화하면서 객체를 만들 수 있습니다.
*
* */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class memberDTO {
    private Long memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createAt;
    private List<memberDetailDTO> details;

    // Member 엔티티를 DTO로 변환하는 정적 메서드
    public static memberDTO of(Member member) {
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
    public static List<memberDTO> of(List<Member> members) {
        return members.stream()
                .map(memberDTO::of)
                .collect(Collectors.toList());
    }

    // DTO를 Member 엔티티로 변환하는 메서드
    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .createDate(this.createAt)
                .build();
    }

}