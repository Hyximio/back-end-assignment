package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.exception.ContractWithOwnException;
import com.mmbackendassignment.mmbackendassignment.exception.EntityNotFromJwtUserException;
import com.mmbackendassignment.mmbackendassignment.exception.UserIsDisabledException;
import com.mmbackendassignment.mmbackendassignment.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtHandler {

    public static boolean isAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth.getPrincipal() instanceof UserDetails userDetails ) {

            Collection authorities = userDetails.getAuthorities();
            for( Object a : authorities){
                if( a.toString().equals( "ADMIN" ) ) return true;
            }
        }
        return false;
    }

//    public static boolean isEnabled(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if( auth.getPrincipal() instanceof UserDetails userDetails ){
//            if ( userDetails.isEnabled() ){
//                throw new UserIsDisabledException();
//            };
//        }
//        return false;
//    }

    public static void abortIfEntityIsNotFromSameUser( Object entity ){
        if (!JwtHandler.isEntityFromSameUser(entity)){
            throw new EntityNotFromJwtUserException();
        }
    }
    public static boolean isEntityFromSameUser( Object entity ){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if( auth.getPrincipal() instanceof UserDetails ) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String username = "";

            if (entity instanceof User user) {
                username = user.getUsername();
            }

            if (entity instanceof Profile profile) {
                username = profile.getUser().getUsername();
            }

            if (entity instanceof Client client) {
                username = client.getProfile().getUser().getUsername();
            }

            if (entity instanceof Owner owner) {
                username = owner.getProfile().getUser().getUsername();
            }

            if (entity instanceof Address address) {
                username = address.getOwner().getProfile().getUser().getUsername();
            }

            if (entity instanceof Field field) {
                username = field.getAddress().getOwner().getProfile().getUser().getUsername();
            }

            return username.equals( userDetails.getUsername() );
        }

        return false;
    }
}
