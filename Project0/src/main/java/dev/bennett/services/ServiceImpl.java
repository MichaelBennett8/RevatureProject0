package dev.bennett.services;

import dev.bennett.daos.AccountDAO;
import dev.bennett.daos.ClientDAO;
import dev.bennett.entities.Account;
import dev.bennett.entities.Client;

import java.util.HashSet;
import java.util.Set;

public class ServiceImpl implements ServiceInterface{

    AccountDAO accountDataAccessObject;
    ClientDAO clientDataAccessObject;

    public ServiceImpl(AccountDAO accountDataAccessObject, ClientDAO clientDataAccessObject){
        this.accountDataAccessObject = accountDataAccessObject;
        this.clientDataAccessObject = clientDataAccessObject;
    }

    @Override
    public Client createClient(Client client) {
        this.clientDataAccessObject.createClient(client);
        return client;
    }

    @Override
    public Set<Client> getAllClients() {
        return this.clientDataAccessObject.getAllClients();
    }

    @Override
    public Client getClient(int id) {
        return this.clientDataAccessObject.getClientByID(id);
    }

    @Override
    public Client updateClient(Client client) {
        return this.clientDataAccessObject.updateClient(client);
    }

    @Override
    public boolean deleteClient(int id) {
        return this.clientDataAccessObject.deleteClientById(id);
    }

    @Override
    public Account createAccount(int clientID, Account account) {
        account.setAccountHolder(clientID);
        return accountDataAccessObject.createAccount(account);
    }

    @Override
    public Set<Account> getAllClientsAccounts(int clientID) {
        return accountDataAccessObject.getAllClientsAccounts(clientID);
    }

    @Override
    public Set<Account> getAllAccountsAbove(int clientID, final double aboveThis) {
        Set<Account> clientAccounts = this.getAllClientsAccounts(clientID);
//        for (Account a: clientAccounts){
//            if (a.getAmount() < aboveThis){
//                clientAccounts.remove(a);
//            }
//        }
//        return clientAccounts;
        return this.getAllAccountsAboveHelper(clientAccounts, aboveThis);
    }

    @Override
    public Set<Account> getAllAccountsBelow(int clientID, final double belowThis) {
        Set<Account> clientAccounts = this.getAllClientsAccounts(clientID);
//        for (Account a: clientAccounts){
//            if (a.getAmount() > belowThis){
//                clientAccounts.remove(a);
//            }
//        }
//        return clientAccounts;
        return this.getAllAccountsBelowHelper(clientAccounts, belowThis);
    }

    @Override
    public Set<Account> getAllAccountsInRange(int clientID, final double aboveThis, final double belowThis) {
        Set<Account> clientAccounts = this.getAllClientsAccounts(clientID);
        Set<Account> clientAccountsAbove = this.getAllAccountsAboveHelper(clientAccounts, aboveThis);
        Set<Account> clientAccountsInRange = this.getAllAccountsBelowHelper(clientAccountsAbove, belowThis);
        return clientAccountsInRange;
    }


    //helpers for getting accounts with respect to a value

    private Set<Account> getAllAccountsAboveHelper(Set<Account> clientAccounts, final double aboveThis){
        Set<Account> results = new HashSet<Account>();
        for (Account a: clientAccounts){
            if (a.getAmount() >= aboveThis){
                //clientAccounts.remove(a);
                results.add(a);
            }
        }
        return results;
    }

    private Set<Account> getAllAccountsBelowHelper(Set<Account> clientAccounts, double belowThis){
        Set<Account> results = new HashSet<Account>();
        for (Account a: clientAccounts){
            if (a.getAmount() <= belowThis){
                //clientAccounts.remove(a);
                results.add(a);
            }
        }
        return results;
    }

    //end helpers


    @Override
    public Account getAccount(int clientID, int accountID) {
        return this.accountDataAccessObject.getAccountByID(accountID);
    }

    @Override
    public Account updateAccount(int clientID, int accountID, Account account) {
        return this.accountDataAccessObject.updateAccount(account);
    }

    @Override
    public boolean deleteAccount(int clientID, int accountID) {
        return this.accountDataAccessObject.deleteAccountById(accountID);
    }
}
