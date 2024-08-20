package com.sc.therapist_appointments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MainApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);


    public static void main(String[] args) {
//        SpringApplication.run(MainApplication.class, args);

        LOGGER.info("Starting the application");
        DemoData.runSolver();
    }


}
