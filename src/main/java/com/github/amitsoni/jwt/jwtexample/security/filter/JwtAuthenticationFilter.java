package com.github.amitsoni.jwt.jwtexample.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsoni.jwt.jwtexample.dto.LoginRequest;
import com.github.amitsoni.jwt.jwtexample.dto.LoginResponse;
import com.github.amitsoni.jwt.jwtexample.security.module.EmployeeDetails;
import com.github.amitsoni.jwt.jwtexample.security.module.JwtTokenProvider;
import com.github.amitsoni.jwt.jwtexample.security.constant.SecurityConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        LoginRequest loginRequest = new LoginRequest();

        try {
            loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

        } catch (IOException e) {
            loginRequest.setEmail("");
            loginRequest.setPassword("");
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {

        String token = tokenProvider.generateToken(authentication, new ArrayList<>());

        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setEmail(employeeDetails.getEmail());
        loginResponse.setEmployeeName(employeeDetails.getEmployeeName());
        loginResponse.setId(employeeDetails.getEmployeeId());
        loginResponse.setMobileNo(employeeDetails.getMobileNo());
        loginResponse.setType(SecurityConstants.TOKEN_HEADER);
        loginResponse.setToken(SecurityConstants.TOKEN_PREFIX + token);

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponse));
    }
}