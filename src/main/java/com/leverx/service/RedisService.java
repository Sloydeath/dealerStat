package com.leverx.service;

/**
 * Interface of redis service
 *
 * @author Andrew Panas
 */

public interface RedisService {
    void setHashcodeForPasswordReset(final String email);
    Object getHashcodeForPasswordReset(final String email);
    void deleteHashcodeForPasswordReset(final String email);
    boolean isCodeForPasswordResetExists(final String email);

    void setHashcodeForEmailActivation(final String email);
    Object getHashcodeForEmailActivation(final String email);
    void deleteHashcodeForEmailActivation(final String email);
    boolean isCodeForEmailActivationExists(final String email);

}
