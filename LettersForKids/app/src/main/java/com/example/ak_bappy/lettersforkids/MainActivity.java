package com.example.ak_bappy.lettersforkids;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    TextToSpeech t1;
    TextView displayLetter;
    Button b1;
    public static int num;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
    }

    private void initializer()
    {
        num = 1;
        b1 = (Button) findViewById(R.id.button);
        displayLetter = (TextView)findViewById(R.id.letter);
        letterGenerator();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void pronounce(String str)
    {
        String toSpeak = str;
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onClickPronounce(View v)
    {
        pronounce("this is ");
        pronounce(letterGenerator());
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public String letterGenerator()
    {
        String letter = null;
        switch (num)
        {
            case 1:
                letter = "Ae";
                displayLetter.setText("A");
                break;
            case 2:
                letter ="B";
                displayLetter.setText("B");
                break;
            case 3:
                letter ="C";
                displayLetter.setText("C");
                break;
            case 4:
                letter ="D";
                displayLetter.setText("D");
                break;
            case 5:
                letter ="E";
                displayLetter.setText("E");
                break;
            case 6:
                letter ="F";
                displayLetter.setText("F");
                break;
            case 7:
                letter ="G";
                displayLetter.setText("G");
                break;
            case 8:
                letter ="H";
                displayLetter.setText("H");
                break;
            case 9:
                letter ="I";
                displayLetter.setText("I");
                break;
            case 10:
                displayLetter.setText("J");
                letter ="J";
                break;
            case 11:
                displayLetter.setText("K");
                letter ="K";
                break;
            case 12:
                displayLetter.setText("L");
                letter ="L";
                break;
            case 13:
                displayLetter.setText("M");
                letter ="M";
                break;
            case 14:
                displayLetter.setText("N");
                letter ="N";
                break;
            case 15:
                displayLetter.setText("O");
                letter ="O";
                break;
            case 16:
                displayLetter.setText("P");
                letter ="P";
                break;
            case 17:
                displayLetter.setText("Q");
                letter ="Q";
                break;
            case 18:
                displayLetter.setText("R");
                letter ="R";
                break;
            case 19:
                displayLetter.setText("S");
                letter ="S";
                break;
            case 20:
                displayLetter.setText("T");
                letter ="T";
                break;
            case 21:
                displayLetter.setText("U");
                letter ="U";
                break;
            case 22:
                displayLetter.setText("V");
                letter ="V";
                break;
            case 23:
                displayLetter.setText("W");
                letter ="W";
                break;
            case 24:
                displayLetter.setText("X");
                letter ="X";
                break;
            case 25:
                displayLetter.setText("Y");
                letter ="Y";
                break;
            case 26:
                displayLetter.setText("Z");
                letter ="Z";
                break;
            default:

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Unexpected Error...! Restart the App.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
        }
        return letter;
    }

    public void onClickNext(View v)
    {
        ++num;
        if(num==27)
        {
            num = 1;
            letterGenerator();
        }
        else
            letterGenerator();
    }
    public void onClickPrevious(View v)
    {
        --num;
        if(num==0)
        {
            num=1;
            letterGenerator();
        }
        else
            letterGenerator();
    }
}