package com.example.runningapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityTrackingActivity extends AppCompatActivity {

    ImageButton btnBack;       // matches your XML
    Button btnStartStop;
    TextView tvTimer, tvSteps, tvDistance, tvCalories, tvElevation;

    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        btnStartStop = findViewById(R.id.btnStartStop);
        tvTimer = findViewById(R.id.tvTimer);
        tvSteps = findViewById(R.id.tvSteps);
        tvDistance = findViewById(R.id.tvDistance);
        tvCalories = findViewById(R.id.tvCalories);
        tvElevation = findViewById(R.id.tvElevation);

        // Back button closes activity
        btnBack.setOnClickListener(v -> finish());

        // Start/Stop tracking
        btnStartStop.setOnClickListener(v -> {
            if (!isTracking) {
                startTracking();
            } else {
                stopTracking();
            }
        });
    }

    private void startTracking() {
        isTracking = true;
        btnStartStop.setText("STOP");
        btnStartStop.setBackgroundTintList(getResources().getColorStateList(R.color.red_500, null));

        // TODO: Add step tracking logic here
        // For now, we can just simulate counting
        tvSteps.setText("1234");
        tvDistance.setText("1.2");
        tvCalories.setText("56 kcal");
        tvElevation.setText("5 m");
    }

    private void stopTracking() {
        isTracking = false;
        btnStartStop.setText("START");
        btnStartStop.setBackgroundTintList(getResources().getColorStateList(R.color.green_500, null));

        // TODO: Stop tracking logic
    }
}