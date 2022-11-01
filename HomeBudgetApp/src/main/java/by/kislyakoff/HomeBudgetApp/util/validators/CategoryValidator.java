package by.kislyakoff.HomeBudgetApp.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.kislyakoff.HomeBudgetApp.model.Category;
import by.kislyakoff.HomeBudgetApp.service.CategoriesService;

@Component
public class CategoryValidator implements Validator {
	
	private final CategoriesService categoriesService;
	
	@Autowired
	public CategoryValidator(CategoriesService categoriesService) {
		this.categoriesService = categoriesService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Category category = (Category) target;
		
		// validate for a new category
		if (category.getId() == null && categoriesService.findByNameAndType(category.getName(), 
																category.getType()).isPresent())
			errors.rejectValue("name", null, "Категория с такими параметрами уже существует");
		// validate when update category with existed name&type and ignore error with own name&type 
		else if (category.getId() != null && categoriesService.findByNameAndType(category.getName(), 
																category.getType()).isPresent() &&
			!categoriesService.findById(category.getId()).getName().equals(category.getName()))
			errors.rejectValue("name", null, "Категория с такими параметрами уже существует");
	}

}
