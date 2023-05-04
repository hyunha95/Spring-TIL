package io.spring.batch;

import io.spring.batch.incrementer.DailyJobTimestamper;
import io.spring.batch.listener.JobLoggerListener;
import io.spring.batch.validator.ParameterValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("basicJob")
                .start(step1()) // 시작
                .validator(validator()) // 검증
                .incrementer(new DailyJobTimestamper()) // 잡 파라미터 증가시키기
                .listener(new JobLoggerListener()) // 잡 리스너 적용
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet(helloWorldTasklet(null, null))
                .build();
    }

    // 1. 파라미터 접근 - ChunkContext
/*
    @Bean
    public Tasklet helloWorldTasklet() {
        return (stepContribution, chunkContext) -> {
            // 실행 시 전달한 파라미터에 접근하는 코드
            String name = (String) chunkContext.getStepContext()
                    .getJobParameters()
                    .get("name");

            System.out.println(String.format("Hello, %s!", name));
            return RepeatStatus.FINISHED;
        }
    };
}*/

    // 2. 파라미터 접근 - 늦은 바인딩
    // 늦은 바인딩으로 구성될 빈은 스텝이나 잡 스코프를 가져야 한다.
    /*
       이 스코프 각각의 기능은 스텝의 실행 범위(스텝 스코프)나 잡의 실행 범위(잡 스코프)에
       들어갈 때까지 빈 생성을 지연시키는 것이다. 이렇게 함으로써 명령행 또는 다른 소스에서
       받아들인 잡 파라미터를 빈 생성 시점에 주입할 수 있다.
     */
    @StepScope
    @Bean
    public Tasklet helloWorldTasklet(@Value("#{jobParameters['name']}") String name,
                                     @Value("#{jobParameters['fileName']}") String fileName) {
        return (stepContribution, chunkContext) -> {
            System.out.println(String.format("Hello, %s!", name));
            System.out.println(String.format("fileName = %s", fileName));
            return RepeatStatus.FINISHED;
        };
    }

    /**
     * 옵션 키가 구성된 경우: 필수 키와 옵션 키 외의 다른 파라미터 변수가 전달되면 유효성 검증에 실패한다.
     * 옵션 키가 구성되지 않은 경우: 필수 키를 전달하기만 하면 그 외 어떤 키의 조합을 전달하더라도 유효성 검증을 통과한다.
     *
     * DefaultJobParametersValidator는 파라미터 존재 여부를 제외한 다른 유효성 검증을 수행하지는 않는다.
     * 더 강력한 유효성 검증이 필요하다면 JobParametersValidator를 용도에 맞게 직접 구현해야 한다.
     */
    @Bean
    public JobParametersValidator validator1() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();

        validator.setRequiredKeys(new String[]{"fileName"});
        validator.setOptionalKeys(new String[]{"name"});

        return validator;
    }

    /**
     * 두 개의 유효성 검증기를 사용하고 싶지만 유효성 검증기 구성에 사용하는 JobBuilder의 메서드는 하나의 JobParameterValidator
     * 인스턴스만 지정하게 돼 있다. 다행히 스프링 배치는 이런 사례에 사용할 수 있는 CompositeJobParametersValidator를 제공한다.
     */
    @Bean
    public CompositeJobParametersValidator validator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        DefaultJobParametersValidator defaultJobParametersValidator =
                new DefaultJobParametersValidator(
                        new String[] {"fileName"},
                        new String[] {"name", "currentDate"}); // RunIdIncrementer를 사용할 경우 run.id를 추가해야 한다.

        defaultJobParametersValidator.afterPropertiesSet();

        validator.setValidators(Arrays.asList(new ParameterValidator(), defaultJobParametersValidator));

        return validator;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

}
