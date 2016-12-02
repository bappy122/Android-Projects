package com.example.ak_bappy.personalaccountant;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Archive extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView text;
    ProgressDialog dialog;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        initializer();
        showProgressBar();
        FetchData();
        updateUi();
    }

    private void initializer()
    {
        //initializes all necessary variables
        myDb = new DatabaseHelper(this);
        text = (TextView)findViewById(R.id.archiveText);
        dialog = new ProgressDialog(this);
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

    private void FetchData()
    {
        res = myDb.getAllData();
        if(res.getCount() == 0)
        {
            AlertDialougeAndTost.makeAlartDialouge(this, "No Data Found...!");
            text.setText("");
        }
    }

    public void updateUi()
    {
        //fills Archive with fetched data
        String data = "\n";
        while(res.moveToNext())
        {
            String msg = "  Transaction ID : "+res.getString(0)+"\n"+
                    "  Description : "+res.getString(1)+"\n"+
                    "  Date : "+res.getString(2)+"\n"+
                    "  Time : "+res.getString(3)+"\n"+
                    "  Category : "+res.getString(4)+"\n"+
                    "  Type : "+res.getString(5)+"\n"+
                    "  Amount : "+res.getString(6)+"\n\n";
            data += msg;
        }
        text.setText(data);
    }
}
