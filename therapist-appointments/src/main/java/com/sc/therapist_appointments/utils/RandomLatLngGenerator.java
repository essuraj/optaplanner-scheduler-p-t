package com.sc.therapist_appointments.utils;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import java.util.Random;

public class RandomLatLngGenerator {

    private static final double HYDERABAD_LAT = 17.3850;
    private static final double HYDERABAD_LNG = 78.4867;
    private static final double LAT_RANGE = 0.1; // Approx 11 km range
    private static final double LNG_RANGE = 0.1; // Approx 11 km range
    private static final double MIN_DISTANCE_KM = 2.0;
    private static final double MAX_DISTANCE_KM = 15.0;

    public static LatLng[] generateRandomLatLngs(int count) {
        LatLng[] coordinates = new LatLng[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            double randomLat = HYDERABAD_LAT + (random.nextDouble() * 2 - 1) * LAT_RANGE;
            double randomLng = HYDERABAD_LNG + (random.nextDouble() * 2 - 1) * LNG_RANGE;
            coordinates[i] = new LatLng(randomLat, randomLng);
        }

        return coordinates;
    }

    public static LatLng[] getFixedLatLngs() {
        LatLng[] coordinates = new LatLng[20];
        Random random = new Random();
        LatLng center = new LatLng(HYDERABAD_LAT, HYDERABAD_LNG);

        for (int i = 0; i < 20; i++) {
            double distanceKm = MIN_DISTANCE_KM + (MAX_DISTANCE_KM - MIN_DISTANCE_KM) * random.nextDouble();
            double bearing = 360 * random.nextDouble();
            coordinates[i] = LatLngTool.travel(center, bearing, distanceKm, LengthUnit.KILOMETER);
        }

        return coordinates;
    }
}
