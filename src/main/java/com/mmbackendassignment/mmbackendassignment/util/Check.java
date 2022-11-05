package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.exception.BindingResultException;
import com.mmbackendassignment.mmbackendassignment.exception.CantDeleteWithDependencyException;
import com.mmbackendassignment.mmbackendassignment.exception.NullValueNotAllowedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;

public class Check {

    public static void hasNullable( Object dto ) {

        try {

            for (Field f : dto.getClass().getDeclaredFields()) {
                if (f.get(dto) == null) {
                    throw new NullValueNotAllowedException();
                }
            }
        }catch( IllegalAccessException e ){
            System.out.println(e);
        }
    }


    public static void bindingResults( BindingResult br) {

        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField())
                  .append(": ")
                  .append(fe.getDefaultMessage())
                  .append("\n");
            }
            throw new BindingResultException(sb.toString());
        }
    }

    public static void hasDependency( Object dependency, String dependencyName ){
        if( dependency != null ) {
            throw new CantDeleteWithDependencyException( dependencyName );
        }
    }

    /* isDependent parameter should be populated with a statement */
    public static void hasDependency( boolean isDependent, String dependencyName ){
        if( isDependent ) {
            throw new CantDeleteWithDependencyException( dependencyName );
        }
    }
}
