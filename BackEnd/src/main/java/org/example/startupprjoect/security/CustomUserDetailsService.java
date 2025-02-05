package org.example.startupprjoect.security;

import org.example.startupprjoect.Repository.UserRepository;
import org.example.startupprjoect.model.Role;
import org.example.startupprjoect.model.UserE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserE user = userRepository.findByEmail(username).
                orElseThrow(()->new UsernameNotFoundException("Email not found"));


        return new User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){

        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }
}
