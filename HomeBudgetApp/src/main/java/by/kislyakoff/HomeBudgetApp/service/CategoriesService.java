package by.kislyakoff.HomeBudgetApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kislyakoff.HomeBudgetApp.model.Category;
import by.kislyakoff.HomeBudgetApp.repository.CategoriesRepository;

@Service
@Transactional(readOnly = true)
public class CategoriesService {
	
public static int DEFAULT_CATEGORIES_PER_PAGE = 10;
	
	public static enum Order {
        DEFAULT,
        NAME_ASC,
        NAME_DESC
    }
	
	private final CategoriesRepository categoriesRepository;

	@Autowired
	public CategoriesService(CategoriesRepository categoriesRepository) {
		this.categoriesRepository = categoriesRepository;
	}
	
	public List<Category> categoriesList() {
		return categoriesRepository.findAllByOrderByNameAsc();
	}
	
	public Page<Category> categoriesListPaginated(String seek, int page, int categoriesPerPage, CategoriesService.Order order) {
		
		Sort sort;
		
		switch (order) {
		
		case NAME_ASC:
			sort = Sort.by("name").ascending();
			break;
		case NAME_DESC:
			sort = Sort.by("name").descending();
			break;
		case DEFAULT:
			default:
				sort = Sort.by("id").ascending();
		}
		
		return seek == null ?
				categoriesRepository.findAll(PageRequest.of(page, categoriesPerPage, sort)) :
					categoriesRepository.findByNameContainingIgnoreCase(seek, PageRequest.of(page, categoriesPerPage, sort));
	}

}
