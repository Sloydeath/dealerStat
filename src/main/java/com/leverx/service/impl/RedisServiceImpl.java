package com.leverx.service.impl;

import com.leverx.repository.RedisRepository;
import com.leverx.service.RedisService;
import com.leverx.util.HashCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    private static final String PREFIX_EMAIL_PASSWORD_RESET = "resetPassword ";

    private final RedisRepository redisRepository;

    @Autowired
    public RedisServiceImpl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void setHashcodeForPasswordReset(String email) {
        String hashcode = HashCodeGenerator.generateHashCode();
        redisRepository.setHashcode(PREFIX_EMAIL_PASSWORD_RESET + email, hashcode);
    }

    @Override
    public Object getHashcodeForPasswordReset(String email) {
        return redisRepository.getHashcode(PREFIX_EMAIL_PASSWORD_RESET + email);
    }

    @Override
    public void deleteHashcodeForPasswordReset(String email) {
        redisRepository.deleteHashcode(PREFIX_EMAIL_PASSWORD_RESET + email);
    }

    @Override
    public boolean isCodeForPasswordResetExists(String email) {
        return redisRepository.isExists(PREFIX_EMAIL_PASSWORD_RESET + email);
    }

    @Override
    public void setHashcodeForEmailActivation(String email) {
        String hashcode = HashCodeGenerator.generateHashCode();
        redisRepository.setHashcode(email, hashcode);
    }

    @Override
    public Object getHashcodeForEmailActivation(String email) {
        return redisRepository.getHashcode(email);
    }

    @Override
    public void deleteHashcodeForEmailActivation(String email) {
        redisRepository.deleteHashcode(email);
    }

    @Override
    public boolean isCodeForEmailActivationExists(String email) {
        return redisRepository.isExists(email);
    }
}
