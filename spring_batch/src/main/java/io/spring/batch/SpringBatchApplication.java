package io.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet(helloWorldTasklet())
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
    public Tasklet helloWorldTasklet(@Value("#{jobParameters['name']}") String name) {
        return (stepContribution, chunkContext) -> {
            System.out.println(String.format("Hello, %s!", name));
            return RepeatStatus.FINISHED;
        };
    }



    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

}
