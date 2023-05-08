package io.spring.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@EnableBatchProcessing
@SpringBootApplication
public class CompleteGuideRestApiApplication {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step 1 ran today!");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @RestController
    public static class JobLaunchingController {

        @Autowired
        private JobLauncher jobLauncher;

        @Autowired
        private ApplicationContext context;

        @Autowired
        private JobExplorer jobExplorer;

        @PostMapping("/run")
        public ExitStatus runJob(@RequestBody JobLaunchRequest request) throws Exception {
            Job job = this.context.getBean(request.getName(), Job.class);

            // JobParametersBuilder.getNextJobParameters(job) 메서드를 호출하면 run.id라는 파라미터가 추가된 새로운 JobParameters 인스턴가 생성된다.
            // getNextJobParameters 메서드는 Job이 JobParametersIncrementer를 가지고 있는지 해당 job을 보고서 판별한다. JobParametersIncrementer를 가지고 있다면
            // 마지막 JobExecution에 사용됐던 JobParamters에 적용한다.
            JobParameters jobParameters = new JobParametersBuilder(request.getJobParameters(), this.jobExplorer)
                    .getNextJobParameters(job)
                    .toJobParameters();

            return this.jobLauncher.run(job, jobParameters).getExitStatus();
        }
    }

    public static class JobLaunchRequest {
        private String name;

        private Properties jobParameters;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Properties getJobParamsProperties() {
            return jobParameters;
        }

        public void setJobParamsProperties(Properties jobParameters) {
            this.jobParameters = jobParameters;
        }

        public JobParameters getJobParameters() {
            Properties properties = new Properties();
            properties.putAll(this.jobParameters);
            return new JobParametersBuilder(properties).toJobParameters();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CompleteGuideRestApiApplication.class, args);
    }

}
