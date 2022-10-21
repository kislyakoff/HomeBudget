package by.kislyakoff.HomeBudgetApp.model.dict;

public enum TransactionType {

	EXPENCE("E"), INCOM("I"), TRANSFER("T"), DEBT("D");
	
	private String type;

	private TransactionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
