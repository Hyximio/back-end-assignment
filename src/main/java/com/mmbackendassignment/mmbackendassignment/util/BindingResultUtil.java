package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.exception.BindingResultException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BindingResultUtil {

    public static void check( BindingResult br) {

        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            throw new BindingResultException(sb.toString());
        }
    }
}
