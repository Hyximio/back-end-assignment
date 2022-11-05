package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.exception.NullValueNotAllowedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

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