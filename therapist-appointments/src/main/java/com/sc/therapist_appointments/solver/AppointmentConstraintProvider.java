package com.sc.therapist_appointments.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.sc.therapist_appointments.domain.Appointment;


public class AppointmentConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{matchTherapyType(constraintFactory), prioritizeCriticality(constraintFactory), matchPatientSchedule(
                constraintFactory), therapistConflict(constraintFactory),};
    }


    private Constraint therapistConflict(ConstraintFactory constraintFactory) {
        // A therapist can have only 1 appointment at the same time.
        return constraintFactory.forEach(Appointment.class)
                                .join(Appointment.class,
                                      Joiners.equal(appointment -> appointment.getTimeslot().getDate()),
                                      Joiners.equal(Appointment::getTherapist))
                                .penalize(HardSoftScore.ONE_HARD)
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
                                .penalize(HardSoftScore.ONE_HARD)
                                .asConstraint("Match Availability between Therapist and Patient");
    }

    private Constraint matchTherapyType(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .filter(appointment -> !appointment.getTherapist()
                                                                   .getSkills()
                                                                   .contains(appointment.getPatient().getTherapyType()))
                                .penalize(HardSoftScore.ONE_HARD)
                                .asConstraint("mismatch therapy type");
    }


    private Constraint prioritizeCriticality(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)

//
                                .reward(HardSoftScore.ONE_HARD,
                                        appointment -> appointment.getPatient().getCriticality())
                                .asConstraint("Prioritize Criticality by patient");
    }


}