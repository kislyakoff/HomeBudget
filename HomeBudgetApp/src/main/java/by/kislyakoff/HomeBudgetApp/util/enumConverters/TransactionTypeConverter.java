package by.kislyakoff.HomeBudgetApp.util.enumConverters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import by.kislyakoff.HomeBudgetApp.model.dict.TransactionType;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

	@Override
	public String convertToDatabaseColumn(TransactionType type) {
		if (type == null)
			return null;
		return type.getType();
	}

	@Override
	public TransactionType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Stream.of(TransactionType.values())
				.filter(t -> t.getType().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
