package org.example.startupprjoect.Controller;


import org.example.startupprjoect.Service.EmailService;
import org.example.startupprjoect.Service.ServiceImpl.EmailServiceImpl;
import org.example.startupprjoect.model.dto.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    private final EmailService emailService;

    @Autowired
    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ContactDTO contact) {
        try {
            emailService.sendEmail(contact);
            return ResponseEntity.ok("Message sent successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}
