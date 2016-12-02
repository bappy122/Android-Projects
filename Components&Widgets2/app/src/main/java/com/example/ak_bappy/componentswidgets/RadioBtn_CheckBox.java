package com.example.ak_bappy.componentswidgets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class RadioBtn_CheckBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_btn__check_box);

    }

    public void refreshAction(View v)
    {
        RadioButton rBtn = (RadioButton)findViewById(R.id.radioButton);
        CheckBox cBox = (CheckBox)findViewById(R.id.checkBox);

        rBtn.setChecked(false);
        cBox.setChecked(false);
    }

    public void stateAction(View v)
    {
        RadioButton rBtn = (RadioButton)findViewById(R.id.radioButton);
        CheckBox cBox = (CheckBox)findViewById(R.id.checkBox);

        if(rBtn.isChecked())
        {
            Toast.makeText(getApplicationContext(),"Radio Button Is Checked...!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Radio Button Is NOT Checked...!", Toast.LENGTH_SHORT).show();
        }

        if(cBox.isChecked())
        {
            Toast.makeText(getApplicationContext(),"CheckBox Is Checked...!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"CheckBox Is NOT Checked...!", Toast.LENGTH_SHORT).show();
        }
    }

}
