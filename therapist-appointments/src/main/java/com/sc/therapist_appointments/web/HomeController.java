package com.sc.therapist_appointments.web;


import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.Schedule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static com.sc.therapist_appointments.DemoData.generateDemoData;
import static com.sc.therapist_appointments.DemoData.runSolver;

@RestController
public class HomeController {


    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    public Schedule home(@RequestParam(value = "name", defaultValue = "World") String name) {
        return generateDemoData();
    }

    @GetMapping("/run")
    public Schedule run() {
        return runSolver();

    }

}