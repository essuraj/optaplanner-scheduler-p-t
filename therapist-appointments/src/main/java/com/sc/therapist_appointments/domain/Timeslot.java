package com.sc.therapist_appointments.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Timeslot {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Timeslot() {
    }

    public Timeslot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return date + " " + startTime;
    }

}
