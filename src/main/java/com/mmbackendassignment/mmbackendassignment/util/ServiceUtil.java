package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class ServiceUtil {

    public static Object getRepoObjectById( JpaRepository repo, long id, String name ){
        Optional op = repo.findById( id );
        if ( op.isEmpty() ){
            throw new RecordNotFoundException( name, id );
        }
        return op.get();
    }

    public static Object getRepoObjectById( JpaRepository repo, String id, String name ){
        Optional op = repo.findById( id );
        if ( op.isEmpty() ){
            throw new RecordNotFoundException( name, id );
        }
        return op.get();
    }
}
