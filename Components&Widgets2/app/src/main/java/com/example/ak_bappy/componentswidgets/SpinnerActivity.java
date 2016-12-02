package com.example.ak_bappy.componentswidgets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
    }


    private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                //doWhatYouWantHere();
                Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
                EditText textBox = (EditText)findViewById(R.id.textBox);
                textBox.setText(mySpinner.getSelectedItem().toString());
            }
            return true;
        }
    };

    public void btnAction(View v)
    {
        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        EditText textBox = (EditText)findViewById(R.id.textBox);
        textBox.setText(mySpinner.getSelectedItem().toString());
    }

}
