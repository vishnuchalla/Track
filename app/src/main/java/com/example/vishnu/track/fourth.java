package com.example.vishnu.track;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class fourth extends Activity {
    String a,s;
    Geocoder geocoder;
    ImageView imageView;
    double latitude,longitude;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        imageView=(ImageView)findViewById(R.id.imageView);
        geocoder = new Geocoder(this, Locale.getDefault());
        Intent ifr=getIntent();
        s=ifr.getStringExtra("s");
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gps = new GPSTracker(fourth.this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{gps.showSettingsAlert();}
        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String sub=addresses.get(0).getSubAdminArea();
            String subl=addresses.get(0).getSubLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            a="LOCATION DETAILS:-\n"+"\nLatitude:-"+latitude+"\nLongitude:-"+longitude+"\nAddress:-"+address+"\nCity:-"+city+"\nSubArea:-"+sub+"\nSublocality:-"+subl+
                    "\nState:-"+state+"\nCountry:-"+country+"\nPin:-"+postalCode+"\nKnownName:-"+knownName+"\n\n"+s;
            Intent iff=new Intent(fourth.this,fifth.class);
            iff.putExtra("a",a);
            startActivity(iff);}
        catch(Exception e)
        {}
    }
    boolean twice = false;
    @Override
    public void onBackPressed() {
        if (twice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            if(android.os.Build.VERSION.SDK_INT >= 21)
                super.finishAndRemoveTask();
            else
                super.finish();
            System.exit(0);
        }
        twice=true;
        Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable()
        {@Override
        public void run(){
            twice=false;}
        },3000);}}
