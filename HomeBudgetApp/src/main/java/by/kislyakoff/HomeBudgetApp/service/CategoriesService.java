package by.kislyakoff.HomeBudgetApp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.kislyakoff.HomeBudgetApp.dto.CategoryDTO;
import by.kislyakoff.HomeBudgetApp.model.Category;
import by.kislyakoff.HomeBudgetApp.model.dict.CategoryType;
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
	private final ModelMapper modelMapper;

	@Autowired
	public CategoriesService(CategoriesRepository categoriesRepository, ModelMapper modelMapper) {
		this.categoriesRepository = categoriesRepository;
		this.modelMapper = modelMapper;
	}
	
	public Category findById(Integer id) {
		return categoriesRepository.findById(id).orElse(null);
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
	
	public Optional<Category> findByNameAndType (String name, CategoryType type) {
		return categoriesRepository.findByNameAndType(name, type);
	}

	@Transactional
	public void create(Category category) {
		categoriesRepository.save(category);
	}

	@Transactional
	public void update(Integer id, Category updatedCategory) {
		updatedCategory.setId(id);
		categoriesRepository.save(updatedCategory);
	}
	
	@Transactional
	public void delete(Integer id) {
		categoriesRepository.deleteById(id);
	}

	
	public Map<String, List<CategoryDTO>> getMapForTransaction() {
	
		List<Category> list = categoriesRepository.findAllByOrderByNameAsc();
		
		Map<String, List<CategoryDTO>> catMap = list.stream()
						.collect(Collectors.groupingBy(e -> e.getType().getType(), 
								Collectors.mapping(this::convertToCategoryDTO, Collectors.toList())));
		
		return catMap;
	}

	private CategoryDTO convertToCategoryDTO(Category category) {
		return modelMapper.map(category, CategoryDTO.class);
	}

}
