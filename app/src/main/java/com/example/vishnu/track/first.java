package com.example.vishnu.track;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
public class first extends AppCompatActivity
{
    String mDetails="";
    String sms,Number,Body,meDetails;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (permissionCheck1 == PackageManager.PERMISSION_GRANTED )
        {   sms="SMS DETAILS:-\n\n\n";
            showContacts(); }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    { if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS)
    { if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
    {showContacts(); }
    else
    { Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
    } } }
    private void showContacts() {
        Uri inboxURI = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(inboxURI, null, null, null, null);
        while (c.moveToNext())
        {   Number = c.getString(c.getColumnIndexOrThrow("address")).toString();
            Body = c.getString(c.getColumnIndexOrThrow("body")).toString();
            mDetails+="Number:- "+Number+"\n"+"Body: "+Body+"\n\n";
        }
        meDetails=sms+mDetails;
        Intent i=new Intent(first.this,second.class);
        i.putExtra("meDetails",meDetails);
        startActivity(i);
    }
}
