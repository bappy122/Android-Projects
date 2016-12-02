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
import android.widget.EditText;
import android.widget.TextView;

public class Search extends AppCompatActivity {

    EditText searchKey;
    TextView data,tv;
    DatabaseHelper myDb;
    Cursor res;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tv = (TextView)findViewById(R.id.title);
        tv.setCursorVisible(false);
        initializer();
        showProgressBar();
        showAllData();
    }

    private void showAllData()
    {
        res = myDb.getAllData();
        if(res.getCount() == 0)
        {
            this.showProgressBar();
            AlertDialougeAndTost.makeAlartDialouge(this, "No Data Found...!");

        }
        else
        {
            this.updateUi();
        }
    }

    private void initializer()
    {
        searchKey = (EditText)findViewById(R.id.searchKey);
        data = (TextView)findViewById(R.id.data);
        myDb = new DatabaseHelper(this);
        dialog = new ProgressDialog(this);
    }


    public void onClickSearch(View v)
    {
        if(!searchKey.getText().toString().equals(""))
        {
            res = myDb.getSearchedData(searchKey.getText().toString());
            if(res.getCount() == 0)
            {
                AlertDialougeAndTost.makeAlartDialouge(this, "No Data Found...!");
                data.setText("");
            }
            else
            {
                this.showProgressBar();
                this.updateUi();
            }

        }
        else
        {
            this.showProgressBar();
            AlertDialougeAndTost.makeAlartDialouge(this, "Enter search Key...!");
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
                //executes after 2second/2000ms
                if(dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }
        },1000);
    }

    private void updateUi()
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
        data.setText(dataSet);
    }

}
