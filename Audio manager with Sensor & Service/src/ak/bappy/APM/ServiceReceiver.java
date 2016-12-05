package ak.bappy.APM;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class ServiceReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
	    String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
	    
	    if (TelephonyManager.EXTRA_STATE_RINGING.equals(state))
	    {
	      //  Log.d("MPR", "Its Ringing [" + number + "]");
	    }
	    if (TelephonyManager.EXTRA_STATE_IDLE.equals(state))
	    {
	        //Log.d("MPR", "Its Idle");
	    }
	    if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state))
	    {
	       // Log.d("MPR", "Its OffHook");
	    }
	}

}
