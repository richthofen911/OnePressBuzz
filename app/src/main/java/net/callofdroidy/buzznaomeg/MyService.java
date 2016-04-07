package net.callofdroidy.buzznaomeg;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Toast;

public class MyService extends Service {
    BroadcastReceiver receiverSent;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);

        receiverSent = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        ((Vibrator)getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
                        unregisterReceiver(this);
                        stopSelf();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        unregisterReceiver(this);
                        stopSelf();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        unregisterReceiver(this);
                        stopSelf();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        unregisterReceiver(this);
                        stopSelf();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        unregisterReceiver(this);
                        stopSelf();
                        break;
                }
            }
        };
        registerReceiver(receiverSent, new IntentFilter("SMS_SENT"));




        return START_NOT_STICKY;
    }
}
