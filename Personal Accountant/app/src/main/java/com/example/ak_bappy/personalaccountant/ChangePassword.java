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
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangePassword extends AppCompatActivity {

    EditText cp, np,rp;
    DatabaseHelper myDb;
    Cursor res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        cp = (EditText)findViewById(R.id.currentPasswordText);
        np = (EditText)findViewById(R.id.newPasswordText);
        rp = (EditText)findViewById(R.id.repetPasswordText);

        myDb = new DatabaseHelper(this);
    }
    public void refreshBtnAction(View v)
    {
        cp.setText("");
        np.setText("");
        rp.setText("");
        AlertDialougeAndTost.makeToast(this, "Refreshed...!");
    }

    public void cngBtnAction(View v)
    {
        if (cp.getText().toString().equals("") || np.getText().toString().equals("") || rp.getText().toString().equals(""))
        {
            AlertDialougeAndTost.makeAlartDialouge(this,"You must fill all the filds...!");
        }
        else
        {
            String dbPass=null;
            res = myDb.getPassword();
            if(res.getCount()==0)
            {
                AlertDialougeAndTost.makeAlartDialouge(this,"Unknown Error occurred...! Restart App.");
                //myDb.insertIntoPassword(DefaultPass);
            }
            else {
                while (res.moveToNext()) {
                    dbPass = res.getString(res.getColumnIndex("PASSWORD"));
                }
            }



            if (cp.getText().toString().equals(dbPass))
            {
                if (np.getText().toString().equals(rp.getText().toString()))
                {
                    //Login.loginPassword = np.getText().toString();
                    myDb.updatePassword(cp.getText().toString(),np.getText().toString());
                    AlertDialougeAndTost.makeToast(this,"Password Successfully Changed...!");
                    cp.setText("");
                    np.setText("");
                    rp.setText("");

                    //keep track of password change date
                    DateFormat df = new SimpleDateFormat("yyy-MM-dd");
                    String currentDate = df.format(new Date());
                    Login.passChangeDate = currentDate;

                    Intent cpActivity = new Intent(getApplicationContext(),Login.class);
                    startActivity(cpActivity);
                }
                else
                {
                    AlertDialougeAndTost.makeAlartDialouge(this,"New password Didn't Match...!");
                }
            }
            else
            {
                AlertDialougeAndTost.makeAlartDialouge(this,"Current Password Didn't Match..!");
            }

        }
    }
}
