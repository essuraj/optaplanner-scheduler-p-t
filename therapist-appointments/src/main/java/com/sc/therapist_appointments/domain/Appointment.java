package com.sc.therapist_appointments.domain;


import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.sc.therapist_appointments.domain.entity.Patient;
import com.sc.therapist_appointments.domain.entity.Therapist;
import com.sc.therapist_appointments.domain.entity.Timeslot;

@PlanningEntity
public class Appointment {

    @PlanningId
    private Long id;

    //    @PlanningVariable(valueRangeProviderRefs = "patientRange")
    private Patient patient;

    @PlanningVariable(valueRangeProviderRefs = "therapistRange", allowsUnassigned = true)
    private Therapist therapist;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange", allowsUnassigned = true)
    private Timeslot timeslot;

    public Appointment() {
    }

    public Appointment(Patient patient) {

        // assign random integer id
        this.id = (long) (Math.random() * 10000);

        this.patient = patient;
    }

    public Appointment(long id, Therapist therapist, Patient patient, Timeslot timeslot) {

        this.id = id;
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


    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", patient=" + patient.getName() + ", therapist=" + therapist + ", timeslot=" + timeslot + '}';
    }
}