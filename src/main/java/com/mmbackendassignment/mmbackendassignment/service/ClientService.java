package com.mmbackendassignment.mmbackendassignment.service;

import com.mmbackendassignment.mmbackendassignment.dto.ClientDto;
import com.mmbackendassignment.mmbackendassignment.dto.OwnerDto;
import com.mmbackendassignment.mmbackendassignment.exception.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.model.Client;
import com.mmbackendassignment.mmbackendassignment.model.Owner;
import com.mmbackendassignment.mmbackendassignment.repository.ClientRepository;
import com.mmbackendassignment.mmbackendassignment.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public ClientDto getClient( long id ){
        Optional<Client> op = repo.findById( id );
        if (op.isPresent()){
            Client client = op.get();

            return clientToDto( client );
        }
        throw new RecordNotFoundException( "client", id );
    }

    private ClientDto clientToDto(Client client ){
        ClientDto dto = new ClientDto();

        dto.profileId = client.getProfile().getId();

        return dto;
    }
}
