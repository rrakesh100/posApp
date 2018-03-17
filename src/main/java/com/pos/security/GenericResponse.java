package com.pos.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.persistence.SecondaryTable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rrampall on 16/03/18.
 */
public class GenericResponse {

    @Getter @Setter
    private String message;

    @Getter @Setter
    private String error;

    public GenericResponse(final String message) {
        super();
        this.message = message;
    }

    public GenericResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }

//    public GenericResponse(List<ObjectError> allErrors, String error) {
//        this.error = error;
//        String temp = allErrors.stream().map(e -> {
//            if (e instanceof FieldError) {
//                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            } else {
//                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            }
//        }).collect(Collectors.joining(","));
//        this.message = "[" + temp + "]";
//    }


}
