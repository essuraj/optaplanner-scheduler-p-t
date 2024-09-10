package com.sc.therapist_appointments;

import com.google.ortools.Loader;
import com.sc.therapist_appointments.or.OrSolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@SpringBootTest
public class OrTest {

    @Test
    void startTest(){
        Loader.loadNativeLibraries();

        OrSolver orSolver = new OrSolver();
        orSolver.scheduleAppointment();
     }
}
