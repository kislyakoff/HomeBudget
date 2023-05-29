package by.kislyakoff.HomeBudgetApp.parsers;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import by.kislyakoff.HomeBudgetApp.model.NbRbCurrencyRate;

@Service
public class NbRbCurrencyRateParserJson {

	public List<NbRbCurrencyRate> parse(String ratesAsString) throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
//		CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class,
//				NbRbCurrencyRate.class);
		CollectionType typeReference = objectMapper.getTypeFactory().constructCollectionType(List.class,
				NbRbCurrencyRate.class);
		List<NbRbCurrencyRate> rates = objectMapper.readValue(ratesAsString, typeReference);

		return rates;
	}

}
