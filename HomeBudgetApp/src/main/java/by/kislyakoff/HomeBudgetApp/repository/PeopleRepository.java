package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

	Optional<Person> findByUsername(String username);
	
	List<Person> findAllByOrderByIdAsc();
}
