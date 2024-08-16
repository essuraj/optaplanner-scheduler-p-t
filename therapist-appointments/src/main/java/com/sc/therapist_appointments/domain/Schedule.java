package com.sc.therapist_appointments.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import com.sc.therapist_appointments.domain.actors.Therapist;

import java.util.List;

@PlanningSolution
public class Schedule {

    @PlanningEntityCollectionProperty
    private List<Appointment> appointmentList;
    @PlanningEntityCollectionProperty
    @ValueRangeProvider(id = "therapistRange")
    private List<Therapist> therapistList;
    @ValueRangeProvider(id = "timeslotRange")
    private List<Timeslot> timeslotList;

    @PlanningScore
    private HardSoftScore score;

    public Schedule() {
    }

    public Schedule(List<Timeslot> timeslotList, List<Therapist> therapistList, List<Appointment> appointmentList) {
        this.timeslotList = timeslotList;
        this.therapistList = therapistList;
        this.appointmentList = appointmentList;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Therapist> getTherapistList() {
        return therapistList;
    }

    public void setTherapistList(List<Therapist> therapistList) {
        this.therapistList = therapistList;
    }



    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public void setTimeslotList(List<Timeslot> timeslotList) {
        this.timeslotList = timeslotList;
    }

    // Getters and setters
}