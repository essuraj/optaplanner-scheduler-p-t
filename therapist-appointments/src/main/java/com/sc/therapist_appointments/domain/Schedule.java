package com.sc.therapist_appointments.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import com.sc.therapist_appointments.domain.actors.Patient;
import com.sc.therapist_appointments.domain.actors.Therapist;

import java.util.List;

@PlanningSolution
public class Schedule {

    @PlanningEntityCollectionProperty
    private List<Appointment> appointmentList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider//(id = "therapistRange")
    private List<Therapist> therapistList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider//(id = "timeslotRange")
    private List<Timeslot> timeslotList;
    @ProblemFactCollectionProperty
    @ValueRangeProvider//(id = "patientRange")
    private List<Patient> patientList;
    @PlanningScore
    private HardSoftScore score;

    public Schedule() {
    }


    public Schedule(List<Timeslot> timeslotList, List<Therapist> therapistList, List<Appointment> appointmentList, List<Patient> patientList) {
        this.timeslotList = timeslotList;
        this.therapistList = therapistList;
        this.appointmentList = appointmentList;
        this.patientList = patientList;
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

    // Getters and setters
}