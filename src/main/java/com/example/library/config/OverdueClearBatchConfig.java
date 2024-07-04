package com.example.library.config;

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
public class OverdueClearBatchConfig {
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

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
    public JpaPagingItemReader<RentManagerEntity> overdueRentClearManagerReader(@Value("#{jobParameters[nowDt]}") String nowDt){
        log.info("rentManagerReader 처리");

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
        reader.setName("overdueRentManagerReader");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setParameterValues(parameterMap);
        reader.setQueryString(
                "select c from RentManagerEntity c " +
                "where managerNo in ( " +
                        "select  managerNo From RentHistoryEntity " +
                        "where managerNo in ( " +
                                "select rm.managerNo from RentManagerEntity rm " +
                                "where rm.overdueFlg = true " +
                                "and not exists " +
                                        "(" +
                                                "select 1 from RentHistoryEntity rh " +
                                                "where rm.managerNo = rh.managerNo "  +
                                                "and rentState= 1 "  +
		                                    ") " +
	                                ") "+
                            "and rentState=3 " +
                            "group by managerNo " +
                            "having date_Add(str_to_date(max(returnDt),'%Y%m%d'),interval +7 Day) =  date_format(now(), '%Y-%m-%d') " +
                 ")"
        );

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
