package org.example.utils.mail;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);
}
