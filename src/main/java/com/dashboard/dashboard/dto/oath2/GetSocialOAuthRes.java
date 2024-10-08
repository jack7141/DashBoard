package com.dashboard.dashboard.dto.oath2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSocialOAuthRes {

    private String jwtToken;
    private int user_num;
    private String accessToken;
    private String tokenType;
}