package com.example.ak_bappy.componentswidgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void datePickerAction(View v)
    {
        Intent intent = new Intent(this, DatePicker.class);
        startActivity(intent);
    }

    public void timePickerAction(View v)
    {
        Intent intent = new Intent(this, TimePicker.class);
        startActivity(intent);
    }

    public void spinnerBtnAction(View v)
    {
        Intent intent = new Intent(this, SpinnerActivity.class);
        startActivity(intent);
    }
    public void checkboxAction(View v)
    {
        Intent intent = new Intent(this, RadioBtn_CheckBox.class);
        startActivity(intent);
    }
    public void listViewAction(View v)
    {
        Intent intent = new Intent(this, List_Scroll.class);
        startActivity(intent);
    }
    public void scrollViewAction()
    {
        Intent intent = new Intent(this, ScrollView.class);
        startActivity(intent);
    }
    public void frameAction(View v)
    {
        Intent intent = new Intent(this, Frame.class);
        startActivity(intent);
    }
    public void gridAction(View v)
    {
        Intent intent = new Intent(this, Grid.class);
        startActivity(intent);
    }
}
