package com.leverx.model.custom;

import lombok.Data;

@Data
public class Rating {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long points;
}
