package exam2.moneyapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import exam2.moneyapp.dto.AccDto;
import exam2.moneyapp.others.Const;
import exam2.moneyapp.others.MyLocalDb;
import exam2.moneyapp.R;
public class HistoryActivity extends AppCompatActivity
{

    List<AccDto> accounts = new ArrayList<>();
    double total;

    double add = 0,minus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TableLayout tableLayout = findViewById(R.id.tableLayoutTransactions);

        accounts = new MyLocalDb().getAccounts();

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
}
