package merit.america.bank.MeritBank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import merit.america.bank.MeritBank.models.CDOffering;

public interface CDOfferingRepository extends JpaRepository<CDOffering, Integer>{
	CDOffering findById(long id);

}
