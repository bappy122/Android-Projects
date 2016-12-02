package hr.web.test;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class WebActivity extends Activity {
    /** Called when the activity is first created. */
	WebView myWebView =null;
	Button button =null;
	Handler mHandler=null;
	boolean ischange=false;
	WebAppInterface ob;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //button= (Button)this.findViewById(R.id.button1);
        
        //web view work
        myWebView = (WebView)findViewById(R.id.webView1);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        //myWebView.addJavascriptInterface(this, "Android");
        
        
        Log.d("Web Activity", "on create");
        mHandler = new Handler();
        ob = new WebAppInterface(this);
    }
	
	public void showToast(String toast) 
	{
	        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
	        //this.button.setText("very new changed");
	}
	/*
	public void clicked(View V)
	{
		if(!ischange)
		{
			button.setText("Change");
			ischange=true;
			showToast(ob.displayText);
		}
		else
		{
			button.setText("Button");
			ischange=false;
		}
	}
	*/
	
	public void btnCclicked(View v)
	{
		ob.displayText = "";
		ob.addNum("");
	}
	
	public void btnDelClicked(View v)
	{
		if(ob.displayText.length() != 0)
		{
			ob.displayText = ob.displayText.substring(0,ob.displayText.length()-1);
			ob.addNum("");
		}

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("Web Activity", "destroid");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("Web Activity", "restart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("Web Activity", "paused");
	}
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Web Activity", "resume");
		
		if(isNetworkAvailable())
		{
			myWebView.loadUrl("http://172.16.7.26/android_test/index.html");
		}
		else 
		{
			myWebView.loadUrl("file:///android_asset/index.html");	
		}
		
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
    
}