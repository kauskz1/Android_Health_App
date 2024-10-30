package com.kaustubh.fisac;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AssessmentActivity extends AppCompatActivity {
    private EditText threeMonthWeight, threeMonthBMI;
    private EditText sixMonthWeight, sixMonthBMI;
    private EditText twelveMonthWeight,twelveMonthBMI;
    private Button saveAssessmentButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment);

        databaseReference = FirebaseDatabase.getInstance().getReference("Assessments");

        threeMonthWeight = findViewById(R.id.threeMonthWeight);
        threeMonthBMI = findViewById(R.id.threeMonthBMI);

        sixMonthWeight = findViewById(R.id.sixMonthWeight);
        sixMonthBMI = findViewById(R.id.sixMonthBMI);

        twelveMonthWeight = findViewById(R.id.twelveMonthWeight);
        twelveMonthBMI = findViewById(R.id.twelveMonthBMI);

        saveAssessmentButton = findViewById(R.id.assBtnSave);
        saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAssessmentData();
            }
        });
    }

    private void saveAssessmentData() {
        String weight3M = threeMonthWeight.getText().toString();
        String bmi3M = threeMonthBMI.getText().toString();

        String weight6M = sixMonthWeight.getText().toString();
        String bmi6M = sixMonthBMI.getText().toString();

        String weight12M = twelveMonthWeight.getText().toString();
        String bmi12M = twelveMonthBMI.getText().toString();

        if (TextUtils.isEmpty(weight3M) && TextUtils.isEmpty(bmi3M) &&
                TextUtils.isEmpty(weight6M)&& TextUtils.isEmpty(bmi6M) &&
                TextUtils.isEmpty(weight12M)&& TextUtils.isEmpty(bmi12M)) {
            Toast.makeText(this, "Please fill in at least one field.", Toast.LENGTH_SHORT).show();
            return;
        }

        String assessmentId = databaseReference.push().getKey();

        Map<String, Object> assessmentData = new HashMap<>();
        assessmentData.put("3MonthWeight", weight3M);
        assessmentData.put("3MonthBMI", bmi3M);

        assessmentData.put("6MonthWeight", weight6M);
        assessmentData.put("6MonthBMI", bmi6M);

        assessmentData.put("12MonthWeight", weight12M);
        assessmentData.put("12MonthBMI", bmi12M);

        databaseReference.child(assessmentId).setValue(assessmentData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AssessmentActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(AssessmentActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
                });
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }

    private void clearFields() {
        threeMonthWeight.setText("");
        threeMonthBMI.setText("");

        sixMonthWeight.setText("");
        sixMonthBMI.setText("");

        twelveMonthWeight.setText("");
        twelveMonthBMI.setText("");
    }
}
