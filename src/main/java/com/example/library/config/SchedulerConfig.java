package com.example.library.config;

import com.example.library.annotation.Timer;
import com.example.library.config.batch.EmailRetryBatchConfig;
import com.example.library.config.batch.OverdueClearBatchConfig;
import com.example.library.config.batch.OverdueRegBatchConfig;
import com.example.library.config.batch.OverdueRentHistoryRegBatchConfig;
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
    private final OverdueRegBatchConfig overdueRegBatchConfig;
    private final OverdueClearBatchConfig overdueClearBatchConfig;
    private final EmailRetryBatchConfig emailRetryBatchConfig;
    private final OverdueRentHistoryRegBatchConfig overdueRentHistoryRegBatchConfig;
    private final PlatformTransactionManager manager;
    private final JobRepository jobRepository;

    @Timer
    @Scheduled(cron = "0 * * * * *" , zone = "Asia/Seoul") //1분마다
    public void overDueRegScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("연체 대상 등록 스케줄러 start");

        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", DateUtil.getDate())
                .addLong("time",new Date().getTime()) //여러번 돌수 있게 세팅
                .toJobParameters();
        jobLauncher.run(overdueRegBatchConfig.overDueRegJob(manager,jobRepository),jobParameters);

        log.info("연체 대상 등록 스케줄러 end");
    }

    @Timer
    @Scheduled(cron = "0 * * * * *" , zone = "Asia/Seoul") //1분마다
    public void overDueClearScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("연체 대상 해제 스케줄러 start");

        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", DateUtil.getDate())
                .addLong("time",new Date().getTime()) //여러번 돌수 있게 세팅
                .toJobParameters();
        jobLauncher.run(overdueClearBatchConfig.overdueClearJob(manager,jobRepository),jobParameters);

        log.info("연체 대상 해제 스케줄러 end");
    }

    @Timer
    @Scheduled(cron = "0 * * * * *" , zone = "Asia/Seoul") //1분마다
    public void overdueRentHistoryRegScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("도서히스토리 예정반납일 연체등록 스케줄러 start");

        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", DateUtil.getDate())
                .addLong("time",new Date().getTime()) //여러번 돌수 있게 세팅
                .toJobParameters();
        jobLauncher.run(overdueRentHistoryRegBatchConfig.overdueRentHistoryRegJob(manager, jobRepository), jobParameters);

        log.info("도서히스토리 예정반납일 연체등록 스케줄러 end");
    }

    @Timer
    @Scheduled(cron = "0 18 * * * *" , zone = "Asia/Seoul") //1분마다
    public void emailRetryScheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("이메일 재발송 스케줄러 start");

        JobParameters jobParameters= new JobParametersBuilder()
                .addString("nowDt", DateUtil.getDate())
                .addLong("time",new Date().getTime())//여러번 돌수 있게 세팅
                .toJobParameters();
        jobLauncher.run(emailRetryBatchConfig.emailRetryJob(manager,jobRepository),jobParameters);

        log.info("이메일 재발송 스케줄러 end");
    }
}
