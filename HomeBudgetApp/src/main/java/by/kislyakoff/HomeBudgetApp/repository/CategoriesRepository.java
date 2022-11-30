package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Category;
import by.kislyakoff.HomeBudgetApp.model.dict.CategoryType;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
	
	List<Category> findAllByOrderByNameAsc();
	
	Optional<Category> findByNameAndType(String name, CategoryType type);
	
	Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
}
