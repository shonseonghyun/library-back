package com.example.library.config.batch;

import com.example.library.annotation.Timer;
import com.example.library.global.mail.domain.mail.application.MailService;
import com.example.library.global.mail.domain.mail.application.dto.MailDto;
import com.example.library.global.mail.domain.mail.domain.mailHistory.MailHistoryEntity;
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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EmailRetryBatchConfig {
    private final EntityManagerFactory entityManagerFactory;
    private final MailService mailService;
    private int chunkSize=1;

    @Timer
    @Bean
    public Job emailRetryJob(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new JobBuilder("emailRetryJob",jobRepository)
                .start(emailRetryStep(transactionManager,jobRepository))
                .build()
                ;
    }

    public Step emailRetryStep(PlatformTransactionManager transactionManager, JobRepository jobRepository){
        return new StepBuilder("emailRetryStep",jobRepository)
                .<MailHistoryEntity,MailHistoryEntity>chunk(chunkSize,transactionManager)
                .reader(emailRetryReader(null))
                .processor(emailRetryProcessor())
                .writer(emailRetryWriter())
                .build()
                ;
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<MailHistoryEntity> emailRetryReader(@Value("#{jobParameters[nowDt]}") String nowDt){
        log.info("emailRetryReader start");

        //override 통한 문제 해결
        JpaPagingItemReader<MailHistoryEntity> reader = new JpaPagingItemReader<MailHistoryEntity>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        //쿼리 내 매개변수 저장
        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("nowDt",nowDt);

        reader.setPageSize(chunkSize);
        reader.setParameterValues(parameterMap);
        reader.setName("emailRetryReader");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select m from MailHistoryEntity m where createdDt=:nowDt and flg='X' order by historyNo");
        return reader;
    }

    @Bean
    public ItemProcessor<MailHistoryEntity,MailHistoryEntity> emailRetryProcessor(){
        log.info("emailRetryProcessor start");
        return item -> {
            log.info("이메일 재발송 처리");
            log.info("HistoryNo {}, ",item.getHistoryNo());
            //이메일 발송
            try{
                mailService.send(new MailDto(item.getEmail(),item.getMailType(),item.getContent()));
            }catch (MailException e){
                log.info("이메일 발송 실패");
                item.sendFail();
            }

            log.info("이메일 발송 성공");
            item.sendSuccess();

            return item;
        };
    }

    @Bean
    public ItemWriter<MailHistoryEntity> emailRetryWriter(){
        log.info("emailRetryWriter start");

        JpaItemWriter<MailHistoryEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);

        return writer;
        //        return list->{
        //            for(MailHistoryEntity mailHistory:list){
        //                log.info(mailHistory.toString());
        //            }
        //        };
    }


}
