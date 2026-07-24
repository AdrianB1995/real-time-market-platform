package com.example.processing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarketEventRepository extends JpaRepository<MarketEntity, UUID> {

    //store market event
    MarketEntity save(MarketEntity marketEntity);
}
