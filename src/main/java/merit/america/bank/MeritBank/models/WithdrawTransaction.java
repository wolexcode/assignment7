package merit.america.bank.MeritBank.models;

import com.meritamerica.assignment5.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment5.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.exceptions.NegativeAmountException;

public class WithdrawTransaction extends Transaction {

    WithdrawTransaction(BankAccount targetAccount, double amount) {
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    @Override
    public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        if (amount > MeritBank.FRAUD_SUSPICION_THRESHOLD) throw new ExceedsFraudSuspicionLimitException("Possible fraud detected on withdrawal");
        if (amount < 0) throw new NegativeAmountException("Withdrawal amount must be greater than zero");
        if (amount > targetAccount.getBalance()) throw new ExceedsAvailableBalanceException("Insufficient funds for withdrawal amount");

        this.targetAccount.withdraw(this.amount);
        this.targetAccount.addTransaction(this);
    }
}