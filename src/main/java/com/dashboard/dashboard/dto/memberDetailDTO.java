package com.dashboard.dashboard.dto;

import com.dashboard.dashboard.domain.MemberDetail;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class memberDetailDTO {
    private String description;
    private String type;

    // MemberDetail 엔티티를 DTO로 변환하는 정적 메서드
    public static memberDetailDTO toDTO(MemberDetail detail) {
        return memberDetailDTO.builder()
                .description(detail.getDescription())
                .type(detail.getPk().getType())  // Pk 객체의 type 필드에 접근
                .build();
    }

}
