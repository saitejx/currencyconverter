package com.example.currencyconverter.repository;

import com.example.currencyconverter.entity.FavoritePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePairRepository extends JpaRepository<FavoritePair, Long> {
}
