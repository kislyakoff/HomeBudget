package by.kislyakoff.HomeBudgetApp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import by.kislyakoff.HomeBudgetApp.model.CachedNbRbCurrencyRates;
import by.kislyakoff.HomeBudgetApp.model.NbRbCurrencyRate;
import by.kislyakoff.HomeBudgetApp.parsers.NbRbCurrencyRateParserJson;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NbRbCurrencyRateService {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	@Value("${nbrb.url}")
	private String url;

	private final NbRbRequester nbRbRequester;
	private final NbRbCurrencyRateParserJson currencyRateParser;
	private final Cache<LocalDate, CachedNbRbCurrencyRates> nbRbCurrencyRateCache;

	public NbRbCurrencyRateService(NbRbRequester nbRbRequester, NbRbCurrencyRateParserJson currencyRateParser,
			Cache<LocalDate, CachedNbRbCurrencyRates> nbRbCurrencyRateCache) {
		this.nbRbRequester = nbRbRequester;
		this.currencyRateParser = currencyRateParser;
		this.nbRbCurrencyRateCache = nbRbCurrencyRateCache;
	}

	public List<NbRbCurrencyRate> getNbRbCurrenctRates(LocalDate date) throws JsonMappingException, JsonProcessingException {
		
		log.info("getNbRbCurrencyRates. date:{}", date);
		
		List<NbRbCurrencyRate> rates;
		
		CachedNbRbCurrencyRates cachedRates = nbRbCurrencyRateCache.get(date);
		
		if(cachedRates == null) {
			
			String urlWithParams = String.format("%s&ondate=%s", url, DATE_FORMATTER.format(date));
			String ratesAsJson = nbRbRequester.getRatesAsJson(urlWithParams);
			rates = currencyRateParser.parse(ratesAsJson);
			nbRbCurrencyRateCache.put(date, new CachedNbRbCurrencyRates(rates));
		}
		else {
			rates = cachedRates.getCurrencyRates();
		}
		
		
		return rates.stream().filter(rate -> isEligibleCurrency(rate)).collect(Collectors.toList());
	}
	
	private boolean isEligibleCurrency(NbRbCurrencyRate rate) {
		return rate.getCur_Abbreviation().equals("USD") ||
				rate.getCur_Abbreviation().equals("EUR") ||
				rate.getCur_Abbreviation().equals("RUB");
	}
}
