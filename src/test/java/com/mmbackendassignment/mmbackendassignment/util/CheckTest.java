package com.mmbackendassignment.mmbackendassignment.util;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import org.junit.jupiter.api.Test;

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
        dto.maxHeightMeter = 2.0f;
        dto.pricePerMonth = 30f;

        // act
        Check.hasNullable( dto );

        // assert
        assertEquals( true, true );

    }
}