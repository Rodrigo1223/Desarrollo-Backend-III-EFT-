package com.duoc.migracion.config;

import com.duoc.migracion.dto.TransaccionDiariaCsvDto;
import com.duoc.migracion.model.TransaccionDiariaEntity;
import com.duoc.migracion.processor.TransaccionDiariaItemProcessor;
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
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class TransaccionDiariaBatchConfig {

    @Bean
    public FlatFileItemReader<TransaccionDiariaCsvDto> readerDiario() {
        FlatFileItemReader<TransaccionDiariaCsvDto> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data/movimientos_financieros_diarios.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<TransaccionDiariaCsvDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // Corregido: Coincide exactamente con las 4 columnas reales del archivo CSV (id, fecha, monto, tipo)
        tokenizer.setNames("id", "fecha", "monto", "tipo");

        BeanWrapperFieldSetMapper<TransaccionDiariaCsvDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TransaccionDiariaCsvDto.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public JpaItemWriter<TransaccionDiariaEntity> writerDiario(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<TransaccionDiariaEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step stepDiario(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                           FlatFileItemReader<TransaccionDiariaCsvDto> readerDiario,
                           TransaccionDiariaItemProcessor processor,
                           JpaItemWriter<TransaccionDiariaEntity> writerDiario) {
        return new StepBuilder("stepDiario", jobRepository)
                .<TransaccionDiariaCsvDto, TransaccionDiariaEntity>chunk(100, transactionManager)
                .reader(readerDiario)
                .processor(processor)
                .writer(writerDiario)
                .faultTolerant()
                .skipLimit(50)
                .skip(Exception.class)
                .build();
    }

    @Bean
    public Job importTransaccionDiariaJob(JobRepository jobRepository, Step stepDiario) {
        return new JobBuilder("importTransaccionDiariaJob", jobRepository)
                .start(stepDiario)
                .build();
    }
}