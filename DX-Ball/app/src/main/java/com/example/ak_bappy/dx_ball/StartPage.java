package com.example.ak_bappy.dx_ball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StartPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start_page);
    }
    public void onClickStart(View v)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void onClickAbout(View v)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("About");
        alertDialog.setMessage("Developed by\nMd. Ahshanul Kabir Bappy\nEmail: ahshanulkabirbappy@gmail.com\nCS, AIUB");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public void onClickExit(View v)
    {
        System.exit(0);
    }

}
