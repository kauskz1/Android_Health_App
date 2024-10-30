package com.kaustubh.fisac;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class BmiActivity extends AppCompatActivity {
    TextView tvHeight,tvWeight,tvBmi,tvBmiResult;
    EditText etHeight,etWeight;
    Button btnCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        SharedPreferences sharedPreferences = getSharedPreferences("app.user", MODE_PRIVATE);

        tvHeight = findViewById(R.id.bmiUserHeight);
        tvWeight = findViewById(R.id.bmiUserWeight);
        tvBmi = findViewById(R.id.bmiUserBmi);
        tvBmiResult = findViewById(R.id.bmiResult);
        etHeight = findViewById(R.id.bmiEtHeight);
        etWeight = findViewById(R.id.bmiEtWeight);
        btnCalculate = findViewById(R.id.bmiBtnCalculate);

        int height = sharedPreferences.getInt("height", 0);
        int weight = sharedPreferences.getInt("weight", 0);

        String sHeight = "Your height is "+height+"cm";
        String sWeight = "Your weight is "+weight+"kg";
        tvHeight.setText(sHeight);
        tvWeight.setText(sWeight);
        DecimalFormat decfor = new DecimalFormat("0.00");
        float bmi = calculateBMI(height,weight);
        String formattedBmi = decfor.format(bmi);
        String sBmi = "BMI = "+formattedBmi;
        tvBmi.setText(sBmi);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sHeight = etHeight.getText().toString().trim();
                String sWeight = etWeight.getText().toString().trim();
                if (TextUtils.isEmpty(sHeight)) {
                    etHeight.setError("Height is required.");
                    return;
                }
                if(TextUtils.isEmpty(sWeight)) {
                    etWeight.setError("Weight is required.");
                    return;
                }
                int height = Integer.parseInt(sHeight);
                int weight = Integer.parseInt(sWeight);

                float bmi = calculateBMI(height,weight);
                String formattedCalcBmi = decfor.format(bmi);
                String sResult = "BMI from input values = "+formattedCalcBmi;
                tvBmiResult.setText(sResult);
            }
        });
    }

    private float calculateBMI(int height, int weight) {
        if(height<=0)return 0;
        float heightInM = (float)height / 100;
        return weight/(heightInM * heightInM);
    }
}