package merit.america.bank.MeritBank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class BankAccount {

    // region instance variables
    protected Date openedOn;
    protected Long accountNumber;
    protected double interestRate;
    protected double balance;

    @JsonIgnore
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    // endregion

    // region constructors

    public BankAccount(){
    }

    public BankAccount(double balance, double interestRate){
        this(MeritBank.getNextAccountNumber(), balance, interestRate, new Date());
    }

    public BankAccount(double balance, double interestRate, Date openedOn){
        this(MeritBank.getNextAccountNumber(), balance, interestRate, openedOn);
    }

    protected BankAccount(long accountNumber, double balance, double interestRate, Date openedOn){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = interestRate;
        this.openedOn = openedOn;
    }
    // endregion

    // region getters/setters
    public long getAccountNumber() { return this.accountNumber; }

    public double getBalance() { return this.balance; }

    public double getInterestRate() { return this.interestRate; }

    public Date getOpenedOn() { return this.openedOn; }

    public boolean withdraw(double amount){
        if (this.balance - amount < 0) {
            return false;
        } else {
            this.balance -= amount;
            return true;
        }
    }

    public boolean deposit (double amount){
        if (amount < 0) {
            return false;
        } else {
            this.balance += amount;
            return true;
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() { return transactions; }
    // endregion

    // region read/write from string
    static BankAccount readFromString(String accountData) throws ParseException { return null; }

    public String writeToString() {
        return this.accountNumber + "," + this.balance + "," + String.format("%.4f", this.interestRate) + "," +
                (this.openedOn.getDate() + 1) + "/" +
                this.openedOn.getMonth() + "/" +
                (this.openedOn.getYear() + 1900);
    }
    // endregion

    public double futureValue(int years) { return MeritBank.recursiveFutureValue(this.balance, this.interestRate, years); }
}