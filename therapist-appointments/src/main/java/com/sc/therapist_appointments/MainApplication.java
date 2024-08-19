package com.sc.therapist_appointments;

import ai.timefold.solver.core.api.score.stream.ConstraintStreamImplType;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.Schedule;
import com.sc.therapist_appointments.domain.Timeslot;
import com.sc.therapist_appointments.domain.actors.Patient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
//        SpringApplication.run(MainApplication.class, args);
        SolverFactory<Schedule> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(Schedule.class)
                .withEntityClasses(Appointment.class)
                .withConstraintStreamImplType(ConstraintStreamImplType.BAVET)

                .withConstraintProviderClass(AppointmentConstraintProvider.class)
                // The solver runs only for 5 seconds on this small dataset.
                // It's recommended to run for at least 5 minutes ("5m") otherwise.
                .withTerminationSpentLimit(Duration.ofSeconds(5)));
        Solver<Schedule> solver = solverFactory.buildSolver();
        Schedule unsolvedSchedule = DemoData.generateDemoData(); // Initialize with patients, therapists, and empty appointments\
        printSchedule(unsolvedSchedule);
        Schedule solvedSchedule = solver.solve(unsolvedSchedule);
        printSchedule(solvedSchedule);
        // Extract the next available appointment
//        Appointment nextAvailableAppointment = solvedSchedule.getAppointmentList().stream()
//                .filter(appointment -> appointment.getPatient() == null)
//                .findFirst()
//                .orElse(null);

//        if (nextAvailableAppointment != null) {
//            System.out.println("Next available appointment: " + nextAvailableAppointment);
////            return nextAvailableAppointment;
//        } else {
//            System.out.println("No available appointments found.");
////            return new Appointment();
//        }

    }

    private static void printSchedule(Schedule schedule) {
        var roomList = schedule.getPatientList();
        List<Appointment> appointmentList = schedule.getAppointmentList();
        System.out.println(appointmentList.size() + " appointments");
        Map<Timeslot, Map<Patient, List<Appointment>>> appointmentMap = appointmentList.stream()
                .filter(app -> app.getTimeslot() != null && app.getPatient() != null)
                .collect(Collectors.groupingBy(Appointment::getTimeslot, Collectors.groupingBy(Appointment::getPatient)));
        System.out.println("|            | " + roomList.stream()
                .map(room -> String.format("%-10s", room.getName())).collect(Collectors.joining(" | ")) + " |");
        System.out.println("|" + "------------|".repeat(roomList.size() + 1));
        for (Timeslot timeslot : schedule.getTimeslotList()) {
            List<List<Appointment>> cellList = roomList.stream()
                    .map(room -> {
                        Map<Patient, List<Appointment>> byRoomMap = appointmentMap.get(timeslot);
                        if (byRoomMap == null) {
                            return Collections.<Appointment>emptyList();
                        }
                        List<Appointment> cellLessonList = byRoomMap.get(room);
                        if (cellLessonList == null) {
                            return Collections.<Appointment>emptyList();
                        }
                        return cellLessonList;
                    })
                    .collect(Collectors.toList());

            System.out.println("| " + String.format("%-10s",
                    timeslot.toString().substring(0, 3) + " " + timeslot.getStartTime()) + " | "
                    + cellList.stream().map(cellLessonList -> String.format("%-10s",
                            cellLessonList
                                    .stream()
                                    .map(Appointment::getTherapist)
                                    .map(x -> x.getName())
                                    .collect(Collectors.joining(", "))))
                    .collect(Collectors.joining(" | "))
                    + " |");
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
        System.out.println(unassignedLessons.size() + " unassigned lessons");
        if (!unassignedLessons.isEmpty()) {
            System.out.println();
            System.out.println("Unassigned lessons");
            for (Appointment lesson : unassignedLessons) {
                System.out.println(lesson);
            }
        }
    }

}
