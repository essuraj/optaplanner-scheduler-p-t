package com.sc.therapist_appointments;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sc.therapist_appointments.domain.Appointment;
import com.sc.therapist_appointments.domain.Schedule;
import com.sc.therapist_appointments.web.HomeController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@SpringBootTest
class AllTests {
    Schedule sol;
    @Autowired
    private HomeController controller;

    @BeforeAll
    public void setUpFixture() throws ExecutionException, InterruptedException {
        assertThat(controller).isNotNull();
        sol = controller.run();
    }

    @Test
    void initSuccess() throws ExecutionException, InterruptedException {
        assertThat(controller).isNotNull();
    }


    @ParameterizedTest
    @MethodSource("getAppointments")
    @DisplayName(value = "Check if all patients have an appointment with therapists within their max travel distance")
    void validateDistance(Appointment appointment) throws Exception {


        assertThat(Math.round(LatLngTool.distance(appointment.getTherapist().getLocation(),
                                                  appointment.getPatient().getLocation(),
                                                  LengthUnit.KILOMETER))).isLessThan(appointment.getTherapist()
                                                                                                .getMaxTravelDistanceKm());


    }


    @ParameterizedTest
    @MethodSource("getAppointments")
    @DisplayName(value = "Check if all patients have an appointments within their availability")
    public void validateAppointmentSlots(Appointment appointment) {
        System.out.println("Appointment Picked as: " + appointment.getTimeslot());
        System.out.println("------------------------------------------------------------------------");
        assertThat(appointment.getPatient().getAvailability().stream().anyMatch(patientAvailableSlot -> {
            boolean match = patientAvailableSlot.getDate()
                                                .equals(appointment.getTimeslot()
                                                                   .getDate()) && patientAvailableSlot.getStartTime()
                                                                                                      .equals(appointment.getTimeslot()
                                                                                                                         .getStartTime());
            if (match) {
                System.out.println(patientAvailableSlot + " v " + appointment.getTimeslot());
                System.out.println("Therapist: " + appointment.getTherapist()
                                                              .getName() + " Patient: " + appointment.getPatient()
                                                                                                     .getName());
                System.out.println("===================================================================");
            } else {
                System.err.println(patientAvailableSlot + " v " + appointment.getTimeslot());
            }
            return match;
        })).isTrue();
    }

    private List<Appointment> getAppointments() {
        return sol.getAppointmentList();
    }
}
