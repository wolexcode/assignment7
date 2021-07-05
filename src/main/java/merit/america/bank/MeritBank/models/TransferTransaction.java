package merit.america.bank.MeritBank.models;

import com.meritamerica.assignment5.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment5.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.exceptions.NegativeAmountException;

public class TransferTransaction extends Transaction {

    TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        if (amount > MeritBank.FRAUD_SUSPICION_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on transfer");
        if (amount < 0) throw new NegativeAmountException("Transfer amount must be greater than zero");
        if (amount > sourceAccount.getBalance()) throw new ExceedsAvailableBalanceException("Insufficient funds for transfer");

        this.sourceAccount.withdraw(this.amount);
        this.sourceAccount.addTransaction(this);

        this.targetAccount.deposit(this.amount);
        this.targetAccount.addTransaction(this);
    }
}