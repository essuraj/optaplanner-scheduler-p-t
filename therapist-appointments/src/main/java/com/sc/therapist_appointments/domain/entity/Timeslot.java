package com.sc.therapist_appointments.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Timeslot implements Comparable<Timeslot> {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


    @Override
    public String toString() {
//        System.out.println(date.format(formatters) + " " + startTime);
        return date.format(formatters) + " " + startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timeslot ts)) {
            return false;
        }
        return Objects.equals(toString(), ts.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int compareTo(Timeslot o) {
        return getDate().compareTo(o.getDate());
    }

}
