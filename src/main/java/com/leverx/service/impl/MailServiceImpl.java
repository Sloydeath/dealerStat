package com.leverx.service.impl;

import com.leverx.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = Logger.getLogger(MailServiceImpl.class);
    private static final String MESSAGE_SUBJECT_REGISTRATION_CONFIRMATION = "Registration Confirmation";
    private static final String MESSAGE_SUBJECT_PASSWORD_RESET = "Password reset";

    private final Session mailSession;

    @Autowired
    public MailServiceImpl(Session mailSession) {
        this.mailSession = mailSession;
    }

    @Override
    public void createMessageForEmailActivationAndSend(String email, String hashcode) {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress(mailSession.getProperty("mail.smtps.user")));
            message.setSubject(MESSAGE_SUBJECT_REGISTRATION_CONFIRMATION);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            String confirmationUrl = "http://localhost:8080/auth/confirm/" + email + "/" + hashcode;
            message.setText(String.format("Click on the reference to confirm your account.\n\n" +
                    "%s", confirmationUrl));
            sendMessage(message);
        }
        catch (MessagingException e) {
            log.error("In method createMessageAndSend: " + e.getMessage());
        }
    }

    @Override
    public void createMessageForPasswordResetAndSend(String email, String hashcode) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress(mailSession.getProperty("mail.smtps.user")));
            message.setSubject(MESSAGE_SUBJECT_PASSWORD_RESET);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setText(String.format("Enter this code in your reset password field.\n\n" +
                    "%s", hashcode));
            sendMessage(message);
        }
        catch (MessagingException e) {
            log.error("In method createMessageAndSend: " + e.getMessage());
        }
    }

    private void sendMessage(MimeMessage message) {
        try (Transport transport = mailSession.getTransport()) {
            transport.connect(null, mailSession.getProperty("mail.smtps.password"));
            transport.sendMessage(message, message.getAllRecipients());
        }
        catch (MessagingException e) {
            log.error("In method sendMessage: " + e.getMessage());
        }
    }
}
