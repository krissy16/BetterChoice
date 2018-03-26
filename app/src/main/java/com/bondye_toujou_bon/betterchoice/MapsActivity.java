package com.bondye_toujou_bon.betterchoice;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    private Address location;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        LatLng address = getLocationFromAddress(this, "6381 Berrybush Court Gilroy California 95020");
//        mMap.addMarker(new MarkerOptions().position(address).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 15));


        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        mMap.setOnMarkerClickListener(this);



    }

    @Override
    public boolean onQueryTextSubmit(String input) {
        mMap.clear();
        LatLng location = getLocationFromAddress(this, input);
        myMarker=mMap.addMarker(new MarkerOptions().position(location).title(input).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        /*LatLng ocala = new LatLng(29.186756, -82138272);
        mMap.addMarker(new MarkerOptions().position(ocala).title(s)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ocala, 15));*/
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(myMarker))
        {
            myMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            Intent intent = new Intent(getBaseContext(), HalfReviews.class);
            intent.putExtra("Latitude", location.getLatitude());
            intent.putExtra("Longitude", location.getLongitude());
            intent.putExtra("Address",location.getAddressLine(0));
            startActivity(intent);
            return true;
        }
        return false;
    }
}
