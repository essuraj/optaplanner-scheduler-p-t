package com.sc.therapist_appointments.domain;


import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.google.ortools.sat.IntVar;
import com.sc.therapist_appointments.domain.entity.Patient;
import com.sc.therapist_appointments.domain.entity.Therapist;
import com.sc.therapist_appointments.domain.entity.Timeslot;
import org.springframework.lang.Nullable;

@PlanningEntity
public class Appointment {

    @PlanningId
    private Long id;

    //    @PlanningVariable(valueRangeProviderRefs = "patientRange")
    private Patient patient;

    @PlanningVariable(valueRangeProviderRefs = "therapistRange"
           )
    @Nullable
    private Therapist therapist;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange" )
    @Nullable
    private Timeslot timeslot;
    private IntVar appointmentVar;
    public Appointment() {
    }

    public Appointment(Patient patient) {

        // assign random integer id
        this.id = (long) (Math.random() * 10000);

        this.patient = patient;
    }

    public Appointment(
            Therapist therapist, Patient patient, Timeslot timeslot) {

        this.id = (long) (Math.random() * 10000);
        this.patient = patient;
        this.therapist = therapist;
        this.timeslot = timeslot;
    }

    public Appointment(
            Therapist therapist, Patient patient, Timeslot timeslot, IntVar appointmentVar) {

        this.id = (long) (Math.random() * 10000);
        this.patient = patient;
        this.therapist = therapist;
        this.timeslot = timeslot;
        this.appointmentVar = appointmentVar;
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
        return "Appointment{" + "id=" + id + ", patient=" + patient + ", therapist=" + therapist + ", timeslot=" + timeslot + '}';
    }


    public IntVar getAppointmentVar() {
        return appointmentVar;
    }

    public void setAppointmentVar(IntVar appointmentVar) {
        this.appointmentVar = appointmentVar;
    }
}