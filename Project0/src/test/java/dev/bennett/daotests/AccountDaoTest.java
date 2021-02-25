package dev.bennett.daotests;

import dev.bennett.daos.AccountDAO;
import dev.bennett.daos.AccountDaoPostgres;
import dev.bennett.entities.Account;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTest {

    private static AccountDAO accountDAO = new AccountDaoPostgres();
    private static Account testAccount = null;

    @Test
    @Order(1)
    void createAccountTest() {
        // Anytime an entity has an ID of zero it means that is is not saved or persisted somewhere
        testAccount = new Account(5.5, 1);
        accountDAO.createAccount(testAccount);// save or persist
        Assertions.assertNotEquals(0, testAccount.getId());
    }

    @Test
    @Order(2)
    void getAllAccountsTest() {
        Set<Account> accounts = accountDAO.getAllAccounts();
        Assertions.assertNotEquals(0, accounts.size());

        int accountTotals = accounts.size();

        accountDAO.createAccount(testAccount);
        accounts = accountDAO.getAllAccounts();

        Assertions.assertTrue(accountTotals < accounts.size());
    }

    @Test
    @Order(3)
    void getAccountByIDTest() {
        int id = testAccount.getId();
        Account account = accountDAO.getAccountByID(id);

        Assertions.assertEquals(testAccount.getId(), account.getId());
        Assertions.assertEquals(testAccount.getAccountHolder(), account.getAccountHolder());
        Assertions.assertEquals(testAccount.getAmount(), account.getAmount());
    }

    @Test
    @Order(4)
    void updateAccountTest() {
        testAccount.setAmount(9001);
        testAccount.setAccountHolder(1);
        accountDAO.createAccount(testAccount);

        accountDAO.updateAccount(testAccount);

        Account account = accountDAO.getAccountByID(testAccount.getId());

        Assertions.assertEquals(9001, account.getAmount());
        Assertions.assertEquals(1,account.getAccountHolder());
    }

    @Test
    @Order(5)
    void deleteAccountByIdTest() {
        Assertions.assertTrue(accountDAO.deleteAccountById(testAccount.getId()));

        Assertions.assertNull(accountDAO.getAccountByID(testAccount.getId()));
    }


}
