package com.droscher.beerucopia.importer;

import com.droscher.beerucopia.domain.Style;
import com.droscher.beerucopia.repository.StyleRepository;
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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by simon on 2014-08-18.
 */
@Configuration
public class ImportStyles {

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public ItemReader<Style> styleReader() {
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
    public ItemWriter<Style> styleWriter() {
        RepositoryItemWriter<Style> writer = new RepositoryItemWriter<Style>();
        writer.setRepository(styleRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Job importStylesJob() {
        return jobs.get("styles")
                    .incrementer(new RunIdIncrementer())
                    .flow(styleStep())
                    .end()
                    .build();
    }

    @Bean
    public Step styleStep() {
        return stepBuilderFactory.get("step")
                    .<Style, Style> chunk(10)
                    .reader(styleReader())
                    .writer(styleWriter())
                    .build();
    }

}
