package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class emergmes extends Activity {

    private static final int REQUEST_SMS_PERMISSION = 123;
    private EditText editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergmes);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
    }

    public void sendSMS(View view) {
        String phoneNumber = editTextPhoneNumber.getText().toString();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_SMS_PERMISSION);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Your Order is placed , see your Food Order app to check your order", null, null);
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS(null); // Call the sendSMS method again after obtaining permission
            } else {
                Toast.makeText(this, "SMS permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
