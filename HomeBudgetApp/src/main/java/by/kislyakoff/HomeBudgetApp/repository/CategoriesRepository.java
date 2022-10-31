package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Category;
import by.kislyakoff.HomeBudgetApp.model.Partner;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
	
	List<Category> findAllByOrderByNameAsc();
	
	Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
