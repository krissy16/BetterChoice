package com.bondye_toujou_bon.betterchoice;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestingActivity extends AppCompatActivity {

   DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
   DatabaseReference mLocationTableRef = mRootRef.child("LocationTable");
//    DatabaseReference mLocationRef;
//
//    DatabaseReference mUsersRef = mRootRef.child("UserTable");
//
//    ListView listView;
//    ArrayList<String> names=new ArrayList<>();

    EditText usernameTxt;
    EditText commentTxt;

    Spinner overallRatingSpinner;
    Spinner bugRatingSpinner;
    Spinner neighborRatingSpinner;
    Spinner drugRatingSpinner;

    String total;
    String bug;
    String nei;
    String drug;
    String username;
    String comment;

    public void onSubmit(View view){
        total = overallRatingSpinner.getSelectedItem().toString();
        bug = bugRatingSpinner.getSelectedItem().toString();
        nei = neighborRatingSpinner.getSelectedItem().toString();
        drug = drugRatingSpinner.getSelectedItem().toString();
        username=String.valueOf(usernameTxt.getText());
        comment=String.valueOf(commentTxt.getText());

        DatabaseReference newRef = mLocationTableRef.child("Location3").push();
        newRef.child("Username").setValue(username);
        newRef.child("Comment").setValue(comment);
        newRef.child("Bug Rating").setValue(bug);
        newRef.child("Neighbor Rating").setValue(nei);
        newRef.child("Drug Rating").setValue(drug);
        newRef.child("Overall Rating").setValue(total);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);



        String[] spinnerArray =  {"0","1","2","3","4","5"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        usernameTxt= (EditText)findViewById(R.id.Username);
        commentTxt = (EditText)findViewById(R.id.comment);

        overallRatingSpinner = (Spinner) findViewById(R.id.overallRating);
        bugRatingSpinner= (Spinner) findViewById(R.id.bugRating);
        neighborRatingSpinner = (Spinner) findViewById(R.id.neighborRating);
        drugRatingSpinner = (Spinner) findViewById(R.id.drugRating);

        overallRatingSpinner.setAdapter(adapter);
        bugRatingSpinner.setAdapter(adapter);
        neighborRatingSpinner.setAdapter(adapter);
        drugRatingSpinner.setAdapter(adapter);

        View.OnFocusChangeListener listener;

        listener =new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
               if (!hasFocus) {
                  hideKeyboard(v);
               }
            }
        };

                usernameTxt.setOnFocusChangeListener(listener);
                commentTxt.setOnFocusChangeListener(listener);

//        listView=findViewById(R.id.listView);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
//        listView.setAdapter(arrayAdapter);
//
//        String address= "Location1";
//        mLocationRef=mLocationTableRef.child(address);
//
//        mLocationRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                HashMap<String, Object> data = (HashMap<String, Object>)dataSnapshot.getValue();
//                String nickname = String.valueOf(data.get("BugRating"));
//                String post = String.valueOf(nickname);
//                names.add(post);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
