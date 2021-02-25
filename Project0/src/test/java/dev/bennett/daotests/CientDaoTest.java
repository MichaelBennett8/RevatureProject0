package dev.bennett.daotests;

import dev.bennett.daos.ClientDAO;
import dev.bennett.daos.ClientDaoPostgres;
import dev.bennett.entities.Client;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CientDaoTest {

    private static ClientDAO clientDAO = new ClientDaoPostgres();
    private static Client testClient = null;

    //CREATE
    @Test
    @Order(1)
    void createClientTest(){
        testClient = new Client("Maverick");
        clientDAO.createClient(testClient);
        Assertions.assertNotEquals(0, testClient.getId());
    }

    //READ
    @Test
    @Order(2)
    void getAllClientsTest(){
        Set<Client> clients = clientDAO.getAllClients();
        int totalClients = clients.size();

        clientDAO.createClient(testClient);
        clients = clientDAO.getAllClients();
        Assertions.assertTrue(totalClients < clients.size());
    }

    @Test
    @Order(3)
    void getClientByIDTest(){
        Client client = clientDAO.getClientByID(testClient.getId());
        Assertions.assertEquals(testClient.getId(), client.getId());
    }

    //UPDATE
    @Test
    @Order(4)
    void updateClientTest(){
        testClient.setName("Goose");
        clientDAO.updateClient(testClient);

        Client client = clientDAO.getClientByID(testClient.getId());
        Assertions.assertEquals(testClient.getName(), client.getName());
    }

    //DELETE
    @Test
    @Order(5)
    void deleteClientByIdTest(){
        Assertions.assertTrue(clientDAO.deleteClientById(testClient.getId()));

        Assertions.assertNull(clientDAO.getClientByID(testClient.getId()));
    }
}
