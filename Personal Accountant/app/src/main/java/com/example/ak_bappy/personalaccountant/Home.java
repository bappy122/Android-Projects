package com.example.ak_bappy.personalaccountant;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Home extends AppCompatActivity {

    Cursor res;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDb = new DatabaseHelper(this);
        checkPassword();
    }

    private void checkPassword()
    {
        String dbPass=null;
        res = myDb.getPassword();
        if(res.getCount()==0)
        {
            AlertDialougeAndTost.makeAlartDialouge(this,"Unknown Error occurred...! Restart App.");
        }
        else
        {
            while (res.moveToNext())
            {
                dbPass = res.getString(res.getColumnIndex("PASSWORD"));
            }
        }

        if(dbPass.equals(Login.DefaultPass))
        {
            changePasswordRequest();
        }
    }

    private void changePasswordRequest()
    {
        //confirmation dialouge for change password
        //creating cinfirmation dialouge
        android.support.v7.app.AlertDialog.Builder alertDlg = new android.support.v7.app.AlertDialog.Builder(this);
        alertDlg.setTitle("Security Issue");
        alertDlg.setMessage("It is Recomanded that, You should Change Default Password.\n\nDo you want to change the password Now?");
        alertDlg.setIcon(android.R.drawable.ic_dialog_alert);
        alertDlg.setCancelable(false);
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Yes button action
                Intent cpActivity = new Intent(getApplicationContext(),ChangePassword.class);
                startActivity(cpActivity);
            }
        });
        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //no button action
            }
        });
        alertDlg.create().show();
    }
    public void cngPassButtonAction(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),ChangePassword.class);
        startActivity(cpActivity);
    }
    public void onClickTransaction(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),AddTransaction.class);
        startActivity(cpActivity);
    }
    public void onClickArchive(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),Archive.class);
        startActivity(cpActivity);
    }
    public void onClickDeleteTransaction(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),DeleteData.class);
        startActivity(cpActivity);
    }
    public void searchButtonAction(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),Search.class);
        startActivity(cpActivity);
    }
    public void onClickReport(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),Report.class);
        startActivity(cpActivity);
    }
    public void onClickHowToUse(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),HowToUse.class);
        startActivity(cpActivity);
    }
    public void onClickAbout(View v)
    {
        Intent cpActivity = new Intent(getApplicationContext(),About.class);
        startActivity(cpActivity);
    }

}
