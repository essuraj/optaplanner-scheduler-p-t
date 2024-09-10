package com.sc.therapist_appointments.or;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.sat.*;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sc.therapist_appointments.DemoData;
import com.sc.therapist_appointments.domain.Appointment;

import java.util.ArrayList;

public class OrSolver {
//    private void solve() {
//        // Solve the problem
//        MPSolver solver = MPSolver.createSolver("GLOP");
//        if (solver == null) {
//            System.out.println("Could not create solver GLOP");
//            return;
//        }
//
//    }

    public void scheduleAppointment() {
        // Schedule the appointment

        CpModel model = new CpModel();
        var schedule = DemoData.generateDemoData();
        var patients = schedule.getPatientList();
        var therapists = schedule.getTherapistList();
        var timeslots = schedule.getTimeslotList();

        // List to store all possible appointments
        var possibleAppointments = new ArrayList<Appointment>();

        // Create variables for each possible appointment
        for (var patient : patients) {
            for (var therapist : therapists) {
                if (therapist.getSkills().contains(patient.getTherapyType())) {
                    double distanceInKm = LatLngTool.distance(patient.getLocation(),
                                                              therapist.getLocation(),
                                                              LengthUnit.KILOMETER);
                    if (distanceInKm <= therapist.getMaxTravelDistanceKm()) {

                        for (var patientTs : patient.getAvailability()) {
                            for (var therapistTs : therapist.getAvailability()) {
                                if (patientTs.equals(therapistTs)) {
                                    var appointment = new Appointment(therapist,
                                                                      patient,
                                                                      patientTs,
                                                                      model.newBoolVar(
                                                                              "A -->" + patient.getName() + "_" + therapist.getName() + "_" + therapistTs));
                                    possibleAppointments.add(appointment);

                                }
                            }

                        }

                    }
                }
            }
        }
        for (var patient : patients) {
            // Each patient should have exactly one appointment
            LinearExprBuilder summer = LinearExpr.newBuilder();
            possibleAppointments.stream()
                                .filter(x -> x.getPatient().equals(patient))
                                .map(x -> x.getAppointmentVar().build())
                                .forEach(summer::add);


            model.addLinearConstraint(summer.build(), 0, 1);
        }
        for (var therapist : therapists) {
            // Each therapist should have exactly one appointment
            LinearExprBuilder summer2 = LinearExpr.newBuilder();
            possibleAppointments.stream().filter(x -> {
                assert x.getTherapist() != null;
                return x.getTherapist().equals(therapist);
            }).map(a->a.getAppointmentVar().build()).forEach(summer2::add);
            model.addLinearConstraint(summer2.build(), 0, 1);
        }
        // Minimize the distance between patient and therapist
        LinearExprBuilder objective = LinearExpr.newBuilder();
        possibleAppointments.forEach(appointment -> {
            double distanceInKm = LatLngTool.distance(appointment.getPatient()
                                                                 .getLocation(),
                                                      appointment.getTherapist()
                                                                 .getLocation(),
                                                      LengthUnit.KILOMETER);
            var distanceInKmLong = (int) Math.round(distanceInKm);
            for (int i = 0; i < distanceInKmLong; i++) {
//                System.out.println("Distance in km: " + distanceInKmLong);
                objective.add(appointment.getAppointmentVar());
            }


        });

        model.minimize(objective.build());
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);

        if (status == CpSolverStatus.OPTIMAL || status == CpSolverStatus.FEASIBLE)
        {
            for (var appointment : possibleAppointments)
            {
                if (solver.value(appointment.getAppointmentVar()) == 1)
                {
//                    int patientId = appointment.Key.Item1;
//                    int therapistId = appointment.Key.Item2;
//                    int slotId = appointment.Key.Item3;
//                    DateTime appointmentTime = GetDateTimeFromSlotId(slotId);

                    schedule.getAppointmentList().add(appointment
                     );

                    System.out.println("Patient {patientId} is scheduled with " +
                              "Therapist" +
                             " " +
                       "{therapistId} at {appointmentTime}");
                }
            }
        }
        else
        {
            System.err.println("No solution found.");
        }


    }
}
