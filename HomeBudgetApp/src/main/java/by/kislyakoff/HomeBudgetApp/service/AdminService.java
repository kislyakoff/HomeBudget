package by.kislyakoff.HomeBudgetApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kislyakoff.HomeBudgetApp.model.Person;
import by.kislyakoff.HomeBudgetApp.repository.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class AdminService {
	
	private final PeopleRepository peopleRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AdminService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
		this.peopleRepository = peopleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<Person> usersList() {
		return peopleRepository.findAllByOrderByIdAsc();
	}
	
	public Person findById(Integer id) {
		return peopleRepository.findById(id).orElse(null);
	}
	
	public Optional<Person> findByName(String username) {
		return peopleRepository.findByUsername(username);
	}
	
	@Transactional
	public void create(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		peopleRepository.save(person);
	}
	
	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
		peopleRepository.save(updatedPerson);
	}
	
	@Transactional
	public void activate(Boolean activate, Integer id) {
		Optional<Person> personToActivate = peopleRepository.findById(id);
		if(personToActivate.isPresent())
			personToActivate.get().setIsActive(activate);
	}


	@Transactional
	public void delete(Integer id) {
		peopleRepository.deleteById(id);
	}
	
	

}
