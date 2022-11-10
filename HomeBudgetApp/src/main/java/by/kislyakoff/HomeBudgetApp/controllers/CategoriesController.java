package by.kislyakoff.HomeBudgetApp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.kislyakoff.HomeBudgetApp.model.Category;
import by.kislyakoff.HomeBudgetApp.service.CategoriesService;
import by.kislyakoff.HomeBudgetApp.util.helpers.PaginationHelper;
import by.kislyakoff.HomeBudgetApp.util.validators.CategoryValidator;

@Controller
@RequestMapping("/homebudget/categories")
public class CategoriesController {
	
	private final CategoriesService categoriesService;
	private final CategoryValidator categoryValidator;
	
	@Autowired	
	public CategoriesController(CategoriesService categoriesService, CategoryValidator categoryValidator) {
		this.categoriesService = categoriesService;
		this.categoryValidator = categoryValidator;
	}


	@GetMapping({"", "/{page}"})
	public String categoriesList(@PathVariable Optional<Integer> page, 
						@RequestParam(name = "seek", required = false) String seek, Model model) {
		
		Integer _page = 0;
		
		if (page.isPresent()) {
			_page = page.get() - 1;
		}
		
		Page<Category> list = categoriesService.categoriesListPaginated(seek, _page, 
					CategoriesService.DEFAULT_CATEGORIES_PER_PAGE, CategoriesService.Order.NAME_ASC);
		model.addAttribute("categories", list.getContent());
		model.addAttribute("page", _page + 1);
		model.addAttribute("pagination", PaginationHelper.generate(_page + 1, list.getTotalPages()));
		model.addAttribute("seek", seek);
		
		return "layouts/categories";
	}

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<HttpStatus> create(@RequestBody Category category, BindingResult bindingResult) {

		categoryValidator.validate(category, bindingResult);
		
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		categoriesService.create(category);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PatchMapping("edit/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> edit(@PathVariable Integer id, @RequestBody Category category, 
															BindingResult bindingResult) {

		categoryValidator.validate(category, bindingResult);
		
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		categoriesService.update(id, category);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@DeleteMapping("/erase/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
		
		categoriesService.delete(id);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
