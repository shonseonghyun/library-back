package com.example.library.domain.heart.infrastructure.repository;

import com.example.library.domain.heart.domain.Heart;
import com.example.library.domain.heart.domain.repository.HeartRepository;
import com.example.library.domain.heart.domain.dto.HeartResponseDto;
import com.example.library.domain.heart.domain.dto.QHeartResponseDto_Response;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.HeartBookNotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.library.domain.book.domain.QBookEntity.bookEntity;
import static com.example.library.domain.heart.domain.QHeart.heart;


@Slf4j
@RequiredArgsConstructor
@Repository
public class HeartRepositoryImpl implements HeartRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final SpringDataJpaHeartRepository springDataJpaHeartRepository;

    @Override
    public List<HeartResponseDto.Response> findHeartsByUserNo(Long heartNo,Long userNo,int pageSize) {
        List<HeartResponseDto.Response> result = jpaQueryFactory.select(new QHeartResponseDto_Response(heart.heartNo,heart.userNo,heart.bookNo,bookEntity.bookName,heart.createdTm, heart.createdTm, bookEntity.bookAuthor,bookEntity.bookPublisher,bookEntity.bookImage.originalFileName,bookEntity.bookImage.fileSize,bookEntity.bookImage.filePath,bookEntity.bookImage.newFileName))
                .from(heart)
                .where(
                        heart.userNo.eq(userNo),
                        ltHeartNo(heartNo)
                )
                .innerJoin(bookEntity)
                .on(heart.bookNo.eq(bookEntity.bookCode))
                .orderBy(heart.heartNo.desc())
                .limit(pageSize)
                .fetch()
                ;

        return result;
    }

    private BooleanExpression ltHeartNo(Long heartNo){
        if(heartNo==null){
            return null;
        }
        return heart.heartNo.lt(heartNo);
    }

    @Override
    public Heart findByUserNoAndBookNo(Long userNo, Long bookNo) {
        Heart entity = springDataJpaHeartRepository.findByUserNoAndBookNo(userNo,bookNo)
                .orElseThrow(() -> new HeartBookNotFoundException(ErrorCode.HEARTNO_NOT_FOUND));
        return entity;
    }

    public Optional<Heart> getByUserNoAndBookNo(Long userNo, Long bookNo) {
        return springDataJpaHeartRepository.findByUserNoAndBookNo(userNo,bookNo);
    }

    @Override
    public void deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
        springDataJpaHeartRepository.deleteByUserNoAndBookNo(userNo,bookNo);
    }

    @Override
    public void deleteByUserNo(Long userNo) {
        springDataJpaHeartRepository.deleteByUserNo(userNo);
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
