package merit.america.bank.MeritBank.models;


import com.meritamerica.assignment5.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment5.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.exceptions.NegativeAmountException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * this class represents Merit Bank and holds all of their account holders, cd offerings and business
 * logic requested by Merit Bank.
 */
public class MeritBank {

    // region global variables
    /** a list of all the account holders of Merit Bank */
    private static final ArrayList<AccountHolder> accountHolders = new ArrayList<>();
    /** a list of all the cd offerings of Merit Bank */
    private static final ArrayList<CDOffering> cdOfferings = new ArrayList<>();
    /** a list of all the transactions that have been sent to the fraud queue for review */
    private static final FraudQueue fraudQueue = new FraudQueue();
    /** an incremented number to associate individual account holders with their accounts */
    private static long nextAccountNumber = 1L;
    /** the maximum balance an account holder must be under to open a new checking or savings account */
    public static final double NEW_ACCOUNT_MAX_BALANCE = 250000.0;
    /** the threshold by which a transaction is determined if it needs to be reviewed */
    public static final double FRAUD_SUSPICION_THRESHOLD = 1000.0;
    /** the interest rate for savings accounts */
    public static final double SAVINGS_INTEREST_RATE = 0.01;
    /** the interest rate for checking accounts */
    public static final double CHECKING_INTEREST_RATE = 0.0001;
    // endregion

    // region set bank stuff

    /**
     * this method adds an account holder to Merit Banks list of account holders
     *
     * @param accountHolder the account holder to be added to Merit Bank
     */
    public static void addAccountHolder(AccountHolder accountHolder) { accountHolders.add(accountHolder); }

    /**
     * this method clears all current cd offerings held by Merit Bank and
     * takes in an array of cd offerings and sets them as the offerings
     * being held by Merit Bank
     *
     * @param cdOfferings an array of cd offerings
     */
    public static void setCDOfferings(CDOffering[] cdOfferings) {
        MeritBank.cdOfferings.clear();
        MeritBank.cdOfferings.addAll(Arrays.asList(cdOfferings));
    }

    /**
     * this method clears all cd offerings held by Merit Bank
     */
    public static void clearCDOfferings() { cdOfferings.clear(); }

    /**
     * this method takes in a cd offering and adds it to a list of offerings
     * being held by Merit Bank
     *
     * @param offering the cd offering to be added
     */
    public static void addCDOffering(CDOffering offering) {
        cdOfferings.add(offering);
    }

    /**
     * this method returns the list of transactions that have been flagged to be
     * reviewed by Merit Banks fraud department
     *
     * @return the transactions in the fraud queue
     */
    public static FraudQueue getFraudQueue() { return fraudQueue; }


