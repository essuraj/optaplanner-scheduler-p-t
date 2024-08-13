package com.sc.therapist_appointments;

import ai.timefold.solver.core.api.score.stream.ConstraintStreamImplType;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.Schedule;
import com.sc.therapist_appointments.domain.Timeslot;
import com.sc.therapist_appointments.domain.actors.Patient;
import com.sc.therapist_appointments.domain.actors.Therapist;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class DemoData {
   static SolverFactory<Schedule> solverFactory = SolverFactory.create(new SolverConfig()
            .withSolutionClass(Schedule.class)
            .withEntityClasses(Appointment.class)
           .withConstraintStreamImplType(ConstraintStreamImplType.BAVET)

           .withConstraintProviderClass(AppointmentConstraintProvider.class)
            // The solver runs only for 5 seconds on this small dataset.
            // It's recommended to run for at least 5 minutes ("5m") otherwise.
            .withTerminationSpentLimit(Duration.ofSeconds(5)));

    public static Schedule generateDemoData() {
        Patient patient1 = new Patient("Patient 1", "Speech Therapy", "BLR", 5);
        Patient patient2 = new Patient("Patient 2", "Occupational Therapy", "HYD", 3);
        Patient patient3 = new Patient("Patient 3", "Occupational Therapy", "BLR", 2);

        Therapist therapist1 = new Therapist("Therapist 1", Arrays.asList(LocalDateTime.of(2021, 1, 1, 9, 0), LocalDateTime.of(2021, 1, 1, 10, 0)), "Location A", List.of("Speech Therapy"));
        Therapist therapist2 = new Therapist("Therapist 2", Arrays.asList(LocalDateTime.of(2021, 1, 1, 9, 0), LocalDateTime.of(2021, 1, 1, 10, 0)), "Location B", List.of("Occupational Therapy"));

        Schedule schedule = new Schedule(null, null, null);
        schedule.setTherapistList(Arrays.asList(therapist1, therapist2));
        schedule.setTimeslotList(
                Arrays.asList(
                        new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0),  LocalTime.of(10, 0))));
        schedule.setAppointmentList(Arrays.asList());
//                new Appointment(1, therapist1, patient1, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))),
//                new Appointment(2, therapist2, patient2, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))),
//                new Appointment(3, therapist2, patient3, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)))

//        )
//        );
        return schedule;
    }

    public static Appointment[] runSolver() {


        // OptaPlanner configuration
//    SolverFactory<Schedule> solverFactory = SolverFactory.createFromXmlResource("solverConfig.xml");
        Solver<Schedule> solver = solverFactory.buildSolver();
        Schedule unsolvedSchedule = DemoData.generateDemoData(); // Initialize with patients, therapists, and empty appointments
        Schedule solvedSchedule = solver.solve(unsolvedSchedule);

        // Extract the next available appointment
        var nextAvailableAppointment = solvedSchedule.getAppointmentList().toArray();
//                .filter(appointment -> appointment.getPatient() == null)
//                .findFirst()
//                .orElse(null);
        return (Appointment[]) nextAvailableAppointment;
//        if (nextAvailableAppointment != null) {
//            System.out.println("Next available appointment: " + nextAvailableAppointment);
//            return nextAvailableAppointment;
//        } else {
//            System.out.println("No available appointments found.");
//            return new Appointment()[];
//        }
    }
}
