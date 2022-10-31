package by.kislyakoff.HomeBudgetApp.dto;

import by.kislyakoff.HomeBudgetApp.model.dict.CategoryType;

public class CategoryDTO {
	
	private String name;
	
	private CategoryType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}
	
	

}
