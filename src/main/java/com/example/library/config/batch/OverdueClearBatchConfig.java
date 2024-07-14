package com.example.library.config.batch;

import com.example.library.config.batch.custom.dto.OverdueClearUserDto;
import com.example.library.config.batch.custom.reader.CustomQuerydslZeroOffsetPagingItemReader;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity;
import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import com.example.library.global.event.Events;
import com.example.library.global.mail.domain.mail.application.dto.MailDto;
import com.example.library.global.mail.domain.mail.application.event.SendedMailEvent;
import com.example.library.global.mail.domain.mail.enums.MailType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.transaction.PlatformTransactionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity.rentHistoryEntity;
import static com.example.library.domain.rent.infrastructure.entity.QRentManagerEntity.rentManagerEntity;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OverdueClearBatchConfig {
    private final EntityManagerFactory entityManagerFactory;

    private final int chunkSize = 2; //pageSize

    @Bean
    public Job overdueClearJob(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new JobBuilder("overdueClearJob",jobRepository)
                .start(overdueClearStep(transactionManager,jobRepository))
                .build()
                ;
    }

    public Step overdueClearStep(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new StepBuilder("overdueClearStep",jobRepository)
                .<OverdueClearUserDto, RentManagerEntity>chunk(chunkSize,transactionManager)
                .reader(overdueClearRentManagerReader())
                .processor(overdueClearRentManagerProcessor(null))
                .writer(overdueClearRentManagerWriter())
                .build()
                ;
    }

    @Bean
    public CustomQuerydslZeroOffsetPagingItemReader<OverdueClearUserDto> overdueClearRentManagerReader(){
        //서브쿼리를 위한 Q엔티티
        QRentHistoryEntity subRentHistory = new QRentHistoryEntity("subRentHistory");

        return new CustomQuerydslZeroOffsetPagingItemReader<>(entityManagerFactory,chunkSize
                ,queryFactory -> queryFactory.select(Projections.fields(OverdueClearUserDto.class,
                        rentManagerEntity.managerNo,
                        rentManagerEntity.userNo,
                        rentManagerEntity.currentRentNumber,
                        rentManagerEntity.overdueFlg,
                        rentHistoryEntity.returnDt.max().as("returnDt")))
                        .from(rentManagerEntity)
                        .join(rentHistoryEntity)
                        .on(rentManagerEntity.managerNo.eq(rentHistoryEntity.managerNo))
                        .where(
                                rentManagerEntity.overdueFlg.eq(true)
                                        .and(
                                                JPAExpressions
                                                        .select(subRentHistory).from(subRentHistory)
                                                        .where(subRentHistory.rentState.eq(RentState.ON_OVERDUE),subRentHistory.managerNo.eq(rentManagerEntity.managerNo))
                                                        .notExists()
                                        )
                                        .and(rentHistoryEntity.rentState.eq(RentState.OVERDUE_RETURN))
                        )
                        .groupBy(rentManagerEntity.managerNo)
                        .orderBy(rentManagerEntity.managerNo.desc())
        );
    }

    @Bean
    @StepScope
    public ItemProcessor<OverdueClearUserDto,RentManagerEntity> overdueClearRentManagerProcessor(@Value("#{jobParameters[nowDt]}") String nowDt){
        log.info("rentManagerProcessor 처리");
        return item -> {
            if(isSameTodayAfter7DaysFromLastReturnDate(nowDt,item.getReturnDt())){ //연체된 도서 중 최대 반납일의 7일 경과 후가 오늘 인 경우 연체 해제 진행
                log.info("managerNo[{}] 연체 해제 성공",item.getManagerNo());

                RentManagerEntity selectedRentManagerEntity = RentManagerEntity.builder()
                        .managerNo(item.getManagerNo())
                        .userNo(item.getUserNo())
                        .currentRentNumber(item.getCurrentRentNumber())
                        .overdueFlg(item.isOverdueFlg())
                        .build()
                        ;

                selectedRentManagerEntity.setOverdueFlg(false);
                Events.raise(new SendedMailEvent(new MailDto(item.getUserNo(), MailType.MAIL_OVERDUE_CLEAR)));
                return selectedRentManagerEntity;
            }
            else{
                log.info("managerNo[{}] 연체 해제 패스",item.getManagerNo());
                log.info("연체도서 반납일자[{}] + 7일 > 금일일자[{}] ",item.getReturnDt(),nowDt);
                return null;
            }
        };
    }

    @Bean
    public JpaItemWriter<RentManagerEntity> overdueClearRentManagerWriter(){
        JpaItemWriter<RentManagerEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    private boolean isSameTodayAfter7DaysFromLastReturnDate(String today,String returnDt) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        //반납일자
        Date strToDt;
        try {
            strToDt = formatter.parse(returnDt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //반납일자로부터 +7일 날자 계산
        cal.setTime(strToDt);
        cal.add(Calendar.DAY_OF_MONTH,+7);
        Date after7Days = cal.getTime();

        //반납일자로부터 +7일 string형식
        String after7DaysStr = formatter.format(after7Days);

        return today.equals(after7DaysStr);
    }
}
