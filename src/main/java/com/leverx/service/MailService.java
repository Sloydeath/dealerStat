package com.leverx.service;

import javax.mail.MessagingException;

public interface MailService {
    void createMessageForEmailActivationAndSend(String email, String hashcode) throws MessagingException;
    void createMessageForPasswordResetAndSend(String email, String hashcode) throws MessagingException;
}
