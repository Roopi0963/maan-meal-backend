package org.techtricks.maanmeal.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${maanmeal.notifications.email.enabled:true}")
    private boolean emailEnabled; // This will read the value from application.properties

    @Value("${spring.mail.username}")
    private String fromEmail; // Read the 'from' email from properties (e.g., your Gmail)

    @Override
    public void sendMail(String to, String subject, String text) {
        // If email notifications are disabled, don't send an email
        if (!emailEnabled) {
            return;
        }

        // Prepare the email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); // Use the 'from' email from properties
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        // Send the email
        try {
            mailSender.send(message);
        } catch (Exception e) {
            // Log the exception or handle the error
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
