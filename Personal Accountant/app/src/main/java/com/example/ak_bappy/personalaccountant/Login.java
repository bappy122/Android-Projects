package com.example.ak_bappy.personalaccountant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText passwordText;
    DatabaseHelper myDb;
    Cursor res;
    public static String passChangeDate;
    public static String DefaultPass="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordText = (EditText)findViewById(R.id.passwordField);
        myDb = new DatabaseHelper(this);
        setDefaultPassword(DefaultPass);
    }

    private void setDefaultPassword(String Pass)
    {
        res = myDb.getPassword();
        if(res.getCount()==0)
        {
            myDb.insertIntoPassword(Pass);
        }
    }

    public void loginButtonAction(View v)
    {
        if(!passwordText.getText().toString().equals(""))
        {
            String dbPass=null;
            res = myDb.getPassword();
            if(res.getCount()==0)
            {
                AlertDialougeAndTost.makeAlartDialouge(this,"Unknown Error occurred...! Restart App.");
                //myDb.insertIntoPassword(DefaultPass);
            }
            else
            {
                while(res.moveToNext())
                {
                    dbPass = res.getString(res.getColumnIndex("PASSWORD"));
                }

                if(passwordText.getText().toString().equals(dbPass))
                {
                    Intent cpActivity = new Intent(getApplicationContext(),Home.class);
                    startActivity(cpActivity);
                    AlertDialougeAndTost.makeToast(this,"Logged in Successfully...!");
                    passwordText.setText("");
                }
                else
                {
                    if(passwordText.getText().toString().equals(DefaultPass))
                    {
                        AlertDialougeAndTost.makeAlartDialouge(this,"Your Password was changed on "+passChangeDate+".");
                    }
                    else
                        AlertDialougeAndTost.makeAlartDialouge(this,"Incorrect Password...!");
                }
            }
        }
        else
        {
            AlertDialougeAndTost.makeAlartDialouge(this,"Enter Password to Login...!");
        }
    }
}
