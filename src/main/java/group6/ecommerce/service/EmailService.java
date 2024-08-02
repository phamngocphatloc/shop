package group6.ecommerce.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void SendEmailTo(String email, String node);
    public void sendEmail(String to, String subject, String text) throws MessagingException;
}
