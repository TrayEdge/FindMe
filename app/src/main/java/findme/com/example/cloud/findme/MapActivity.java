package findme.com.example.cloud.findme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;


public class MapActivity extends Fragment implements
        OnMapReadyCallback {
    public static final int LAYOUT = R.layout.activity_map;
    private View view;

    private static GoogleMap mMap;
    public static MapActivity getInstance() {
        Bundle args = new Bundle();
        MapActivity exampleFragment = new MapActivity();
        exampleFragment.setArguments(args);
        return exampleFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) fm
                        .findFragmentById(R.id.map2);
        if(mapFragment==null){
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map2,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(-33.87365, 151.20689))
                .title("Marker"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.87365, 151.20689),12.0f));

    }
    public static void updateLocation(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        double lat = Double.parseDouble(sharedPreferences.getString(context.getString(R.string.latitude_pref), "-33.87365"));
        double lon = Double.parseDouble(sharedPreferences.getString(context.getString(R.string.longitude_pref),"151.20689"));
        Log.v("Location",lat+" "+lon);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon))
                .title("Misha is here"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),
                12.0f));
    }

}
