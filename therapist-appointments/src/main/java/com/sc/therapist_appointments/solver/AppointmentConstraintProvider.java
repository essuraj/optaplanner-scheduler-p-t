package com.sc.therapist_appointments.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.entity.Patient;


public class AppointmentConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                matchTherapyType(constraintFactory),
                therapistConflict(constraintFactory),
//                patientConflict(constraintFactory),
                prioritizeCriticality(constraintFactory),
              //  matchPatientSchedule(constraintFactory),
//                therapistPatientConflict(
//                constraintFactory),
//            locationMatch(constraintFactory),
//            availabilityMatch(constraintFactory),
//            minimizeProximity(constraintFactory)
        };
    }



    private Constraint therapistConflict(ConstraintFactory constraintFactory) {
        // A therapist can have only 1 appointment at the same time.
        return constraintFactory.forEach(Appointment.class)
                .join(Appointment.class,
                        Joiners.equal(Appointment::getTimeslot),
                        Joiners.equal(Appointment::getTherapist),
                        Joiners.lessThan(Appointment::getId)
                     )
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Therapist conflict");
    }
//    private Constraint patientConflict(ConstraintFactory constraintFactory) {
//        // A therapist can have only 1 appointment at the same time.
//        return constraintFactory.forEach(Appointment.class)
//                                .join(Appointment.class,
//                                      Joiners.equal(Appointment::getTimeslot),
//                                      Joiners.equal(Appointment::getPatient)
////                        Joiners.lessThan(Appointment::getId)
//                                     )
//                                .penalize(HardSoftScore.ONE_HARD)
//                                .asConstraint("Patient conflict");
//    }
//    private Constraint therapistPatientConflict(ConstraintFactory constraintFactory) {
//        // A therapist can have only 1 appointment at the same time.
//        return constraintFactory.forEach(Appointment.class)
//                                .join(Appointment.class,
//                                      Joiners.equal(Appointment::getTimeslot),
//                                      Joiners.equal(Appointment::getPatient),
//                                      Joiners.equal(Appointment::getTherapist),
//                                      Joiners.lessThan(Appointment::getId))
//                                .penalize(HardSoftScore.ONE_HARD)
//                                .asConstraint("Therapist-Patient conflict");
//    }

    private Constraint matchTherapyType(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                .filter(appointment -> !appointment.getTherapist().getSkills().contains(appointment.getPatient().getTherapyType()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("mismatch therapy type");
    }
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
private Constraint prioritizeCriticality(ConstraintFactory constraintFactory) {
    return constraintFactory.forEach(Appointment.class)
            .join(Appointment.class,Joiners.equal(Appointment::getTherapist))
                            .filter((appointment1, appointment2) -> appointment1.getPatient().getCriticality() > appointment2.getPatient().getCriticality())
                            .reward(HardSoftScore.ONE_SOFT)
                            .asConstraint("Prioritize Criticality by patient");
}


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