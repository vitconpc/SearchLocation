package vn.com.example.locationbase.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Function {
    public static double CalculationByDistance(double lat1, double lng1,
                                               double lat2, double lng2) {
        int Radius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);

        return Radius * c;
    }

    @SuppressLint("MissingPermission")
    public static void getCurrentLocation(Context context, OnSuccessListener<Location> callback) {
        LocationServices.getFusedLocationProviderClient(context)
                .getLastLocation()
                .addOnSuccessListener(callback);
    }

    public static String formatDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
