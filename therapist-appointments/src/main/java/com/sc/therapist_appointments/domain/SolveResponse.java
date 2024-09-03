package com.sc.therapist_appointments.domain;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

public class SolveResponse {
    private final Schedule schedule;
    private final ScoreAnalysis<HardSoftScore> scoreAnalysis;

    public SolveResponse(Schedule schedule, ScoreAnalysis<HardSoftScore> scoreAnalysis) {
        this.schedule = schedule;
        this.scoreAnalysis = scoreAnalysis;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public ScoreAnalysis<HardSoftScore> getScoreAnalysis() {
        return scoreAnalysis;
    }
}