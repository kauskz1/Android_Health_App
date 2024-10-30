package com.kaustubh.fisac;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

/** @noinspection ALL*/
public class HomeActivity extends AppCompatActivity {
    private TextView tvBat;
    private Button userProfileButton, assessmentButton, dietButton,dietGuidelinesButton, physicalActivityButton, bmiButton, callButton, signOutButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        tvBat = findViewById(R.id.homeTvBat);
        userProfileButton = findViewById(R.id.homeBtnUserProfile);
        assessmentButton = findViewById(R.id.homeBtnAssessment);
        dietButton = findViewById(R.id.homeBtnDietInfo);
        dietGuidelinesButton = findViewById(R.id.homeBtnDietGuidelines);
        physicalActivityButton = findViewById(R.id.homeBtnPhysicalActivity);
        bmiButton = findViewById(R.id.homeBtnBmi);
        callButton = findViewById(R.id.homeBtnCall);
        signOutButton = findViewById(R.id.homeBtnSignOut);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = (int)(level * 100 / (float)scale);
        String sBat = String.valueOf(batteryPct);
        tvBat.setText(sBat+"%");

        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AssessmentActivity.class);
                startActivity(intent);
            }
        });
        dietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DietActivity.class);
                startActivity(intent);
            }
        });
        dietGuidelinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DietGuidelinesActivity.class);
                startActivity(intent);
            }
        });
        physicalActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PhysicalActivity.class);
                startActivity(intent);
            }
        });
        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BmiActivity.class);
                startActivity(intent);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "09980772661"));
                startActivity(intent);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutUser();
            }
        });


    }

    private void signOutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("app.user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "");
        editor.putInt("age", 0);
        editor.putInt("height", 0);
        editor.putInt("weight", 0);
        editor.apply();

        firebaseAuth.signOut();
        Toast.makeText(HomeActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}