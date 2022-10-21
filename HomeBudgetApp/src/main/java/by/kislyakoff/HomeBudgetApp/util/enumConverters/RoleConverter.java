package by.kislyakoff.HomeBudgetApp.util.enumConverters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import by.kislyakoff.HomeBudgetApp.model.dict.Role;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

	@Override
	public String convertToDatabaseColumn(Role role) {
		if (role == null)
			return null;
		return role.getRole();
	}

	@Override
	public Role convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Stream.of(Role.values())
					.filter(r -> r.getRole().equals(dbData))
					.findFirst()
					.orElseThrow(IllegalArgumentException::new);
	}


}
