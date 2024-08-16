package com.sc.therapist_appointments.domain;


import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.sc.therapist_appointments.domain.actors.Patient;
import com.sc.therapist_appointments.domain.actors.Therapist;

@PlanningEntity
public class Appointment {
    @PlanningId
    private Long id;
private String therapyType;
    private Patient patient;

    @PlanningVariable(valueRangeProviderRefs = "therapistRange")
    private Therapist therapist;
    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;

    public Appointment( ) {
    }

    public Appointment(long id, String therapyType, Therapist therapist, Patient patient, Timeslot timeslot) {
        this.therapyType = therapyType;
        this.setId(id);
        this.patient = patient;
        this.therapist = therapist;
        this.timeslot = timeslot;
    }

    // Getters and setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }


    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTherapyType() {
        return therapyType;
    }

    public void setTherapyType(String therapyType) {
        this.therapyType = therapyType;
    }
}