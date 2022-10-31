package by.kislyakoff.HomeBudgetApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.kislyakoff.HomeBudgetApp.model.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
	
	List<Category> findAllByOrderByNameAsc();
	
	Optional<Category> findByName(String name);
	
	Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
