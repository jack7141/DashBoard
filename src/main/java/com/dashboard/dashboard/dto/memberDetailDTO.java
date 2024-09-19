package com.dashboard.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class memberDetailDTO {
    private String description;
    private String type;
}
