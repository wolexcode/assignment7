package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.CDAccount;

public interface CDAccountRepository extends JpaRepository<CDAccount, Integer>{
	CDAccount findById(long id);

}
