package com.example.autoloc.service;

import com.example.autoloc.entities.Client;
import com.example.autoloc.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IClientServiceImp implements IClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client ajouterClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client modifierClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> consulterClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client consulterClientById(Long idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public void supprimerClientById(Long idClient) {
        clientRepository.deleteById(idClient);
    }
}
