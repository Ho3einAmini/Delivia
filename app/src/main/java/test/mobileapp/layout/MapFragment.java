package test.mobileapp.layout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import test.mobileapp.R;
import test.mobileapp.controller.MapController;
import test.mobileapp.model.DeliveryModel;

/**
 * Created by Hossein on 1/12/2018.
 **/

public class MapFragment extends Fragment implements   GoogleApiClient.OnConnectionFailedListener
{

    public static MapFragment newInstance()
    {
        return new MapFragment();
    }
    private MapView mMapView;
    private MapController mapController;
    private DeliveryModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_map, container, false);
        mMapView = rootView.findViewById(R.id.googleMapView);
        mMapView.onCreate(savedInstanceState);
        initializer();
        return rootView;
    }


    public void setModel(DeliveryModel model) {
        this.model = model;
    }

    @SuppressLint("NewApi")
    void initializer() {
        try {
            MapsInitializer.initialize(Objects.requireNonNull(this.getActivity()));
            MapController.initialize(this.getActivity());
        } catch (Exception e)
        {
            e.getStackTrace();
        }
        initMap();
    }

    void  initMap()
    {
        if(mapController == null)
            mapController = new MapController(mMapView, new MapController.MapControllerReady() {
                @Override
                public void already(MapController controller) {
                    buildShowPosition();
                }
            }, getActivity());
    }

    void buildShowPosition()
    {
        mapController.animateTo(model.getPosition(), 14, new MapController.ChangePosition() {
            @Override
            public void changed(GoogleMap map, CameraPosition p) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(model.getPosition());
                if(model.getDescription() != null)
                    markerOptions.title(model.getDescription());
                mapController.addMarker(markerOptions, true, new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        DetailsBS detailsBS = new DetailsBS();
                        detailsBS.setModel(model);
                        detailsBS.show(getFragmentManager(), "details");
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try
        {
            if(mMapView != null)
                mMapView.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
