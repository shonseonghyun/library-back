package com.example.library.domain.heart.infrastructure;

import com.example.library.domain.heart.domain.Heart;
import com.example.library.domain.heart.domain.HeartRepository;
import com.example.library.domain.heart.domain.dto.HeartResponseDto;
import com.example.library.domain.heart.application.dto.QHeartResponseDto_Response;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.HeartBookNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.library.domain.book.domain.QBookEntity.bookEntity;
import static com.example.library.domain.heart.domain.QHeart.heart;


@Slf4j
@RequiredArgsConstructor
@Repository
public class HeartRepositoryImpl implements HeartRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final SpringDataJpaHeartRepository springDataJpaHeartRepository;

    @Override
    public List<HeartResponseDto.Response> findHeartsByUserNo(Long userNo) {
        List<HeartResponseDto.Response> result = jpaQueryFactory.select(new QHeartResponseDto_Response(heart.heartNo,heart.userNo,heart.bookNo,bookEntity.bookName,heart.createdTm, heart.createdTm, bookEntity.bookAuthor,bookEntity.bookPublisher,bookEntity.bookImage))
                .from(heart)
                .where(heart.userNo.eq(userNo))
                .innerJoin(bookEntity)
                .on(heart.bookNo.eq(bookEntity.bookCode))
                .fetch()
                ;
        return result;
    }

    @Override
    public Heart findByUserNoAndBookNo(Long userNo, Long bookNo) {
        Heart entity = springDataJpaHeartRepository.findByUserNoAndBookNo(userNo,bookNo)
                .orElseThrow(() -> new HeartBookNotFoundException(ErrorCode.HEARTNO_NOT_FOUND));
        return entity;
    }

    @Override
    public Heart save(Heart heart) {
        return springDataJpaHeartRepository.save(heart);
    }

    @Override
    public void deleteById(Long heartNo) {
        springDataJpaHeartRepository.deleteById(heartNo);
    }
}
