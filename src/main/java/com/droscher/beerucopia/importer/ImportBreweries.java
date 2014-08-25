package com.droscher.beerucopia.importer;

import com.droscher.beerucopia.domain.Brewery;
import com.droscher.beerucopia.repository.BreweryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by simon on 2014-08-24.
 */
@Configuration
public class ImportBreweries {

    @Autowired
    private BreweryRepository breweryRepository;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Brewery> breweryReader() {
        FlatFileItemReader<Brewery> reader = new FlatFileItemReader<Brewery>();
        reader.setResource(new ClassPathResource("breweries.csv"));

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"name", "country", "region"});

        BeanWrapperFieldSetMapper<Brewery> fieldSetMapper = new BeanWrapperFieldSetMapper<Brewery>();
        fieldSetMapper.setTargetType(Brewery.class);

        DefaultLineMapper<Brewery> lineMapper = new DefaultLineMapper<Brewery>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemWriter<Brewery> breweryWriter() {
        RepositoryItemWriter<Brewery> writer = new RepositoryItemWriter<Brewery>();
        writer.setRepository(breweryRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Job importBreweriesJob() {
        return jobs.get("breweries")
                .incrementer(new RunIdIncrementer())
                .flow(breweryStep())
                .end()
                .build();
    }

    @Bean
    public Step breweryStep() {
        return stepBuilderFactory.get("breweryStep")
                .<Brewery, Brewery> chunk(10)
                .reader(breweryReader())
                .writer(breweryWriter())
                .build();
    }

}
