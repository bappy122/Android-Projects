package calculator.ahshanul.kabir.bappy;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Toast;

public class CalculatorActivity extends Activity {
    /** Called when the activity is first created. */
	Configuration Config;
	DatabaseHelper myDb;
	EditText display;
	String displayText = "";
	static String storeDisplayText = "";
	
	int dotCount = 0;
	int operationCount=0;
	int operation=0; 
	int equalButtonPress = 0;
	int mr = 0; //memory retrive helper
	String keepDisplayAfterOriantation = ""; 

	String optn;
	String firstOperand="", secondOperand="";
	int operatorCount = 0;
	String tempResult = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        
    }

    public void init()
    {
        display = (EditText)findViewById(R.id.display);
        myDb = new DatabaseHelper(this);
    }
    
    public void cancel()
    {
    	firstOperand = "";
    	secondOperand = "";
    	operation = 0;
    	displayText = "";
    	display.setText(displayText);
    	dotCount = 0;
    	operationCount = 0;
    	storeDisplayText = "";
    	equalButtonPress = 0;
    	mr = 0;
    	
    }
    
    public void calculate(String operand1,int option, String operand2)
    {
    	
    	double op1,op2,result = 0;
    	op1 = Double.parseDouble(operand1);
    	op2 = Double.parseDouble(operand2);
    	
    	if(operation != 0)
    	{
        	switch(option)
        	{
            case 1:  //  plus operation
                result = op1 + op2;
                optn = "+";
                printResult(result);
                break;
                
            case 2: //minus operation
            	if(equalButtonPress>1)
            	{
            		result = op2 - op1;
            	}
            	else 
            	{
            		result = op1 - op2;
				}       	
            	optn = "-";
            	printResult(result);
                break;
                
            case 3:  //multiply operation
            	result = op1 * op2;
            	optn = "*";
            	printResult(result);
                break;
                
            case 4: // division operation
            	if(op2 != 0 )
            	{
                	if(equalButtonPress>1)
                	{
                		if(op1 != 0)
                		{
                			result = op2 / op1;
                		}
                    	else
                    	{
                    		cancel();
                    		displayText = "Undefined";
                    		display.setText(displayText);
                    		displayText = "";
                    	}              		
                	}
                	else 
                	{
                		result = op1 / op2;
    				}
            		optn = "/";
            		printResult(result);
            	}
            	else
            	{
            		cancel();
            		displayText = "Undefined";
            		display.setText(displayText);
            		displayText = "";
            	}
                break;
            default:
            	result = 0;
                break;
        	}
    	}  	
    }
     
    public void printResult(double result)
    {
    	
    	if(result < 100000000000000.0 && result > -100000000000000.0)
    	{
        	if(result % 1 == 0)
        	{
        		displayText = String.format( "%.0f", result );
        		dotCount = 0;
        	}
        	else
        	{
        		displayText = String.format( "%.10f", result );
        		if(displayText.equals("0.0000000000"))
        		{
        			displayText = "0";
        		}
        		else
        		{
        			displayText = trimZeros(displayText);
				}
        		dotCount++;
        	}
        	storeDisplayText = displayText;
        	writeToDB("\n"+firstOperand +" "+optn+" " + secondOperand + " = " + displayText +"    "+" Date: "+getDate()+"   Time: "+getTime());
    		
        	if(equalButtonPress<=1)
    		{
    			firstOperand = secondOperand;
    		}
        	
    		display.setText(displayText);
    		tempResult = "result";    		
    	}
    	else
    	{
    		makeAlert("Memory Limit Exceeded...!");
    		cancel();
    		display.setText(displayText);
    	}   	
    }
      
    public void print(String content)
    {
    	if(!displayText.toString().equals(""))
    	{
    		double value;
    		value = Double.parseDouble(content);
            
    		if(value < 100000000000000.0 && value > -100000000000000.0) //checking digit limit
            {
    			if(content.indexOf(".") >= 0)
    			{
    				if(chekDecimalPoints(content))
    				{
                    	display.setText(content);
                    	storeDisplayText = content;
    				}
    				else
    				{
    	            	makeToast("Digit Limit Reached...!");
    	            	displayText = displayText.substring(0,displayText.length()-1);
    	            	storeDisplayText = displayText;
					}
    			}
    			else
    			{
                	display.setText(content);
                	storeDisplayText = content;
				}
            }
            else
            {
            	makeToast("Digit Limit Reached...!");
            	displayText = displayText.substring(0,displayText.length()-1);
            	storeDisplayText = displayText;
            }
    	}
    }
    
    private void saveInMemory(String value) 
    {
    	double currentValue = Double.parseDouble(value);
		double previousValue = Double.parseDouble(retriveValue());
		double temp = currentValue + previousValue;
		storeValue(String.valueOf(temp));
	}   

	public void makeAlert(String alertMessage)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(CalculatorActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(alertMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    
    public void makeToast(String toastMessage)
    {
    	Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

	public void clear() 
	{
		displayText = "";
		display.setText(displayText);
		dotCount = 0;
		storeDisplayText = "";
		mr = 0;
	}
	
	public String retriveValue()
	{
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		String storedValue = sharedPref.getString("M+", "0");
		return storedValue;
	}
	
	public void storeValue(String value)
	{
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("M+", value);
		editor.commit();
	}
	
	public void writeToDB(String data) 
	{
        boolean success = myDb.insertData(data);
        if(success)
        {
        	//success message
        }
        else 
        {
			makeToast("Couldn't Save data into Database");
		}     
	}
		
	public boolean chekDecimalPoints(String s)
	{
		int dotPosition = s.indexOf(".");
		String decimalDigitsString = s.substring(dotPosition+1, s.length());

		if(decimalDigitsString.length() > 14)
			return false;
		else 
			return true;	
	}
		
	public String getTime()
	{
		//generate time
	    Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
	    String currentTime = sdf.format(d);
	    return currentTime; 
	}
		
	public String getDate()
	{
	    //generate date
	    DateFormat df = new SimpleDateFormat("dd/MM/yy");
	    String now = df.format(new Date());
	    return now;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    //makeToast("Called");
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 
	    {
	    	keepDisplayAfterOriantation = display.getText().toString();
	    	storeDisplayText = displayText;
	    	setContentView(R.layout.landscape);
	    	init();
	    	displayText = storeDisplayText;
	    	
	    	if(storeDisplayText.equals(""))
	    		display.setText(keepDisplayAfterOriantation);
	    	else
	    		display.setText(storeDisplayText);
	    } 
	    else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
	    {
	    	keepDisplayAfterOriantation = display.getText().toString();
	    	
	    	storeDisplayText = displayText;
	    	setContentView(R.layout.main);
	    	init();
	    	displayText = storeDisplayText;
	    	
	    	if(storeDisplayText.equals(""))
	    		display.setText(keepDisplayAfterOriantation);
	    	else
	    		display.setText(storeDisplayText);  
	    }
	}
	  
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	init();
    	displayText = storeDisplayText;
    	display.setText(storeDisplayText);
    }
	
    public void btnPress(View v)
    {
    	
    	switch(v.getId())
    	{
    		case R.id.btn0:
    			numberButtonPressed("0");
    			break;
    			
    		case R.id.btn1:
    			numberButtonPressed("1");
    			break;
    			
    		case R.id.btn2:
    			numberButtonPressed("2");
    			break;
    			
    		case R.id.btn3:
    			numberButtonPressed("3");
    			break;
    			
    		case R.id.btn4:
    			numberButtonPressed("4");
    			break;
    			
    		case R.id.btn5:
    			numberButtonPressed("5");
    			break;
    			
    		case R.id.btn6:
    			numberButtonPressed("6");
    			break;
    			
    		case R.id.btn7:
    			numberButtonPressed("7");
    			break;
    			
    		case R.id.btn8:
    			numberButtonPressed("8");			
    			break;
    			
    		case R.id.btn9:
    			numberButtonPressed("9");
    			break;
    			
    		case R.id.btnDot: //point button
    				dotButtonPressed();
    			break;
    			
    		case R.id.btnMplus: //memory plus
    			if(!displayText.equals(""))
    			{
    				saveInMemory(displayText);
    			}
    			break;
    			
    		case R.id.btnMminus: // memory clear
    			storeValue("0");
    			break;
    			
    		case R.id.btnMR:   //memory retrive
				if(!retriveValue().equals(""))
				{
					displayText = "";
					displayText += retriveValue();
					print(displayText);
					mr++;
					//tempResult = "-1";
				}

    			break;
    			
    		case R.id.btnC:
    			
    			cancel();
    			break;
    			
    		case R.id.btnCE:
    			clear();
    			
    			break;
    			
    		case R.id.btnDel:
    			if(displayText.length() > 0)
    			{
    				equalButtonPress = 0;
        			displayText = displayText.substring(0,displayText.length()-1);
        			display.setText(displayText);
        			
                	if(displayText.indexOf('.') < 0 )
                	{
                		dotCount = 0;
                	}
                	if(display.getText().toString().length() == 0)
                	{
                		mr = 0;
                	}
    			}
    			break;
    			
    		case R.id.btnOff:
    			finish();
    			break;
    			
    		case R.id.btnHistory:
    			
    			Intent i = new Intent(getApplicationContext(), calculator.ahshanul.kabir.bappy.HistoryActivity.class);
    			startActivity(i);
    			break;
    			
    		case R.id.btnDivision:
    			divisionBtnPressed();
    			break;
    			
    		case R.id.btnPlus:
    			plusBtnPressed();
				break;
    			
    		case R.id.btnMinus:
    			minusBtnPressed();
    			break;
    			
    		case R.id.btnMultiply:
    			multiplyBtnPressed();
      			break;
      			
    		case R.id.btnEqual:
    			equalButtonPress++;
    			if(!displayText.toString().equals("") && !firstOperand.equals(""))
    			{
    				operationCount = 0;
    				secondOperand = displayText;
    				calculate(firstOperand,operation,secondOperand); 				
    			}
    			else
    			{
    				//do nothing
    			}
    			break;
    			
    		default:
    			makeToast("Unexpected Error");
    			break;
    	}
    }
    
    private void numberButtonPressed(String num) 
    {
    	if(mr>0)
		{
			displayText = "";
			mr = 0;
		}
		
		if(tempResult == "result")
			displayText = "";
		
		displayText += num;
		print(displayText);
		tempResult = "-1";
		equalButtonPress = 0;
		
	}

	private void dotButtonPressed() 
    {
    	if(mr > 0)
		{
			displayText = "";
			mr = 0;
		}
		if(tempResult == "result")
		{
			displayText = "";
			dotCount = 0;
		}
			
			if(dotCount < 1)
			{
				equalButtonPress = 0;
				if(displayText.toString().equals(""))
				{
					displayText += "0.";
				}
				else if(displayText.toString().equals("-"))
				{
					displayText += "0.";
				}
				else
				{
					displayText += ".";
				}
				dotCount++;
				print(displayText);
				tempResult = "-1";
			}
			else
			{
				//makeToast("Invalid Number..!");
			}
		
	}
	
	public void plusBtnPressed()
	{
		equalButtonPress = 0;
        dotCount = 0;
        
		if(operationCount > 0)
		{
			sequentialCalculationCommonGround(1);
		}
		else 
		{
			if(displayText.length()==0)
			{
				operation = 1;
			}
			else if(displayText.equals("-"))
			{
				displayText = "";
				display.setText(displayText);
			}
			else
			{
				firstOperand = displayText.toString();
				operation = 1;
				displayText = "";
				print(displayText);
			}
			
		}
		if(!display.getText().toString().equals("") && !display.getText().toString().equals("-") && !display.getText().toString().equals("Undefined"))
			operationCount++;
				
	}
	
	public void minusBtnPressed()
	{
		equalButtonPress = 0;
		dotCount = 0;
        
		if(operationCount > 0)
		{
			minusBtnSequentialCalculationHelper(2);
		}
		else 
		{				
			if(displayText.length()==0)
			{
				if(operation == 3 || operation == 4)
				{
					displayText = "-";
					display.setText(displayText);	
					tempResult = "-1";
				}
				else 
				{
					displayText = "-";
					display.setText(displayText);
					operationCount--;
					tempResult = "-1";
				}
			}
			else if(display.getText().toString().equals("-"))
			{
				//do nothing
			}
			else
			{
				firstOperand = displayText.toString();
				operation = 2;
				displayText = "";
				print(displayText);
			}
			
		}
		if(!display.getText().toString().equals("") && !display.getText().toString().equals("-") && !display.getText().toString().equals("Undefined"))
			operationCount++;
	}
	
	private void minusBtnSequentialCalculationHelper(int oprtn) 
	{
		if(!displayText.toString().equals("") && !firstOperand.equals("") && !displayText.equals("-"))
		{
			secondOperand = displayText;
			calculate(firstOperand,operation,secondOperand); 			
			firstOperand = displayText;
			displayText = "";
			operation = oprtn;
		}
		else if(display.getText().toString().equals("-"))
		{
			displayText = "";
			display.setText(displayText);
		}
		else 
		{
			if(operation == 3 || operation == 4)
			{
				displayText = "-";
				display.setText(displayText);
				tempResult = "-1";
			}
			else
			{
				operation = oprtn;
			}			
		}
		
	}

	public void divisionBtnPressed()
	{
		equalButtonPress = 0;
        dotCount = 0;
        
		if(operationCount > 0)
		{
			sequentialCalculationCommonGround(4);
		}
		else 
		{
			if(displayText.length()==0)
			{
				operation = 4;
			}
			else if(displayText.equals("-"))
			{
				displayText="";
				display.setText(displayText);
			}
			else
			{
				firstOperand = displayText.toString();
				operation = 4;
				displayText = "";
				print(displayText);
			}
		}
		if(!display.getText().toString().equals("") && !display.getText().toString().equals("-") && !display.getText().toString().equals("Undefined"))
			operationCount++;		
	}
	
	public void multiplyBtnPressed()
	{
		equalButtonPress = 0;
        dotCount = 0;
        
		if(operationCount > 0)
		{
			sequentialCalculationCommonGround(3);
		}
		else 
		{
			
			if(displayText.length()==0)
			{
				operation = 3;
			}
			else if(displayText.equals("-"))
			{
				displayText = "";
				display.setText(displayText);
			}
			else
			{
				firstOperand = displayText.toString();
				operation = 3;
				displayText = "";
				print(displayText);
			}
		}
		if(!display.getText().toString().equals("") && !display.getText().toString().equals("-") && !display.getText().toString().equals("Undefined"))
			operationCount++;
	}
	
	public void sequentialCalculationCommonGround(int oprtn)
	{
		if(!displayText.toString().equals("") && !firstOperand.equals("") && !display.getText().toString().equals("-"))
		{
			secondOperand = displayText;
			calculate(firstOperand,operation,secondOperand); 
			
			firstOperand = displayText;
			operation = oprtn;
			displayText = "";
		}
		else if(display.getText().toString().equals("-"))
		{
			// do nothing
		}
		else 
		{
			operation = oprtn;
		}
	}
	
	public String trimZeros(String str)
    {
    	while(str.charAt(str.length()-1)=='0')
    	{
    		str = str.substring(0,str.length()-1);
    	}
		if(str.charAt(str.length()-1)=='.')
		{
			str = str.substring(0,str.length()-1);
		}
    	return str;
    }
}