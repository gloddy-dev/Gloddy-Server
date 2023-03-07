package com.gloddy.server.auth.security;


import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.jwt.JwtUserAdapter;
import com.gloddy.server.user.repository.UserRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));

        return JwtUserAdapter.from(user);

    }
}
