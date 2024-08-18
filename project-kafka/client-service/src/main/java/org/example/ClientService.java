package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ClientService {

    @Inject
    ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.listAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public Client createClient(Client client) {
        clientRepository.persist(client);
        return client;
    }

    @Transactional
    public Client updateClient(Long id, Client client) {
        Client entity = clientRepository.findById(id);
        if (entity != null) {
            entity.name = client.name;
            entity.email = client.email;
            entity.phone = client.phone;
        }
        return entity;
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

