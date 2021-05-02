package com.leverx.model.custom;

import lombok.Data;

/**
 * DTO class of rating of traders
 *
 * @author Andrew Panas
 */

@Data
public class Rating {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long points;
}
