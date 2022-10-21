package by.kislyakoff.HomeBudgetApp.model.dict;

public enum CategoryType {
	
	EXPENCE("E"), INCOME("I");
	
	private String type;

	private CategoryType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
