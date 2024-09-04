package com.sc.therapist_appointments.domain.entity;

import com.javadocmd.simplelatlng.LatLng;

import java.util.List;
import java.util.Objects;


public class Therapist {
    private String name;
    private List<Timeslot> availability;
    private int maxTravelDistanceKm;
    private LatLng location;
    private List<String> skills;

    public Therapist(String name, List<Timeslot> availability, LatLng location, List<String> skills, int maxTravelDistanceKm) {
        this.name = name;
        this.availability = availability;
        this.location = location;
        this.skills = skills;
        this.maxTravelDistanceKm = maxTravelDistanceKm;
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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
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
        return "TH {" + "name='" + name + '\'' + ", maxTravelKm=" + maxTravelDistanceKm  + '\'' + ", skills=" + skills + '}';
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

    public int getMaxTravelDistanceKm() {
        return maxTravelDistanceKm;
    }

    public void setMaxTravelDistanceKm(int maxTravelDistanceKm) {
        this.maxTravelDistanceKm = maxTravelDistanceKm;
    }
}