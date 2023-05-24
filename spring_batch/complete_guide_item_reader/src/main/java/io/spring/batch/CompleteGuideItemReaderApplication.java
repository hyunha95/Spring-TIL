package io.spring.batch;

import io.spring.batch.entity.Customer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

/**
 * https://github.com/AcornPublishing/definitive-spring-batch/tree/main/def-guide-spring-batch-master/Chapter07
 */
@SpringBootApplication
@EnableBatchProcessing
public class CompleteGuideItemReaderApplication {

    /**
     * 고정 너비 파일을 읽는 법
     */
    @Bean
    @StepScope
    public FlatFileItemReader<Customer> customerItemReader(@Value("#{jovParameters['customerFile']}") Resource inputFile) {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .resource(inputFile)
                .fixedLength()
                .columns(new Range[]{new Range(1, 11), new Range(12, 12), new Range(13, 22), new Range(23, 26), new Range(27, 46), new Range(47, 62), new Range(63, 64), new Range(65, 69)})
                .names(new String[]{"firstName", "middleInitial", "lastName", "addressNumber", "street", "city", "state", "zipCode"})
                .targetType(Customer.class)
                .build();
    }


    public static void main(String[] args) {
        SpringApplication.run(CompleteGuideItemReaderApplication.class, args);
    }

}
