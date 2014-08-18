package com.droscher.beerucopia.data.mongo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by simon on 2014-08-18.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableBatchProcessing
public class ImportStylesApplication {

    @Autowired
    private StyleRepository styleRepository;

    @Bean
    public ItemReader<Style> reader() {
        FlatFileItemReader<Style> reader = new FlatFileItemReader<Style>();
        reader.setResource(new ClassPathResource("styles.csv"));

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"name", "key", "ratebeerId"});

        BeanWrapperFieldSetMapper<Style> fieldSetMapper = new BeanWrapperFieldSetMapper<Style>();
        fieldSetMapper.setTargetType(Style.class);

        DefaultLineMapper<Style> lineMapper = new DefaultLineMapper<Style>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemWriter<Style> writer() {
        RepositoryItemWriter<Style> writer = new RepositoryItemWriter<Style>();
        writer.setRepository(styleRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public static Job importStylesJob(final JobBuilderFactory jobs, final Step step) {
        return jobs.get("importStylesJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(step)
                    .end()
                    .build();
    }

    @Bean
    public static Step step(final StepBuilderFactory stepBuilderFactory,
                     final ItemReader<Style> reader,
                     final ItemWriter<Style> writer) {
        return stepBuilderFactory.get("step")
                    .<Style, Style> chunk(10)
                    .reader(reader)
                    .writer(writer)
                    .build();
    }

    public static void main(final String[] args) {
        SpringApplication.run(ImportStylesApplication.class, args);
    }
}
