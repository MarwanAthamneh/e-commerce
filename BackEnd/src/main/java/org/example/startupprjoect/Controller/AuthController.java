package org.example.startupprjoect.Controller;

import org.example.startupprjoect.Service.ServiceImpl.AuthServiceImpl;
import org.example.startupprjoect.model.dto.AuthResponseDTO;
import org.example.startupprjoect.model.dto.LoginDTO;
import org.example.startupprjoect.model.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO)
    {

        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO)
    {
        return new ResponseEntity<>(authService.login(loginDTO),HttpStatus.OK);
    }

    @GetMapping("/debug")
    public ResponseEntity<?> debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getAuthorities());
    }
}
