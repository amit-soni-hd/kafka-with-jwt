package com.github.amitsoni.jwt.jwtexample.security.filter;

import com.github.amitsoni.jwt.jwtexample.security.service.EmployeeDetailsService;
import com.github.amitsoni.jwt.jwtexample.security.module.JwtTokenProvider;
import com.github.amitsoni.jwt.jwtexample.security.constant.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final AuthenticationManager authenticationManager;
    private EmployeeDetailsService employeeDetailsService;
    private JwtTokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  EmployeeDetailsService employeeDetailsService, JwtTokenProvider tokenProvider) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.employeeDetailsService = employeeDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                String email = tokenProvider.getUserFromJWT(jwt);
                UserDetails userDetails = employeeDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (userDetails, null,null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}