    /**
     * this method looks at all of Merit Banks account holders bank accounts and
     * returns the bank account that matches the account id passed in
     *
     * @param accountId the account id associated with a particular bank account
     * @return the bank account that matches the account id
     */
    public static BankAccount getBankAccount(long accountId) {
        for (AccountHolder accountHolder: accountHolders) {
            List<CheckingAccount> checkingAccounts = accountHolder.getCheckingAccounts();
            for (int i = 0; i < checkingAccounts.size(); i++) {
                if (accountId == checkingAccounts.get(i).getAccountNumber()) {
                    return checkingAccounts.get(i);
                }
            }
            List<SavingsAccount> savingsAccounts = accountHolder.getSavingsAccounts();
            for (int i = 0; i < savingsAccounts.size(); i++) {
                if (accountId == savingsAccounts.get(i).getAccountNumber()) {
                    return savingsAccounts.get(i);
                }
            }
            List<CDAccount> cdAccounts = accountHolder.getCDAccounts();
            for (int i = 0; i < cdAccounts.size(); i++) {
                if (accountId == cdAccounts.get(i).getAccountNumber()) {
                    return cdAccounts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * this method sets the next account number for new bank accounts
     *
     * @param nextAccountNumber the number being set for the next bank account created
     */
    private static void setNextAccountNumber(long nextAccountNumber) { MeritBank.nextAccountNumber = nextAccountNumber; }
    // endregion

    // region get bank stuff

    /**
     * this method returns a list of all account holders of Merit Bank
     *
     * @return the list of all account holders of Merit Bank
     */
    public static List<AccountHolder> getAccountHolders() { return accountHolders; }

    /**
     * this method returns an account holder located by their id
     *
     * @param id the id associated with an individual account holder
     * @return the account holder requested by their id
     */
    public static AccountHolder getAccountHolderbyId(int id) {
        for (AccountHolder accountHolder : accountHolders) {
            if (accountHolder.getId() == id) {
                return accountHolder;
            }
        }
        return null;
    }

    /**
     * this method returns a list of all cd offerings being held by Merit Bank
     *
     * @return the list of all cd offerings of Merit Bank
     */
    public static List<CDOffering> getCDOfferings() { return cdOfferings; }

    public static CDOffering getCDOfferingById(int id) {
        for (CDOffering cdOffering: cdOfferings) {
            if (id == cdOffering.getId()) {
                return cdOffering;
            }
        }
        return null;
    }

    /**
     * this method compares all cd offerings being held by Merit Bank and returns the
     * one with the largest calculated future value
     *
     * @param depositAmount the amount the cd offerings will calculate the future value for
     * @return the cd offering with the greatest calculated future value
     */
    public static CDOffering getBestCDOffering(double depositAmount){
        double currentValue;
        double highest = 0.0;
        CDOffering highestOffering = null;

        for (CDOffering cdOffering : cdOfferings) {
            currentValue = futureValue(depositAmount, cdOffering.getInterestRate(), cdOffering.getTerm());

            if (currentValue > highest) {
                highest = currentValue;
                highestOffering = new CDOffering(cdOffering.getTerm(), cdOffering.getInterestRate());
            }
        }

        return highestOffering;
    }

    /**
     * this method compares all cd offerings being held by Merit Bank and returns the
     * one with the second largest calculated future value
     *
     * @param depositAmount the amount the cd offerings will calculate the future value for
     * @return the cd offering with the second greatest calculated future value
     */
    public static CDOffering getSecondBestCDOffering(double depositAmount){
        double current;
        double highest = 0.0;
        double secondHighest = 0.0;
        CDOffering currentOffering;
        CDOffering highestOffering = null;
        CDOffering secondHighestOffering = null;

        for (CDOffering cdOffering : cdOfferings) {
            current = futureValue(depositAmount, cdOffering.getInterestRate(), cdOffering.getTerm());
            currentOffering = new CDOffering(cdOffering.getTerm(), cdOffering.getInterestRate());

            if (current > highest) {
                secondHighest = highest;
                secondHighestOffering = highestOffering;
                highest = current;
                highestOffering = currentOffering;
            } else if (current > secondHighest) {
                secondHighest = highest;
                secondHighestOffering = highestOffering;
            }
        }

        return secondHighestOffering;
    }

    public static long getNextAccountNumber() { return nextAccountNumber++; }

    /**
     * this method combines all the balances of all account holders and returns the total
     * balance of all accounts held by Merit Bank
     *
     * @return the total balance of all accounts held by Merit Bank
     */
    public static double totalBalances(){
        double total = 0.0;
        for (AccountHolder accountHolder : accountHolders) total += accountHolder.getCombinedBalance();
        return total;
    }

    /**
     * this method calculates the future value of an account
     *
     * @param presentValue the present value of an account
     * @param interestRate the interest rate of an account
     * @param term the term of an account
     * @return the calculated future value of an account
     * @deprecated recursive future value should be used instead
     */
    @Deprecated
    public static double futureValue(double presentValue, double interestRate, int term) {
        return recursiveFutureValue(presentValue, interestRate, term);
    }

    /**
     * this method calculates the future value of an account
     *
     * @param presentValue the present value of an account
     * @param interestRate the interest rate of an account
     * @param term the term of an account
     * @return the calculated future value of an account
     */
    public static double recursiveFutureValue(double presentValue, double interestRate, int term) {
        if (term == 1){
            return presentValue * (interestRate + 1);

        } else {
            return (interestRate + 1) * recursiveFutureValue(presentValue, interestRate, term - 1);
        }
    }
    // endregion

    // region manipulate bank stuff

    /**
     * this method sorts all account holders of Merit Bank in descending order by their
     * total balance.
     *
     * @return the sorted array of account holders of Merit Bank
     */
    public static AccountHolder[] sortAccountHolders(){
        Collections.sort(accountHolders);
        return accountHolders.toArray(new AccountHolder[0]);
    }

    /**
     * this method takes in a transaction, checks to see if it has a date associated with
     * and adds the current date if it does not. It then sends it for processing or to the
     * fraud queue if it exceeds the fraud suspicion threshold.
     *
     * @param transaction the transaction being processed
     * @return a boolean that records that the transaction has been processed
     */
    public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
        try{
            if (transaction.getTransactionDate() == null) {
                transaction.setTransactionDate(new Date());
            }
            transaction.process();
        } catch (ExceedsFraudSuspicionLimitException e){
            transaction.setProcessedByFraudTeam(true);
            fraudQueue.addTransaction(transaction);
            throw e;
        }
        return true;
    }
    // endregion

    // region read/write from files
    public static boolean readFromFile(String fileName) {
        accountHolders.clear();
        ArrayList<String> tempTransactions = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(fileName))) {
            setNextAccountNumber(sc.nextLong());

            CDOffering[] temp = new CDOffering[sc.nextInt()];
            for (int i = 0; i < temp.length; i++){
                temp[i] = CDOffering.readFromString(sc.next());
            }
            setCDOfferings(temp);

            int numOfAccountHolders = sc.nextInt();
            for (int i = 0; i < numOfAccountHolders; i++){
                AccountHolder currentAC = AccountHolder.readFromString(sc.next());
                int numCheckAccounts = sc.nextInt();
                //creating C
                for (int a = 0; a < numCheckAccounts; a++){
                    CheckingAccount currentCh = CheckingAccount.readFromString(sc.next());
                    int numCheckTransactions = sc.nextInt();
                    for (int a1 = 0; a1 < numCheckTransactions; a1++) {
                        String x = sc.next();
                        tempTransactions.removeIf(x::equals);
                        tempTransactions.add(x);
                    }
                    currentAC.addCheckingAccount(currentCh);
                }
                int numSavingsAccounts = sc.nextInt();
                //
                for (int b = 0; b < numSavingsAccounts; b++){
                    SavingsAccount currentSA = SavingsAccount.readFromString(sc.next());
                    int numSavsTransactions = sc.nextInt();
                    for (int b1 = 0; b1 < numSavsTransactions; b1++) {
                        String x = sc.next();
                        tempTransactions.removeIf(x::equals);
                        tempTransactions.add(x);
                    }
                    currentAC.addSavingsAccount(currentSA);
                }
                int numCDAccounts = sc.nextInt();
                for (int c = 0; c < numCDAccounts; c++){
                    CDAccount currentCD = CDAccount.readFromString(sc.next());
                    int numCDTransactions = sc.nextInt();
                    for (int c1 = 0; c1 < numCDTransactions; c1++) {
                        String x = sc.next();
                        tempTransactions.removeIf(x::equals);
                        tempTransactions.add(x);
                    }
                    currentAC.addCDAccount(currentCD);
                }
                addAccountHolder(currentAC);
            }
            for (String trans: tempTransactions){
                Transaction result = Transaction.readFromString(trans);
                if (result.sourceAccount != null) result.sourceAccount.addTransaction(result);
                if (result.targetAccount != null) result.targetAccount.addTransaction(result);
            }
            int numFrauds = sc.nextInt();
            for (int i = 0; i < numFrauds; i++){
                MeritBank.fraudQueue.addTransaction(Transaction.readFromString(sc.next()));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeToFile(String fileName){
        StringBuilder result = new StringBuilder(nextAccountNumber + "\n");

        result.append(cdOfferings.size()).append("\n");
        for (CDOffering cdOffering : cdOfferings) result.append(cdOffering.writeToString()).append("\n");

        result.append(accountHolders.size()).append("\n");
        for (AccountHolder accountHolder : accountHolders) result.append(accountHolder.writeToString());

        Transaction trans = fraudQueue.getTransaction();
        ArrayList<Transaction> transList = new ArrayList<>();
        while (trans != null){
            transList.add(trans);
            trans = fraudQueue.getTransaction();
        }

        result.append(transList.size()).append("\n");
        for (Transaction currentTrans : transList) result.append(currentTrans.writeToString()).append("\n");

        try(FileWriter fr = new FileWriter(fileName)){
            fr.write(result.toString());
        } catch (IOException e){
            return false;
        }

        return true;
    }
    // endregion
}