package com.example.ak_bappy.json;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG_CONTACTS = "contacts";
    private static String TAG_ID = "id";
    private static String TAG_NAME = "name";
    private static String TAG_EMAIL = "email";
    private static String TAG_ADDRESS = "address";
    private static String TAG_GENDER = "gender";
    private static String TAG_PHONE = "phone";
    private static String TAG_MOBILE = "mobile";
    private static String TAG_HOME = "home";
    private static String TAG_OFFICE = "office";
    
    ProgressDialog dialog;

    ArrayList<Contact> contacts;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializer();
        new ParseJSONTask().execute();
    }

    private void initializer() {
        dialog = new ProgressDialog(this);
        contacts = new ArrayList<Contact>();
        tvData = (TextView)findViewById(R.id.tvData);
    }


    private void updateUi()
    {
        String contactString = "";
        for(Contact aContact : contacts)
        {
            contactString += aContact.covertToString();
        }
        tvData.setText(contactString);
    }

    private class ParseJSONTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog.setMessage("Loading Data....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            WebServiceHelper webServiceHelper = new WebServiceHelper();
            String jsonStr = webServiceHelper.getJSONData("http://api.androidhive.info/contacts/");
            try
            {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray contactsJSON = jsonObject.getJSONArray(TAG_CONTACTS);

                for (int i = 0; i < contactsJSON.length(); i++) {
                    Contact aContact = new Contact();
                    JSONObject contactObject = contactsJSON.getJSONObject(i);

                    aContact.id = contactObject.getString(TAG_ID);
                    aContact.name = contactObject.getString(TAG_NAME);
                    aContact.email = contactObject.getString(TAG_EMAIL);
                    aContact.address = contactObject.getString(TAG_ADDRESS);
                    aContact.gender = contactObject.getString(TAG_GENDER);

                    JSONObject phoneObject = contactObject.getJSONObject(TAG_PHONE);

                    aContact.mobile = phoneObject.getString(TAG_MOBILE);
                    aContact.home = phoneObject.getString(TAG_HOME);
                    aContact.office = phoneObject.getString(TAG_OFFICE);

                    contacts.add(aContact);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            updateUi();

        }
    }

}
