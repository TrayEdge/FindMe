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


import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;


import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;


public class MapsActivity extends Fragment implements OnStreetViewPanoramaReadyCallback {
    public static final int LAYOUT = R.layout.activity_maps;
    private View view;
    private static StreetViewPanorama streetPanorama;
    public static MapsActivity getInstance() {
        Bundle args = new Bundle();
        MapsActivity exampleFragment = new MapsActivity();
        exampleFragment.setArguments(args);
        return exampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public static void updateLocation(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        double lat = Double.parseDouble(sharedPreferences.getString(context.getString(R.string.latitude_pref), "-33.87365"));
        double lon = Double.parseDouble(sharedPreferences.getString(context.getString(R.string.longitude_pref),"151.20689"));
        Log.v("Location",lat+" "+lon);
       streetPanorama.setPosition(new LatLng(lat,lon));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) fm
                        .findFragmentById(R.id.map);
        if(streetViewPanoramaFragment==null){
            streetViewPanoramaFragment = SupportStreetViewPanoramaFragment.newInstance();
            fm.beginTransaction().replace(R.id.map,streetViewPanoramaFragment).commit();
        }
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);


    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetPanorama=streetViewPanorama;
        streetViewPanorama.setPosition(new LatLng(-33.87365, 151.20689));


    }
}
