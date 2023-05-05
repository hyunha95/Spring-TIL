package io.spring.batch.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

public class FlowJob {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet loadStockFile() {
        return (stepContribution, chunkContext) -> {
            System.out.println("This stock file has benn loaded");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet loadCustomerFile() {
        return (stepContribution, chunkContext) -> {
            System.out.println("This start has been updated");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet updateStart() {
        return (stepContribution, chunkContext) -> {
            System.out.println("The start has benen updated!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet runBatchTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("The batch has been run");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Flow preProcessingFlow() {
        return new FlowBuilder<Flow>("preProcessingFlow").start(loadFileStep())
                .next(loadCustomerStep())
                .next(updateStartStep())
                .build();
    }

    @Bean
    Job preProcessingJob() {
        return this.jobBuilderFactory.get("preProcessingJob")
                .start(loadFileStep())
                .next(loadCustomerStep())
                .next(updateStartStep())
                .build();
    }

    /**
     * 1 .플로우를 잡에서 참조하는 방법
     */
    @Bean
    public Job conditionalStepLogicJob() {
        return this.jobBuilderFactory.get("conditionalStepLoginJob")
                .start(preProcessingFlow())
                .next(runBatch())
                .end()
                .build();
    }

    @Bean
    public Job conditionalStepLogicJob1() {
        return this.jobBuilderFactory.get("conditionalStepLogicJob1")
//                .start(initializeBatch()) // 플로우 스텝 사용하기
                .start(initializeBatch1()) // 잡 스텝 사용하기
                .next(runBatch())
                .build();
    }

    /**
     * 2. 플로우 스텝 사용하기
     *
     * 플로우를 잡 빌더로 전달하는 것과 플로우 스텝을 사용하는 것의 차이:
     * JobRepository에서 일어나는 일에 차이가 있다. 잡 빌더에서 flow 메소드를 사용하면 잡에
     * 스텝을 구성하는 것과 동일하다. 반면 플로우 스텝을 상용하면 추가적인 항목이
     * 더해진다. 플로우 스텝을 사용하면 스프링 배치는 해당 플로우가 담긴 스텝을 하나의 스텝처럼 기록한다.
     * 이렇게 하면 주된 이점은 모니터링과 리포팅이다. 플로우 스텝을 사용하면 개별 스텝을 집계하지 않고도 플로우의 영향을 전체적으로 볼 수 있다.
     */
    @Bean
    public Step initializeBatch() {
        return this.stepBuilderFactory.get("initializeBatch")
                .flow(preProcessingFlow())
                .build();
    }

    /**
     * 3. 잡 스텝 사용하기
     *
     * JobParametersExtractor: 잡을 구동하면 해당 잡은 잡 이름과 잡 파라미터로 식별된다. 이때 사용자는 서브 잡인
     * preProcessingJob에 해당 파라미터를 직접 전달하지 않는다. 대신 상위 잡의 JobParameters 또는 ExecutionContext
     * (DefaultJobParametersExtractor는 JobParameters와 ExecutionContext 모두 확인함)에서 파라미터를 추출해 하위 잡으로 전달하는 클래스를 정의한다.
     */
    @Bean
    public Step initializeBatch1() {
        return this.stepBuilderFactory.get("initializeBatch")
                .job(preProcessingJob())
                .parametersExtractor(new DefaultJobParametersExtractor())
                .build();
    }



    @Bean
    public Step loadFileStep() {
        return this.stepBuilderFactory.get("loadFileStep")
                .tasklet(loadStockFile())
                .build();
    }

    @Bean
    public Step loadCustomerStep() {
        return this.stepBuilderFactory.get("loadCustomerStep")
                .tasklet(loadCustomerFile())
                .build();
    }

    @Bean
    public Step updateStartStep() {
        return this.stepBuilderFactory.get("updateStartStep")
                .tasklet(updateStart())
                .build();
    }

    @Bean
    public Step runBatch() {
        return this.stepBuilderFactory.get("runBatch")
                .tasklet(runBatchTasklet())
                .build();
    }

}
