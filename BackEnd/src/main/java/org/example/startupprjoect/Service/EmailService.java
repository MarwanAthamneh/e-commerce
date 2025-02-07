package org.example.startupprjoect.Service;

import org.example.startupprjoect.model.dto.ContactDTO;

import java.io.IOException;

public interface EmailService {
    void sendEmail(ContactDTO contact) throws IOException;
}