package hr.web.test;

import android.R.anim;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class WebAppInterface {
	WebActivity wa=null;
	Infix infix = new Infix();
	public static String displayText = "";
	public static String opt = "";
	public static String num1 = "",num2 = "";
	
    /** Instantiate the interface and set the condisplayText */
    WebAppInterface(Context c) {
        wa=(WebActivity)c;
    }
    
    public void showToast(String toast) {
        Toast.makeText(wa, toast, Toast.LENGTH_LONG).show();
    }
    public void reload(){
    	wa.mHandler.post(new Runnable() {
			public void run() {
				WebAppInterface.this.wa.myWebView.reload();
				Log.d("try", "reload");
			}
		});
	}
    
    public void testMethod(){
    	Log.d("changed event","success");
    	//wa.button.setdisplayText("changed name");
    	/*wa.mHandler.post(new Runnable() {
			public void run() {
				Button btn= (Button)WebAppInterface.this.wa.findViewById(R.id.button1);
				btn.setdisplayText("new Changed");
			}
		});*/
    	wa.runOnUiThread(new Runnable() {
			public void run() {
				//Button btn= (Button)WebAppInterface.this.wa.findViewById(R.id.button1);
				//btn.setText("Changed2");
				//showToast("button pressed");
			}
		});
	}
    
    public void addNum(String num)
    {
    	displayText += num;
    	wa.myWebView.loadUrl("javascript:document.getElementById('res').value = '"+displayText.toString()+"'");
    	
    	//dot count validation
    	if(!dotCountIsOk(displayText))
    	{
    		displayText = displayText.substring(0,displayText.length()-1);
    		addNum("");
    	}
    }
    public String getResult()
    {
    	String result = String.valueOf(infix.infix(displayText));
    	
    	if(result.equals("Infinity") || result.equals("NaN"))
    	{
    		result = "Undefined";
    		displayText = "";
    	}
    	else
    	{
    		displayText = "";  		
    	}	
    	return result;
    }
    public void addOperator(String operator)
    {
    	//operatorCorrection(displayText, operator);
    	
    	displayText += operator;
    	wa.myWebView.loadUrl("javascript:document.getElementById('res').value = '"+displayText.toString()+"'");
    }
    
    public boolean expressionIsCorrect(String expr)
    {
    	return true;
    }
    
    public void operatorCorrection(String expr, String operator)
    {
    	if(expr.charAt(expr.length()-1) == '+' || expr.charAt(expr.length()-1) == '/' || expr.charAt(expr.length()-1) == '*' || expr.charAt(expr.length()-1) == '-')
    	{
    		displayText = displayText.substring(0,displayText.length()-1);
    	}
    }
    
    public boolean dotCountIsOk(String expr)
    {
    	int dotCount = 0;
    	for(int i = 0; i < expr.length(); i++)
    	{
    		if(expr.charAt(i) == '.')
    			dotCount++;
    		if(expr.charAt(i) == '+' || expr.charAt(i) == '-' || expr.charAt(i) == '*' || expr.charAt(i) == '/')
    			dotCount = 0;
    		if(dotCount > 1)
    			return false;
    	}
    	return true;
    }
    
}
