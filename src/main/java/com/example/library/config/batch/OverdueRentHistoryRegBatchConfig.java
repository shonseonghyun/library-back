package com.example.library.config.batch;

import com.example.library.config.batch.custom.reader.CustomQuerydslNoOffsetPagingItemReader;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.RentHistoryEntity;
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
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity.rentHistoryEntity;
import static com.example.library.domain.rent.infrastructure.entity.QRentManagerEntity.rentManagerEntity;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OverdueRentHistoryRegBatchConfig {
    private final EntityManagerFactory entityManagerFactory;
    private final RentRepository rentRepository;

    private final int chunkSize = 2; //pageSize

    @Bean
    public Job overdueRentHistoryRegJob(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new JobBuilder("overdueRentHistoryRegJob",jobRepository)
                .start(overdueRentHistoryRegStep(transactionManager,jobRepository))
                .build()
                ;
    }

    public Step overdueRentHistoryRegStep(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new StepBuilder("overdueRentHistoryRegStep",jobRepository)
                .<RentHistoryEntity, RentHistoryEntity>chunk(chunkSize,transactionManager)
                .reader(overdueRentHistoryRegReader(null))
                .processor(overdueRentHistoryRegProcessor())
                .writer(overdueRentHistoryRegWriter())
                .build()
                ;
    }

    @Bean
    @StepScope
    public CustomQuerydslNoOffsetPagingItemReader<RentHistoryEntity> overdueRentHistoryRegReader(@Value("#{jobParameters[nowDt]}") String nowDt){
        return new CustomQuerydslNoOffsetPagingItemReader<>(entityManagerFactory,nowDt,chunkSize,
                queryFactory->queryFactory.selectFrom(rentHistoryEntity)
                        .join(rentManagerEntity)
                        .on(rentHistoryEntity.managerNo.eq(rentManagerEntity.managerNo))
                        .where(
                                rentManagerEntity.overdueFlg.eq(false),
                                rentHistoryEntity.haveToReturnDt.lt(nowDt),
                                rentHistoryEntity.returnDt.isNull(),
                                rentHistoryEntity.rentState.eq(RentState.ON_RENT),
                                ltHistoryNo(lastHistoryNo)
                        )
                        .orderBy(rentHistoryEntity.historyNo.desc()));
    }

    @Bean
    public ItemProcessor<RentHistoryEntity,RentHistoryEntity> overdueRentHistoryRegProcessor(){
        return item -> {
            log.info("historyNo[{}] overdue reg" ,item.getHistoryNo());
            item.setRentState(RentState.ON_OVERDUE);
            return item;
        };
    }

    @Bean
    public JpaItemWriter<RentHistoryEntity> overdueRentHistoryRegWriter(){
        JpaItemWriter<RentHistoryEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
