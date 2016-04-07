package net.callofdroidy.buzznaomeg;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    SharedPreferences spSMSContent;

    EditText etDestination;
    EditText etSMSContent;

    private static final int requestCodeSendSMS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!PermissionHandler.checkPermission(this, Manifest.permission.SEND_SMS)){
            PermissionHandler.requestPermission(this, Manifest.permission.SEND_SMS, requestCodeSendSMS);
        }

        etDestination = (EditText) findViewById(R.id.et_destination);
        etSMSContent = (EditText) findViewById(R.id.et_content);

        spSMSContent = getApplicationContext().getSharedPreferences("SMS_content", 0);

        etDestination.setText(spSMSContent.getString("destination", "Phone Number as +1**********"));
        etSMSContent.setText(spSMSContent.getString("content", "Content Here"));

        findViewById(R.id.btn_gun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSMSContent.setText("滚了");
            }
        });

        findViewById(R.id.btn_da).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSMSContent.setText("打过来");
            }
        });

        findViewById(R.id.btn_deng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSMSContent.setText("稍微等一下");
            }
        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case requestCodeSendSMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "no permission to run this app", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
