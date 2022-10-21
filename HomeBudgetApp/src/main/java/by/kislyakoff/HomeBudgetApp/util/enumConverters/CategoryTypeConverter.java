package by.kislyakoff.HomeBudgetApp.util.enumConverters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import by.kislyakoff.HomeBudgetApp.model.dict.CategoryType;

@Converter(autoApply = true)
public class CategoryTypeConverter implements AttributeConverter<CategoryType, String> {

	@Override
	public String convertToDatabaseColumn(CategoryType type) {
		if (type == null)
			return null;
		return type.getType();
	}

	@Override
	public CategoryType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Stream.of(CategoryType.values())
				.filter(t -> t.getType().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
