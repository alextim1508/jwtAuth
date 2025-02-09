package com.alextim.jwtAuth.service;

import com.alextim.jwtAuth.dto.JwtAuthenticationResponse;
import com.alextim.jwtAuth.dto.SignInRequest;
import com.alextim.jwtAuth.dto.SignUpRequest;
import com.alextim.jwtAuth.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = userMapper.toDomain(request);

        userService.save(user);

        var jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        var user = userService
                .loadUserByUsername(request.getUsername());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }
}
