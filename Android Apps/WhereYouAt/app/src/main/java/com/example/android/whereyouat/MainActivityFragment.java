package com.example.android.whereyouat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

    Button getLocation;
    Button startIntent;
    TextView currLocation;

    GPSTracker gps;
    double latitude = 0;
    double longitude = 0;
    String address = "Unable to find the address!";
    String gmapsLink = "googlemaps.com";

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        getLocation = (Button) rootView.findViewById(R.id.getLocation);
        startIntent = (Button) rootView.findViewById(R.id.startIntent);
        currLocation = (TextView) rootView.findViewById(R.id.currLocation);

        getLocation.setOnClickListener(this);
        startIntent.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.getLocation:

                gps = new GPSTracker(getActivity());

                if(gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    address = gps.getAddress(latitude, longitude);
                    //gmapsLink = "https://www.google.com/maps/preview/@" + latitude + "," + longitude + "," + "8z";
                    gmapsLink = "http://maps.google.com/maps?q=" + latitude + "," + longitude;

                    currLocation.setText("Your Location\nLat: " + latitude + "\nLong: " + longitude + "\n\nAddress \n" + address);

                } else {
                    gps.showSettingsAlert();
                }

                break;
            case R.id.startIntent:
                //Toast.makeText(getActivity().getBaseContext() , "msg", Toast.LENGTH_LONG).show();

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Hey I am around:\n" + address + "\n\nGoogle Maps Link: " + gmapsLink + "<a href='http://maps.google.com'>Google Maps</a>");

                startActivity(sendIntent);
        }
    }

}
