package com.gloddy.server.auth.security;


import com.gloddy.server.user.domain.User;
import com.gloddy.server.auth.jwt.JwtUserAdapter;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserQueryHandler userQueryHandler;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userQueryHandler.findById(Long.parseLong(userId));
        return JwtUserAdapter.from(user);
    }
}
