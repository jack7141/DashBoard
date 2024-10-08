package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.member.Member;
import com.dashboard.dashboard.domain.member.Role;
import com.dashboard.dashboard.dto.member.CustomOauth2UserDetails;
import com.dashboard.dashboard.dto.member.GoogleUserDetails;
import com.dashboard.dashboard.jwt.JWTUtil;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final DataJPAMemberRepository  dataJPAMemberRepository;
    private final JWTUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());

        }
        System.out.println("OAuth2User Attributes: " + oAuth2User.getAttributes());
//        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
//        String loginId = provider + "_" + providerId;
        String name = oAuth2UserInfo.getName();
        System.out.println("name: " + name);
        System.out.println("email: " + email);
        Member member = dataJPAMemberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .name(name)
                            .email(email)
                            .role(Role.USER.getKey())
                            .build();
                    return dataJPAMemberRepository.save(newMember);
                });

        String token = jwtUtil.createJwt(email, Role.USER.getKey());

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("token", token);
        System.out.println("OAuth2User token: " + token);
        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
}
