package com.example.ak_bappy.personalaccountant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.*;
import java.text.SimpleDateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTransaction extends AppCompatActivity {

    Spinner spinner;
    EditText description;
    EditText othersCategory;
    EditText amount;
    DatabaseHelper myDb;
    CheckBox checkBoxExpense,checkBoxEarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        //initializing all variables
        spinner = (Spinner)findViewById(R.id.spinner);
        description = (EditText)findViewById(R.id.description);
        othersCategory = (EditText)findViewById(R.id.othersCategory);
        amount = (EditText)findViewById(R.id.amount);
        checkBoxExpense = (CheckBox)findViewById(R.id.checkBoxExpense);
        checkBoxEarning = (CheckBox)findViewById(R.id.checkBoxEarning);
        myDb = new DatabaseHelper(this);
    }

    public void onClickAddTransaction(View v)
    {
        String des=null,category=null,money=null,date=null,time=null,type=null;

        //generate time
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String currentTime = sdf.format(d);

        //generate date
        DateFormat df = new SimpleDateFormat("yyy-MM-dd");
        String currentDate = df.format(new Date());

        date = currentDate.toString();
        time = currentTime.toString();

        //input Fiels checking

        //checking description and setting value
        if(!description.getText().toString().equals(""))
            des = description.getText().toString();
        else
            des = "";

        //checking category and setting value
        if(!othersCategory.getText().toString().equals(""))
            category = othersCategory.getText().toString();
        else
            category = spinner.getSelectedItem().toString();


        //checking transaction type and amount
        if((!checkBoxExpense.isChecked() && !checkBoxEarning.isChecked()) || (checkBoxExpense.isChecked() && checkBoxEarning.isChecked()) )
        {
            AlertDialougeAndTost.makeAlartDialouge(this,"A Transaction must be either be an Expense or Earning");
        }
        else if(checkBoxEarning.isChecked())
        {
            type = "Earning";
            if(amount.getText().toString().equals(""))
            {
                AlertDialougeAndTost.makeAlartDialouge(this,"You must need to Enter Amount...!");
            }
            else
            {
                money = amount.getText().toString();
                confirmationDialouge(des,date,time,category,type,money);
            }
        }
        else if(checkBoxExpense.isChecked())
        {
            type = "Expense";
            if(amount.getText().toString().equals(""))
            {
                AlertDialougeAndTost.makeAlartDialouge(this,"You must need to Enter Amount...!");
            }
            else
            {
                money = amount.getText().toString();
                confirmationDialouge(des,date,time,category,type,money);
            }
        }
    }

    private void confirmationDialouge(String des,String date,String time,String category,String type,String money)
    {
        //in confirmation dialouge,  action Listener function only accepts final/constant variables
        final String descriptionFinal = des;
        final String dateFinal = date;
        final String timeFinal = time;
        final String categoryFinal = category;
        final String typeFinal = type;
        final String moneyFinal = money;

        //creating cinfirmation dialouge
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("Confirmation");
        alertDlg.setMessage("Do you really want to save this Transaction?");
        alertDlg.setIcon(android.R.drawable.ic_dialog_alert);
        alertDlg.setCancelable(false);
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //adding data in database
                myDb.insertData(descriptionFinal,dateFinal,timeFinal,categoryFinal,typeFinal,moneyFinal);
                confirmationMessage();
            }
        });
        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //if No nothing happens
            }
        });
        alertDlg.create().show();
    }
    private void confirmationMessage()
    {
        //showing success message
        AlertDialougeAndTost.makeToast(this,"Transaction Added Successfully");
    }
}
