package by.kislyakoff.HomeBudgetApp.dto.projections;

public class CategoryProjection {

	private String name;

	public CategoryProjection(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
