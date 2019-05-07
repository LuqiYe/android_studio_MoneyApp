package exam2.moneyapp.activities;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import exam2.moneyapp.dto.AccDto;
import exam2.moneyapp.others.Const;
import exam2.moneyapp.others.MyLocalDb;
import exam2.moneyapp.R;

public class GraphScreen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    List<AccDto> accounts = new ArrayList<>();
    double total;

    double add = 0,minus = 0;

    private Button sDate,eDate;
    private DatePickerDialog datePickerDialog = null;
    private int rtype = 0;
    private String from = "",to = "";
    private PieChart chart1;


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
                calculateNow();
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
        setContentView(R.layout.graph);

        chart1 = findViewById(R.id.chart1);
        sDate=findViewById(R.id.startDate);
        eDate=findViewById(R.id.endDate);

        accounts = new MyLocalDb().getAccounts();

        initChart();

        sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    rtype = 0;
                    datePickerDialog = new DatePickerDialog(GraphScreen.this, GraphScreen.this, myCalendar
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
                    datePickerDialog = new DatePickerDialog(GraphScreen.this, GraphScreen.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }
            }
        });

        calculateNow();
    }


    private void initChart() {
        try{
            chart1.setUsePercentValues(true);
            chart1.getDescription().setEnabled(false);
            chart1.setExtraOffsets(5, 10, 5, 5);

            chart1.setDragDecelerationFrictionCoef(0.95f);

            chart1.setCenterText("Add / Spent Graph");

            chart1.setDrawHoleEnabled(true);
            chart1.setHoleColor(Color.WHITE);

            chart1.setTransparentCircleColor(Color.WHITE);
            chart1.setTransparentCircleAlpha(110);

            chart1.setHoleRadius(58f);
            chart1.setTransparentCircleRadius(61f);

            chart1.setDrawCenterText(true);

            chart1.setRotationAngle(0);
            chart1.setRotationEnabled(true);
            chart1.setHighlightPerTapEnabled(true);

            chart1.animateY(1400, Easing.EaseInOutQuad);
            // chart.spin(2000, 0, 360);

            Legend l = chart1.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

            // entry label styling
            chart1.setEntryLabelColor(Color.WHITE);
            chart1.setEntryLabelTextSize(12f);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void calculateNow() {

        total = 0;
        minus = 0;
        add = 0;

        TextView textViewBalance = findViewById(R.id.textViewBalance);
        textViewBalance.setText("Current Balance: $" + String.format("%.2f", total));

        for(AccDto ab:accounts){
            if (ab.getType().equals(Const.added))
            {
                add+=Double.parseDouble(ab.getAmount());
            }else {
                minus+=Double.parseDouble(ab.getAmount());
            }
        }
        total = add - minus;

//        added_txt.setText("Added: $"+add);
//        spent_txt.setText("Spent: $"+minus);
        textViewBalance.setText("Current balance: $"+total);

        double perSpent = (minus/add)*100;
        double perRemain = (total/add)*100;

        setData(perSpent,perRemain);

    }

    private void setData(double spent, double added) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        int count = 1;

        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) spent,
                    "Spent",
                    getResources().getDrawable(R.drawable.ic_launcher_background)));
            entries.add(new PieEntry((float) added,
                    "Remain",
                    getResources().getDrawable(R.drawable.ic_launcher_background)));
        }


        PieDataSet dataSet = new PieDataSet(entries, "Expense Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.RED);
        colors.add(Color.GREEN);



        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart1));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart1.setData(data);

        // undo all highlights
        chart1.highlightValues(null);

        chart1.invalidate();
    }

    public void SearchNow(View v){
        try{
            if(from.length()>0) {
                if(to.length()>0) {
                    accounts= new MyLocalDb().SerachAcc(from,to);
                    if(accounts.size()>0){
                       calculateNow();
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
