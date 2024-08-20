package com.sc.therapist_appointments;

import ai.timefold.solver.core.api.score.stream.ConstraintStreamImplType;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.Schedule;
import com.sc.therapist_appointments.domain.entity.Patient;
import com.sc.therapist_appointments.domain.entity.Therapist;
import com.sc.therapist_appointments.domain.entity.Timeslot;
import com.sc.therapist_appointments.solver.AppointmentConstraintProvider;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class DemoData {

    static SolverFactory<Schedule> solverFactory = SolverFactory.create(new SolverConfig().withSolutionClass(Schedule.class)
                                                                                          .withEntityClasses(Appointment.class)
                                                                                          .withConstraintStreamImplType(
                                                                                                  ConstraintStreamImplType.BAVET)
                                                                                          .withConstraintProviderClass(
                                                                                                  AppointmentConstraintProvider.class)
                                                                                          // The solver runs only for 5 seconds on this small dataset.
                                                                                          // It's recommended to run for at least 5 minutes ("5m") otherwise.
                                                                                          .withTerminationSpentLimit(
                                                                                                  Duration.ofSeconds(15)));

    public static Schedule generateDemoData() {
        List<Timeslot> timeslotList = new ArrayList<>(10);
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(14, 30), LocalTime.of(15, 30)));

        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 12), LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 12), LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 12), LocalTime.of(10, 30), LocalTime.of(12, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 12), LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(LocalDate.of(2024, 9, 12), LocalTime.of(14, 30), LocalTime.of(15, 30)));


        Patient patient1 = new Patient("Patient 1",
                                       "Speech Therapy",
                                       "BLR",
                                       5,
                                       timeslotList.stream().limit(5).collect(Collectors.toList()));
        Patient patient2 = new Patient("Patient 2",
                                       "Occupational Therapy",
                                       "HYD",
                                       1,
                                       timeslotList.stream().limit(1).collect(Collectors.toList()));
        Patient patient3 = new Patient("Patient 3",
                                       "Occupational Therapy",
                                       "BLR",
                                       5,
                                       timeslotList.stream().limit(1).collect(Collectors.toList()));
        Patient patient4 = new Patient("Patient 4",
                                       "Speech Therapy",
                                       "BLR",
                                       2,
                                       timeslotList.stream().limit(8).collect(Collectors.toList()));
        Patient patient5 = new Patient("Patient 5",
                                       "Occupational Therapy",
                                       "HYD",
                                       1,
                                       timeslotList.stream().limit(1).collect(Collectors.toList()));

        Therapist therapist1 = new Therapist("Therapist 1", Arrays.asList(
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0))),
                                             "BLR",
                                             List.of("Speech Therapy"));
        Therapist therapist2 = new Therapist("Therapist 2", Arrays.asList(
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0))), "HYD", List.of("Occupational Therapy"));
        Therapist therapist3 = new Therapist("Therapist 3", Arrays.asList(
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0))),
                "BLR", List.of("Speech Therapy", "Occupational Therapy"));

        // generate 10 more therapists
        Therapist therapist4 = new Therapist("Therapist 4", Arrays.asList(
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0))),
                                             "PGT",
                                             List.of("Speech Therapy"));
        Therapist therapist5 = new Therapist("Therapist 5", Arrays.asList(
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Timeslot(LocalDate.of(2024, 9, 11), LocalTime.of(9, 0), LocalTime.of(10, 0))), "HYD", List.of("Occupational Therapy"));


        Schedule schedule = new Schedule(null, null, null, null);
        schedule.setTherapistList(Arrays.asList(therapist1, therapist2, therapist3, therapist4, therapist5));
        schedule.setPatientList(Arrays.asList(patient1, patient5, patient2, patient3, patient4));
        schedule.setTimeslotList(timeslotList);
        int i = 0;
        schedule.setAppointmentList(schedule.getPatientList().stream().map(Appointment::new).toList());
        //        schedule.setTimeslotList(
//                Arrays.asList(
//                        new Timeslot(LocalDate.of(2024,9,11), LocalTime.of(9, 0), LocalTime.of(10, 0)),
//                new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 0),  LocalTime.of(10, 0))));
//        schedule.setAppointmentList(List.of(new Appointment(1, therapist1, patient1, timeslotList.get(0)),
//                new Appointment(2, therapist2, patient2, timeslotList.get(1)),
//                new Appointment(3, therapist2, patient3, timeslotList.get(2)),
//                new Appointment(4, therapist1, patient4, timeslotList.get(3))
//        ));
//                new Appointment(1, therapist1, patient1, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))),
//                new Appointment(2, therapist2, patient2, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0))),
//                new Appointment(3, therapist2, patient3, new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(12, 30)))

