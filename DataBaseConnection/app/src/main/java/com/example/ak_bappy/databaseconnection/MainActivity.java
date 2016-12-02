package com.example.ak_bappy.databaseconnection;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
    }
    public void addDataAction(View v)
    {
        EditText name = (EditText)findViewById(R.id.editTextName);
        EditText surname = (EditText)findViewById(R.id.editTextSurname);
        EditText marks = (EditText)findViewById(R.id.editTextMarks);

        boolean success = myDb.insertData(name.getText().toString(),surname.getText().toString(),marks.getText().toString());

        if(success)
            Toast.makeText(getApplicationContext(), "Data Inserted Sucessfully..!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Data Insertion Failed..!", Toast.LENGTH_LONG).show();
    }
    public void viewBtnAction(View v)
    {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0)
        {
            msgDialouge("Data", "No Data Found...!");
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext())
        {
            buffer.append("ID      : "+res.getString(0)+"\n");
            buffer.append("Name    : "+res.getString(1)+"\n");
            buffer.append("Surname : "+res.getString(2)+"\n");
            buffer.append("Marks   : "+res.getString(3)+"\n\n");
        }

        msgDialouge("Data",buffer.toString());
    }
    public void msgDialouge(String title, String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public void updateBtnAction(View v)
    {
        EditText id = (EditText)findViewById(R.id.editTextId);
        EditText name = (EditText)findViewById(R.id.editTextName);
        EditText surname = (EditText)findViewById(R.id.editTextSurname);
        EditText marks = (EditText)findViewById(R.id.editTextMarks);

        boolean isUpdate =  myDb.updateData(id.getText().toString(),name.getText().toString(),surname.getText().toString(),marks.getText().toString());
        if(isUpdate==true)
            Toast.makeText(getApplicationContext(), "Data Updated Sucessfully..!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Data Update Failed..!", Toast.LENGTH_SHORT).show();
    }
    public void deleteBtnAction(View v)
    {
        EditText id = (EditText)findViewById(R.id.editTextId);
        Integer deletedRows = myDb.deleteData(id.getText().toString());

        if(deletedRows>0)
            Toast.makeText(getApplicationContext(), "Data Deleted Sucessfully..!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Data Delete Faield..!", Toast.LENGTH_SHORT).show();
    }
}
