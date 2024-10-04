package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.member.Member;
import com.dashboard.dashboard.dto.member.CustomUserDetails;
import com.dashboard.dashboard.dto.member.LoginReq;
import com.dashboard.dashboard.dto.member.memberDTO;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import com.dashboard.dashboard.jwt.JWTUtil;
import com.exceptons.DuplicateMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final DataJPAMemberRepository dataJPAMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public memberDTO register(memberDTO memberDTO) {
        Member newMember = memberDTO.toEntity(passwordEncoder);

        validateDuplicateEmailAndPhoneNumber(newMember);
        Member savedMember = dataJPAMemberRepository.save(newMember);
        return memberDTO.of(savedMember);
    }

    public String login(LoginReq LoginReq) {
        try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(LoginReq.getEmail(), LoginReq.getPassword())
                );
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                String email = customUserDetails.getUsername();

                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                String role = authorities.iterator().next().getAuthority();
                return jwtUtil.createJwt(email, role);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("인증 실패: 잘못된 이메일 또는 비밀번호.");
        }
    }


    private void validateDuplicateEmailAndPhoneNumber(Member member) {
        if (dataJPAMemberRepository.existsByEmailOrMemberDetail_PhoneNumber(
                member.getEmail(), member.getMemberDetail().getPhoneNumber())) {
            throw new DuplicateMemberException("중복된 이메일 또는 핸드폰 번호로 회원 가입을 시도했습니다.");
        }
    }

    public Optional<memberDTO> getMemberById(Long memberId) {
        return dataJPAMemberRepository.findById(memberId).map(memberDTO::of);
    }

    public Optional<memberDTO> getMemberByUserName(String userName) {
        return dataJPAMemberRepository.findByName(userName).map(memberDTO::of);
    }

    public List<memberDTO> getMembers() {
        List<Member> members = dataJPAMemberRepository.findAll();
        return members.stream()
                .map(memberDTO::of)
                .collect(Collectors.toList());
    }

    public Optional<memberDTO> getMembersByUserPhoneNumber(String phoneNumber) {
        return dataJPAMemberRepository.findByMemberDetail_PhoneNumber(phoneNumber).map(memberDTO::of);
    }
}