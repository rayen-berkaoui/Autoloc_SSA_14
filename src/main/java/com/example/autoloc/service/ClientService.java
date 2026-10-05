package com.example.autoloc.service;

import com.example.autoloc.dto.ClientDto;
import com.example.autoloc.entities.Client;
import com.example.autoloc.exception.ConflictException;
import com.example.autoloc.exception.ResourceNotFoundException;
import com.example.autoloc.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<ClientDto.Response> findAll() {
        return clientRepository.findAll().stream().map(ClientDto.Response::from).toList();
    }

    @Transactional(readOnly = true)
    public ClientDto.Response findById(Long id) {
        return ClientDto.Response.from(getEntity(id));
    }

    public ClientDto.Response create(ClientDto.Request r) {
        if (clientRepository.existsByEmail(r.email())) {
            throw new ConflictException("Email déjà utilisé : " + r.email());
        }
        if (clientRepository.existsByNumPermis(r.numPermis())) {
            throw new ConflictException("Numéro de permis déjà enregistré : " + r.numPermis());
        }
        Client c = new Client();
        apply(c, r);
        c.setDateInscription(LocalDate.now());
        return ClientDto.Response.from(clientRepository.save(c));
    }

    public ClientDto.Response update(Long id, ClientDto.Request r) {
        Client c = getEntity(id);
        if (!c.getEmail().equals(r.email()) && clientRepository.existsByEmail(r.email())) {
            throw new ConflictException("Email déjà utilisé : " + r.email());
        }
        if (!c.getNumPermis().equals(r.numPermis()) && clientRepository.existsByNumPermis(r.numPermis())) {
            throw new ConflictException("Numéro de permis déjà enregistré : " + r.numPermis());
        }
        apply(c, r);
        return ClientDto.Response.from(c);
    }

    public void delete(Long id) {
        clientRepository.delete(getEntity(id));
    }

    Client getEntity(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    private void apply(Client c, ClientDto.Request r) {
        c.setNom(r.nom());
        c.setPrenom(r.prenom());
        c.setEmail(r.email());
        c.setTelephone(r.telephone());
        c.setNumPermis(r.numPermis());
    }
}
