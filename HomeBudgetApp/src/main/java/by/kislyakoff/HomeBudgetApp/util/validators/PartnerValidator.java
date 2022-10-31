package by.kislyakoff.HomeBudgetApp.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.kislyakoff.HomeBudgetApp.model.Partner;
import by.kislyakoff.HomeBudgetApp.service.PartnersService;

@Component
public class PartnerValidator implements Validator {
	
	private final PartnersService partnersService;
	
	@Autowired
	public PartnerValidator(PartnersService partnersService) {
		this.partnersService = partnersService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Partner.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Partner partner = (Partner) target;
		
		// validate for new partner
				if (partner.getId() == null && partnersService.findByName(partner.getName()).isPresent())
					errors.rejectValue("name", null, "Партнер с таким именем уже существует");
				
				// validate when update partner with existed name and ignore error with own name 
				if (partner.getId() != null && partnersService.findByName(partner.getName()).isPresent() &&
						!partnersService.findById(partner.getId()).getName().equals(partner.getName())) {
					errors.rejectValue("name", null, "Партнер с таким именем уже существует");
				}
	}

}
