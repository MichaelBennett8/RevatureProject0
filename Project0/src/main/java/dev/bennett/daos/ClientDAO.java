package dev.bennett.daos;

import dev.bennett.entities.Account;
import dev.bennett.entities.Client;

import java.util.Set;

public interface ClientDAO {

    //CREATE
    Client createClient(Client client);

    //READ
    Set<Client> getAllClients();
    Client getClientByID(int id);

    //UPDATE
    Client updateClient(Client client);

    //DELETE
    boolean deleteClientById(int id);

}
