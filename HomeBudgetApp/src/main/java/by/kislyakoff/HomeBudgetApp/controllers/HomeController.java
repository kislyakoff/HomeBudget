package by.kislyakoff.HomeBudgetApp.controllers;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import by.kislyakoff.HomeBudgetApp.dto.projections.BalanceByCurrencyView;
import by.kislyakoff.HomeBudgetApp.model.CbrCurrencyRate;
import by.kislyakoff.HomeBudgetApp.service.AccountsService;
import by.kislyakoff.HomeBudgetApp.service.CbrCurrencyRateService;

@Controller
@RequestMapping("/homebudget")
public class HomeController {

        private final AccountsService accountsService;
        private final CbrCurrencyRateService cbrCurrencyRateService;

        @Autowired
        public HomeController(AccountsService accountsService, CbrCurrencyRateService cbrCurrencyRateService) {
                this.accountsService = accountsService;
                this.cbrCurrencyRateService = cbrCurrencyRateService;
        }

        @GetMapping
        public String home() {
                return "layouts/home";
        }

        @GetMapping("/balances")
        @ResponseBody
        public List<BalanceByCurrencyView> getBalance(@AuthenticationPrincipal(expression = "id") Integer ownerId) {
                return accountsService.getBalance(ownerId);
        }


        @GetMapping("/cbr")
        @ResponseBody
        public List<CbrCurrencyRate> getCbrCurrencyRates() throws ParserConfigurationException, SAXException, IOException {
                return cbrCurrencyRateService.getCurrencyRates(LocalDate.now());
        }

}
