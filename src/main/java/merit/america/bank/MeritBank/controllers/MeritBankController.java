package merit.america.bank.MeritBank.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import merit.america.bank.MeritBank.exceptions.BalanceException;
import merit.america.bank.MeritBank.exceptions.NotFoundException;
import merit.america.bank.MeritBank.models.AccountHolder;
import merit.america.bank.MeritBank.models.CDAccount;
import merit.america.bank.MeritBank.models.CDOffering;
import merit.america.bank.MeritBank.models.CheckingAccount;
import merit.america.bank.MeritBank.models.SavingsAccount;
	
@RestController
public class MeritBankController {

	List<String> temp = new ArrayList<String>();
	List<CDOffering> cdOfferings = new ArrayList<CDOffering>();
			List<CDOffering> cdOffering = new ArrayList<CDOffering>();
	List<AccountHolder> accHolders = new ArrayList<AccountHolder>();
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders")
	public AccountHolder addAcc(@RequestBody @Validated AccountHolder acc){
		accHolders.add(acc);
		return acc;
	}
	
	@GetMapping(value = "/AccountHolders")
	 public List<AccountHolder> getAccs() {
		 return accHolders;
	 }
	
	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getAccById(@PathVariable int id) throws NotFoundException{
		if(id > accHolders.size()) {
			
			throw new NotFoundException("id does not exist");
		}
		
		return accHolders.get(id - 1);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount addCheckingByAccId(@PathVariable int id, @RequestBody CheckingAccount acc) throws NotFoundException, BalanceException {
		if(id > accHolders.size()) {
			
			throw new NotFoundException("id does not exist");
		}
		
	
	if(acc.getBalance() < 0) {
		throw new BalanceException("Balance cannot be less than 0");
	}
	if(acc.getInterestRate() <= 0 || acc.getInterestRate() >= 1) {
		throw new BalanceException("Interest rate cannot be less than 0, cannot be greater than 1");
	}
	if(acc.getTerm() < 1) {
		throw new BalanceException("Term cannot be less than 1");
	}
	accHolders.get(id - 1).addCDAccounts(acc);
	return acc;
}

@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
public List<CDAccount> getCDAccountsByAccountId(@PathVariable int id) throws NotFoundException {
	if(id > accHolders.size()) {
		
		throw new NotFoundException("id does not exist");
	}
	return accHolders.get(id - 1).getCDAccount();
}

@ResponseStatus(HttpStatus.CREATED)	
@PostMapping(value = "/CDOfferings")
public CDOffering addCDOffering(@RequestBody @Validated CDOffering off) throws BalanceException {
	if(off.getTerm() < 1) {
		throw new BalanceException("Term cannot be less than 1");
	}
	
			if(off.getInterestRate() <= 0 || off.getInterestRate() >= 1) {
				throw new BalanceException("Interest rate cannot be less than 0, cannot be greater than 1");
			}
			cdOfferings.add(off);
			return off;
		}
		
		@GetMapping(value = "/CDOfferings")
		public List<CDOffering> getCDOfferings() {
			return cdOfferings;
		}
		
		
		
	}