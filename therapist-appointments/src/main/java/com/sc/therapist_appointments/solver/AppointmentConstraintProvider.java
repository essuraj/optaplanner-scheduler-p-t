package com.sc.therapist_appointments.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.entity.Patient;
import com.sc.therapist_appointments.domain.entity.Therapist;
import com.sc.therapist_appointments.domain.entity.Timeslot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AppointmentConstraintProvider implements ConstraintProvider {

    public static List<Timeslot> searchAvailableAppointments(Patient patient, Therapist therapist, List<Appointment> existingAppointments) {
        List<Timeslot> availableSlots = new ArrayList<>();

        // Get the common available slots between patient and therapist
        List<Timeslot> commonSlots = patient.getAvailability()
                                            .stream()
                                            .filter(patientSlot -> therapist.getAvailability()
                                                                            .stream()
                                                                            .anyMatch(therapistSlot -> therapistSlot.getDate()
                                                                                                                    .equals(patientSlot.getDate()) && therapistSlot.getStartTime()
                                                                                                                                                                   .equals(patientSlot.getStartTime())))
                                            .collect(Collectors.toList());

        // Filter out the slots that are already booked
        for (Timeslot slot : commonSlots) {
            boolean isBooked = existingAppointments.stream()
                                                   .anyMatch(appointment -> appointment.getTimeslot()
                                                                                       .getDate()
                                                                                       .equals(slot.getDate()) && appointment.getTimeslot()
                                                                                                                             .getStartTime()
                                                                                                                             .equals(slot.getStartTime()) && appointment.getTherapist()
                                                                                                                                                                        .equals(therapist));
            if (!isBooked) {
                availableSlots.add(slot);
            }
        }

        return availableSlots;
    }

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{matchTherapyType(constraintFactory), prioritizeCriticality(constraintFactory), matchPatientSchedule(
                constraintFactory), therapistConflict(constraintFactory), maxTravelDistance(constraintFactory)};
    }

    private Constraint therapistConflict(ConstraintFactory constraintFactory) {
        // A therapist can have only 1 appointment at the same time.
        return constraintFactory.forEach(Appointment.class)
                                .join(Appointment.class,
                                      Joiners.equal(appointment -> appointment.getTimeslot().getDate()),
                                      Joiners.equal(Appointment::getTherapist))
                                .penalize(HardSoftLongScore.ONE_HARD)
                                .asConstraint("Therapist conflict");
    }

    private Constraint matchPatientSchedule(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class).rewardLong(HardSoftLongScore.ONE_HARD, appointment -> {
                                    return appointment.getPatient().getAvailability().stream().anyMatch(patientTimeslot -> {

                                        return appointment.getTherapist().getAvailability().stream().anyMatch(therapistTimeslot -> {
                                            return therapistTimeslot.getDate()
                                                                    .equals(patientTimeslot.getDate()) && therapistTimeslot.getStartTime()
                                                                                                                           .equals(patientTimeslot.getStartTime());
                                        });
                                    }) ? 20 : 1;
                                })
//                                .penalize(HardSoftLongScore.ONE_HARD * 100000)
                                .asConstraint("Mismatch patient schedule");
    }


    private Constraint matchTherapyType(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .filter(appointment -> !appointment.getTherapist()
                                                                   .getSkills()
                                                                   .contains(appointment.getPatient().getTherapyType()))
                                .penalize(HardSoftLongScore.ONE_HARD)
                                .asConstraint("mismatch therapy type");
    }

//    private Constraint maxTravelDistance(ConstraintFactory constraintFactory) {
//        return constraintFactory.forEach(Appointment.class).filter(appointment -> {
//            double distance = LatLngTool.distance(appointment.getTherapist().getLocation(),
//                                                  appointment.getPatient().getLocation(),
//                                                  LengthUnit.KILOMETER);
////            System.out.println("Distance: " + distance);
////            System.out.println("Max Distance: " + appointment.getTherapist().getMaxTravelDistanceKm());
//            return distance > appointment.getTherapist().getMaxTravelDistanceKm();
//        }).penalize(HardSoftLongScore.ONE_HARD).asConstraint("Max Travel Distance");
//    }

    protected Constraint prioritizeCriticality(ConstraintFactory factory) {
        return factory.forEach(Appointment.class)
                      .rewardLong(HardSoftLongScore.ONE_HARD,
                                  appointment -> appointment.getPatient().getCriticality() * 2L)
                      .asConstraint("Prioritize Criticality by patient");
    }

    private Constraint maxTravelDistance(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class).rewardLong(HardSoftLongScore.ONE_HARD, appointment -> {
            long distance = Math.round(LatLngTool.distance(appointment.getTherapist().getLocation(),
                                                           appointment.getPatient().getLocation(),
                                                           LengthUnit.KILOMETER));
//            System.out.println("Distance: " + distance);
//            System.out.println("Max Distance: " + appointment.getTherapist().getMaxTravelDistanceKm());
            return (appointment.getTherapist()
                               .getMaxTravelDistanceKm() - distance) < 0 ? 0 : (appointment.getTherapist()
                                                                                           .getMaxTravelDistanceKm() - distance) * 10L;
        }).asConstraint("Max Travel Distance");
    }
}