package by.kislyakoff.HomeBudgetApp.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.kislyakoff.HomeBudgetApp.model.CbrCurrencyRate;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CbrCurrencyRateParserXml {

	public List<CbrCurrencyRate> parse(String url) throws ParserConfigurationException, SAXException, IOException {
		
		List<CbrCurrencyRate> rates = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		log.info("request and parse rates from url:{}", url);
		Document doc = db.parse(url);
		doc.getDocumentElement().normalize();

		NodeList list = doc.getDocumentElement().getElementsByTagName("Valute");

		for (int valIdx = 0; valIdx < list.getLength(); valIdx++) {
			Node node = list.item(valIdx);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				String numCode = element.getElementsByTagName("NumCode").item(0).getTextContent();
				String charCode = element.getElementsByTagName("CharCode").item(0).getTextContent();
				Integer nominal = Integer.parseInt(element.getElementsByTagName("Nominal").item(0).getTextContent());
				String name = element.getElementsByTagName("Name").item(0).getTextContent();
				Double value = Double
						.parseDouble(element.getElementsByTagName("Value").item(0).getTextContent().replace(",", "."));

				CbrCurrencyRate rate = new CbrCurrencyRate(numCode, charCode, nominal, name, value);
				rates.add(rate);
			}

		}
		return rates;
	}

}
