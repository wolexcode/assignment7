package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.AccountHoldersContactDetails;

public interface AccountHoldersContactDetailsRepository 
extends JpaRepository<AccountHoldersContactDetails, Integer>{
	  AccountHoldersContactDetails findById(long id);


}