package com.example.library.domain.rent.infrastructure.repository;

import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.RentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaRentHistoryRepository extends JpaRepository<RentHistoryEntity,Long> {
    Optional<RentHistoryEntity> findByUserNoAndBookNoAndRentState(Long userNo, Long bookNo, RentState rentState);
    List<RentHistoryEntity> findByUserNoAndRentState(Long userNo, RentState rentState);
    List<RentHistoryEntity> findByUserNoOrderByHistoryNo(Long userNo);
    void deleteByUserNo(Long userNo);
}
