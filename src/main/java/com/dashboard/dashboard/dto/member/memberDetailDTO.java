package com.dashboard.dashboard.dto.member;

import com.dashboard.dashboard.domain.MemberDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class memberDetailDTO {
    private Long memberId;
    private String address;
    private String phoneNumber;

    public static memberDetailDTO of(MemberDetail memberDetail) {
        return memberDetailDTO.builder()
                .memberId(memberDetail.getMemberId())
                .address(memberDetail.getAddress())
                .phoneNumber(memberDetail.getPhoneNumber())
                .build();
    }

    public MemberDetail toEntity() {
        return MemberDetail.builder()
                .memberId(this.memberId)
                .address(this.address)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
