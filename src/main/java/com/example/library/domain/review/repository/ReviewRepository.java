package com.example.library.domain.review.repository;

import com.example.library.domain.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(
            value = "select s from ReviewEntity s " +
                    "join fetch s.book n " +
                    "where s.user.userNo=:userNo " +
                    "and s.book.bookCode= n.bookCode "
    )
    Page<ReviewEntity> findFetchJoinReviewsByUserNo(Long userNo, Pageable pageable);

 @Query(
                         value = "select s,b,u from ReviewEntity s " +
                                 "join fetch s.book b " +
                                 "left outer join s.user u " +
                                 "on s.user.userNo= u.userNo " +
                                 "where s.book.bookCode = b.bookCode " +
                                 "and s.book.bookCode=:bookNo"
                         )
    List<ReviewEntity> findFetchJoinReviewsByBookNo(Long bookNo);
}