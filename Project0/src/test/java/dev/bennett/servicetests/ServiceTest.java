package dev.bennett.servicetests;

import dev.bennett.daos.AccountDaoPostgres;
import dev.bennett.daos.ClientDaoPostgres;
import dev.bennett.entities.Account;
import dev.bennett.entities.Client;
import dev.bennett.services.ServiceImpl;
import dev.bennett.services.ServiceInterface;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTest {

    private static ServiceInterface services = new ServiceImpl(new AccountDaoPostgres(), new ClientDaoPostgres());
    private static Client testClient = null;
    private static Account testAccount = null;

    @Test
    @Order(1)
    void createClientTest(){
        Client testClient = new Client("Sophie");
        Client newClient = services.createClient(testClient);
        Assertions.assertEquals(testClient, newClient);

        newClient = services.getClient(testClient.getId());

        Assertions.assertEquals(testClient.getId(), newClient.getId());
    }

    @Test
    @Order(2)
    void getAllClientsTest(){
        Set<Client> clients = services.getAllClients();
        int cSize1 = clients.size();

        services.createClient(new Client("Peter"));

        clients = services.getAllClients();
        int cSize2 = clients.size();

        Assertions.assertEquals(1, cSize2 - cSize1);

        services.createClient(new Client("Angie"));

        clients = services.getAllClients();
        int cSize3 = clients.size();

        Assertions.assertEquals(1, cSize3 - cSize2);
        Assertions.assertEquals(2, cSize3 - cSize1);
    }

    @Test
    @Order(3)
    void getClientTest(){
        testClient = services.createClient(new Client("Patric"));
        int clientID = testClient.getId();
        Client client = services.getClient(clientID);
        Assertions.assertEquals(clientID, client.getId());
    }

    @Test
    @Order(4)
    void updateClientTest(){
        testClient.setName("Chilled");
        services.updateClient(testClient);

        Client client = services.getClient(testClient.getId());
        Assertions.assertEquals("Chilled", client.getName());
    }

    @Test
    @Order(13)
    void deleteClientTest(){
        Assertions.assertTrue(services.deleteClient(testClient.getId()));

        Assertions.assertNull(services.getClient(testClient.getId()));
    }

    @Test
    @Order(5)
    void createAccountTest(){
        testClient = services.createClient(new Client("Arthur Dent"));
        int clientID = testClient.getId();

        testAccount = new Account(42, clientID);
        services.createAccount(clientID,testAccount);

        Account account = services.getAccount(clientID, testAccount.getId());

        Assertions.assertEquals(clientID, account.getAccountHolder());
        Assertions.assertEquals(testAccount.getId(), account.getId());
    }

    @Test
    @Order(6)
    void getAllClientsAccountsTest(){
        int clientID = testClient.getId();

        Set<Account> accounts = services.getAllClientsAccounts(clientID);
        int accAmt1 = accounts.size();

        services.createAccount(clientID, new Account(12345, clientID));

        accounts = services.getAllClientsAccounts(clientID);
        int accAmt2 = accounts.size();

        Assertions.assertEquals(1, accAmt2 - accAmt1);
    }

    @Test
    @Order(7)
    void getAllAccountsAboveTest(){
        testClient = services.createClient(new Client("Pythagoras"));
        int clientID = testClient.getId();

        services.createAccount(clientID, new Account(3, clientID));
        services.createAccount(clientID, new Account(4, clientID));
        services.createAccount(clientID, new Account(5, clientID));

        Set<Account> accounts1 = services.getAllAccountsAbove(clientID,3);
        int accAmt1 = accounts1.size();

        Set<Account> accounts2 = services.getAllAccountsAbove(clientID,4);
        int accAmt2 = accounts2.size();

        Set<Account> accounts3 = services.getAllAccountsAbove(clientID,5);
        int accAmt3 = accounts3.size();

        Set<Account> accounts4 = services.getAllAccountsAbove(clientID,6);
        int accAmt4 = accounts4.size();

        Assertions.assertEquals(0, accAmt4);
        Assertions.assertEquals(1, accAmt3);
        Assertions.assertEquals(2, accAmt2);
        Assertions.assertEquals(3, accAmt1);

//        int clientID = testClient.getId();
//        services.createAccount(clientID, new Account(123, clientID));
//        services.createAccount(clientID, new Account(456, clientID));
//        services.createAccount(clientID, new Account(789, clientID));
//
//        Set<Account> accounts1 = services.getAllAccountsAbove(clientID,333);
//        Assertions.assertTrue(accounts1.size() >=2);
    }

    @Test
    @Order(8)
    void getAllAccountsBelowTest(){
        int clientID = testClient.getId();

        Set<Account> accounts = services.getAllAccountsBelow(clientID,3);
        int accAmt1 = accounts.size();

        accounts = services.getAllAccountsBelow(clientID,4);
        int accAmt2 = accounts.size();

        accounts = services.getAllAccountsBelow(clientID,5);
        int accAmt3 = accounts.size();

        accounts = services.getAllAccountsBelow(clientID,2);
        int accAmt4 = accounts.size();

        Assertions.assertEquals(0, accAmt4);
        Assertions.assertEquals(3, accAmt3);
        Assertions.assertEquals(2, accAmt2);
        Assertions.assertEquals(1, accAmt1);

//        int clientID = testClient.getId();
//
//        Set<Account> accounts1 = services.getAllAccountsBelow(clientID,333);
//        Assertions.assertTrue(accounts1.size() <=1);
    }

    @Test
    @Order(9)
    void getAllAccountsInRangeTest(){
        int clientID = testClient.getId();

        Set<Account> accounts = services.getAllAccountsInRange(clientID,3, 4);
        int accAmt1 = accounts.size();

        Assertions.assertEquals(2, accAmt1);
    }

    @Test
    @Order(10)
    void getAccountTest(){
        int clientID = testClient.getId();
        testAccount = services.createAccount(clientID, new Account(2048, clientID));
        Account account = services.getAccount(clientID, testAccount.getId());

        Assertions.assertEquals(testAccount.getId(), account.getId());
    }

    @Test
    @Order(11)
    void updateAccountTest(){
        testAccount.setAmount(4096);
        services.updateAccount(testAccount.getAccountHolder(), testAccount.getId(), testAccount);

        Account account = services.getAccount(testAccount.getAccountHolder(), testAccount.getId());

        Assertions.assertEquals(4096, account.getAmount());
    }

    @Test
    @Order(12)
    void deleteAccountTest(){
        Assertions.assertTrue(services.deleteAccount(testAccount.getAccountHolder(), testAccount.getId()));

        Assertions.assertNull(services.getAccount(testAccount.getAccountHolder(), testAccount.getId()));
    }
}
