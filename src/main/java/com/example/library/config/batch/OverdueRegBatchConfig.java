package com.example.library.config.batch;

import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import com.example.library.global.event.Events;
import com.example.library.global.mail.domain.mail.application.event.SendedMailEvent;
import com.example.library.global.mail.domain.mail.domain.mailHistory.MailHistoryEntity;
import com.example.library.global.mail.domain.mail.enums.MailType;
import com.example.library.global.mail.domain.mail.application.dto.MailDto;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
//@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "overDueRegJob")
public class OverdueRegBatchConfig {
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job overDueRegJob(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new JobBuilder("overDueRegJob",jobRepository)
                .start(overDueRegStep(transactionManager,jobRepository))
                .build()
                ;
    }

    public Step overDueRegStep(PlatformTransactionManager transactionManager,JobRepository jobRepository){
        return new StepBuilder("overDueRegStep",jobRepository)
                .<RentManagerEntity, RentManagerEntity>chunk(chunkSize,transactionManager)
                .reader(rentManagerReader(null))
                .processor(rentManagerProcessor())
                .writer(rentManagerWriter())
                .build()
                ;
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<RentManagerEntity> rentManagerReader(@Value("#{jobParameters[nowDt]}") String nowDt){
        log.info("rentManagerReader start");

        //override 통한 문제 해결
        JpaPagingItemReader<RentManagerEntity> reader = new JpaPagingItemReader<RentManagerEntity>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        Map<String,Object> parameterMap =new HashMap<String,Object>();
        parameterMap.put("nowDt",nowDt);
        reader.setPageSize(chunkSize);
        reader.setName("jpaPagingItemReader");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setParameterValues(parameterMap);
        reader.setQueryString(
                "select c from RentManagerEntity c where userNo in ( select distinct(userNo) from RentHistoryEntity where rentState=0 and haveToReturnDt< :nowDt) and overdueFlg=false order by c.managerNo");
        return reader;
    }

    @Bean
    public ItemProcessor<RentManagerEntity,RentManagerEntity> rentManagerProcessor(){
        log.info("rentManagerProcessor start");
        return item -> {
            log.info("process 처리!!");
            item.setOverdueFlg(true);
            Events.raise(new SendedMailEvent(new MailDto(item.getUserNo(), MailType.MAIL_OVERDUE_REQ)));
            return item;
        };
    }

    @Bean
    public JpaItemWriter<RentManagerEntity> rentManagerWriter(){
        log.info("rentManagerWriter start");
        JpaItemWriter<RentManagerEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
