package com.leverx.controller;

import com.leverx.error.exception.UserNotFoundException;
import com.leverx.model.User;
import com.leverx.model.dto.PasswordDTO;
import com.leverx.service.MailService;
import com.leverx.service.RedisService;
import com.leverx.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = Logger.getLogger(AuthController.class);
    private final RedisService redisService;
    private final MailService mailService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(RedisService redisService, MailService mailService, UserService userService, PasswordEncoder passwordEncoder) {
        this.redisService = redisService;
        this.mailService = mailService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/confirm/{email}/{hash_code}")
    public ResponseEntity<?> activateUserByCode(@PathVariable("hash_code") String hashCode, @PathVariable("email") String email) {

        if (redisService.getHashcodeForEmailActivation(email).equals(hashCode)) {
            User user = userService.findUserByEmail(email);
            user.setActive(true);
            userService.update(user);
            redisService.deleteHashcodeForEmailActivation(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        redisService.deleteHashcodeForEmailActivation(email);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> sendCodeToResetPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            log.info("In method sendCodeToResetPassword: User not found Exception");
            throw new UserNotFoundException("User not found");
        }
        redisService.setHashcodeForPasswordReset(email);
        final String hashCode = redisService.getHashcodeForPasswordReset(email);
        try {
            mailService.createMessageForPasswordResetAndSend(email, hashCode);
        }
        catch (MessagingException ex) {
            log.error("Error in method sendCodeToResetPassword: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        model.addAttribute(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email, @RequestBody PasswordDTO password) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            log.info("In method resetPassword: User not found Exception");
            throw new UserNotFoundException("User not found");
        }
        if (password.getCode().equals(redisService.getHashcodeForPasswordReset(email))) {
            redisService.deleteHashcodeForPasswordReset(email);
            user.setPassword(passwordEncoder.encode(password.getNewPassword()));
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/check_code")
    public ResponseEntity<?> checkIfCodeExists(@RequestParam("email") String email) throws MessagingException {
        boolean codeIsExists = redisService.isCodeForEmailActivationExists(email);
        if (!codeIsExists) {
            User user = userService.findUserByEmail(email);
            if (user == null) {
                log.info("In method checkIfCodeExists: User not found Exception");
                throw new UserNotFoundException("User not found");
            }
            if (!user.isActive()) {
                redisService.setHashcodeForEmailActivation(email);
                String code = redisService.getHashcodeForEmailActivation(email);
                mailService.createMessageForEmailActivationAndSend(email, code);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}