package com.sc.therapist_appointments.domain.actors;

import java.time.LocalDateTime;
import java.util.List;

public class Therapist {
    private String name;
    private List<LocalDateTime> availability;
    private String location;
    private List<String> skills;

    public Therapist(String name, List<LocalDateTime> availability, String location, List<String> skills) {
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

    public List<LocalDateTime> getAvailability() {
        return availability;
    }

    public void setAvailability(List<LocalDateTime> availability) {
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
}