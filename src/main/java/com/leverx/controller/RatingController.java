package com.leverx.controller;

import com.leverx.model.custom.IRating;
import com.leverx.model.custom.Rating;
import com.leverx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingController {

    private final UserService userService;

    @Autowired
    public RatingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rating")
    public ResponseEntity<List<IRating>> getTradersRating() {
        List<IRating> rating = userService.getTradersRating();
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }
}
