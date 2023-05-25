package com.ecommerce.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationAccountEmail(String from, String receiver, String link, String code) throws Exception{
        
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(from));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject("Activate Account");

        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center;\">Activate Account</h1>\n");
        sb.append("<p style=\"text-align: center;\">In the following link you can activate your account entering the following code: "+code+"</p>\n");
        sb.append("<p style=\"text-align: center;\"><a href=\"" + link + "\">Click Here To Follow The Link</a></p>\n");
        String htmlContent = sb.toString();
        message.setContent(htmlContent, "text/html; charset=utf-8");
    
        mailSender.send(message);
    }

    public void sendRecoverPasswordEmail(String from, String receiver, String link) throws Exception{

        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(from));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject("Recover Password");

        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center;\">Recover password</h1>\n");
        sb.append("<p style=\"text-align: center;\">In the following link you can update your password</p>\n");
        sb.append("<p style=\"text-align: center;\"><a href=\"" + link + "\">Click Here To Follow The Link</a></p>\n");
        String htmlContent = sb.toString();
        message.setContent(htmlContent, "text/html; charset=utf-8");
    
        mailSender.send(message);

    }
}
