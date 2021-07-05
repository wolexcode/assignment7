package merit.america.bank.MeritBank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.AccountHolder;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
	  List<AccountHolder> findByLastName(String lastName);

	  AccountHolder findById(long id);

}
