package merit.america.bank.MeritBank.models;


public class CDOffering {
	
	Integer term;
	
Double interestRate;
	
	public CDOffering() {
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
}