package dev.bennett.entities;

public class Account {

    private double amount;
    private int id;
    private int accountHolder;

    public Account(){

    }

    public Account(double amount, int accountHolder) {
        this.amount = amount;
        this.accountHolder = accountHolder;
    }

    public Account (double amount){
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(int accountHolder) {
        this.accountHolder = accountHolder;
    }
}
