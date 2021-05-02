package com.leverx.service;

import javax.mail.MessagingException;

/**
 * Interface of mail service
 *
 * @author Andrew Panas
 */

public interface MailService {
    void createMessageForEmailActivationAndSend(String email, String hashcode) throws MessagingException;
    void createMessageForPasswordResetAndSend(String email, String hashcode) throws MessagingException;
}
