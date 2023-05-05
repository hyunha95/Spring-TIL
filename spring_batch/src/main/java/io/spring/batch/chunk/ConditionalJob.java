package io.spring.batch.chunk;

import io.spring.batch.decider.RandomDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

/**
 * 조건에 따라 실행될 스텝 지정하기
 */
public class ConditionalJob {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet passTasklet() {
        return (stepContribution, chunkContext) -> RepeatStatus.FINISHED;
    }

    @Bean
    public Tasklet successTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Success!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet failTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Failure!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("conditionalJob")
                .start(firstStep())
                .on("FAILED").to(failureStep())
                .from(firstStep()).on("*").to(successStep())
                .end()
                .build();
    }

    @Bean
    public Job job1() {
        return this.jobBuilderFactory.get("conditionalJob")
                .start(firstStep())
                .next(decider())
                .from(decider())
                .on("FAILED").to(failureStep())
                .from(decider())
                .on("*").to(successStep())
                .end()
                .build();
    }

    @Bean
    public Job job2() {
        return this.jobBuilderFactory.get("conditionalJob")
                .start(firstStep())
//                .on("FAILED").end() // Completed 상태로 잡을 종료하게 된다.
//                .on("FAILED").fail() // Failed 상태로 잡을 종료하게 된다.
                .on("FAILED").stopAndRestart(successStep())
                .from(firstStep()).on("*").to(successStep())
                .end()
                .build();
    }

    @Bean
    public Step firstStep() {
        return this.stepBuilderFactory.get("firstStep")
                .tasklet(passTasklet())
                .build();
    }

    @Bean
    public Step successStep() {
        return this.stepBuilderFactory.get("successStep")
                .tasklet(successTasklet())
                .build();
    }

    @Bean
    public Step failureStep() {
        return this.stepBuilderFactory.get("failureStep")
                .tasklet(failTasklet())
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new RandomDecider();
    }
}
