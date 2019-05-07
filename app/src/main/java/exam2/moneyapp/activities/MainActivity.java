package exam2.moneyapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import exam2.moneyapp.dto.AccDto;
import exam2.moneyapp.others.Const;
import exam2.moneyapp.others.MyLocalDb;
import exam2.moneyapp.others.Validations;
import exam2.moneyapp.R;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button editTextDate;
    private TextView textViewBalance;
    private EditText editTextAmount, editTextCategory;
    private String dateStr = "";
    private DatePickerDialog datePickerDialog = null;
    private TableLayout tableLayoutTransactions;
    List<AccDto> accounts = new ArrayList<>();
    double total;

    double add = 0, minus = 0;

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String day = dayOfMonth + "";
        String month1 = (month + 1) + "";

        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month1.length() == 1) {
            month1 = "0" + month1;
        }
        dateStr = day + "-" + month1 + "-" + year;
        editTextDate.setText(dateStr);
        datePickerDialog = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // Initialize the program and create the database if it doesn't exist
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setupNow();
    }
public void Search1(View v){
        startActivity(new Intent(getApplicationContext(),SeearchActivity.class));
}

    public void Chart1(View v){
        startActivity(new Intent(getApplicationContext(),GraphScreen.class));
    }

    private void setupNow() {
        try {
            add = 0;
            minus = 0;
            total = 0;
            accounts = new MyLocalDb().getAccounts();
            tableLayoutTransactions.removeAllViews();

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.table_row, null);

            TextView trn = (TextView) view.findViewById(R.id.col1);
            trn.setText("Date");
            TextView arr = (TextView) view.findViewById(R.id.txt_arr);
            arr.setText("Money");
            TextView dep = (TextView) view.findViewById(R.id.txt_dep);
            dep.setText("Reason");
            tableLayoutTransactions.addView(view);
            for (AccDto accDto : accounts) {
                String date = accDto.getDate();
                double amount = Double.parseDouble(accDto.getAmount());
                String category = accDto.getCat();

                if (accDto.getType().equals(Const.added)) {
                    add += Double.parseDouble(accDto.getAmount());
                } else {
                    minus += Double.parseDouble(accDto.getAmount());

                }

                view = inflater.inflate(R.layout.table_row, null);

                trn = (TextView) view.findViewById(R.id.col1);
                trn.setText(date + "");
                arr = (TextView) view.findViewById(R.id.txt_arr);
                arr.setText(String.format("%.2f", amount));
                dep = (TextView) view.findViewById(R.id.txt_dep);
                dep.setText(category + "");
                tableLayoutTransactions.addView(view);
            }

            total = add - minus;
            TextView textViewBalance = findViewById(R.id.textViewBalance);
            textViewBalance.setText("Current Balance: $" + String.format("%.2f", total));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        editTextDate = findViewById(R.id.editTextDate);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextCategory = findViewById(R.id.editTextCategory);
        tableLayoutTransactions = findViewById(R.id.tableLayoutTransactions);
        textViewBalance = findViewById(R.id.textViewBalance);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }

            }
        });
    }

    // Record new income
    public void addIncome(View view) {
        addTransaction(true);
    }

    // Record new expense
    public void addExpense(View view) {
        addTransaction(false);
    }

    // displays the history
    public void viewHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void addTransaction(boolean isIncome) {


        if (Validations.valEdit(MainActivity.this, editTextAmount, "Amount")
                && Validations.valEdit(MainActivity.this, editTextCategory, "Category")) {
            if (dateStr.length() > 0) {
                AccDto ub = new AccDto();

                if (isIncome == false) {
                    double balance = new MyLocalDb().getBalance();
                    double amt = Double.parseDouble(editTextAmount.getText().toString());
                    if (amt > balance) {
                        Toast.makeText(getApplicationContext(), "You Have not Sufficient Balance", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                final int min = 200;
                final int max = 999999;
                final int random = new Random().nextInt((max - min) + 1) + min;

                ub.setTransId("" + random);
                ub.setCat(editTextCategory.getText().toString());
                ub.setDate(editTextDate.getText().toString());
                if (isIncome) {
                    ub.setType(Const.added);
                } else {
                    ub.setType(Const.spent);
                }
                ub.setAmount(editTextAmount.getText().toString());

                new MyLocalDb().saveAccount(ub);

                Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                editTextAmount.setText("");
                editTextDate.setText("Choose Date");
                editTextCategory.setText("");
                dateStr = "";
                setupNow();
            } else {
                Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
