package dev.bennett.services;

import dev.bennett.entities.Account;
import dev.bennett.entities.Client;

import java.util.Set;

public interface ServiceInterface {

    Client createClient(Client client);

    Set<Client> getAllClients();

    Client getClient(int id);

    Client updateClient(Client client);

    boolean deleteClient(int id);

    Account createAccount(int clientID, Account account);

    Set<Account> getAllClientsAccounts(int clientID);

    Set<Account> getAllAccountsAbove(int clientID, double aboveThis);

    Set<Account> getAllAccountsBelow(int clientID, double belowThis);

    Set<Account> getAllAccountsInRange(int clientID, double aboveThis, double belowThis);

    Account getAccount(int clientID, int accountID);

    Account updateAccount(int clientID, int accountID, Account account);

    boolean deleteAccount(int clientID, int accountID);
}
