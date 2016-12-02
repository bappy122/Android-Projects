package calculator.ahshanul.kabir.bappy;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HistoryActivity extends Activity {
	TextView history;
	DatabaseHelper myDb;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.history);
	        myDb = new DatabaseHelper(this);
	        history = (TextView)findViewById(R.id.history);
	        history.setText(readFromFile());
   }

	public String readFromFile() 
	 {
        Cursor res = myDb.getAllData();

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext())
        {
            buffer.append(res.getString(0)+"\n");
        }
        return buffer.toString();
	 }
	
	public void backButtonPress(View v)
	{
		finish();
	}

}
