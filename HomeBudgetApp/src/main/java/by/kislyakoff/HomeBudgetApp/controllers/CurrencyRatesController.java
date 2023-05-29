package by.kislyakoff.HomeBudgetApp.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import by.kislyakoff.HomeBudgetApp.dto.CbrCurrencyRateDTO;
import by.kislyakoff.HomeBudgetApp.dto.NbRbCurrencyRateDTO;
import by.kislyakoff.HomeBudgetApp.model.CbrCurrencyRate;
import by.kislyakoff.HomeBudgetApp.model.NbRbCurrencyRate;
import by.kislyakoff.HomeBudgetApp.service.CbrCurrencyRateService;
import by.kislyakoff.HomeBudgetApp.service.NbRbCurrencyRateService;

@RestController
@RequestMapping("/homebudget/rates")
public class CurrencyRatesController {

	private final CbrCurrencyRateService cbrCurrencyRateService;
	private final NbRbCurrencyRateService nbRbCurrencyRateService;
	private final ModelMapper modelMapper;

	public CurrencyRatesController(CbrCurrencyRateService cbrCurrencyRateService,
			NbRbCurrencyRateService nbRbCurrencyRateService, ModelMapper modelMapper) {
		this.cbrCurrencyRateService = cbrCurrencyRateService;
		this.nbRbCurrencyRateService = nbRbCurrencyRateService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/cbr")
	@ResponseBody
	public List<CbrCurrencyRateDTO> getCbrCurrencyRates() throws ParserConfigurationException, SAXException, IOException {
		return cbrCurrencyRateService.getCurrencyRates(LocalDate.now()).stream()
				.map(rate -> modelMapper.map(rate, CbrCurrencyRateDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/nbrb")
	@ResponseBody
	public List<NbRbCurrencyRateDTO> getNbRbCurrencyRates()
			throws ParserConfigurationException, SAXException, IOException {
		return nbRbCurrencyRateService.getNbRbCurrenctRates(LocalDate.now()).stream()
				.map(rate -> modelMapper.map(rate, NbRbCurrencyRateDTO.class)).collect(Collectors.toList());
	}
}
