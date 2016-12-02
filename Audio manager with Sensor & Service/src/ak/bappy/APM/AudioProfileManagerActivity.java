package ak.bappy.APM;

import ak.bappy.APM.R.id;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AudioProfileManagerActivity extends Activity implements SensorEventListener {
    /** Called when the activity is first created. */
	
	AudioManager audioManager;
    private SensorManager mSensorManager;
    private Sensor mProximity;
    EditText display,displayXYZ;
    int led = 0;
    double x=100.0,y=100.0,z=100.0,prox = 100.0;
    
    // for acclerometer
    Sensor acclerometer;
    SensorManager sm;
    
    // led light notification
    NotificationManager notif;
    

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        display = (EditText)findViewById(id.display);
        displayXYZ =(EditText)findViewById(id.displayXYZ);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       
        
        // for acclerometer
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        acclerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
       
        
    }
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this,acclerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

	public void btnSilentClick(View v)
    {
    	audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    	Toast.makeText(this, "Silent Mode Activated", Toast.LENGTH_SHORT).show();
    }
    
    public void btnRingClick(View v)
    {
    	audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    	Toast.makeText(this, "Normal Mode Activated", Toast.LENGTH_SHORT).show();
    }
    
    public void btnVibrateClick(View v)
    {
    	audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    	Toast.makeText(this, "Vibration Mode Activated", Toast.LENGTH_SHORT).show();
    }

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) 
	{
		
		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) 
		{
            if (event.values[0] >= -0.01 && event.values[0]<= 0.01) 
            {
                display.setText("Proximity Sensor Value: Near " + event.values[0]);
                prox = event.values[0];
            }
            
            else 
            {
            	display.setText("Proximity Sensor Value: Far " + event.values[0]);
            	prox = event.values[0];
            }
        }
		else 
		{
			//acclerometer
			x=event.values[0];
			y=event.values[1];
			z=event.values[2];
			displayXYZ.setText("Acclerometer Sensor\nX : "+event.values[0] + "\nY: "+event.values[1] + "\nZ: "+event.values[2]);
		}
		
		if(z > 7.0 && prox != 0)
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		if(z < -7.0 && prox == 0)
			audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(this);
		mSensorManager.unregisterListener(this);
	}
	
	public void btnStartServiceClicked(View v)
	{
		Intent intent = new Intent(this, MyService.class);
		startService(intent);
	}
	public void btnStopServiceClicked(View v)
	{
		Intent intent = new Intent(this, MyService.class);
		stopService(intent);
	}

	public void btnLedClick(View v)
	{
		final Notification notification = new Notification();

		switch(led)
		{
		case 0:
			notification.ledARGB = Color.MAGENTA;
			Toast.makeText(this, "LED Light set to Magenta", Toast.LENGTH_SHORT).show();
			led++;
			break;
		case 1:
			notification.ledARGB = Color.BLUE;
			Toast.makeText(this, "LED Light set to Blue", Toast.LENGTH_SHORT).show();
			led++;
			break;
		case 2:
			notification.ledARGB = Color.CYAN;
			Toast.makeText(this, "LED Light set to Cyan", Toast.LENGTH_SHORT).show();
			led++;
			break;
		case 3:
			notification.ledARGB = Color.GREEN;
			Toast.makeText(this, "LED Light set to Green", Toast.LENGTH_SHORT).show();
			led++;
			break;
		case 4:
			notification.ledARGB = Color.YELLOW;
			Toast.makeText(this, "LED Light set to Yellow", Toast.LENGTH_SHORT).show();
			led++;
			break;
			
		case 5:
			led=0;
			Toast.makeText(this, "LED Light Off...!", Toast.LENGTH_SHORT).show();
			notif.cancel(1);
			break;	
		}
	   notification.flags |= Notification.FLAG_SHOW_LIGHTS;
	   notif.notify(1, notification);
	}
}