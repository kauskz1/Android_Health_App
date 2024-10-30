package com.kaustubh.fisac;
;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {
    private EditText etName, etAge, etHeight, etWeight;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnSave = findViewById(R.id.btnSave);

        loadUserProfile();

        btnSave.setOnClickListener(v -> saveUserProfile());
    }

    private void saveUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("app.user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = etName.getText().toString();
        String sAge = etAge.getText().toString();
        String sHeight = etHeight.getText().toString();
        String sWeight = etWeight.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required.");
            return;
        }
        if (TextUtils.isEmpty(sAge)) {
            etAge.setError("Age is required.");
            return;
        }
        if (TextUtils.isEmpty(sHeight)) {
            etHeight.setError("Height is required.");
            return;
        }
        if (TextUtils.isEmpty(sWeight)) {
            etWeight.setError("Weight is required.");
            return;
        }
        int age = Integer.parseInt(sAge);
        int height = Integer.parseInt(sHeight);
        int weight = Integer.parseInt(sWeight);
        float bmi = calculateBMI(height,weight);
        editor.putString("name", name);
        editor.putInt("age", age);
        editor.putInt("height", height);
        editor.putInt("weight", weight);
        editor.apply();

        Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();



        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    private void loadUserProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("app.user", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        int age = sharedPreferences.getInt("age", 0);
        int height = sharedPreferences.getInt("height", 0);
        int weight = sharedPreferences.getInt("weight", 0);

        etName.setText(name);
        etAge.setText(age>0?String.valueOf(age):"");
        etHeight.setText(height>0?String.valueOf(height):"");
        etWeight.setText(weight>0?String.valueOf(weight):"");
    }

    private float calculateBMI(int height, int weight) {
        if(height<=0)return 0;
        float heightInM = (float)height / 100;
        return weight/(heightInM * heightInM);
    }
}