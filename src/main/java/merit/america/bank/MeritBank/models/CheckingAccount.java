package merit.america.bank.MeritBank.models;

public class CheckingAccount {
	static int nextId = 1;
	double interestRate;
	double balance;
	long accountNumber;
	long openedOn;
	
	public CheckingAccount() {
		this.interestRate = .0001;
		this.balance = 0;
		this.accountNumber = nextId++;
		this.openedOn = 0;
	}
	
	

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getOpenedOn() {
		return openedOn;
	}

	public void setOpenedOn(long openedOn) {
		this.openedOn = openedOn;
	}



	public int getTerm() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}