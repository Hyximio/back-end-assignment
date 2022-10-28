package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ContractInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ContractOutputDto;
import com.mmbackendassignment.mmbackendassignment.exception.ContractWithOwnException;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.model.Contract;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ContractRepository;
import com.mmbackendassignment.mmbackendassignment.repository.FieldRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository repo;
    private final ClientRepository clientRepo;
    private final FieldRepository fieldRepo;

    public ContractService(ContractRepository repo, ClientRepository clientRepo, FieldRepository fieldRepo) {
        this.repo = repo;
        this.clientRepo = clientRepo;
        this.fieldRepo = fieldRepo;
    }

    public ContractOutputDto getContract(long id ){

        Contract contract = (Contract) getRepoObjectById( repo, id, "contract" );
        System.out.println( contract.getEndDate() );
        return contractToDto( contract );
    }

    public String agreeClientContract( long id, boolean agree ){
        Contract contract = (Contract) getRepoObjectById( repo, id, "contract" );

        contract.setClientAgreement( true );
        repo.save( contract );
        return "Done";
    }

    public String agreeOwnerContract( long id, boolean agree ){
        Contract contract = (Contract) getRepoObjectById( repo, id, "contract" );

        contract.setOwnerAgreement( true );
        repo.save( contract );
        return "Done";
    }

    public String createContract( long clientId, long fieldId, ContractInputDto dto ){
        Contract contract = dtoToContract( dto );

        Client client = (Client)getRepoObjectById( clientRepo, clientId, "client" );
        Field field = (Field)getRepoObjectById( fieldRepo, fieldId, "field" );

        contract.setClient( client );
        contract.setField( field );

        repo.save( contract );

        return "Contract created";
    }

    public String deleteContract( long id ){
        repo.deleteById( id );
        return "Deleted";
    }

    public String editContract( long id, ContractInputDto dto ){

        Contract contract = (Contract) getRepoObjectById( repo, id, "contract" );
        contract = (Contract) Convert.objects( dto, contract );
        repo.save( contract );

        return "Done";
    }

    private ContractOutputDto contractToDto( Contract contract ){

        ContractOutputDto dto = (ContractOutputDto) Convert.objects( contract, new ContractOutputDto() );
        dto.fieldId = contract.getField().getId();
        dto.clientId = contract.getClient().getId();
        dto.ownerId = contract.getField().getAddress().getOwner().getId();

        return dto;
    }

    private Contract dtoToContract( ContractInputDto dto ){

        Contract contract = (Contract) Convert.objects( dto, new Contract() );
        return contract;
    }

    private Object getRepoObjectById( JpaRepository repo, long id, String name ){

        Optional op = repo.findById( id );
        if ( op.isEmpty() ){
            throw new RecordNotFoundException( name, id );
        }
        return op.get();
    }
}
