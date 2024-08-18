package com.sc.therapist_appointments.domain.actors;

import com.sc.therapist_appointments.domain.Timeslot;

import java.util.List;
import java.util.Objects;


public class Therapist {
    private String name;
    private List<Timeslot> availability;

    private String location;
    private List<String> skills;

    public Therapist(String name, List<Timeslot> availability, String location, List<String> skills) {
        this.name = name;
        this.availability = availability;
        this.location = location;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Timeslot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Timeslot> availability) {
        this.availability = availability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Therapist{" +
                "name='" + name + '\'' +
                ", availability=" + availability +
                ", location='" + location + '\'' +
                ", skills=" + skills +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Therapist employee)) {
            return false;
        }
        return Objects.equals(getName(), employee.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}