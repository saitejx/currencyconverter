package com.example.currencyconverter.repository;

import com.example.currencyconverter.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {
    List<ConversionHistory> findAllByOrderByCreatedAtDesc();
}
