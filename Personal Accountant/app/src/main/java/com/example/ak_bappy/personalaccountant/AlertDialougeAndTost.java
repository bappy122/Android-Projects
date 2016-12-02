package com.example.ak_bappy.personalaccountant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
/**
 * Created by BAPPY on 5/7/2016.
 */
public class AlertDialougeAndTost {

    public static void makeToast(Activity v,String msg)
    {
        Toast.makeText(v,msg, Toast.LENGTH_SHORT).show();
    }
    public static void makeAlartDialouge(Activity v, String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(v).create();
        alertDialog.setTitle("Message");
        alertDialog.setMessage(msg);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
