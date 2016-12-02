package com.example.ak_bappy.personalaccountant;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class Report extends AppCompatActivity {

    TextView text;
    EditText from,to,expense,earn,savings,shortage;
    DatabaseHelper myDb;
    ProgressDialog dialog;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initilizer();
    }
    private void initilizer()
    {
        text = (TextView)findViewById(R.id.data);
        dialog = new ProgressDialog(this);
        myDb = new DatabaseHelper(this);

        from = (EditText)findViewById(R.id.dateFrom);
        to = (EditText)findViewById(R.id.dateTo);
        expense = (EditText)findViewById(R.id.expenditure);
        earn = (EditText)findViewById(R.id.totalEarning);
        savings = (EditText)findViewById(R.id.savings);
        shortage = (EditText)findViewById(R.id.shortage);
    }
    public void onClickGenerateReport(View v)
    {
        if(from.getText().toString().equals("") || to.getText().toString().equals(""))
        {
            AlertDialougeAndTost.makeAlartDialouge(this,"You must need to fill Both Date Fields.");
        }
        else
        {
            showProgressBar();
            updateTransactions();
            updateExpendeture();
            updateEarning();
            updateSavings(earn.getText().toString(),expense.getText().toString());
        }

    }
    public void showProgressBar()
    {
        dialog.setMessage("Loading Data....");
        dialog.setCancelable(false);
        dialog.show();

        //delay function
        final Handler handaler = new Handler();
        handaler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                //executes after 1second/1000ms
                if(dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }
        },1000);
    }

    private void updateExpendeture()
    {
        double expendeture=0,temp = 0;
        res = myDb.getExpenditure(from.getText().toString(),to.getText().toString());
        if(res.getCount() == 0)
        {
            expense.setText("0");
        }
        else
        {
            while(res.moveToNext())
            {
                temp = Double.parseDouble(res.getString(0));
                expendeture += temp;
            }
            expense.setText(String.valueOf(expendeture));
        }
    }

    private void updateEarning()
    {
        double earning=0,temp = 0;
        res = myDb.getEarning(from.getText().toString(),to.getText().toString());
        if(res.getCount() == 0)
        {
            earn.setText("0");
        }
        else
        {
            while(res.moveToNext())
            {
                temp = Double.parseDouble(res.getString(0));
                earning += temp;
            }
            earn.setText(String.valueOf(earning));
        }
    }

    private void updateSavings(String earning, String expense)
    {
        double income = Double.parseDouble(earning);
        double cost = Double.parseDouble(expense);

        if(income==cost)
        {
            savings.setText("0");
            shortage.setText("0");
        }
        else if(income>cost)
        {
            savings.setText(String.valueOf(income-cost));
            shortage.setText("0");
        }
        else
        {
            shortage.setText(String.valueOf(cost-income));
            savings.setText("0");
        }
    }

    public void updateTransactions()
    {
        res = myDb.getDataBetweenDate(from.getText().toString(),to.getText().toString());
        if(res.getCount() == 0)
        {
            AlertDialougeAndTost.makeAlartDialouge(this, "No Data Found...!");
            text.setText("");
        }
        else
        {
            String dataSet = "\n";
            while(res.moveToNext())
            {
                String msg = "  Transaction ID : "+res.getString(0)+"\n"+
                        "  Description : "+res.getString(1)+"\n"+
                        "  Date : "+res.getString(2)+"\n"+
                        "  Time : "+res.getString(3)+"\n"+
                        "  Category : "+res.getString(4)+"\n"+
                        "  Type : "+res.getString(5)+"\n"+
                        "  Amount : "+res.getString(6)+"\n\n";
                dataSet += msg;
            }
            text.setText(dataSet);
        }
    }

}
