package org.example.startupprjoect.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenrator jwtGenrator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJWTFromRequest(request);

        if(StringUtils.hasText(token) && jwtGenrator.validateToken(token)) {
            String username = jwtGenrator.getUsernameFromJwt(token);

            List<String> roles = jwtGenrator.getRolesFromJwt(token);


            List<GrantedAuthority> authorities = new ArrayList<>();
            if (roles != null && !roles.isEmpty()) {
                authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }

            if (authorities.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (userDetails == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Authorization Header Received: " + bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}