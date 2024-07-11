package com.example.library.config.batch;

import com.example.library.config.batch.custom.reader.CustomJpaPagingItemReader;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import com.example.library.global.event.Events;
import com.example.library.global.mail.domain.mail.application.dto.MailDto;
import com.example.library.global.mail.domain.mail.application.event.SendedMailEvent;
import com.example.library.global.mail.domain.mail.enums.MailType;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OverdueClearBatchConfig {
    private final EntityManagerFactory entityManagerFactory;
    private final RentRepository rentRepository;

    private int chunkSize = 2; //pagesize

    @Bean
    public Job overdueClearJob(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new JobBuilder("overdueClearJob",jobRepository)
                .start(overdueClearStep(transactionManager,jobRepository))
                .build()
                ;
    }

    public Step overdueClearStep(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new StepBuilder("overdueClearStep",jobRepository)
                .<RentManagerEntity, RentManagerEntity>chunk(chunkSize,transactionManager)
                .reader(overdueRentClearManagerReader(null))
                .processor(overdueRentClearManagerProcessor())
                .writer(overdueRentClearManagerWriter())
                .build()
                ;
    }

    @Bean
    @StepScope
    public CustomJpaPagingItemReader overdueRentClearManagerReader(@Value("#{jobParameters[nowDt]}") String nowDt){
        log.info("rentManagerReader 처리");
        CustomJpaPagingItemReader reader =  new CustomJpaPagingItemReader(rentRepository,chunkSize);
        return reader;
    }

    @Bean
    public ItemProcessor<RentManagerEntity,RentManagerEntity> overdueRentClearManagerProcessor(){
        log.info("rentManagerProcessor 처리");
        return item -> {
            log.info(item.toString());
            log.info("ItemProcessor 처리 진행");
            item.setOverdueFlg(false);
            Events.raise(new SendedMailEvent(new MailDto(item.getUserNo(), MailType.MAIL_OVERDUE_CLEAR)));
            return item;
        };
    }

    @Bean
    public JpaItemWriter<RentManagerEntity> overdueRentClearManagerWriter(){
        log.info("rentManagerWriter start");
        JpaItemWriter<RentManagerEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
