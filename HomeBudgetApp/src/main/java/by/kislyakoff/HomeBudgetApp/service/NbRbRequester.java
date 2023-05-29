package by.kislyakoff.HomeBudgetApp.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import by.kislyakoff.HomeBudgetApp.exceptions.RequesterException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NbRbRequester {
	
	public String getRatesAsJson(String url) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
		
		log.info("request for url:{}", url);
		
		try {
			HttpResponse<String> response = 
					client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (Exception ex) {
			if (ex instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			log.error("NbRb request error, url:{}", url, ex);
			throw new RequesterException(ex);
		} 
	}

}
