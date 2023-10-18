package com.example.log4j2sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class Log4jSampleApplication implements ApplicationRunner {
	private static final Logger logger = LogManager.getLogger(Log4jSampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(Log4jSampleApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		/*logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("This is a warning!");
		logger.error("Oops! We have an Error. OK");
		logger.fatal("Fatal error. Please fix me.");*/
	}
}
