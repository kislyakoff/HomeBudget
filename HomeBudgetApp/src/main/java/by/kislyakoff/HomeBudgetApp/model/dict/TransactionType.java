package by.kislyakoff.HomeBudgetApp.model.dict;

public enum TransactionType {

	EXPENCE("E"), INCOME("I"), TRANSFER("T");
	
	private String type;

	private TransactionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
