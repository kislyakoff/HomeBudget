package by.kislyakoff.HomeBudgetApp.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.kislyakoff.HomeBudgetApp.exceptions.RequesterException;
//TODO pending to delete
@Service
public class CbrRequester {
	
	private static final Logger log = LoggerFactory.getLogger(CbrRequester.class);

	public String getRatesAsXml(String url) {
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
		try {
			
			log.info("request for url:{}", url);
			HttpResponse<String> response = 
					client.send(request, HttpResponse.BodyHandlers.ofString());
			
			return response.body();
			
		} catch (Exception ex) {
			if (ex instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			log.error("cbr request error, url:{}", url, ex);
			throw new RequesterException(ex);
		} 
	}
}
