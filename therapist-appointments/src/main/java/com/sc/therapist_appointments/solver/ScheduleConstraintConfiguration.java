package com.sc.therapist_appointments.solver;

import ai.timefold.solver.core.api.domain.constraintweight.ConstraintConfiguration;
import ai.timefold.solver.core.api.domain.constraintweight.ConstraintWeight;
import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

@ConstraintConfiguration(constraintPackage = "com.sc.therapist_appointments.solver")
public class ScheduleConstraintConfiguration {
    @ConstraintWeight("Prioritize Criticality by patient")
    private final HardSoftLongScore prioritizeCriticality = HardSoftLongScore.ofHard(10);
}
