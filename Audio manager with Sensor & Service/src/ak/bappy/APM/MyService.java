package ak.bappy.APM;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.Toast;

public class MyService extends Service implements SensorEventListener {    
	
	private static final int SHAKE_THRESHOLD = 150;
	private static long lastUpdate = 0;
	AudioManager audioManager;
    private SensorManager mSensorManager;
    private Sensor mProximity;
    
    int led = 0;
    double x,y,z,prox, last_x,last_y,last_z;
    
    // for acclerometer
    Sensor acclerometer;
    SensorManager sm;
    
    final class MyThread implements Runnable
    {
    	int service_id;
    	
    	MyThread(int service_id) {
			// TODO Auto-generated constructor stub
    		this.service_id = service_id;
		}

		public void run() {
			// TODO Auto-generated method stub
			
			synchronized (this) 
			{
				try 
				{
					wait(1000);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				stopSelf(service_id);
			}
		}
    	
    }
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        // for acclerometer
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        acclerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service Stated", Toast.LENGTH_SHORT).show();
		
		mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this,acclerometer,SensorManager.SENSOR_DELAY_NORMAL);
		
		Thread thread =new Thread(new MyThread(startId));
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		sm.unregisterListener(this);
		mSensorManager.unregisterListener(this);
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
		
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) 
	{
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) 
		{
            if (event.values[0] >= -0.01 && event.values[0]<= 0.01) 
            {
               // display.setText("Proximity Sensor Value: Near " + event.values[0]);
                prox = event.values[0];
            }
            
            else 
            {
            	//display.setText("Proximity Sensor Value: Far " + event.values[0]);
            	prox = event.values[0];
            }
        }
		else 
		{
			x=event.values[0];
			y=event.values[1];
			z=event.values[2];
			//displayXYZ.setText("Acclerometer Sensor\nX : "+event.values[0] + "\nY: "+event.values[1] + "\nZ: "+event.values[2]);
		}
		detectShake(x,y,2.0);
		silentVibrate(z);
	}
	private void silentVibrate(double z) 
	{
		// TODO Auto-generated method stub
		if(z > 7.0 && prox != 0)
			Ring();
		if(z < -7.0 && prox == 0)
			Vibrate();
	}
	
	public void detectShake(double x, double y, double z)
	{
		long curTime = System.currentTimeMillis();
	    // only allow one update every 100ms.
	    if ((curTime - lastUpdate) > 100) {
	      long diffTime = (curTime - lastUpdate);
	      lastUpdate = curTime;

	      float speed = (float) (Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000);

	      if (speed > SHAKE_THRESHOLD) {
	        Vibrate();
	       // Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
	      }
	      last_x = x;
	      last_y = y;
	      last_z = z;
	      lastUpdate = curTime;
	    }
	}
	
	public void Silent()
    {
		if(audioManager.getRingerMode() != 0)
		{
	    	audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	    	//Toast.makeText(this, "Silent Mode Activated", Toast.LENGTH_SHORT).show();
		}
    }
    
    public void Ring()
    {
    	if(audioManager.getRingerMode() != 2)
    	{
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        	//Toast.makeText(this, "Normal Mode Activated", Toast.LENGTH_SHORT).show();
    	}

    }
    
    public void Vibrate()
    {
    	if(audioManager.getRingerMode() != 1)
    	{
        	audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        	//Toast.makeText(this, "Vibration Mode Activated", Toast.LENGTH_SHORT).show();
    	}
    }


}
