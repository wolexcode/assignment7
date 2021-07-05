package merit.america.bank.MeritBank.models;

import com.meritamerica.assignment5.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment5.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.exceptions.NegativeAmountException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {

    //region InstanceVariables
    protected BankAccount sourceAccount;
    protected BankAccount targetAccount;
    protected double amount;
    protected final double FRAUD_SUSPICION_THRESHOLD = 1000.0;
    private Date transactionDate;
    private boolean isProcessed;
    private String reason;
    //endregion

    // region getters/setters
    public BankAccount getSourceAccount() { return this.sourceAccount; }

    public void setSourceAccount(BankAccount sourceAccount) { this.sourceAccount = sourceAccount; }

    public BankAccount getTargetAccount() { return this.targetAccount; }

    public void setTargetAccount(BankAccount targetAccount) { this.targetAccount = targetAccount; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public Date getTransactionDate() { return this.transactionDate; }

    public void setTransactionDate(Date date) { this.transactionDate = date; }
    // endregion

    // region read/write from strings
    public String writeToString(){
        StringBuilder result = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (this.getSourceAccount() == null) {
            result.append("-1,");
        } else {
            result.append(this.getSourceAccount().getAccountNumber()).append(",");
        }

        result.append(this.getTargetAccount().getAccountNumber()).append(",")
                .append(this.getAmount()).append(",")
                .append(dateFormat.format(this.getTransactionDate()));

        return result.toString();
    }

    enum TransactionType { TRANSFER, NON_TRANSFER }

    public static Transaction readFromString(String transactionDataString) throws ParseException {
        String[] temp = transactionDataString.split(",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        TransactionType transType = Integer.parseInt(temp[0]) == -1 ?
                TransactionType.NON_TRANSFER :
                TransactionType.TRANSFER;
        double amount = Double.parseDouble(temp[2]);
        Date transactionDate = dateFormat.parse(temp[3]);

        Transaction result;
        switch (transType){
            case NON_TRANSFER:
                long accountNum = Long.parseLong(temp[1]);
                if (amount > 0) {
                    result = new DepositTransaction(MeritBank.getBankAccount(accountNum), amount);
                } else {
                    result = new WithdrawTransaction(MeritBank.getBankAccount(accountNum), amount);
                }
                break;
            case TRANSFER:
                long sourceAccountNum = Long.parseLong(temp[0]);
                long targetAccountNum = Long.parseLong(temp[1]);
                result = new TransferTransaction(
                        MeritBank.getBankAccount(sourceAccountNum),
                        MeritBank.getBankAccount(targetAccountNum),
                        amount);
                break;
            default:
                throw new NumberFormatException();
        }
        result.setTransactionDate(transactionDate);
        return result;
    }
    // endregion

    // region Fraud
    public boolean isProcessedByFraudTeam() { return this.isProcessed; }

    public void setProcessedByFraudTeam(boolean isProcessed) { this.isProcessed = isProcessed; }

    public String getRejectionReason() { return this.reason; }

    public void setRejectionReason(String reason) { this.reason = reason; }
    // endregion

    public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
}