package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ContractInputDto;
import com.mmbackendassignment.mmbackendassignment.dto.ContractOutputDto;
import com.mmbackendassignment.mmbackendassignment.exception.ContractWithOwnException;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.model.Contract;
import com.mmbackendassignment.mmbackendassignment.model.Field;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.repository.ContractRepository;
import com.mmbackendassignment.mmbackendassignment.repository.FieldRepository;
import com.mmbackendassignment.mmbackendassignment.util.Convert;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;

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

    public ContractOutputDto getContract(long id, String perspective ){
        Contract contract = (Contract) ServiceUtil.getRepoObjectById(repo, id, "contract");
        if (!JwtHandler.isAdmin()) {
            switch( perspective ){
                case "CLIENT":
                    JwtHandler.abortIfEntityIsNotFromSameUser( contract.getClient() );
                    break;
                case "OWNER":
                    JwtHandler.abortIfEntityIsNotFromSameUser( contract.getField() );
                    break;
            }
        }
        return contractToDto( contract );
    }

    public String agreeClientContract( long id, boolean agree ){
        Contract contract = (Contract) ServiceUtil.getRepoObjectById(repo, id, "contract");
        JwtHandler.abortIfEntityIsNotFromSameUser( contract.getClient() );

        contract.setClientAgreement( true );
        repo.save( contract );
        return "Done";
    }

    public String agreeOwnerContract( long id, boolean agree ){
        Contract contract = (Contract) ServiceUtil.getRepoObjectById(repo, id, "contract");
        JwtHandler.abortIfEntityIsNotFromSameUser( contract.getField() );

        contract.setOwnerAgreement( true );
        repo.save( contract );
        return "Done";
    }

    public long createContract( long clientId, long fieldId, ContractInputDto dto ){
        Contract contract = dtoToContract( dto );

        Client client = (Client) ServiceUtil.getRepoObjectById( clientRepo, clientId, "client" );
        JwtHandler.abortIfEntityIsNotFromSameUser( client );

        Field field = (Field) ServiceUtil.getRepoObjectById( fieldRepo, fieldId, "field" );

        if( field.getAddress().getOwner().getProfile().getId() == client.getProfile().getId()){
            throw new ContractWithOwnException();
        }

        contract.setClient( client );
        contract.setField( field );

        Contract savedContract = repo.save( contract );

        return savedContract.getId();
    }

    public String deleteContract( long id ){
        Contract contract = (Contract) ServiceUtil.getRepoObjectById(repo, id, "contract");
        if (!JwtHandler.isAdmin()) JwtHandler.abortIfEntityIsNotFromSameUser( contract.getClient() );

        repo.deleteById( id );
        return "Deleted";
    }

    public String editContract( long id, ContractInputDto dto ){
        Contract contract = (Contract) ServiceUtil.getRepoObjectById(repo, id, "contract");
        JwtHandler.abortIfEntityIsNotFromSameUser(contract.getClient());

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

}
