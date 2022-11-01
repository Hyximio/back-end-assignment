package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.FieldInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.FieldOutputDto;
import com.mmbackendassignment.mmbackendassignment.model.Address;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.repository.AddressRepository;
import com.mmbackendassignment.mmbackendassignment.repository.FieldRepository;
import com.mmbackendassignment.mmbackendassignment.util.Check;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


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

        System.out.println( "found in repo ");
        return fieldToDto( field );

    }

    public ArrayList<FieldOutputDto> getFields( long addressId ){

        Address address = (Address) ServiceUtil.getRepoObjectById(addressRepo, addressId, "address");
        ArrayList<FieldOutputDto> fieldDtos = new ArrayList<>();
        for( Field f : address.getFields() ){
            fieldDtos.add( fieldToDto(f) );
        }

        return fieldDtos;
    }

    public Object createField( long addressId, FieldInputDto dto ){

        Address address = (Address) ServiceUtil.getRepoObjectById(addressRepo, addressId, "address");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(address);

        Field field = dtoToField( dto );
        field.setAddress( address );

        Field savedField = repo.save( field );
        address.addField( savedField );
        addressRepo.save( address );

        return savedField.getId();

    }

    public String editField( long id, FieldInputDto dto ){

        Field field = (Field) ServiceUtil.getRepoObjectById(repo, id, "field");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(field);

        field = (Field) Convert.objects(dto, field );
        repo.save( field );

        return "Done";

    }

    public String deleteField( long fieldId ){
        Field field = (Field)ServiceUtil.getRepoObjectById(repo, fieldId, "field");

        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser(field);
        Check.hasDependency( field.getContracts().size() != 0, "contract");

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
