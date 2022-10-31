package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Partner;

@Repository
public interface PartnersRepository extends JpaRepository<Partner, Integer> {
	
	List<Partner> findAllByOrderByNameAsc();

	Optional<Partner> findByName(String name);
	
	Page<Partner> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
