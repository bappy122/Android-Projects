package com.example.ak_bappy.personalaccountant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class DeleteData extends AppCompatActivity {

    EditText id;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        id = (EditText)findViewById(R.id.ID);
        myDb = new DatabaseHelper(this);
    }

    public void onClickDelete(View v)
    {
        if(!id.getText().toString().equals(""))
        {
            if(myDb.deleteData(id.getText().toString()) > 0)
                AlertDialougeAndTost.makeAlartDialouge(this,"Data Deleted...!");
            else
                AlertDialougeAndTost.makeAlartDialouge(this,"Transaction ID Did not Match...!");
        }
        else
            AlertDialougeAndTost.makeAlartDialouge(this,"Enter Transaction ID...!");

    }
    public void onClickDeleteAll(View v)
    {
        //creating cinfirmation dialouge
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("Confirmation");
        alertDlg.setMessage("All Data will be lost..!\n\nDo you really want to Delete all Data?");
        alertDlg.setIcon(android.R.drawable.ic_dialog_alert);
        alertDlg.setCancelable(false);
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //adding data in database
                myDb.deleteAllData();
                confirmationDialouge();
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

    private void confirmationDialouge()
    {
        AlertDialougeAndTost.makeAlartDialouge(this,"All Data Deleted....!");
    }


}
