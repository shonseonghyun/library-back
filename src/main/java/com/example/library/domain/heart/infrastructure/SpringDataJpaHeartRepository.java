package com.example.library.domain.heart.infrastructure;

import com.example.library.domain.heart.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpringDataJpaHeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserNoAndBookNo(Long userNo, Long bookNo);

//    @Modifying
//    @Query(value = "delete from Heart h where h.bookNo = :bookCode")
//    void deleteByBookBookCode(Long bookCode);

    @Modifying
    @Query(value = "delete from Heart h where h.userNo = :userNo")
    void deleteByUserUserNo(Long userNo);

    void deleteByUserNoAndBookNo(Long userNo,Long bookNo);
    void deleteByUserNo(Long userNo);

}
