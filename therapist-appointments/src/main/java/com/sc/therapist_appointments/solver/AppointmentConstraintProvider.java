package com.sc.therapist_appointments.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sc.therapist_appointments.domain.Appointment;


public class AppointmentConstraintProvider implements ConstraintProvider {

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
        return constraintFactory.forEach(Appointment.class)
                                .filter(appointment -> !appointment.getPatient()
                                                                   .getAvailability()
                                                                   .stream()
                                                                   .anyMatch(patientTimeslot -> appointment.getTherapist()
                                                                                                           .getAvailability()
                                                                                                           .stream()
                                                                                                           .anyMatch(
                                                                                                                   therapistTimeslot -> therapistTimeslot.getDate()
                                                                                                                                                         .equals(patientTimeslot.getDate()) && therapistTimeslot.getStartTime()
                                                                                                                                                                                                                .equals(patientTimeslot.getStartTime()))))
                                .penalize(HardSoftLongScore.ONE_HARD)
                                .asConstraint("Match Availability between Therapist and Patient");
    }

    private Constraint matchTherapyType(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .filter(appointment -> !appointment.getTherapist()
                                                                   .getSkills()
                                                                   .contains(appointment.getPatient().getTherapyType()))
                                .penalize(HardSoftLongScore.ONE_HARD)
                                .asConstraint("mismatch therapy type");
    }


    protected Constraint prioritizeCriticality(ConstraintFactory factory) {
        return factory.forEach(Appointment.class)
                      .rewardLong(HardSoftLongScore.ONE_SOFT, appointment -> appointment.getPatient().getCriticality())
                      .asConstraint("Prioritize Criticality by patient");
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

    private Constraint maxTravelDistance(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class).rewardLong(HardSoftLongScore.ONE_HARD, appointment -> {
            long distance = Math.round(LatLngTool.distance(appointment.getTherapist().getLocation(),
                                                           appointment.getPatient().getLocation(),
                                                           LengthUnit.KILOMETER));
//            System.out.println("Distance: " + distance);
//            System.out.println("Max Distance: " + appointment.getTherapist().getMaxTravelDistanceKm());
            return (appointment.getTherapist()
                               .getMaxTravelDistanceKm() - distance) < 0 ? 0 : (appointment.getTherapist()
                                                                                           .getMaxTravelDistanceKm() - distance);
        }).asConstraint("Max Travel Distance");
    }

}