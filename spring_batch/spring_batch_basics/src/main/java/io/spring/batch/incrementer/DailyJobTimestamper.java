package io.spring.batch.incrementer;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.Date;

/**
 * 잡 실행 시마다 타임스탬프를 파라미터로 사용하는 방법
 */
public class DailyJobTimestamper implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder()
                .addDate("currentDate", new Date())
                .toJobParameters();
    }

}
