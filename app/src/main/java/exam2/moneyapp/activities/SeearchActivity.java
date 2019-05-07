package exam2.moneyapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import exam2.moneyapp.dto.AccDto;
import exam2.moneyapp.others.Const;
import exam2.moneyapp.others.MyLocalDb;
import exam2.moneyapp.R;

public class SeearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    List<AccDto> accounts = new ArrayList<>();
    double total;

    double add = 0,minus = 0;

    private Button sDate,eDate;
    private DatePickerDialog datePickerDialog = null;
    private int rtype = 0;
    private String from = "",to = "";
    private TableLayout tableLayout;


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
        if(rtype==0) {
            from = day + "-" + month1 + "-" + year;
            sDate.setText(from);
        }
        else{
            to = day + "-" + month1 + "-" + year;
            eDate.setText(to);
        }
        datePickerDialog = null;
    }

    public void ResetNow(View v){
        try {
            from = "";
            to = "";
            sDate.setText("Start Date");
            eDate.setText("End Date");

             accounts= new MyLocalDb().getAccounts();
            if(accounts.size()>0){
                setUpData();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.srch_screen);

         tableLayout = findViewById(R.id.tableLayoutTransactions);
        sDate=findViewById(R.id.startDate);
        eDate=findViewById(R.id.endDate);

        accounts = new MyLocalDb().getAccounts();

        sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    rtype = 0;
                    datePickerDialog = new DatePickerDialog(SeearchActivity.this, SeearchActivity.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }
            }
        });

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    rtype = 1;
                    datePickerDialog = new DatePickerDialog(SeearchActivity.this, SeearchActivity.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }
            }
        });

       setUpData();
    }

    private void setUpData() {
        add = 0;
        minus = 0;
        total = 0;
        tableLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.table_row, null);

        TextView trn = (TextView) view.findViewById(R.id.col1);
        trn.setText("Date");
        TextView arr = (TextView) view.findViewById(R.id.txt_arr);
        arr.setText("Money");
        TextView dep = (TextView) view.findViewById(R.id.txt_dep);
        dep.setText("Reason");
        tableLayout.addView(view);
        for(AccDto accDto:accounts)
        {
            String date = accDto.getDate();
            double amount = Double.parseDouble(accDto.getAmount());
            String category = accDto.getCat();

            if (accDto.getType().equals(Const.added))
            {
                add+=Double.parseDouble(accDto.getAmount());
            }else {
                minus+=Double.parseDouble(accDto.getAmount());

            }
            view = inflater.inflate(R.layout.table_row, null);

            trn = (TextView) view.findViewById(R.id.col1);
            trn.setText(date + "");
            arr = (TextView) view.findViewById(R.id.txt_arr);
            arr.setText(String.format("%.2f", amount));
            dep = (TextView) view.findViewById(R.id.txt_dep);
            dep.setText(category + "");
            tableLayout.addView(view);
        }

        total = add-minus;
        TextView textViewBalance = findViewById(R.id.textViewBalance);
        textViewBalance.setText("Current Balance: $" + String.format("%.2f", total));
    }

    public void SearchNow(View v){
        try{
            if(from.length()>0) {
                if(to.length()>0) {
                    accounts= new MyLocalDb().SerachAcc(from,to);
                    if(accounts.size()>0){
                        setUpData();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Select End Date", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Please Select Start Date", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
