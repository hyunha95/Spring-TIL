package io.spring.batch.execution.context;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

public class HelloWorld implements Tasklet {
    private static final String HELLO_WORLD = "Hello, %s";

    @Override
    public RepeatStatus execute(StepContribution step, ChunkContext context) throws Exception {
        String name = (String) context.getStepContext()
                .getJobParameters()
                .get("name");

        // 스텝의 ExecutionContext를 가져오고 싶다면 JobExecution 대신 StepExecution에서 가져온다.
        ExecutionContext jobContext = context.getStepContext()
                                             .getStepExecution()
//                                             .getJobExecution()
                                             .getExecutionContext();

        jobContext.put("user.name", name);

        System.out.println(String.format(HELLO_WORLD, name));
        return RepeatStatus.FINISHED;
    }
}
