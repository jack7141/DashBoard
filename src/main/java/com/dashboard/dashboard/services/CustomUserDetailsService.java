package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.member.Member;
import com.dashboard.dashboard.dto.member.CustomUserDetails;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DataJPAMemberRepository DataJPAMemberRepository;

    public CustomUserDetailsService(DataJPAMemberRepository DataJPAMemberRepository) {

        this.DataJPAMemberRepository = DataJPAMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        Member userData = DataJPAMemberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));;

        if (userData != null) {

            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
    }

}
