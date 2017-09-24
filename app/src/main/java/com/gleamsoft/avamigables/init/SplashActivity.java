package com.gleamsoft.avamigables.init;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gleamsoft.avamigables.R;
import com.gleamsoft.avamigables.home.MainActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;


public class SplashActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser != null) {
        startActivity(new Intent(this,MainActivity.class));
    } else {
        startActivity(new Intent(this,LoginActivity.class));
    }
    
}
}
