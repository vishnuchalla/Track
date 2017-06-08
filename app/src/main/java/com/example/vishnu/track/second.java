package com.example.vishnu.track;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class second extends AppCompatActivity{
    String cDetails="";
    ProgressDialog pDialog;
    String contacts,contactname,phNumber,meDetails;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permissionCheck1 == PackageManager.PERMISSION_GRANTED )
        {  contacts="CONTACT DETAILS:-\n\n\n";
            showContacts();
            }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    { if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS)
    { if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
    {showContacts();
        }
    else
    { Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
    } } }

    private void showContacts() {
        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.Contacts.DISPLAY_NAME+" ASC ");
        while (c.moveToNext())
        {   contactname  = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cDetails+="Contact Name:- "+contactname+"\n"+"Number:- "+phNumber+"\n\n";
        }
        Intent i=getIntent();
        meDetails=i.getStringExtra("meDetails");
        cDetails=meDetails+contacts+cDetails;
        Intent it=new Intent(second.this,third.class);
        it.putExtra("cDetails",cDetails);
        startActivity(it);

    }
}
