package com.sc.therapist_appointments.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import com.sc.therapist_appointments.domain.entity.Patient;
import com.sc.therapist_appointments.domain.entity.Therapist;
import com.sc.therapist_appointments.domain.entity.Timeslot;

import java.util.List;

@PlanningSolution
public class Schedule {


    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "therapistRange")
    private List<Therapist> therapistList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeslotRange")
    private List<Timeslot> timeslotList;

    //    @ProblemFactCollectionProperty
//    @ValueRangeProvider(id = "patientRange")
    private List<Patient> patientList;

    @PlanningEntityCollectionProperty
    private List<Appointment> appointmentList;

    @PlanningScore
    private HardSoftLongScore score;

    // No-arg constructor required for OptaPlanner
    public Schedule() {
    }


    public Schedule(List<Timeslot> timeslotList, List<Therapist> therapistList, List<Patient> patientList, List<Appointment> appointmentList) {
        this.timeslotList = timeslotList;
        this.therapistList = therapistList;
        this.patientList = patientList;
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


    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public void setTimeslotList(List<Timeslot> timeslotList) {
        this.timeslotList = timeslotList;
    }

    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    // Getters and setters
}