package com.droscher.beerucopia;

import com.droscher.beerucopia.importer.ImportStylesApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * Application.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	public static void main(final String[] args) {
		PropertySource cliProperties = new SimpleCommandLinePropertySource(args);
		if (cliProperties.containsProperty("import")) {
			SpringApplication.run(ImportStylesApplication.class, args);
		} else {
			SpringApplication.run(Application.class, args);
		}
	}

}
