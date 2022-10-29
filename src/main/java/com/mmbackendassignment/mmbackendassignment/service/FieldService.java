package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.FieldOutputDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.model.Profile;
import com.mmbackendassignment.mmbackendassignment.model.User;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.FieldRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FieldService {

    private final FieldRepository repo;
    private final AddressRepository addressRepo;

    public FieldService(FieldRepository repo, AddressRepository addressRepo) {
        this.repo = repo;
        this.addressRepo = addressRepo;
    }

    public FieldOutputDto getField( long id ){

        Field field = (Field) ServiceUtil.getRepoObjectById(repo, id, "field");
        return fieldToDto( field );

        /*
        Optional<Field> op = repo.findById( id );

        if (op.isPresent() ){
            Field field = op.get();

            return fieldToDto( field );
        }else{
            throw new RecordNotFoundException();
        }
        */

    }

    public ArrayList<FieldOutputDto> getFields( long addressId ){

        Address address = (Address) ServiceUtil.getRepoObjectById(addressRepo, addressId, "address");
        ArrayList<FieldOutputDto> fieldDtos= new ArrayList<>();
        for( Field f : address.getFields() ){
            fieldDtos.add( fieldToDto(f) );
        }

        return fieldDtos;
        /*
        Optional<Address> op = addressRepo.findById( addressId );

        if (op.isPresent() ) {

            Address address = op.get();
            ArrayList<FieldOutputDto> fieldDtos= new ArrayList<>();
            for( Field f : address.getFields() ){
                fieldDtos.add( fieldToDto(f) );
            }

            return fieldDtos;

        } else {
            throw new RecordNotFoundException( "address", addressId );
        }

         */
    }

    public Object createField( long addressId, FieldInputDto dto ){

        Address address = (Address) ServiceUtil.getRepoObjectById(addressRepo, addressId, "address");
        Field field = dtoToField( dto );
        field.setAddress( address );

//            address.addField( field );

        Field savedField = repo.save( field );
        address.addField( savedField );
        addressRepo.save( address );

        return savedField.getId();

        /*
        Optional<Address> op = addressRepo.findById( addressId );

        if (op.isPresent() ){
            Address address = op.get();
            Field field = dtoToField( dto );
            field.setAddress( address );

//            address.addField( field );

            Field savedField = repo.save( field );
            address.addField( savedField );
            addressRepo.save( address );

            return savedField.getId();
        } else {
            throw new RecordNotFoundException("address", addressId );
        }

         */
    }

    public String editField( long id, FieldInputDto dto ){

        Field field = (Field) ServiceUtil.getRepoObjectById(repo, id, "field");
        field = (Field) Convert.objects(dto, field );
        repo.save( field );

        return "Done";

        /*
        Optional<Field> op = repo.findById( fieldId );

        if (op.isPresent() ){

            Field field = (Field) Convert.objects(dto, op.get() );
            repo.save( field );

            return "Done";
        } else {
            throw new RecordNotFoundException("field", fieldId );
        }

         */
    }

    public String deleteField( long fieldId ){
        System.out.println("Try to delete" + fieldId);
        ServiceUtil.getRepoObjectById(repo, fieldId, "field");
        repo.deleteById( fieldId );
        return "Deleted";
    }

    private Field dtoToField( FieldInputDto dto ){
        return (Field)Convert.objects( dto, new Field() );
    }

    private FieldOutputDto fieldToDto( Field field ){
        FieldOutputDto fieldDto = (FieldOutputDto)Convert.objects( field, new FieldOutputDto() );
        fieldDto.addressId = field.getAddress().getId();
        fieldDto.features = field.getFeatures();
        fieldDto.contracts = field.getContractIds();
        return fieldDto;
    }
}
