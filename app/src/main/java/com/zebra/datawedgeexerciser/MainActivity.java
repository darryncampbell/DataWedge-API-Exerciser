package com.zebra.datawedgeexerciser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_SOFTSCANTRIGGER = "com.symbol.datawedge.api.ACTION_SOFTSCANTRIGGER";
    private static final String ACTION_SCANNERINPUTPLUGIN = "com.symbol.datawedge.api.ACTION_SCANNERINPUTPLUGIN";
    private static final String ACTION_ENUMERATESCANNERS = "com.symbol.datawedge.api.ACTION_ENUMERATESCANNERS";
    private static final String ACTION_SETDEFAULTPROFILE = "com.symbol.datawedge.api.ACTION_SETDEFAULTPROFILE";
    private static final String ACTION_RESETDEFAULTPROFILE = "com.symbol.datawedge.api.ACTION_RESETDEFAULTPROFILE";
    private static final String ACTION_SWITCHTOPROFILE = "com.symbol.datawedge.api.ACTION_SWITCHTOPROFILE";

    //  Parameters associated with the application actions
    private static final String EXTRA_PARAMETER = "com.symbol.datawedge.api.EXTRA_PARAMETER";
    private static final String EXTRA_PROFILENAME = "com.symbol.datawedge.api.EXTRA_PROFILENAME";

    //  Enumerated Scanner receiver
    private static final String ACTION_ENUMERATEDLISET = "com.symbol.datawedge.api.ACTION_ENUMERATEDSCANNERLIST";
    private static final String KEY_ENUMERATEDSCANNERLIST = "DWAPI_KEY_ENUMERATEDSCANNERLIST";

    private static final String LOG_TAG = "Datawedge API Exerciser";

    //  todo Receive scans via SendService

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //  Were we invoked through a StartActivity intent?
        Intent initiatingIntent = getIntent();
        if (initiatingIntent != null)
        {
            String action = initiatingIntent.getAction();
            if (action.equalsIgnoreCase(getResources().getString(R.string.activity_intent_filter_action)))
            {
                //  Received a barcode through StartActivity
                //Log.d(LOG_TAG, "Received Barcode from DataWedge through StartActivity: " + decodedData);
                displayScanResult(initiatingIntent, "via StartActivity");
            }

        }

        //  SoftScanTrigger Intent
        final Spinner spinnerSoftScanTrigger = (Spinner) findViewById(R.id.spinnerSoftScanTrigger);
        final Button btnSoftScanTrigger = (Button) findViewById(R.id.btnSoftScanTrigger);
        btnSoftScanTrigger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Send SoftScanTriggerIntent
                sendDataWedgeIntentWithExtra(ACTION_SOFTSCANTRIGGER, EXTRA_PARAMETER, spinnerSoftScanTrigger.getSelectedItem().toString());
            }
        });

        //  ScannerInputPlugin
        final CheckBox checkScannerInputPlugin = (CheckBox) findViewById(R.id.checkScannerInputPlugin);
        final Button btnScannerInputPlugin = (Button) findViewById(R.id.btnScannerInputPlugin);
        btnScannerInputPlugin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Send ScannerInput Plugin intent
                String parameter = "DISABLE_PLUGIN";
                if (checkScannerInputPlugin.isChecked())
                    parameter = "ENABLE_PLUGIN";
                sendDataWedgeIntentWithExtra(ACTION_SCANNERINPUTPLUGIN, EXTRA_PARAMETER, parameter);
            }
        });

        //  EnumerateScanners
        final Button btnEnumerateScanners = (Button) findViewById(R.id.btnEnumerateScaners);
        btnEnumerateScanners.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithoutExtra(ACTION_ENUMERATESCANNERS);
            }
        });
        // Create a filter for the broadcast intent
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ENUMERATEDLISET);
        //  Whilst we're here also register to receive broadcasts via DataWedge scanning
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        filter.addAction(getResources().getString(R.string.activity_action_from_service));
        registerReceiver(myBroadcastReceiver, filter);

        //  SetDefaultProfile
        final EditText editSetDefaultProfile = (EditText) findViewById(R.id.editSetDefaultProfile);
        final Button btnSetDefaultProfile = (Button) findViewById(R.id.btnSetDefaultProfile);
        btnSetDefaultProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Note by default on my device (all devices?) the initial default profile is named 'Profile0 (default)'.
                //  Those parentheses are PART of the name, they aren't to denote which profile is the default profile!
                sendDataWedgeIntentWithExtra(ACTION_SETDEFAULTPROFILE, EXTRA_PROFILENAME, editSetDefaultProfile.getText().toString());
            }
        });

        //  ResetDefaultProfile
        final Button btnResetDefaultProfile = (Button) findViewById(R.id.btnResetDefaultProfile);
        btnResetDefaultProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithoutExtra(ACTION_RESETDEFAULTPROFILE);
            }
        });

        //  SwitchToProfile
        final EditText editSwitchToProfile = (EditText) findViewById(R.id.editSwitchToProfile);
        final Button btnSwitchToProfile = (Button) findViewById(R.id.btnSwitchToProfile);
        btnSwitchToProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_SWITCHTOPROFILE, EXTRA_PROFILENAME, editSwitchToProfile.getText().toString());
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_ENUMERATEDLISET)) {
                Bundle b = intent.getExtras();
                for (String key : b.keySet())
                {
                    //  Note, documentation for key was wrong for this.  I will get that fixed
                    Log.v(LOG_TAG, key);
                }
                String[] scanner_list = b.getStringArray(KEY_ENUMERATEDSCANNERLIST);
                String userFriendlyScanners = "";
                for (int i = 0; i < scanner_list.length; i++)
                {
                    userFriendlyScanners += "{" + scanner_list[i] + "} ";
                }
                Toast.makeText(getApplicationContext(), userFriendlyScanners, Toast.LENGTH_LONG).show();
            }
            else if (action.equals(getResources().getString(R.string.activity_intent_filter_action)))
            {
                try {
                    displayScanResult(intent, "via Broadcast");
                }
                catch (Exception e)
                {
                    //  Catch if the UI does not exist when we receive the broadcast... this is not designed to be a production app
                }
            }
            else if (action.equals(getResources().getString(R.string.activity_action_from_service)))
            {
                try {
                    displayScanResult(intent, "via Service");
                }
                catch (Exception e)
                {
                    //  Catch if the UI does not exist when we receive the broadcast... this is not designed to be a production app
                }
            }
        }
    };

    private void sendDataWedgeIntentWithExtra(String action, String extraKey, String extraValue)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extraValue);
        this.sendBroadcast(dwIntent);
    }

    private void sendDataWedgeIntentWithoutExtra(String action)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        this.sendBroadcast(dwIntent);
    }

    private void displayScanResult(Intent initiatingIntent, String howDataReceived)
    {
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));

        final TextView lblScanSource = (TextView) findViewById(R.id.lblScanSource);
        final TextView lblScanData = (TextView) findViewById(R.id.lblScanData);
        final TextView lblScanLabelType = (TextView) findViewById(R.id.lblScanDecoder);
        lblScanSource.setText(decodedSource + " " + howDataReceived);
        lblScanData.setText(decodedData);
        lblScanLabelType.setText(decodedLabelType);
    }

    public static void receivedIntentFromService(Intent intent)
    {
        //displayScanResult(intent, "via Service");
    }



/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
