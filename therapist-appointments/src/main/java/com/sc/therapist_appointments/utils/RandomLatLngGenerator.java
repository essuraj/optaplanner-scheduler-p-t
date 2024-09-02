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
        var fixed = new double[][]{new double[]{0.4477482490853033, 0.11963445248358928}, new double[]{0.47537370651916855, 0.9328691781488845}, new double[]{0.9232064370353699, 0.7116901972796634}, new double[]{0.5143345223969982, 0.3002246250401428}, new double[]{0.5288599466856345, 0.060844738321488734}, new double[]{0.2757711061732939, 0.35182387720466035}, new double[]{0.748436642200988, 0.35604060359789147}, new double[]{0.5278382257451956, 0.1336868380919396}, new double[]{0.8853548065761095, 0.4799427884455981}, new double[]{0.08085660376828996, 0.28283993567996113}, new double[]{0.052062014379409605, 0.5970403253019161}, new double[]{0.04755630739506045, 0.5760194383693558}, new double[]{0.26856590082629384, 0.7381464301973351}, new double[]{0.2535277110601625, 0.7814141428458035}, new double[]{0.564688099591698, 0.3070791153992046}, new double[]{0.3174112171781557, 0.36422890795194207}, new double[]{0.23057249138668134, 0.6853030437558428}, new double[]{0.17035695314693022, 0.8468502739167887}, new double[]{0.6099792517758672, 0.3603141192714616}, new double[]{0.034285726380005044, 0.8743953057078286}};
        LatLng[] coordinates = new LatLng[20];
        Random random = new Random();
        LatLng center = new LatLng(HYDERABAD_LAT, HYDERABAD_LNG);

        for (int i = 0; i < 20; i++) {
            double distanceKm = MIN_DISTANCE_KM + (MAX_DISTANCE_KM - MIN_DISTANCE_KM) * fixed[i][0];//random
            // .nextDouble();
            double bearing = 360 * fixed[i][1];//random.nextDouble();
//            System.out.println("[" + random.nextDouble() + "," + random.nextDouble() + "],");
            coordinates[i] = LatLngTool.travel(center, bearing, distanceKm, LengthUnit.KILOMETER);
        }

        return coordinates;
    }
}
