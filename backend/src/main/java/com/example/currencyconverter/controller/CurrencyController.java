package com.example.currencyconverter.controller;

import com.example.currencyconverter.dto.ConversionRequest;
import com.example.currencyconverter.dto.ConversionResponse;
import com.example.currencyconverter.entity.ConversionHistory;
import com.example.currencyconverter.entity.FavoritePair;
import com.example.currencyconverter.repository.ConversionHistoryRepository;
import com.example.currencyconverter.repository.FavoritePairRepository;
import com.example.currencyconverter.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final ExchangeRateService exchangeRateService;
    private final ConversionHistoryRepository historyRepository;
    private final FavoritePairRepository favoritePairRepository;

    @Autowired
    public CurrencyController(ExchangeRateService exchangeRateService,
                              ConversionHistoryRepository historyRepository,
                              FavoritePairRepository favoritePairRepository) {
        this.exchangeRateService = exchangeRateService;
        this.historyRepository = historyRepository;
        this.favoritePairRepository = favoritePairRepository;
    }

    @GetMapping("/currencies")
    public ResponseEntity<Set<String>> getCurrencies() {
        return ResponseEntity.ok(exchangeRateService.getSupportedCurrencies());
    }

    @GetMapping("/rates")
    public ResponseEntity<?> getRates(@RequestParam(defaultValue = "USD") String base) {
        return ResponseEntity.ok(exchangeRateService.getLatestRates(base));
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(@RequestBody ConversionRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (request.getFromCurrency() == null || request.getToCurrency() == null || request.getFromCurrency().equals(request.getToCurrency())) {
            throw new IllegalArgumentException("Invalid currencies provided");
        }

        BigDecimal rate = exchangeRateService.getExchangeRate(request.getFromCurrency(), request.getToCurrency());
        BigDecimal convertedAmount = request.getAmount().multiply(rate).setScale(2, RoundingMode.HALF_UP);

        ConversionHistory history = new ConversionHistory();
        history.setFromCurrency(request.getFromCurrency());
        history.setToCurrency(request.getToCurrency());
        history.setAmount(request.getAmount());
        history.setExchangeRate(rate);
        history.setConvertedAmount(convertedAmount);
        historyRepository.save(history);

        ConversionResponse response = new ConversionResponse(
                request.getAmount(),
                request.getFromCurrency(),
                request.getToCurrency(),
                rate,
                convertedAmount,
                history.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ConversionHistory>> getHistory() {
        return ResponseEntity.ok(historyRepository.findAllByOrderByCreatedAtDesc());
    }

    @DeleteMapping("/history")
    public ResponseEntity<Void> clearHistory() {
        historyRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoritePair> addFavorite(@RequestBody FavoritePair pair) {
        if (pair.getFromCurrency() == null || pair.getToCurrency() == null || pair.getFromCurrency().equals(pair.getToCurrency())) {
            throw new IllegalArgumentException("Invalid favorite pair");
        }
        return ResponseEntity.ok(favoritePairRepository.save(pair));
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoritePair>> getFavorites() {
        return ResponseEntity.ok(favoritePairRepository.findAll());
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoritePairRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
