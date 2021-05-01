package com.leverx.registration;

import com.leverx.model.User;
import com.leverx.service.MailService;
import com.leverx.service.RedisService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final RedisService redisService;
    private final MailService mailService;

    @Autowired
    public RegistrationListener(RedisService redisService, MailService mailService) {
        this.redisService = redisService;
        this.mailService = mailService;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        User user = event.getUser();
        redisService.setHashcodeForEmailActivation(user.getEmail());
        Object hashcode = redisService.getHashcodeForEmailActivation(user.getEmail());
        if (hashcode != null) {
            mailService.createMessageForEmailActivationAndSend(user.getEmail(), hashcode.toString());
        }
        else {
            throw new RuntimeException("Hash doesn't exist");
        }
    }
}
