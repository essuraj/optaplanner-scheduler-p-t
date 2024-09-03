package com.sc.therapist_appointments;

import ai.timefold.solver.core.api.score.stream.ConstraintStreamImplType;
import ai.timefold.solver.core.api.solver.SolverManager;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.SolverManagerConfig;
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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.sc.therapist_appointments.utils.RandomLatLngGenerator.getFixedLatLngs;

public class DemoData {

    static SolverConfig solverConfig = new SolverConfig().withSolutionClass(Schedule.class)
                                                         .withEntityClasses(Appointment.class)
                                                         .withConstraintStreamImplType(ConstraintStreamImplType.BAVET)
//                                                         .withEnvironmentMode(EnvironmentMode.FULL_ASSERT)

                                                         .withConstraintProviderClass(AppointmentConstraintProvider.class)
                                                         // The solver runs only for 5 seconds on this small dataset.
                                                         // It's recommended to run for at least 5 minutes ("5m") otherwise.
                                                         .withTerminationSpentLimit(Duration.ofSeconds(5));
    static SolverManager<Schedule, UUID> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());

    // method to generate timeslots for in a gap of 15minutes each for a given start and end date
    public static List<Timeslot> generateTimeslots(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<Timeslot> timeslotList = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            LocalTime currentTime = startTime;
            while (!currentTime.isAfter(endTime)) {
                timeslotList.add(new Timeslot(currentDate, currentTime, currentTime.plusHours(1)));
                currentTime = currentTime.plusMinutes(15);
            }
            currentDate = currentDate.plusDays(1);
        }
        return timeslotList;
    }

    public static Schedule generateDemoData() {
        var timeslotList = generateTimeslots(LocalDate.of(2024, 9, 11),
                                             LocalDate.of(2024, 9, 14),
                                             LocalTime.of(8, 0),
                                             LocalTime.of(18, 0));


//        LatLng locs = new LatLng(17.3850, 78.4867); // Hyderabad
        var locs = getFixedLatLngs();
        //RandomLatLngGenerator.generateRandomLatLngs(14);

        Patient patient1 = new Patient("Patient 1",
                                       "Speech Therapy",
                                       locs[0],
                                       5,
                                       timeslotList.stream()
                                                   .filter(x -> x.getDate().getDayOfMonth() == 11 && x.getStartTime()
                                                                                                      .isBefore(
                                                                                                              LocalTime.of(
                                                                                                                      12,
                                                                                                                      00)))
                                                   .toList());
        Patient patient2 = new Patient("Patient 2",
                                       "Depression Therapy",
                                       locs[2],
                                       5,
                                       timeslotList.stream()
                                                   .filter(x -> x.getDate().getDayOfMonth() == 12 && x.getStartTime()
                                                                                                      .isAfter(LocalTime.of(
                                                                                                              12,
                                                                                                              00)))
                                                   .toList());
        Patient patient3 = new Patient("Patient 3",
                                       "Occupational Therapy",
                                       locs[3],
                                       1,
                                       timeslotList.stream()
                                                   .filter(x -> x.getDate().getDayOfMonth() == 11 && x.getStartTime()
                                                                                                      .isBefore(
                                                                                                              LocalTime.of(
                                                                                                                      12,
                                                                                                                      00)))
                                                   .toList());
        Patient patient4 = new Patient("Patient 4",
                                       "Speech Therapy",
                                       locs[4],
                                       2,
                                       timeslotList.stream()
                                                   .filter(x -> x.getDate().getDayOfMonth() == 12 && x.getStartTime()
                                                                                                      .isAfter(LocalTime.of(
                                                                                                              12,
                                                                                                              00)))
                                                   .toList());
        Patient patient5 = new Patient("Patient 5",
                                       "Occupational Therapy",
                                       locs[5],
                                       5,
                                       timeslotList.stream()
                                                   .filter(x -> x.getDate().getDayOfMonth() == 12 && x.getStartTime()
                                                                                                      .isBefore(
                                                                                                              LocalTime.of(
                                                                                                                      18,
                                                                                                                      00)))
                                                   .toList());
        Patient patient6 = new Patient("Patient 6",
                                       "Occupational Therapy",
                                       locs[12],
                                       5,
                                       timeslotList.stream()
                                                   .filter(x -> x.getDate().getDayOfMonth() == 12 && x.getStartTime()
                                                                                                      .isBefore(
                                                                                                              LocalTime.of(
                                                                                                                      18,
                                                                                                                      00)))
                                                   .toList());
        Therapist therapist1 = new Therapist("Therapist 1",
                                             timeslotList.stream()
                                                         .filter(x -> x.getDate().getDayOfMonth() < 15)
                                                         .toList(),
                                             locs[6],
                                             List.of("Speech Therapy"),
                                             5);
        Therapist therapist2 = new Therapist("Therapist 2",
                                             timeslotList.stream()
                                                         .filter(x -> x.getDate().getDayOfMonth() < 15)
                                                         .toList(),
                                             locs[7],
                                             List.of("Occupational Therapy"),
                                             12);
        Therapist therapist3 = new Therapist("Therapist 3",
                                             timeslotList.stream()
                                                         .filter(x -> x.getDate().getDayOfMonth() < 12)
                                                         .toList(),
                                             locs[8],
                                             List.of("Speech Therapy", "Occupational Therapy"),
                                             12);

        // generate 10 more therapists
        Therapist therapist4 = new Therapist("Therapist 4",
                                             timeslotList.stream()
                                                         .filter(x -> x.getDate().getDayOfMonth() < 15)
                                                         .toList(),
                                             locs[9],
                                             List.of("Depression Therapy"),
                                             3);
        Therapist therapist5 = new Therapist("Therapist 5",
                                             timeslotList.stream()
                                                         .filter(x -> x.getDate().getDayOfMonth() < 15)
                                                         .toList(),
                                             locs[10],
                                             List.of("Occupational Therapy"),
                                             15);


        Schedule schedule = new Schedule(null, null, null, null);

        List<Therapist> therapists = Arrays.asList(therapist1, therapist2, therapist3, therapist4, therapist5);
        List<Patient> patients = Arrays.asList(patient1, patient2, patient3, patient4, patient5, patient6);

        patients.forEach(System.out::println);
        therapists.forEach(System.out::println);

        schedule.setTherapistList(therapists);
        schedule.setPatientList(patients);
        schedule.setTimeslotList(timeslotList);

        schedule.setAppointmentList(schedule.getPatientList().stream().map(Appointment::new).toList());

        return schedule;
    }

    public static Schedule runSolver() throws ExecutionException, InterruptedException {


        // Load the problem
        Schedule problem = DemoData.generateDemoData();


        UUID problemId = UUID.randomUUID();

        // Solve the problem
        var solverJob = solverManager.solve(problemId, problem);
        var solvedSchedule = solverJob.getFinalBestSolution();
        System.out.println(solvedSchedule.getScore());
//        System.out.println(solverManager.);

        // Visualize the solution
//        printScheduleSimple(solvedSchedule);
        printSchedule(solvedSchedule);

        // Extract the next available appointment


        return solvedSchedule;

    }

    private static void printScheduleSimple(Schedule schedule) {
        for (var app : schedule.getAppointmentList()) {
            System.out.println(app);
        }
    }

    private static void printSchedule(Schedule schedule) {
        var roomList = schedule.getPatientList();
        List<Appointment> appointmentList = schedule.getAppointmentList();
        System.out.println(appointmentList.size() + " appointments");
        var appointmentMap = appointmentList.stream()
                                            .filter(app -> app.getTimeslot() != null && app.getPatient() != null)
                                            .collect(Collectors.groupingBy(Appointment::getTimeslot,
                                                                           Collectors.groupingBy(Appointment::getPatient)));
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
                                                                                                                             .map(appointments -> String.format(
                                                                                                                                     "%-10s",
                                                                                                                                     appointments.stream()
                                                                                                                                                 .map(Appointment::getTherapist)
                                                                                                                                                 .map(x -> x.getName())
                                                                                                                                                 .collect(
                                                                                                                                                         Collectors.joining(
                                                                                                                                                                 ", "))))
                                                                                                                             .collect(
                                                                                                                                     Collectors.joining(
                                                                                                                                             " | ")) + " |");

            System.out.println("|" + "------------|".repeat(roomList.size() + 1));
        }
        List<Appointment> unassignedLessons = appointmentList.stream()
                                                             .filter(lesson -> lesson.getTimeslot() == null || lesson.getPatient() == null)
                                                             .toList();
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
