package com.leverx.controller;

import com.leverx.error.exception.UserAlreadyExistException;
import com.leverx.registration.OnRegistrationCompleteEvent;
import com.leverx.model.User;
import com.leverx.service.UserService;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller for registration's URL.
 * Check src/main/resources/postman/Dealer_stat.postman_collection.json for more detail URL mapping.
 *
 * @author Andrew Panas
 */

@RestController
public class RegistrationController {
    private static final Logger log = Logger.getLogger(RegistrationController.class);
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;

    @Autowired
    public RegistrationController(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @SneakyThrows
    @PostMapping("/user/registration")
    public ResponseEntity<?> registerUserAccount(@RequestBody @Valid User user, HttpServletRequest request) {
        log.debug(String.format("Registering user account with information: %s", user));
        User registered = userService.registerNewUserAccount(user);
        if (registered == null) {
            log.debug("In method registerUserAccount: User already exist exception");
            throw new UserAlreadyExistException();
        }
        String appUrl = "http://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
