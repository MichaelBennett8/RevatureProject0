package dev.bennett.daos;

import dev.bennett.entities.Account;

import java.util.Set;

public interface AccountDAO {

    //CREATE
    Account createAccount(Account account);

    //READ
    Set<Account> getAllAccounts();
    Set<Account> getAllClientsAccounts(int clientId);
    Account getAccountByID(int id);


    //UPDATE
    Account updateAccount(Account account);

    //DELETE
    boolean deleteAccountById(int id);
}
