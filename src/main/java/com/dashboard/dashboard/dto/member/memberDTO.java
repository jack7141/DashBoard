package com.dashboard.dashboard.dto.member;

import com.dashboard.dashboard.domain.member.Member;
import com.dashboard.dashboard.domain.member.MemberDetail;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class memberDTO {
    private Long memberId;
    private String name;
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role;
    private memberDetailDTO memberDetail;

    public static memberDTO of(Member member) {
        return memberDTO.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getRole())
                .memberDetail(member.getMemberDetail() != null ? memberDetailDTO.of(member.getMemberDetail()) : null)
                .build();
    }


    public Member toEntity(PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .memberId(this.memberId)
                .name(this.name)
                .email(this.email)
                .role(this.role != null ? this.role : "ROLE_USER")
                .password(this.password != null ? passwordEncoder.encode(this.password) : null)
                .build();

        if (this.memberDetail != null) {
            MemberDetail detail = this.memberDetail.toEntity();
            member.setMemberDetail(detail);
        }

        return member;
    }

}
