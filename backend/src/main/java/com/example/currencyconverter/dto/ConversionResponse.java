package com.example.currencyconverter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConversionResponse {
    private BigDecimal amount;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;
    private LocalDateTime timestamp;

    public ConversionResponse() {
    }

    public ConversionResponse(BigDecimal amount, String fromCurrency, String toCurrency, BigDecimal exchangeRate, BigDecimal convertedAmount, LocalDateTime timestamp) {
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
