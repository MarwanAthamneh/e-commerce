package org.example.startupprjoect.Service.ServiceImpl;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.example.startupprjoect.Service.EmailService;
import org.example.startupprjoect.model.dto.ContactDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final String sendGridApiKey;


    public EmailServiceImpl(@Value("${sendgrid.api-key}") String sendGridApiKey) {
        this.sendGridApiKey = sendGridApiKey;
        System.out.println("SendGrid API Key: " + sendGridApiKey); // Debug line
    }

    public void sendEmail(ContactDTO contact) throws IOException {
        SendGrid sg = new SendGrid(sendGridApiKey);

        Email from = new Email("marwan.geed@gmail.com");
        Email to = new Email("marwan.geed@gmail.com");

        String emailContent = String.format(
                "Contact Form Submission\n\n" +
                        "Name: %s\n" +
                        "Email: %s\n" +
                        "Subject: %s\n\n" +
                        "Message:\n%s",
                contact.getName(),
                contact.getEmail(),
                contact.getSubject(),
                contact.getMessage()
        );

        Content content = new Content("text/plain", emailContent);
        Mail mail = new Mail(from, contact.getSubject(), to, content);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                logger.info("Email sent successfully. Status: {}", response.getStatusCode());
            } else {
                logger.error("Failed to send email. Status: {}", response.getStatusCode());
                throw new RuntimeException("Failed to send email");
            }
        } catch (IOException e) {
            logger.error("Error sending email", e);
            throw e;
        }
    }
}