//        )
//        );
        return schedule;
    }

    public static Schedule runSolver() {

//        SolverFactory<Schedule> solverFactory = SolverFactory.create(new SolverConfig()
//                                                                             .withSolutionClass(Schedule.class)
//                                                                             .withEntityClasses(Appointment.class)
//                                                                             .withConstraintStreamImplType(ConstraintStreamImplType.BAVET)
//                                                                             .withConstraintProviderClass(AppointmentConstraintProvider.class)
//                                                                             // The solver runs only for 5 seconds on this small dataset.
//                                                                             // It's recommended to run for at least 5 minutes ("5m") otherwise.
//                                                                             .withTerminationSpentLimit(Duration.ofSeconds(5)));

        // Load the problem
        Schedule problem = DemoData.generateDemoData();

        // Solve the problem
        Solver<Schedule> solver = solverFactory.buildSolver();
        Schedule solvedSchedule = solver.solve(problem);

        // Visualize the solution
        printSchedule(solvedSchedule);

        // Extract the next available appointment

        if (solvedSchedule != null) {
            System.out.println("Next available appointment: " + solvedSchedule);
            return solvedSchedule;
        } else {
            System.out.println("No available appointments found.");
            return new Schedule();
        }
    }

    private static void printSchedule(Schedule schedule) {
        var roomList = schedule.getPatientList();
        List<Appointment> appointmentList = schedule.getAppointmentList();
        System.out.println(appointmentList.size() + " appointments");
        Map<Timeslot, Map<Patient, List<Appointment>>> appointmentMap = appointmentList.stream()
                                                                                       .filter(app -> app.getTimeslot() != null && app.getPatient() != null)
                                                                                       .collect(Collectors.groupingBy(
                                                                                               Appointment::getTimeslot,
                                                                                               Collectors.groupingBy(
                                                                                                       Appointment::getPatient)));
        System.out.println("|            | " + roomList.stream()
                                                       .map(room -> String.format("%-10s", room.getName()))
                                                       .collect(Collectors.joining(" | ")) + " |");
        System.out.println("|" + "------------|".repeat(roomList.size() + 1));
        for (Timeslot timeslot : schedule.getTimeslotList()) {
            List<List<Appointment>> cellList = roomList.stream().map(room -> {
                Map<Patient, List<Appointment>> byRoomMap = appointmentMap.get(timeslot);
                if (byRoomMap == null) {
                    return Collections.<Appointment>emptyList();
                }
                List<Appointment> cellLessonList = byRoomMap.get(room);
                if (cellLessonList == null) {
                    return Collections.<Appointment>emptyList();
                }
                return cellLessonList;
            }).collect(Collectors.toList());

            System.out.println("| " + String.format("%-10s",
                                                    timeslot.toString()
                                                            .substring(0,
                                                                       3) + " " + timeslot.getStartTime()) + " | " + cellList.stream()
                                                                                                                             .map(cellLessonList -> String.format(
                                                                                                                                     "%-10s",
                                                                                                                                     cellLessonList.stream()
                                                                                                                                                   .map(Appointment::getTherapist)
                                                                                                                                                   .map(x -> x.getName())
                                                                                                                                                   .collect(
                                                                                                                                                           Collectors.joining(
                                                                                                                                                                   ", "))))
                                                                                                                             .collect(
                                                                                                                                     Collectors.joining(
                                                                                                                                             " | ")) + " |");
//           System.out.println("|            | "
//                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
//                            cellLessonList.stream().map(Appointment::getTherapist).collect(Collectors.joining(", "))))
//                    .collect(Collectors.joining(" | "))
//                    + " |");
//           System.out.println("|            | "
//                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
//                            cellLessonList.stream().map(Appointment::getPatient).collect(Collectors.joining(", "))))
//                    .collect(Collectors.joining(" | "))
//                    + " |");
            System.out.println("|" + "------------|".repeat(roomList.size() + 1));
        }
        List<Appointment> unassignedLessons = appointmentList.stream()
                                                             .filter(lesson -> lesson.getTimeslot() == null || lesson.getPatient() == null)
                                                             .collect(Collectors.toList());
        System.out.println(unassignedLessons.size() + " unassigned appointments");
        if (!unassignedLessons.isEmpty()) {
            System.out.println();
            System.out.println("Unassigned appointments");
            for (Appointment lesson : unassignedLessons) {
                System.out.println(lesson);
            }
        }
    }
}
