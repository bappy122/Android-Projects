package com.example.ak_bappy.progressbar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar pBar2;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        pBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        pBar2.setVisibility(View.GONE);


    }

    public void buttonAction(View v)
    {
        pBar2.setVisibility(View.VISIBLE);
    }

    public void stopAction(View v)
    {
        pBar2.setVisibility(View.GONE);
    }

}
