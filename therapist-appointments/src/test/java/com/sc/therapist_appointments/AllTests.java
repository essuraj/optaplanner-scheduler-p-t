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

import java.util.Comparator;
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

    private List<Appointment> getAppointments() {
        return sol.getAppointmentList();
    }

    @ParameterizedTest
    @MethodSource("getAppointments")
    @DisplayName(value = "Must be < travel distance of the therapist")
    void validateDistance(Appointment appointment) throws Exception {


        assertThat((LatLngTool.distance(appointment.getTherapist().getLocation(),
                                        appointment.getPatient().getLocation(),
                                        LengthUnit.KILOMETER))).isLessThanOrEqualTo(appointment.getTherapist()
                                                                                               .getMaxTravelDistanceKm());


    }


    @ParameterizedTest
    @MethodSource("getAppointments")
    @DisplayName(value = "Must match patient/therapist availability")
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

    @ParameterizedTest
    @MethodSource("getAppointments")
    @DisplayName(value = "Must match right skill of the therapist")
    public void validateAppointmentSkills(Appointment appointment) {

        assertThat(appointment.getTherapist().getSkills()).contains(appointment.getPatient().getTherapyType());
    }

    @Test
    @DisplayName(value = "Prioritized based on criticality")
    public void validateCriticality() {
        var sortedAppointments = getAppointments().stream()
                                                  .sorted(Comparator.comparing(appointment -> appointment.getTimeslot()
                                                                                                         .getDate()))
                                                  .toList();
        sortedAppointments.forEach(appointment -> {
            System.out.println("Patient: " + appointment.getPatient()
                                                        .getName() + " Criticality: " + appointment.getPatient()
                                                                                                   .getCriticality());
        });
        System.out.println("Unsorted");
        getAppointments().forEach(appointment -> {
            System.out.println("Patient: " + appointment.getPatient()
                                                        .getName() + " Criticality: " + appointment.getPatient()
                                                                                                   .getCriticality());
        });

    }
}
