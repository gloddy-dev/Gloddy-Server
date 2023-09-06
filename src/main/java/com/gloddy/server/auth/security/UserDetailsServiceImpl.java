package com.gloddy.server.auth.security;


import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.jwt.JwtUserAdapter;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
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

    private final UserQueryHandler userQueryHandler;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        User user = userQueryHandler.findByPhone(new Phone(phoneNumber))
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));

        return JwtUserAdapter.from(user);
    }
}
