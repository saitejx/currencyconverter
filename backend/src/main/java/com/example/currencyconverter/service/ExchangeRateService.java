package com.example.currencyconverter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Service
public class ExchangeRateService {

    @Value("${exchange.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ExchangeRateService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getLatestRates(String baseCurrency) {
        String url = apiUrl + baseCurrency;
        return restTemplate.getForObject(url, Map.class);
    }
    
    public Set<String> getSupportedCurrencies() {
        // We use USD as a base to get the keys
        Map<String, Object> response = getLatestRates("USD");
        if (response != null && response.containsKey("rates")) {
            Map<String, Object> rates = (Map<String, Object>) response.get("rates");
            return rates.keySet();
        }
        throw new RuntimeException("Could not fetch supported currencies");
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equalsIgnoreCase(toCurrency)) {
            return BigDecimal.ONE;
        }

        Map<String, Object> response = getLatestRates(fromCurrency);
        if (response != null && response.containsKey("rates")) {
            Map<String, Object> rates = (Map<String, Object>) response.get("rates");
            if (rates.containsKey(toCurrency)) {
                Object rateObj = rates.get(toCurrency);
                if (rateObj instanceof Integer) {
                    return BigDecimal.valueOf((Integer) rateObj);
                } else if (rateObj instanceof Double) {
                    return BigDecimal.valueOf((Double) rateObj);
                } else {
                    return new BigDecimal(rateObj.toString());
                }
            }
        }
        throw new IllegalArgumentException("Invalid currency code or API error");
    }
}
