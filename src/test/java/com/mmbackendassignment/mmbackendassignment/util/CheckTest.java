package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.exception.EntityNotFromJwtUserException;
import com.mmbackendassignment.mmbackendassignment.exception.NullValueNotAllowedException;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckTest {

    @Test
    void shouldCheckIfObjectContainNullValues() {

        // arrange
        FieldInputDto dto = new FieldInputDto();
        dto.description = "Groot veld";
        dto.meters = 8.0f;
        dto.features = new ArrayList<String>();
        dto.maxHeightMeter = null;
        dto.pricePerMonth = 30f;

        // assert
        assertThrows( NullValueNotAllowedException.class, () -> Check.hasNullable( dto ) );

    }
}