package com.dashboard.dashboard.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class memberDTO {
    private Long memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createAt;
    private List<memberDetailDTO> details;
}