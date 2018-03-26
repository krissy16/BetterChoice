package com.bondye_toujou_bon.betterchoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickGuest(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void clickLogin(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void clickSignUp(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
