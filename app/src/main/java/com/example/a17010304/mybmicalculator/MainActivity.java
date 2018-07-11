package com.example.a17010304.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    TextView tvBMI, tvDate;
    Button btCalc, btReset;
    Calendar now = Calendar.getInstance();

    String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etHeight = findViewById(R.id.editTextHeight);
        etWeight = findViewById(R.id.editTextWeight);
        tvBMI  = findViewById(R.id.textViewBMI);
        tvDate = findViewById(R.id.textViewDate);
        btCalc = findViewById(R.id.buttonCalculate);
        btReset = findViewById(R.id.buttonReset);


        btCalc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

              String weight =  etWeight.getText().toString().trim();
            String height =  etWeight.getText().toString().trim();
            float dWeight = Float.parseFloat(weight);
            float dHeight = Float.parseFloat(height);
            float bmi = dWeight/(dHeight*dHeight);


            tvDate.setText("Last Calculated Date: "+ datetime);
            tvBMI.setText("Last calculated BMI: " + bmi);

            }
        });

        btReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                etHeight.setText(null);
                etWeight.setText(null);
                tvBMI.setText(null);
                tvDate.setText(null);
            }
        });

    }

   @Override
    protected void onPause() {
        super.onPause();
        String weight =  etWeight.getText().toString().trim();
        String height =  etWeight.getText().toString().trim();
        float dWeight = Float.parseFloat(weight);
        float dHeight = Float.parseFloat(height);
        float bmi = (dHeight*dHeight)/dWeight;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefs.edit();
         prefEdit.putFloat("weight", dWeight);
       prefEdit.putFloat("height", dHeight);
        prefEdit.putFloat("bmi", bmi);
        prefEdit.putString("date", datetime);
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        String weight =  etWeight.getText().toString().trim();
        String height =  etWeight.getText().toString().trim();
        float dWeight = Float.parseFloat(weight);
        float dHeight = Float.parseFloat(height);
        float bmi = (dHeight*dHeight)/dWeight;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float resumeWeight = prefs.getFloat("weight", dWeight);
        Float resumeHeight = prefs.getFloat("height" , dHeight);
        Float resumeBMI =  prefs.getFloat("bmi", bmi);
        String resumeDate = prefs.getString("date", datetime);

        etWeight.setText(String.format("%.f", resumeWeight));
        etHeight.setText(String.format("%.f", resumeHeight));
        tvDate.setText(resumeDate);
        tvBMI.setText(String.format("%.2f", resumeBMI));



    }

}
