package by.kislyakoff.HomeBudgetApp.util.enumConverters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import by.kislyakoff.HomeBudgetApp.model.dict.CurrencyName;

@Converter(autoApply = true)
public class CurrencyNameConverter implements AttributeConverter<CurrencyName, Integer> {

	@Override
	public Integer convertToDatabaseColumn(CurrencyName name) {
		if (name == null)
			return null;
		return name.getCode();
	}

	@Override
	public CurrencyName convertToEntityAttribute(Integer dbData) {
		if (dbData == null)
			return null;
		
		return Stream.of(CurrencyName.values())
				.filter(c -> c.getCode().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
