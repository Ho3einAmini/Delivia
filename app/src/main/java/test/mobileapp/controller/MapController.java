package test.mobileapp.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapController {
    private Context context;
    private GoogleMap map;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    public MapController(MapView mapView, final MapControllerReady callback, final Context context) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapController.this.map = googleMap;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        checkLocationPermission();
                if (callback != null) {
                    callback.already(MapController.this);
                }
            }
        });
        this.context = context;
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(context)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions((Activity)context,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions((Activity)context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    public static void initialize(Context context) {
        MapsInitializer.initialize(context);
    }


    public void animateTo(LatLng latLng, int zoom, final ChangePosition callback) {
        if (callback != null)
            callback.changed(map, null);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    public void addMarker(MarkerOptions opts, boolean showInfoWindow, final GoogleMap.OnMarkerClickListener listener) {
        Marker marker = map.addMarker(opts);
        map.setOnMarkerClickListener(listener);
        if (showInfoWindow)
            marker.showInfoWindow();
    }


    public interface MapControllerReady {
         void already(MapController controller);
    }

    public interface ChangePosition {
         void changed(GoogleMap map, CameraPosition position);
    }
}