package com.example.autoloc.controller;

import com.example.autoloc.entities.Client;
import com.example.autoloc.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client ajouterClient(@RequestBody Client client) {
        return clientService.ajouterClient(client);
    }

    @GetMapping
    public List<Client> consulterClients() {
        return clientService.consulterClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> consulterClientById(@PathVariable Long id) {
        Client client = clientService.consulterClientById(id);
        return client == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(@PathVariable Long id, @RequestBody Client client) {
        if (clientService.consulterClientById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        client.setIdClient(id);
        return ResponseEntity.ok(clientService.modifierClient(client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClientById(@PathVariable Long id) {
        if (clientService.consulterClientById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        clientService.supprimerClientById(id);
        return ResponseEntity.noContent().build();
    }
}
