package dev.bennett.controllers;

import com.google.gson.Gson;
import dev.bennett.daos.AccountDaoLocal;
import dev.bennett.daos.AccountDaoPostgres;
import dev.bennett.daos.ClientDaoPostgres;
import dev.bennett.daos.ClientDoaLocal;
import dev.bennett.entities.Account;
import dev.bennett.entities.Client;
import dev.bennett.services.ServiceImpl;
import dev.bennett.services.ServiceInterface;
import io.javalin.http.Handler;

import java.util.Set;


public class BankController {

    private ServiceInterface service = new ServiceImpl(new AccountDaoPostgres(), new ClientDaoPostgres());
    private Gson gson = new Gson();


    //Client Handlers


    //    	POST /clients => Creates a new client
    //		return a 201 status code
    public Handler createClientHandler = (ctx) ->{
        try {
            Client client = gson.fromJson(ctx.body(), Client.class);
            if (client.getName() == null){
                ctx.result("Error: No client information provided");
                ctx.status(404);
            }
            else {
                this.service.createClient(client);

                ctx.result(gson.toJson(client));
                ctx.status(201);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
            ctx.result("Error: No client information provided");
            ctx.status(404);
        }
    };

    //	GET /clients => gets all clients
    //		return 200
    public Handler getAllClientsHandler = (ctx) ->{

        Set<Client> clients = this.service.getAllClients();

        ctx.result(gson.toJson(clients));
        ctx.status(200);
    };

    //	GET /clients/10 => get client with id of 10
    //		return 404 if no such client exist
    public Handler getClientByIdHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        Client client = this.service.getClient(id);

        if (client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }
        else {
            ctx.result(gson.toJson(client));
            ctx.status(200);
        }
    };

    //	PUT /clients/12 => updates client with id of 12
    //		return 404 if no such client exist
    public Handler updateClientHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        String body = ctx.body();

        Client client = gson.fromJson(body, Client.class);
        client.setId(id);

        if(this.service.updateClient(client) == null){
            ctx.result("Client not found");
            ctx.status(404);
        }
        else {
            ctx.result(gson.toJson(client));
            ctx.status(200);
        }
    };

    //	DELETE /clients/15 => deletes client with the id of15
    //		return 404 if no such client exist
    public Handler deleteClientHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        Client client = this.service.getClient(id);

        if (this.service.deleteClient(id)){
            ctx.result(gson.toJson(client));
            ctx.status(200);
        }
        else {
            ctx.result("Client not found");
            ctx.status(404);
        }
    };


    //Account Handlers


    //	POST /clients/5/accounts =>creates a new account for client with the id of 5
    //		return a 201 status code
    public Handler createAccountHandler = (ctx) ->{
        try {
            int id = Integer.parseInt(ctx.pathParam("clientId"));
            Account account = gson.fromJson(ctx.body(), Account.class);
            this.service.createAccount(id, account);

            ctx.result(gson.toJson(account));
            ctx.status(201);
        }
        catch(NullPointerException e){
            e.printStackTrace();
            ctx.result("Error: No account information provided");
            ctx.status(404);
        }
    };

    //	GET /clients/7/accounts => get all accounts for client 7
    //	GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and 2000
    //		return 404 if no client exists
    public Handler getAllAccounts = (ctx) ->{
        Set<Account> accounts;

        int clientId = Integer.parseInt(ctx.pathParam("clientId"));

        String aboveThis = ctx.queryParam("amountGreaterThan","NONE");
        String belowThis = ctx.queryParam("amountLessThan", "NONE");

        //System.out.println(aboveThis + " " + belowThis);
        //no amounts
        if (aboveThis.equals("NONE") && belowThis.equals("NONE")){
            accounts = this.service.getAllClientsAccounts(clientId);
        }
        //above amount
        else if(!aboveThis.equals("NONE") && belowThis.equals("NONE")){
            double gt = Double.parseDouble(aboveThis);
            accounts = this.service.getAllAccountsAbove(clientId, gt);
        }
        //below amount
        else if(aboveThis.equals("NONE") && !belowThis.equals("NONE")){
            double lt = Double.parseDouble(belowThis);
            accounts = this.service.getAllAccountsBelow(clientId, lt);
        }
        //both amounts
        else {
            double gt = Double.parseDouble(aboveThis);
            double lt = Double.parseDouble(belowThis);
            accounts = this.service.getAllAccountsInRange(clientId, gt, lt);
        }

        if (accounts.isEmpty()){
            ctx.result("Accounts not found");
            ctx.status(404);
        }
        else {
            ctx.result(gson.toJson(accounts));
            ctx.status(200);
        }
    };

    //	GET /clients/9/accounts/4 => get account 4 for client 9
    //		return 404 if no account or client exists
    public Handler getAccountByIdHandler = (ctx) ->{
        int clientId = Integer.parseInt(ctx.pathParam("clientId"));
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        Account account = this.service.getAccount(clientId, accountId);

        if (account == null){
            ctx.result("Account not found");
            ctx.status(404);
        }
        else {
            ctx.result(gson.toJson(account));
            ctx.status(200);
        }
    };

    //	PUT /clients/10/accounts/3 => update account  with the id 3 for client 10
    //		return 404 if no account or client exists
    public Handler updateAccountHandler = (ctx) ->{
        int clientId = Integer.parseInt(ctx.pathParam("clientId"));
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        String body = ctx.body();

        Account account = gson.fromJson(body, Account.class);
        account.setId(accountId);

        if(this.service.updateAccount(clientId, accountId, account) == null){
            ctx.result("Account not found");
            ctx.status(404);
        }
        else {
            ctx.result(gson.toJson(account));
            ctx.status(200);
        }
    };

    //	DELETE /clients/15/accounts/6 => delete account 6 for client 15
    //		return 404 if no account or client exists
    public Handler deleteAccountHandler = (ctx) ->{
        int clientId = Integer.parseInt(ctx.pathParam("clientId"));
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        Account account = this.service.getAccount(clientId, accountId);

        if (this.service.deleteAccount(clientId,accountId)){
            ctx.result(gson.toJson(account));
            ctx.status(200);
        }
        else {
            ctx.result("Client not found");
            ctx.status(404);
        }
    };
}
