package merit.america.bank.MeritBank.models;

import com.meritamerica.assignment5.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment5.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.exceptions.NegativeAmountException;

public class DepositTransaction extends Transaction {

    DepositTransaction(BankAccount targetAccount, double amount) {
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        if (amount > MeritBank.FRAUD_SUSPICION_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on deposit");
        if (amount < 0) throw new NegativeAmountException("Deposit amount must be greater than 0");

        this.targetAccount.deposit(this.amount);
        this.targetAccount.addTransaction(this);
    }
}