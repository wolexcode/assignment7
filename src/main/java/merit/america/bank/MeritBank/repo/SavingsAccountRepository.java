package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.SavingsAccount;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer>{
	SavingsAccount findById(long id);

}
