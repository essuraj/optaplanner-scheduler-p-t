package com.sc.therapist_appointments.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sc.therapist_appointments.domain.Appointment;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;


public class AppointmentConstraintProvider implements ConstraintProvider {


    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                matchTherapyType(constraintFactory),
                prioritizeCriticality(constraintFactory),
                matchPatientTimeslot(constraintFactory),
                matchTherapistTimeslot(constraintFactory),
                therapistConflict(constraintFactory),
                maxTravelDistance(constraintFactory)
        };
    }


    private Constraint therapistConflict(ConstraintFactory constraintFactory) {
        // A therapist can have only 1 appointment at the same time.
        return constraintFactory.forEachUniquePair(Appointment.class,
                                                   equal(Appointment::getTherapist),
                                                   equal(Appointment::getTimeslot))

                                .penalize(HardSoftScore.ONE_HARD)
                                .asConstraint("Therapist conflict");
    }


    private Constraint matchPatientTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .penalize(HardSoftScore.ONE_HARD,
                                          appointment -> appointment.getPatient()
                                                                    .getAvailability()
                                                                    .stream()
                                                                    .noneMatch(
                                                                            patientTimeslot -> patientTimeslot.equals(
                                                                                    appointment.getTimeslot())) ? 2 : 0)

                                .asConstraint("Must Match Patient Timeslot");
    }

    private Constraint matchTherapistTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .penalize(HardSoftScore.ONE_HARD,
                                          appointment -> appointment.getTherapist()
                                                                    .getAvailability()
                                                                    .stream()
                                                                    .noneMatch(
                                                                            therapistTs -> therapistTs.equals(
                                                                                    appointment.getTimeslot())) ? 2 : 0)

                                .asConstraint("Must Match Therapist Timeslot");
    }

    private Constraint matchTherapyType(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .penalize(HardSoftScore.ofHard(100),
                                        appointment -> appointment.getTherapist()
                                                                  .getSkills()
                                                                  .contains(
                                                                          appointment.getPatient()
                                                                                     .getTherapyType())? 0 : 1)

                                .asConstraint("Missing therapy type");
    }


    protected Constraint prioritizeCriticality(ConstraintFactory factory) {
        return factory.forEach(Appointment.class)
                      .reward(HardSoftScore.ONE_SOFT,
                              appointment -> appointment.getPatient()
                                                        .getCriticality() * 100)
                      .asConstraint("Prioritize Criticality by patient");
    }

    private Constraint maxTravelDistance(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Appointment.class)
                                .penalize(HardSoftScore.ONE_HARD, appointment -> {
                                    var distance = (LatLngTool.distance(
                                            appointment.getTherapist()
                                                       .getLocation(),
                                            appointment.getPatient()
                                                       .getLocation(),
                                            LengthUnit.KILOMETER));
                                    return appointment.getTherapist()
                                                      .getMaxTravelDistanceKm() < distance ? 2 : 0;
                                })
                                .asConstraint("Max Travel Distance");
    }
}