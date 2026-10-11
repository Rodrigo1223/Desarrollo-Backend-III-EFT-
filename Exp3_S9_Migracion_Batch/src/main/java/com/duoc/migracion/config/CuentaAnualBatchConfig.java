package com.duoc.migracion.config;

import com.duoc.migracion.dto.CuentaAnualCsvDto;
import com.duoc.migracion.model.CuentaAnualEntity;
import com.duoc.migracion.processor.CuentaAnualItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class CuentaAnualBatchConfig {

    @Bean
    public FlatFileItemReader<CuentaAnualCsvDto> readerAnual() {
        FlatFileItemReader<CuentaAnualCsvDto> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data/movimientos_financieros_anuales.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<CuentaAnualCsvDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // CORRECCIÓN AQUÍ: "cuentaId" en lugar de "cuenta_id" para que coincida con el DTO
        tokenizer.setNames("cuentaId", "fecha", "transaccion", "monto", "descripcion");

        BeanWrapperFieldSetMapper<CuentaAnualCsvDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CuentaAnualCsvDto.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public JpaItemWriter<CuentaAnualEntity> writerAnual(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<CuentaAnualEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("BatchThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Step stepAnual(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                          FlatFileItemReader<CuentaAnualCsvDto> readerAnual,
                          CuentaAnualItemProcessor processor,
                          JpaItemWriter<CuentaAnualEntity> writerAnual,
                          TaskExecutor taskExecutor) {
        return new StepBuilder("stepAnual", jobRepository)
                .<CuentaAnualCsvDto, CuentaAnualEntity>chunk(100, transactionManager)
                .reader(readerAnual)
                .processor(processor)
                .writer(writerAnual)
                .taskExecutor(taskExecutor)
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                .skipLimit(100)
                .skip(IllegalArgumentException.class)
                .build();
    }

    @Bean
    public Job importCuentaAnualJob(JobRepository jobRepository, Step stepAnual) {
        return new JobBuilder("importCuentaAnualJob", jobRepository)
                .start(stepAnual)
                .build();
    }
}