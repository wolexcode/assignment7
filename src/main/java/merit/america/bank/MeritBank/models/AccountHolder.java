package merit.america.bank.MeritBank.models;

import java.util.ArrayList;
import java.util.List;


public class AccountHolder {
	static int nextId = 1;
	
	int id;
	
	String firstName;
	
	String middleName;
	
	
	String lastName;
	
	
	String ssn;
	
	
	List<CheckingAccount> checkingAccs = new ArrayList<CheckingAccount>();
	List<SavingsAccount> savingsAccs = new ArrayList<SavingsAccount>();
	List<CDAccount> cdAccounts = new ArrayList<CDAccount>();
	
	public AccountHolder() {
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.ssn = "";
		this.id = nextId;
	}

	public CheckingAccount addChecking(CheckingAccount checking) {
		checkingAccs.add(checking);
		return checking;
	}
	public List<CheckingAccount> getCheckingAccount() {
		return checkingAccs;
	}
	
	public SavingsAccount addSavings(SavingsAccount savings) {
		savingsAccs.add(savings);
		return savings;
	}
	
	
	public List<SavingsAccount> getSavingsAccount() {
		return savingsAccs;
	}
	
	public CDAccount addCDAccounts(CDAccount acc) {
		cdAccounts.add(acc);
		return acc;
	}
	
	public List<CDAccount> getCDAccount() {
		return cdAccounts;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void addCDAccounts(CheckingAccount acc) {
		// TODO Auto-generated method stub
		
	}
}