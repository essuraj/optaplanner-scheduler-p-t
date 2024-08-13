package com.sc.therapist_appointments;

import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

public class AppointmentConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
//            therapyTypeMatch(constraintFactory),
//            locationMatch(constraintFactory),
//            availabilityMatch(constraintFactory),
//          prioritizeCriticality(constraintFactory),
//            minimizeProximity(constraintFactory)
        };
    }

//    private Constraint therapyTypeMatch(ConstraintFactory constraintFactory) {
//        return constraintFactory.from(Appointment.class)
//            .filter(appointment -> !appointment.getTherapist().getSkills().contains(appointment.getPatient().getTherapyType()))
//            .penalize("Therapy type mismatch", HardSoftScore.ONE_HARD);
//    }
//
//    private Constraint locationMatch(ConstraintFactory constraintFactory) {
//        return constraintFactory.from(Appointment.class)
//            .filter(appointment -> !appointment.getTherapist().getLocation().equals(appointment.getPatient().getLocation()))
//            .penalize("Location mismatch", HardSoftScore.ONE_HARD);
//    }
//
//    private Constraint availabilityMatch(ConstraintFactory constraintFactory) {
//        return constraintFactory.from(Appointment.class)
//            .filter(appointment -> !appointment.getTherapist().getAvailability().contains(appointment.getStartTime()))
//            .penalize("Availability mismatch", HardSoftScore.ONE_HARD);
//    }

//    private Constraint prioritizeCriticality(ConstraintFactory constraintFactory) {
//        return constraintFactory.forEach(Appointment.class)
//                .
//
//
//                .reward("Prioritize criticality",
//                        appointment -> appointment.getPatient().getCriticality());
//    }

//    private Constraint minimizeProximity(ConstraintFactory constraintFactory) {
//        return constraintFactory.from(Appointment.class)
//            .penalize("Minimize proximity", HardSoftScore.ONE_SOFT,
//                appointment -> calculateDistance(appointment.getPatient().getLocation(), appointment.getTherapist().getLocation()));
//    }
//
//    private int calculateDistance(String location1, String location2) {
//        // Implement a method to calculate the distance between two locations
//        // For simplicity, return a dummy value
//        return (int) (Math.random() * 100); // Replace with actual distance calculation logic
//    }
}