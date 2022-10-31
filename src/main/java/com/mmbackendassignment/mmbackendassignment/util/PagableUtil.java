package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.dto.PageDto;
import com.mmbackendassignment.mmbackendassignment.exception.SortNotSupportedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PagableUtil {

    public static PageDto createPageDto( ArrayList content, Page page ) {

        PageDto dto = new PageDto(
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalPages()
        );

        dto.content = content;

        return dto;
    }


    public static PageRequest createPageRequest(int page, int size, String sort, Class dto ) {
        sort = getSortName( sort, dto );

        return PageRequest.of(page, size, Sort.by(sort));
    }

    private static String getSortName( String sort, Class dto ){
        boolean sortExist = false;
        for (Field f : dto.getDeclaredFields() ){
            if (f.getName().equalsIgnoreCase( sort )){
                sort = f.getName();
                sortExist = true;
                break;
            }
        }

        if (!sortExist){
            throw new SortNotSupportedException( sort );
        }
        return sort;
    }
}
