package com.example.ak_bappy.temperatureconverter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String value=null;
    TextView textEdit=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "On Create Called", Toast.LENGTH_LONG).show();
        textEdit= (TextView) findViewById(R.id.textView);
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

            Toast.makeText(getApplicationContext(), "Developed By: Bappy", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*value=textEdit.getText().toString();
        outState.putString("textvalue",value);

        Toast.makeText(this,"save",Toast.LENGTH_SHORT).show();*/

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*value=savedInstanceState.getString("textvalue");
        textEdit.setText(value);
        Toast.makeText(this,"Restore",Toast.LENGTH_SHORT).show();*/
    }

    public void refreshAction(View v)
    {

        CheckBox kelvinFrom = (CheckBox)findViewById(R.id.kelvinFromCheckBox);
        CheckBox celFrom = (CheckBox)findViewById(R.id.celFromCheckBox);
        CheckBox farFrom = (CheckBox)findViewById(R.id.farFromCheckBox);


        CheckBox kelvinTo = (CheckBox)findViewById(R.id.kelvinToCheckBox);
        CheckBox celTo = (CheckBox)findViewById(R.id.celToCheckBox);
        CheckBox farTo = (CheckBox)findViewById(R.id.farToCheckBox);

        EditText inputTemperature = (EditText)findViewById(R.id.enteredTemperature);
        TextView res = (TextView)findViewById(R.id.resultBox);

        kelvinFrom.setChecked(false);
        celFrom.setChecked(false);
        farFrom.setChecked(false);

        kelvinTo.setChecked(false);
        celTo.setChecked(false);
        farTo.setChecked(false);

        inputTemperature.setText("");
        res.setText("");

        Toast.makeText(getApplicationContext(), "Refreshed...!", Toast.LENGTH_LONG).show();
    }

    public double input;
    public double output;

    public void convertButtonAction(View v)
    {

        CheckBox kelvinFrom = (CheckBox)findViewById(R.id.kelvinFromCheckBox);
        CheckBox celFrom = (CheckBox)findViewById(R.id.celFromCheckBox);
        CheckBox farFrom = (CheckBox)findViewById(R.id.farFromCheckBox);


        CheckBox kelvinTo = (CheckBox)findViewById(R.id.kelvinToCheckBox);
        CheckBox celTo = (CheckBox)findViewById(R.id.celToCheckBox);
        CheckBox farTo = (CheckBox)findViewById(R.id.farToCheckBox);

        EditText inputTemperature = (EditText)findViewById(R.id.enteredTemperature);
        TextView res = (TextView)findViewById(R.id.resultBox);


        if(inputTemperature.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Temperature...!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(kelvinFrom.isChecked() && celFrom.isChecked() && farFrom.isChecked() || kelvinFrom.isChecked() && celFrom.isChecked() || celFrom.isChecked() && farFrom.isChecked() || farFrom.isChecked()&&kelvinFrom.isChecked())
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Select only One Unit");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            else if(kelvinFrom.isChecked())
            {
                input = 0;
                output = 0;

                input = Double.parseDouble(inputTemperature.getText().toString());
                if(kelvinTo.isChecked() && celTo.isChecked() && farTo.isChecked() || kelvinTo.isChecked() && celTo.isChecked() || celTo.isChecked() && farTo.isChecked() || farTo.isChecked()&&kelvinTo.isChecked())
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Select only One Unit");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else if(kelvinTo.isChecked())
                {
                    output = input;

                }
                else if(celTo.isChecked())
                {
                    output = kelvinToCel(input);
                }
                else if(farTo.isChecked()) {
                    output = kelvinToFar(input);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Select the Temperature Unit You want to Convert in..!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }


            }
            else if(celFrom.isChecked())
            {
                input = 0;
                output = 0;
                input = Double.parseDouble(inputTemperature.getText().toString());
                if(kelvinTo.isChecked()) {
                    output = celToKelvin(input);

                }
                else if(celTo.isChecked())
                {
                    output = input;
                }
                else if(farTo.isChecked())
                {
                    output = celToFar(input);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Select the Temperature Unit You want to Convert in..!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
            else if(farFrom.isChecked())
            {
                input = 0;
                output = 0;

                input = Double.parseDouble(inputTemperature.getText().toString());
                if(kelvinTo.isChecked())
                {
                    output = farToKelvin(input);

                }
                else if(celTo.isChecked())
                {
                    output = farToCel(input);
                }
                else if(farTo.isChecked())
                {
                    output = input;
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Select the Temperature Unit You want to Convert in..!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Select the Temperature Unit You want to Convert From..!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }

        res.setText(String.valueOf(output));
    }


    public double kelvinToCel(double kelvin)
    {
        return (kelvin-273.15);
    }

    public double celToKelvin(double cel)
    {
        return (cel+273.15);
    }

    public double celToFar(double cel)
    {
        return ((cel*1.8)+32.0);
    }

    public double farToCel(double far)
    {
        return ((far-32.0)/1.8);
    }

    public double kelvinToFar(double kelvin)
    {
        return ((kelvin*(9.0/5.0))-459.67);
    }

    public double farToKelvin(double far)
    {
        return ((far+459.67)*(5.0/9.0));
    }

    @Override
    protected void onStop() {
        Toast.makeText(getApplicationContext(), "On Stop Called", Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(), "On Destroy Called", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Toast.makeText(getApplicationContext(), "On Pause Called", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Toast.makeText(getApplicationContext(), "On Resume Called", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    protected void onStart() {
        Toast.makeText(getApplicationContext(), "On Start Called", Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Toast.makeText(getApplicationContext(), "On Restart Called", Toast.LENGTH_SHORT).show();
        super.onRestart();
    }
}
