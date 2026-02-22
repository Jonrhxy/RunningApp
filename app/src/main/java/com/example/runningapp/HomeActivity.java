package com.example.runningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    LinearLayout btnAddExpense, btnStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnStartActivity = findViewById(R.id.btnStartActivity);

        btnAddExpense.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, AddExpenseActivity.class)));

        btnStartActivity.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ActivityTrackingActivity.class)));
    }
}