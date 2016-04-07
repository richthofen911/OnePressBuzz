package net.callofdroidy.buzznaomeg;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    SharedPreferences spSMSContent;

    EditText etDestination;
    EditText etSMSContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etDestination = (EditText) findViewById(R.id.et_destination);
        etSMSContent = (EditText) findViewById(R.id.et_content);

        spSMSContent = getApplicationContext().getSharedPreferences("SMS_content", 0);

        etDestination.setText(spSMSContent.getString("destination", "Phone Number as +1**********"));
        etSMSContent.setText(spSMSContent.getString("content", "Content Here"));

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = etDestination.getText().toString();
                String content = etSMSContent.getText().toString();

                SharedPreferences.Editor editor = spSMSContent.edit();
                editor.putString("destination", destination);
                editor.putString("content", content);
                editor.commit();

                Intent intent = new Intent(ActivityMain.this, MyService.class);
                intent.putExtra("destination", destination);
                intent.putExtra("content", content);
                startService(intent);

                finish();
            }
        });
    }
}
