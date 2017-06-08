package com.example.vishnu.track;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.TextView;
import java.util.Date;

public class third extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    String s,cDetails;
    private static final int URL_LOADER = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        getLoaderManager().initLoader(URL_LOADER, null, third.this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {


        switch (loaderID) {
            case URL_LOADER:
                return new CursorLoader(
                        this,
                        CallLog.Calls.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor managedCursor) {

        StringBuilder sb = new StringBuilder();

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        sb.append("CALL LOG DETAILS:-");
        sb.append("\n");

        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;

            int callTypeCode = Integer.parseInt(callType);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;
            }

            sb.append("\nPhone Number:-")
                    .append(phNumber);
            sb.append("\nCall Type:-")
                    .append(dir);
            sb.append("\nDate&Time:-")
                    .append(callDayTime);
            sb.append("\nCall Duration:-")
                    .append(callDuration+"seconds");
            sb.append("\n-------------------------------------------------");
        }
        Intent it=getIntent();
        cDetails=it.getStringExtra("cDetails");
        managedCursor.close();
        s=sb.toString()+"\n\n"+cDetails;
        Intent ifr=new Intent(third.this,fourth.class);
        ifr.putExtra("s",s);
        startActivity(ifr);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
