package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.CheckingAccount;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Integer>{
	CheckingAccount findById(long id);

}
