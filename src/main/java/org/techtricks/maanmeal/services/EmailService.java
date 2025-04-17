package org.techtricks.maanmeal.services;

public interface EmailService {
    void sendMail(String to, String subject, String text);
}
