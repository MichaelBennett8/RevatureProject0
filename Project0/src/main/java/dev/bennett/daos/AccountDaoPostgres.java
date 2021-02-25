package dev.bennett.daos;

import dev.bennett.entities.Account;
import dev.bennett.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoPostgres implements AccountDAO{

    private Logger logger = Logger.getLogger(AccountDaoPostgres.class.getName());

    @Override
    public Account createAccount(Account account) {
        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "insert into account (amount, client) values (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,account.getAmount());
            ps.setInt(2, account.getAccountHolder());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int accountID = rs.getInt("account_id");
            account.setId(accountID);

            return account;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to create account", sqlException);
            return null;
        }
    }

    @Override
    public Set<Account> getAllAccounts() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<Account> accounts = new HashSet<Account>();

            while(rs.next()){
                accounts.add(accountTableToObject(rs));
            }
            return accounts;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get accounts", sqlException);
            return null;
        }
    }

    @Override
    public Set<Account> getAllClientsAccounts(int clientId) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where client = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            Set<Account> accounts = new HashSet<Account>();

            while(rs.next()){
                accounts.add(accountTableToObject(rs));
            }
            return accounts;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get accounts", sqlException);
            return null;
        }
    }

    @Override
    public Account getAccountByID(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return accountTableToObject(rs);
            }
            return null;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get account", sqlException);
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "update account set client = ?, amount = ? where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(3,account.getId());
            ps.setInt(1,account.getAccountHolder());
            ps.setDouble(2,account.getAmount());

            ps.executeUpdate();
            return account;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to update account", sqlException);
            return null;
        }
    }

    @Override
    public boolean deleteAccountById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to delete account", sqlException);
            return false;
        }
    }

    //helpers
    private Account accountTableToObject(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("account_id"));
        account.setAccountHolder(rs.getInt("client"));
        account.setAmount(rs.getFloat("amount"));

        return account;
    }
}
