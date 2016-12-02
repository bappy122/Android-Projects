package ak.bappy.APM;

import ak.bappy.APM.R.id;
import android.R.integer;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.Toast;

public class MyService extends Service {    
    
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
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Service Stated", Toast.LENGTH_SHORT).show();
		Thread thread =new Thread(new MyThread(startId));
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
		
	}

}
