package com.javatechie.spring.batch.config;


import com.javatechie.spring.batch.entity.customerReport;
import com.javatechie.spring.batch.model.Customer;
import com.javatechie.spring.batch.repository.CustomerReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private CustomerReportRepository customerReportRepository;

    private DataSource dataSource;

    private CustomerWriter customerWriter;

    @Bean
    public JdbcPagingItemReader<Customer> pagingItemReader(){
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(1);
        reader.setRowMapper(new CustomerRowMapper());

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.DESCENDING);

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("select id, firstName, lastName");
        queryProvider.setFromClause("from customer");
        queryProvider.setSortKeys(sortKeys);


        reader.setQueryProvider(queryProvider);

        return reader;
    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }



    @Bean
    public ItemWriter<customerReport> customerItemWriter(){
        System.out.print("customerItemWriter");
        RepositoryItemWriter<customerReport> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerReportRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    @Qualifier("transactionReport")
    public Job transactionReport() {
        return jobBuilderFactory.get("transactionReport")
                .flow(transactionReportStep1()).end().build();

    }
    @Bean
    @Qualifier("linkingReport")
    public Job linkingReport() {
        return jobBuilderFactory.get("linkingReport")
                .flow(linkingReportStep1()).end().build();

    }

    @Bean
    @Qualifier("refundReport")
    public Job refundReport() {
        return jobBuilderFactory.get("refundReport")
                .flow(refundReportStep1()).end().build();

    }

    @Bean
    public Step transactionReportStep1() {
        return stepBuilderFactory.get("csv-step").<Customer, customerReport>chunk(10)
                .reader(pagingItemReader())
                .processor(processor())
                .writer(customerItemWriter())
                // .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step linkingReportStep1() {
        return stepBuilderFactory.get("csv-step").<Customer, customerReport>chunk(10)
                .reader(pagingItemReader())
                .processor(processor())
                .writer(customerItemWriter())
                // .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step refundReportStep1() {
        return stepBuilderFactory.get("csv-step").<Customer, customerReport>chunk(10)
                .reader(pagingItemReader())
                .processor(processor())
                .writer(customerItemWriter())
                // .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

}
