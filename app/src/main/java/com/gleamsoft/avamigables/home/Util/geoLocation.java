package com.gleamsoft.avamigables.home.Util;

/**
 * Created by Developer on 23/09/2017.
 */

public class geoLocation {
public static String formatNumber(double distance) {
    String unit = "m";
    if (distance < 1) {
        distance *= 1000;
        unit = "mm";
    } else if (distance > 1000) {
        distance /= 1000;
        unit = "km";
    }
    
    return String.format("%4.3f%s", distance, unit);
}
    
    
}