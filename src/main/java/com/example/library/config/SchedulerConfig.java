package com.example.library.config;

import com.example.library.global.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;


@RequiredArgsConstructor
@EnableScheduling
@Configuration
@Slf4j
public class SchedulerConfig {


    private final JobLauncher jobLauncher;
    private final JobConfig jobConfig;
    private final EmailRetryBatchConfig emailRetryBatchConfig;
    private final PlatformTransactionManager manager;
    private final JobRepository jobRepository;

    @Scheduled(cron = "0 * * * * *" , zone = "Asia/Seoul") //1분마다
    public void overDueRegScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("연체 대상 등록 스케줄러 start");

        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", DateUtil.getDate())
                .addLong("time",new Date().getTime())//여러번 돌수 있게 세팅
                .toJobParameters();
        jobLauncher.run(jobConfig.overDueRegJob(manager,jobRepository),jobParameters);

        log.info("연체 대상 등록 스케줄러 end");
    }

    @Scheduled(cron = "0 * * * * *" , zone = "Asia/Seoul") //1분마다
    public void emailRetryScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("이메일 재발송 스케줄러 start");

        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", DateUtil.getDate())
                .addLong("time",new Date().getTime())//여러번 돌수 있게 세팅
                .toJobParameters();
        jobLauncher.run(emailRetryBatchConfig.emailRetryJob(manager,jobRepository),jobParameters);

        log.info("이메일 재발송 스케줄러 end");
    }

//    @Scheduled(cron = "0 * * * * *" , zone = "Asia/Seoul") //1분마다
//    public void doTest() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//        log.info("1분마다 실행하는 스케줄러");
////        jobLauncher.run(jobConf2.test(manager,jobRepository));
//        JobParameters jobParameters= new JobParametersBuilder()
//                .addString("nowDt", DateUtil.getDate())
//                .addLong("time",new Date().getTime())//여러번 돌수 있게 세팅
//                .toJobParameters();
//        jobLauncher.run(jobConf2.test(manager,jobRepository),jobParameters);
//    }
}
