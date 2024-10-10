package com.dashboard.dashboard.dto.oath2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor // Add this so Jackson can deserialize
@Getter
@Setter
public class GoogleOAuthToken {
    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
