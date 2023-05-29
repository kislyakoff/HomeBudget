package by.kislyakoff.HomeBudgetApp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import by.kislyakoff.HomeBudgetApp.model.CachedCbrCurrencyRates;
import by.kislyakoff.HomeBudgetApp.model.CbrCurrencyRate;
import by.kislyakoff.HomeBudgetApp.parsers.CbrCurrencyRateParserXml;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CbrCurrencyRateService {

	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	@Value("${cbr.url}")
	private String url;
	private final CbrCurrencyRateParserXml parser;
	private final Cache<LocalDate, CachedCbrCurrencyRates> cache;

	public CbrCurrencyRateService(CbrCurrencyRateParserXml parser,
			Cache<LocalDate, CachedCbrCurrencyRates> cache) {
		this.parser = parser;
		this.cache = cache;
	}
	
	public List<CbrCurrencyRate> getCurrencyRates(LocalDate date) throws ParserConfigurationException, SAXException, IOException {
		
		log.info("getCbrCurrencyRates. date:{}", date);
		
		List<CbrCurrencyRate> rates;
		
		CachedCbrCurrencyRates cachedRates = cache.get(date);
		
		if (cachedRates == null) {
			
			String urlWithParams = String.format("%s?date_req=%s", url, DATE_FORMATTER.format(date));
			rates = parser.parse(urlWithParams);
			cache.put(date, new CachedCbrCurrencyRates(rates));
		}
		else {
			rates = cachedRates.getCurrencyRates();
		}
		
		return rates.stream().filter(rate -> isEligibleCurrency(rate)).collect(Collectors.toList());
	}
	
	/* selecting of valute to collect */
	private boolean isEligibleCurrency(CbrCurrencyRate rate) {
		return rate.getCharCode().equals("USD") ||
				rate.getCharCode().equals("EUR");
	}

}
