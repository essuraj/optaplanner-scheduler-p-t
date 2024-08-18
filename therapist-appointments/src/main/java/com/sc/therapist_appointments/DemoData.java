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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoData {
//    SolverManager<Schedule, String> solverManager;
//    SolutionManager<Schedule, HardSoftBigDecimalScore> solutionManager;

    static SolverFactory<Schedule> solverFactory = SolverFactory.create(new SolverConfig()
            .withSolutionClass(Schedule.class)
            .withEntityClasses(Appointment.class)
            .withConstraintStreamImplType(ConstraintStreamImplType.BAVET)

            .withConstraintProviderClass(AppointmentConstraintProvider.class)
            // The solver runs only for 5 seconds on this small dataset.
            // It's recommended to run for at least 5 minutes ("5m") otherwise.
            .withTerminationSpentLimit(Duration.ofSeconds(10)));

    public static Schedule generateDemoData() {
        List<Timeslot> timeslotList = new ArrayList<>(10);
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(14, 30), LocalTime.of(15, 30)));

        timeslotList.add(new Timeslot(LocalDate.of(2024,9,12), LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,12), LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,12), LocalTime.of(10, 30), LocalTime.of(12, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,12), LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024,9,12), LocalTime.of(14, 30), LocalTime.of(15, 30)));


        Patient patient1 = new Patient("Patient 1", "Speech Therapy", "BLR", 5);
        Patient patient2 = new Patient("Patient 2", "Occupational Therapy", "HYD", 3);
        Patient patient3 = new Patient("Patient 3", "Occupational Therapy", "BLR", 5);
        Patient patient4 = new Patient("Patient 4", "Speech Therapy", "BLR", 2);

        Therapist therapist1 = new Therapist("Therapist 1", Arrays.asList(
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0))), "BLR", List.of("Speech Therapy"));
        Therapist therapist2 = new Therapist("Therapist 2", Arrays.asList(
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0))), "HYD", List.of("Occupational Therapy"));
        Therapist therapist3 = new Therapist("Therapist 3", Arrays.asList(
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0))),
                "BLR", List.of("Speech Therapy","Occupational Therapy"));

        // generate 10 more therapists
        Therapist therapist4 = new Therapist("Therapist 4", Arrays.asList(
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0))), "BLR", List.of("Speech Therapy"));
        Therapist therapist5 = new Therapist("Therapist 5", Arrays.asList(
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0))), "HYD", List.of("Occupational Therapy"));



        Schedule schedule = new Schedule(  null,null, null,null);
        schedule.setTherapistList(Arrays.asList(therapist1, therapist2,therapist3,therapist4,therapist5));
        schedule.setPatientList(Arrays.asList(patient1, patient2,patient3,patient4));
        schedule.setTimeslotList(timeslotList);

        //        schedule.setTimeslotList(
//                Arrays.asList(
//                        new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
//                new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0),  LocalTime.of(10, 0))));
        schedule.setAppointmentList(List.of(new Appointment(1, therapist1,patient1, timeslotList.get(0)),
                new Appointment(2, therapist2, patient2, timeslotList.get(1)),
                new Appointment(3, therapist2, patient3, timeslotList.get(2)),
                new Appointment(4, therapist1, patient4, timeslotList.get(3))
        ));
//                new Appointment(1, therapist1, patient1, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))),
//                new Appointment(2, therapist2, patient2, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))),
//                new Appointment(3, therapist2, patient3, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)))

//        )
//        );
        return schedule;
    }

    public static Appointment runSolver() {


        // OptaPlanner configuration
//    SolverFactory<Schedule> solverFactory = SolverFactory.createFromXmlResource("solverConfig.xml");
        Solver<Schedule> solver = solverFactory.buildSolver();
        Schedule unsolvedSchedule = DemoData.generateDemoData(); // Initialize with patients, therapists, and empty appointments
        Schedule solvedSchedule = solver.solve(unsolvedSchedule);

        // Extract the next available appointment
        Appointment nextAvailableAppointment = solvedSchedule.getAppointmentList().stream()
                .filter(appointment -> appointment.getPatient() == null)
                .findFirst()
                .orElse(null);
        if (nextAvailableAppointment != null) {
            System.out.println("Next available appointment: " + nextAvailableAppointment);
            return nextAvailableAppointment;
        } else {
            System.out.println("No available appointments found.");
            return new Appointment();
        }
    }
}
