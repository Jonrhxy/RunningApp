package com.example.runningapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.models.ExpenseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddExpenseActivity extends AppCompatActivity {

    ImageButton btnBack;
    Button btnSaveExpense;
    EditText etAmount, etDescription;
    TextView tvWeeklyTotal, tvMonthlyTotal;
    RecyclerView rvExpenses;

    ArrayList<ExpenseModel> expenseList;
    ExpenseAdapter adapter;

    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Correct types!
        btnBack = findViewById(R.id.btnBack); // ImageButton
        btnSaveExpense = findViewById(R.id.btnSaveExpense); // Button
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        tvWeeklyTotal = findViewById(R.id.tvWeeklyTotal);
        tvMonthlyTotal = findViewById(R.id.tvMonthlyTotal);
        rvExpenses = findViewById(R.id.rvExpenses);

        sharedPreferences = getSharedPreferences("ExpenseData", MODE_PRIVATE);
        gson = new Gson();

        loadExpenses();

        adapter = new ExpenseAdapter(expenseList);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(adapter);

        calculateTotals();

        btnBack.setOnClickListener(v -> finish());
        btnSaveExpense.setOnClickListener(v -> saveExpense());
    }

    private void loadExpenses() {
        String json = sharedPreferences.getString("expenses", null);
        Type type = new TypeToken<ArrayList<ExpenseModel>>() {}.getType();
        expenseList = gson.fromJson(json, type);
        if (expenseList == null) expenseList = new ArrayList<>();
    }

    private void saveExpense() {
        String amountText = etAmount.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (amountText.isEmpty()) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ExpenseModel expense = new ExpenseModel(amount, description, currentDate);
        expenseList.add(expense);

        sharedPreferences.edit().putString("expenses", gson.toJson(expenseList)).apply();

        adapter.notifyDataSetChanged();
        calculateTotals();

        etAmount.setText("");
        etDescription.setText("");
    }

    private void calculateTotals() {
        double weeklyTotal = 0;
        double monthlyTotal = 0;

        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        for (ExpenseModel expense : expenseList) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expense.getDate());
                Calendar expenseCal = Calendar.getInstance();
                expenseCal.setTime(date);

                if (expenseCal.get(Calendar.YEAR) == currentYear) {
                    if (expenseCal.get(Calendar.WEEK_OF_YEAR) == currentWeek)
                        weeklyTotal += expense.getAmount();
                    if (expenseCal.get(Calendar.MONTH) == currentMonth)
                        monthlyTotal += expense.getAmount();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tvWeeklyTotal.setText("₱" + String.format("%.2f", weeklyTotal));
        tvMonthlyTotal.setText("₱" + String.format("%.2f", monthlyTotal));
    }
}