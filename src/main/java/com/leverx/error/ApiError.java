package com.leverx.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class is a model of error @ResponseBody
 *
 * @author Andrew Panas
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ApiError(String message, String debugMessage){
        this.message=message;
        this.debugMessage=debugMessage;
    }
}
