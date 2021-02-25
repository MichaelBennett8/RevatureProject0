package dev.bennett.daos;

import dev.bennett.entities.Account;
import dev.bennett.entities.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountDaoLocal implements AccountDAO{

    private static Map<Integer, Account> accountTable = new HashMap<Integer, Account>();
    private static int idMaker = 0;

    @Override
    public Account createAccount(Account account) {
        account.setId(++idMaker);
        accountTable.put(account.getId(),account);
        return account;
    }

    @Override
    public Set<Account> getAllAccounts() {
        return new HashSet<Account>(accountTable.values());
    }

    @Override
    public Set<Account> getAllClientsAccounts(int clientId) {
        Set<Account> allAccounts = this.getAllAccounts();
        for (Account a: allAccounts){
            if (a.getAccountHolder() == clientId){
                allAccounts.remove(a);
            }
        }
        return allAccounts;
    }

    @Override
    public Account getAccountByID(int id) {
        return accountTable.get(id);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountTable.put(account.getId(),account);
    }

    @Override
    public boolean deleteAccountById(int id) {
        return false;
    }
}
