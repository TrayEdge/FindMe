package findme.com.example.cloud.findme.adapter;



import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import findme.com.example.cloud.findme.MapActivity;
import findme.com.example.cloud.findme.MapsActivity;

/**
 * Created by Cloud on 14.09.2015.
 */
public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs = new String[]{

                "Street panorama",
                "Map"

        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return MapsActivity.getInstance();

            case 1:

                return MapActivity.getInstance();



        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

}
