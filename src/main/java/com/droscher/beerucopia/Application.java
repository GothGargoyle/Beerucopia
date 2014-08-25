package com.droscher.beerucopia;



import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import javax.annotation.Resource;
import java.util.List;

/**
 * Application.
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan
public class Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @Parameter(names = "--import", description = "Import the given documents")
    private List<String> imports;

    @Parameter(names = "--help", help = true, description = "Display this message")
    private boolean help;


    @Bean
    public JobRegistryBeanPostProcessor registerJobs() {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    public static void main(final String[] args) {
        new SpringApplicationBuilder(Application.class)
                .logStartupInfo(false)
                .showBanner(false)
                .properties("spring.batch.job.enabled=false")
                .run(args);
	}

    @Override
    public void run(String... args) throws Exception {

        JCommander jCommander = new JCommander(this, args);
        jCommander.setProgramName("beerucopia");

        if (help) {
            jCommander.usage();
            System.exit(0);
        }

        if (imports != null) {
            for (String importDocument : imports) {
                LOG.info("Processing import for " + importDocument);
                if (jobRegistry.getJobNames().isEmpty()) {
                    LOG.error("No jobs found!!");
                    System.exit(1);
                }
                for (String jobName : jobRegistry.getJobNames()) {
                    LOG.info("Checking job " + jobName);
                    if (jobName.startsWith(importDocument)) {
                        LOG.info("Running job " + jobName);
                        jobLauncher.run(jobRegistry.getJob(jobName), new JobParameters());
                    }
                }
            }
        }
    }


}
