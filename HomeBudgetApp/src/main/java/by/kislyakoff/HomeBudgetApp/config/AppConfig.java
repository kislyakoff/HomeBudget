package by.kislyakoff.HomeBudgetApp.config;

import java.time.LocalDate;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import by.kislyakoff.HomeBudgetApp.model.CachedCbrCurrencyRates;
import by.kislyakoff.HomeBudgetApp.model.CachedNbRbCurrencyRates;
import by.kislyakoff.HomeBudgetApp.util.enumConverters.CurrencyNameConverter;
import by.kislyakoff.HomeBudgetApp.util.enumConverters.TransactionTypeConverter;

@Configuration
public class AppConfig {

	private final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

	@Bean
	TransactionTypeConverter transactionTypeConverter() {
		return new TransactionTypeConverter();
	}

	@Bean
	CurrencyNameConverter currencyNameConverter() {
		return new CurrencyNameConverter();
	}

	@Bean
	Cache<LocalDate, CachedCbrCurrencyRates> currencyRateCache(@Value("${app.cache.size}") int cacheSize) {
		return cacheManager.createCache("CurrencyRate-Cache",
				CacheConfigurationBuilder.newCacheConfigurationBuilder(LocalDate.class, CachedCbrCurrencyRates.class,
						ResourcePoolsBuilder.heap(cacheSize)).build());
	}
	
	@Bean
	Cache<LocalDate, CachedNbRbCurrencyRates> nBcurrencyRateCache(@Value("${app.cache.size}") int cacheSize) {
		return cacheManager.createCache("NbCurrencyRate-Cache",
				CacheConfigurationBuilder.newCacheConfigurationBuilder(LocalDate.class, CachedNbRbCurrencyRates.class,
						ResourcePoolsBuilder.heap(cacheSize)).build());
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
