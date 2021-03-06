package com.bondye_toujou_bon.betterchoice;

import android.content.Intent;
import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HalfReviews extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,GoogleMap.OnMarkerClickListener  {

    private GoogleMap mMap;
    private Marker myMarker;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mLocationTableRef = mRootRef.child("LocationTable");
    DatabaseReference mLocationRef;

    ArrayList<String> Usernames, Comments=new ArrayList<>();
    ArrayList<Integer> Overallscores, Images=new ArrayList<>();


    ArrayList<Integer> Bugscores,Neighborscores, Drugscores=new ArrayList<>();

    CustomAdapter customAdapter;
    String address;


    ListView reviews;

    public static int[] imgId={R.drawable.bobs, R.drawable.pablos, R.drawable.bettys,R.drawable.daisys};
    public static int[] totalStars ={0, 5, 3, 4};

    TextView overallScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half_reviews);




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        reviews = (ListView)findViewById(R.id.listView);
        overallScores = (TextView)findViewById(R.id.overallScore);


        double average = average(totalStars);
        overallScores.setText(Double.toString(average));


       //mLocationRef=mLocationTableRef.child(address);
       // mLocationRef=mLocationTableRef.child("Location3");


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        Intent intent = getIntent();


        address = intent.getStringExtra("Address");
        double latitude = intent.getDoubleExtra("Latitude", 0.0);
        double longitude = intent.getDoubleExtra("Longitude", 0.0);

        final String addressFnl = address;

        LatLng searchedCoord = new LatLng( latitude, longitude);
        myMarker=mMap.addMarker(new MarkerOptions().position(searchedCoord).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchedCoord, 15));

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);










        customAdapter=new CustomAdapter(this, Usernames, Overallscores, Comments, Images);
        ArrayList<String> noRecords= new ArrayList<>();
        noRecords.add("There are no reviews!");
        final ArrayAdapter<String> itemsAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noRecords);



        mLocationTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(addressFnl)){
                    reviews.setAdapter(customAdapter);
                    mLocationRef=mLocationTableRef.child(addressFnl);
                    addLocations();
                }
                else{
                    reviews.setAdapter(itemsAdapter);
                    //Toast.makeText(HalfReviews.this, "Create review", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }

    @Override
    public void onMapClick(LatLng latLng) {
        finish();
    }

    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(myMarker))
        {
            finish();
            return true;
        }
        return false;
    }



    public void addRecClick(View view){
        Intent intent2=new Intent(this,AddReview.class);
        intent2.putExtra("Address",address);
        startActivity(intent2);
    }


    public void addComment(DataSnapshot dataSnapshot){
        HashMap<String, Object> data = (HashMap<String, Object>)dataSnapshot.getValue();
        String username = String.valueOf(data.get("Username"));
        String comment = String.valueOf(data.get("Comment"));
        Integer rating = Integer.valueOf((String)data.get("Overall Rating"));
        Usernames.add(username);
        Overallscores.add(rating);
        Comments.add(comment);
    }

    public void changeComment(DataSnapshot dataSnapshot){
        HashMap<String, Object> data = (HashMap<String, Object>)dataSnapshot.getValue();
        String username = String.valueOf(data.get("Username"));
        String comment = String.valueOf(data.get("Comment"));
        Integer rating = Integer.valueOf((String)data.get("Overall Rating"));
        Usernames.add(username);
        Overallscores.add(rating);
        Comments.add(comment);
    }

    public void removeComment(DataSnapshot dataSnapshot){

    }

    public void addLocations(){


        Images.add(R.drawable.bobs);
        Images.add(R.drawable.pablos);

        mLocationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addComment(dataSnapshot);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public double average (int[] arr){
        int sum=0;
        for (int data:arr){
            sum+=data;
        }
        double avg = 1.0d * sum / arr.length;
        return avg;
    }
}
