package findme.com.example.cloud.findme;




import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashSet;
import java.util.Set;

import findme.com.example.cloud.findme.adapter.TabsPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static FragmentManager fragmentManager;
    TabsPagerFragmentAdapter adapter;
    GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private final int LAYOUT = R.layout.activity_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeDefault);

        setContentView(LAYOUT);
        fragmentManager =getSupportFragmentManager();
        initDrawerLayout();

        initToolBar();
        initTabs();

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.current_location_street){
                    Toast.makeText(getApplicationContext(), "Street", Toast.LENGTH_SHORT).show();
                    MapActivity.updateLocation(getApplicationContext());
                }else if(item.getItemId()==R.id.current_location_map){
                    Toast.makeText(getApplicationContext(), "Map", Toast.LENGTH_SHORT).show();
                    MapsActivity.updateLocation(getApplicationContext());
                }
                return true;
            }
        });

        toolbar.inflateMenu(R.menu.action_bar_menu);

    }



    private void initTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        viewPager = (ViewPager) findViewById(R.id.viewPager);

         adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void initDrawerLayout() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);}
    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sp.edit();
            Log.v("Location","Current location - lat: "+mLastLocation.getLatitude()+" lon: "+mLastLocation.getLongitude());
            editor.putString(getString(R.string.latitude_pref), mLastLocation.getLatitude()+"");
            editor.putString(getString(R.string.longitude_pref),mLastLocation.getLongitude()+"");
            editor.commit();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
