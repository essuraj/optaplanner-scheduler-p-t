package com.sc.therapist_appointments.domain.entity;


import java.util.List;
import java.util.Objects;

public class Patient {
    private String name;
    private List<Timeslot> availability;
    private String therapyType;
    private String location;
    private int criticality;

    public Patient(String name, String therapyType, String location, int criticality, List<Timeslot> availability) {
        this.name = name;
        this.availability = availability;
        this.therapyType = therapyType;
        this.location = location;
        this.criticality = criticality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTherapyType() {
        return therapyType;
    }

    public void setTherapyType(String therapyType) {
        this.therapyType = therapyType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCriticality() {
        return criticality;
    }

    public void setCriticality(int criticality) {
        this.criticality = criticality;
    }


    public List<Timeslot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Timeslot> availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient patient)) {
            return false;
        }
        return Objects.equals(getName(), patient.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
 
 

    
   
    
 