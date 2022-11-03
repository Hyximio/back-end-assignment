package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.FieldOutputDto;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.FieldRepository;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(JwtHandler.class)
class FieldServiceTest {

    @Mock
    FieldRepository repo;

    @Mock
    AddressRepository addressRepo;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    FieldService service;


    @Captor
    ArgumentCaptor<Field> fieldCaptor;

    Field field1;
    Field field2;
    Address address1;

    @BeforeEach
    void setup(){

        field1 = new Field();
        field1.setId(1);
        field1.setDescription("Awesome");
        field1.setMeters(8.0f);
        field1.setMaxHeightMeter(1.5f);
        field1.setPricePerMonth(30.0f);

        address1 = new Address();

        field1.setAddress( address1 );
        field1.setContracts(new ArrayList<>());

        List<Field> fields = new ArrayList<>();
        fields.add( field1 );
        address1.setFields( fields );
    }

    @Test
    void getField() {

        when( repo.findById(1L) ).thenReturn( Optional.ofNullable(field1) );

        FieldOutputDto result = service.getField( 1L );

        assertEquals( result.description, "Awesome");
        assertEquals( result.meters, 8.0f);
        assertEquals( result.maxHeightMeter, 1.5f);
        assertEquals( result.pricePerMonth, 30.0f);
    }

    @Test
    void getAllFields() {

        when( addressRepo.findById(1L) ).thenReturn( Optional.ofNullable( address1 ) );

        ArrayList<FieldOutputDto> result = service.getFields( 1L );

        assertEquals( result.size(), 1);
        assertEquals( result.get(0).description, "Awesome");
        assertEquals( result.get(0).meters, 8.0f);
        assertEquals( result.get(0).maxHeightMeter, 1.5f);
        assertEquals( result.get(0).pricePerMonth, 30.0f);
    }

    @Test
    @WithMockUser( username="Mick", authorities="ADMIN")
    void createField() {

        FieldInputDto inputDto = (FieldInputDto) Convert.objects( field1, new FieldInputDto() );

        when( addressRepo.findById( 1L )).thenReturn(Optional.of( address1 ));
        when( addressRepo.save( any() )).thenReturn( address1 );
        when( repo.save( any() )).thenReturn( field1 );

        long result = service.createField( 1L, inputDto);

        verify(repo).save(fieldCaptor.capture());
        Field captured = fieldCaptor.getValue();

        assertEquals( result, 1);
        assertEquals( field1.getDescription(), captured.getDescription());
        assertEquals( field1.getMeters(), captured.getMeters());
        assertEquals( field1.getMaxHeightMeter(), captured.getMaxHeightMeter());
        assertEquals( field1.getFeatures(), captured.getFeatures());

    }

    @Test
    @WithMockUser( username="Mick", authorities="ADMIN")
    void editField() {

        FieldInputDto dto = new FieldInputDto();
        dto.description = "Big area!";
        dto.meters = 12.0f;

        when( repo.findById(1L) ).thenReturn( Optional.of( field1 ) );
        when( repo.save( any() )).thenReturn( null );


        service.editField( 1L, dto);

        verify(repo).save(fieldCaptor.capture());
        Field captured = fieldCaptor.getValue();

        assertEquals( dto.description, captured.getDescription());
        assertEquals( dto.meters, captured.getMeters());
        assertEquals( field1.getMaxHeightMeter(), captured.getMaxHeightMeter());
        assertEquals( field1.getFeatures(), captured.getFeatures());
    }

    @Test
    @WithMockUser( username="Mick", authorities="ADMIN")
    void deleteField() {

        when( repo.findById(1L) ).thenReturn( Optional.of( field1 ) );

        service.deleteField(1L );
        verify(repo).deleteById( 1L );
    }
}