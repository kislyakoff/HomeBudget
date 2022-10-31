package by.kislyakoff.HomeBudgetApp.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.kislyakoff.HomeBudgetApp.model.Person;
import by.kislyakoff.HomeBudgetApp.service.AdminService;

@Component
public class PersonValidator implements Validator {
	
	private final AdminService adminService;
	
	@Autowired
	public PersonValidator(AdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		
		// validate for new person
		if (person.getId() == null && adminService.findByName(person.getUsername()).isPresent())
			errors.rejectValue("username", null, "Пользователь с таким логином уже зарегистрирован");
		
		// validate when update person with existed username and ignore error with own username 
		if (person.getId() != null && adminService.findByName(person.getUsername()).isPresent() &&
				!adminService.findById(person.getId()).getUsername().equals(person.getUsername())) {
			errors.rejectValue("username", null, "Пользователь с таким логином уже зарегистрирован");
		}
	}

}
