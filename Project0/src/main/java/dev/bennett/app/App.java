package dev.bennett.app;

import dev.bennett.controllers.BankController;
import io.javalin.Javalin;

public class App {

    public static void main (String[] args){

        Javalin app = Javalin.create();

        BankController bc = new BankController();


        //        POST /clients => Creates a new client
        //		return a 201 status code
        app.post("/clients", bc.createClientHandler);

        //	GET /clients => gets all clients
        //		return 200
        app.get("/clients", bc.getAllClientsHandler);

        //	GET /clients/10 => get client with id of 10
        //		return 404 if no such client exist
        app.get("/clients/:id", bc.getClientByIdHandler);

        //	PUT /clients/12 => updates client with id of 12
        //		return 404 if no such client exist
        app.put("/clients/:id", bc.updateClientHandler);

        //	DELETE /clients/15 => deletes client with the id of15
        //		return 404 if no such client exist
        app.delete("/clients/:id", bc.deleteClientHandler);

        //	POST /clients/5/accounts =>creates a new account for client with the id of 5
        //		return a 201 status code
        app.post("/clients/:clientId/accounts", bc.createAccountHandler);

        //	GET /clients/9/accounts/4 => get account 4 for client 9
        //		return 404 if no account or client exists
        app.get("clients/:clientId/accounts/:accountId", bc.getAccountByIdHandler);

        //	GET /clients/7/accounts => get all accounts for client 7
        //	GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and 2000
        //		return 404 if no client exists
        app.get("clients/:clientId/accounts", bc.getAllAccounts);
        app.get("clients/:clientId/accounts?*", bc.getAllAccounts);

        //	PUT /clients/10/accounts/3 => update account  with the id 3 for client 10
        //		return 404 if no account or client exists
        app.put("clients/:clientId/accounts/:accountId", bc.updateAccountHandler);

        //	DELETE /clients/15/accounts/6 => delete account 6 for client 15
        //		return 404 if no account or client exists
        app.delete("clients/:clientId/accounts/:accountId", bc.deleteAccountHandler);

        app.start();
    }
}
