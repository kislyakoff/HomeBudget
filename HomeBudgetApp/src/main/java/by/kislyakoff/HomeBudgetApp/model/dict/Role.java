package by.kislyakoff.HomeBudgetApp.model.dict;

public enum Role {

	ADMIN("A"), USER("U");
	
	private String role;

	private Role(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	
}
