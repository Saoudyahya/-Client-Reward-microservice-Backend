package com.example.clientMicroservice.service;

import com.example.clientMicroservice.models.Client;
import com.example.clientMicroservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setName(updatedClient.getName());
            existingClient.setEmail(updatedClient.getEmail());
            existingClient.setCountry(updatedClient.getCountry());
            existingClient.setState(updatedClient.getState());
            return clientRepository.save(existingClient);
        }).orElse(null); // Handle the case where the client doesn't exist
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

}