package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ClientDto;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.util.JwtHandler;
import com.mmbackendassignment.mmbackendassignment.util.ServiceUtil;
import org.springframework.stereotype.Service;



@Service
public class ClientService {
    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public ClientDto getClient( long id ){
        Client client = (Client) ServiceUtil.getRepoObjectById(repo, id, "client");
        if( !JwtHandler.isAdmin() ) JwtHandler.abortIfEntityIsNotFromSameUser( client );

        return clientToDto( client );

    }

    private ClientDto clientToDto(Client client ){
        ClientDto dto = new ClientDto();

        dto.profileId = client.getProfile().getId();
        dto.contracts = client.getContractIds();

        return dto;
    }
}
