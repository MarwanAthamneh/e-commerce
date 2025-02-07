package org.example.startupprjoect.Service.ServiceImpl;

import org.example.startupprjoect.Repository.RoleRepository;
import org.example.startupprjoect.Repository.UserRepository;
import org.example.startupprjoect.Service.AuthService;
import org.example.startupprjoect.Util.PasswordValidator;
import org.example.startupprjoect.model.Cart;
import org.example.startupprjoect.model.UserE;
import org.example.startupprjoect.model.dto.AuthResponseDTO;
import org.example.startupprjoect.model.dto.LoginDTO;
import org.example.startupprjoect.model.dto.RegisterDTO;
import org.example.startupprjoect.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTGenerator jwtGenrator;

    @Transactional
    public String register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (!PasswordValidator.isPasswordStrong(registerDTO.getPassword())) {

            List<String> passwordErrors = PasswordValidator.getPasswordStrengthErrors(registerDTO.getPassword());
            throw new RuntimeException("Weak password: " + String.join(", ", passwordErrors));

        }


        UserE user = new UserE();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setName(registerDTO.getName());


        Cart cart = new Cart();
        cart.setUserE(user);
        cart.setItems(new ArrayList<>());


        user.setCart(cart);


        userRepository.save(user);


        return "User registered successfully";
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserE user = userRepository.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtGenrator.generateToken(authentication, user);

            return new AuthResponseDTO(token);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}