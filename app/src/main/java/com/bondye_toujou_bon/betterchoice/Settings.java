package com.bondye_toujou_bon.betterchoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserTablesRef = mRootRef.child("UserTable");

    EditText editTextUsername;
    Button submitBtn;

    String username, userId;

    public void onSubmit(View view){
        DatabaseReference newRef = mUserTablesRef.child(userId);
        newRef.child("Username").setValue(username);
        Toast.makeText(this, "Username is now set to: "+username, Toast.LENGTH_SHORT).show();
    }

    public void checkClick(View view){
        username =String.valueOf(editTextUsername.getText()).trim();
        mUserTablesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)){
                    if(!submitBtn.isClickable()){
                        Toast.makeText(Settings.this, "Username already taken!", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(username.equals("")){
                    Toast.makeText(Settings.this, "Please enter a username!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Settings.this, "Username Available!", Toast.LENGTH_SHORT).show();
                    submitBtn.setClickable(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent=getIntent();
        userId=intent.getStringExtra("UserID");

        editTextUsername = findViewById(R.id.editTextUsername);
        submitBtn = findViewById(R.id.buttonSignIn);
           View.OnFocusChangeListener listener;

        listener =new View.OnFocusChangeListener(){
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        };

        editTextUsername.setOnFocusChangeListener(listener);
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                submitBtn.setClickable(false);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}