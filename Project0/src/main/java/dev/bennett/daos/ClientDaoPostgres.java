package dev.bennett.daos;

import dev.bennett.entities.Client;
import dev.bennett.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientDaoPostgres implements ClientDAO{

    private Logger logger = Logger.getLogger(AccountDaoPostgres.class.getName());

    @Override
    public Client createClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into client (client_name) values (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int clientID = rs.getInt("client_id");
            client.setId(clientID);
            return client;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to create client", sqlException);
            return null;
        }
    }

    @Override
    public Set<Client> getAllClients() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<Client> clients = new HashSet<Client>();

            while(rs.next()){
//                Client client= new Client();
//                client.setId(rs.getInt("client_id"));
//                client.setName(rs.getNString("client_name"));
//                clients.add(client);
                clients.add(clientTableToObject(rs));
            }
            return clients;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get clients", sqlException);
            return null;
        }
    }

    @Override
    public Client getClientByID(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return clientTableToObject(rs);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get client", sqlException);
            return null;
        }
    }

    @Override
    public Client updateClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "update client set client_name = ? where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(2,client.getId());
            ps.setString(1,client.getName());

            ps.executeUpdate();
            return client;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to update client", sqlException);
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to delete client", sqlException);
            return false;
        }
    }

    //helpers
    private Client clientTableToObject(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setName(rs.getString("client_name"));

        return client;
    }
}
