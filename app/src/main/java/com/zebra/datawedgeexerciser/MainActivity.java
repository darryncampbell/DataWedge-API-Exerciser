package com.zebra.datawedgeexerciser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //  6.x API
    private static final String ACTION_SOFTSCANTRIGGER = "com.symbol.datawedge.api.ACTION_SOFTSCANTRIGGER";
    private static final String ACTION_SCANNERINPUTPLUGIN = "com.symbol.datawedge.api.ACTION_SCANNERINPUTPLUGIN";
    private static final String ACTION_ENUMERATESCANNERS = "com.symbol.datawedge.api.ACTION_ENUMERATESCANNERS";
    private static final String ACTION_SETDEFAULTPROFILE = "com.symbol.datawedge.api.ACTION_SETDEFAULTPROFILE";
    private static final String ACTION_RESETDEFAULTPROFILE = "com.symbol.datawedge.api.ACTION_RESETDEFAULTPROFILE";
    private static final String ACTION_SWITCHTOPROFILE = "com.symbol.datawedge.api.ACTION_SWITCHTOPROFILE";
    //  Intent Extras (DataWedge 6.x)
    private static final String EXTRA_PARAMETER = "com.symbol.datawedge.api.EXTRA_PARAMETER";
    private static final String EXTRA_PROFILENAME = "com.symbol.datawedge.api.EXTRA_PROFILENAME";
    //  Enumerated Scanner receiver
    private static final String ACTION_ENUMERATEDLIST = "com.symbol.datawedge.api.ACTION_ENUMERATEDSCANNERLIST";
    private static final String KEY_ENUMERATEDSCANNERLIST = "DWAPI_KEY_ENUMERATEDSCANNERLIST";

    //  DataWedge 6.2 API
    private static final String ACTION_DATAWEDGE_FROM_6_2 = "com.symbol.datawedge.api.ACTION";
    private static final String ACTION_RESULT_DATAWEDGE_FROM_6_2 = "com.symbol.datawedge.api.RESULT_ACTION";
    //  Intent Extras (DataWedge 6.2)
    private static final String EXTRA_GET_ACTIVE_PROFILE = "com.symbol.datawedge.api.GET_ACTIVE_PROFILE";
    private static final String EXTRA_GET_PROFILES_LIST = "com.symbol.datawedge.api.GET_PROFILES_LIST";
    private static final String EXTRA_EMPTY = "";
    private static final String EXTRA_DELETE_PROFILE = "com.symbol.datawedge.api.DELETE_PROFILE";
    private static final String EXTRA_CLONE_PROFILE = "com.symbol.datawedge.api.CLONE_PROFILE";
    private static final String EXTRA_RENAME_PROFILE = "com.symbol.datawedge.api.RENAME_PROFILE";
    private static final String EXTRA_ENABLE_DATAWEDGE = "com.symbol.datawedge.api.ENABLE_DATAWEDGE";
    //  Extra Parameter results (from 6.2 onwards)
    private static final String EXTRA_RESULT_GET_ACTIVE_PROFILE = "com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE";
    private static final String EXTRA_RESULT_GET_PROFILE_LIST = "com.symbol.datawedge.api.RESULT_GET_PROFILES_LIST";

    private static final String LOG_TAG = "Datawedge API Exerciser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //  UI stuff
        final Spinner spinnerProfileCommands = (Spinner) findViewById(R.id.spinnerSelectCommand62);
        final TextView txtProfileCommandParameter = (TextView) findViewById(R.id.txtProfileCommandParameter);
        final EditText editProfileCommandParameter = (EditText) findViewById(R.id.editProfileCommandParameter);
        spinnerProfileCommands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0 || position == 1)
                {
                    txtProfileCommandParameter.setEnabled(false);
                    editProfileCommandParameter.setEnabled(false);
                }
                else
                {
                    txtProfileCommandParameter.setEnabled(true);
                    editProfileCommandParameter.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        //  Were we invoked through a StartActivity intent?
        Intent initiatingIntent = getIntent();
        if (initiatingIntent != null)
        {
            String action = initiatingIntent.getAction();
            if (action.equalsIgnoreCase(getResources().getString(R.string.activity_intent_filter_action)))
            {
                //  Received a barcode through StartActivity
                displayScanResult(initiatingIntent, "via StartActivity");
            }
        }

        //  SoftScanTrigger Intent (6.x API)
        final Spinner spinnerSoftScanTrigger = (Spinner) findViewById(R.id.spinnerSoftScanTrigger);
        final Button btnSoftScanTrigger = (Button) findViewById(R.id.btnSoftScanTrigger);
        btnSoftScanTrigger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Send SoftScanTriggerIntent
                sendDataWedgeIntentWithExtra(ACTION_SOFTSCANTRIGGER, EXTRA_PARAMETER, spinnerSoftScanTrigger.getSelectedItem().toString());
            }
        });

        //  ScannerInputPlugin (6.x API)
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

        //  EnumerateScanners (6.x API)
        final Button btnEnumerateScanners = (Button) findViewById(R.id.btnEnumerateScaners);
        btnEnumerateScanners.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithoutExtra(ACTION_ENUMERATESCANNERS);
            }
        });

        //  SetDefaultProfile (6.x API)
        final EditText editSetDefaultProfile = (EditText) findViewById(R.id.editSetDefaultProfile);
        final Button btnSetDefaultProfile = (Button) findViewById(R.id.btnSetDefaultProfile);
        btnSetDefaultProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Note by default on my device (all devices?) the initial default profile is named 'Profile0 (default)'.
                //  Those parentheses are PART of the name, they aren't to denote which profile is the default profile!
                sendDataWedgeIntentWithExtra(ACTION_SETDEFAULTPROFILE, EXTRA_PROFILENAME, editSetDefaultProfile.getText().toString());
            }
        });

        //  ResetDefaultProfile (6.x API)
        final Button btnResetDefaultProfile = (Button) findViewById(R.id.btnResetDefaultProfile);
        btnResetDefaultProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithoutExtra(ACTION_RESETDEFAULTPROFILE);
            }
        });

        //  SwitchToProfile (6.x API)
        final EditText editSwitchToProfile = (EditText) findViewById(R.id.editSwitchToProfile);
        final Button btnSwitchToProfile = (Button) findViewById(R.id.btnSwitchToProfile);
        btnSwitchToProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_SWITCHTOPROFILE, EXTRA_PROFILENAME, editSwitchToProfile.getText().toString());
            }
        });

        //  Profile based commands newly added for 6.2 (Delete, Clone, Rename) (6.2 API)
        final Button btnProfileCommand62 = (Button) findViewById(R.id.btnProfileCommand62);
        btnProfileCommand62.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String commandToExecute = spinnerProfileCommands.getSelectedItem().toString();
                Spinner profileListSpinner = (Spinner) findViewById(R.id.spinnerSelectProfile62);
                String selectedProfile = profileListSpinner.getSelectedItem().toString();
                final EditText editProfileCommandParameter = (EditText) findViewById(R.id.editProfileCommandParameter);
                String newProfile = editProfileCommandParameter.getText().toString();
                if (commandToExecute.equals(getString(R.string.command_refresh_ui)))
                {
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_PROFILES_LIST, EXTRA_EMPTY);
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_ACTIVE_PROFILE, EXTRA_EMPTY);
                }
                else if (commandToExecute.equals(getString(R.string.command_delete_profile)))
                {
                    String[] values = {selectedProfile};
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_DELETE_PROFILE, values);
                }
                else if (commandToExecute.equals(getString(R.string.command_clone_profile)))
                {
                    String[] values = {selectedProfile, newProfile};
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_CLONE_PROFILE, values);
                }
                else if (commandToExecute.equals(getString(R.string.command_rename_profile)))
                {
                    String[] values = {selectedProfile, newProfile};
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_RENAME_PROFILE, values);
                }
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_PROFILES_LIST, EXTRA_EMPTY);
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_ACTIVE_PROFILE, EXTRA_EMPTY);
            }
        });

        //  DisableEnableDatawedge (6.2 API)
        final CheckBox checkDisableEnableDatawedge = (CheckBox) findViewById(R.id.checkDisableEnableDataWedge);
        final Button btnDisableEnableDatawedge = (Button) findViewById(R.id.btnDisableEnableDataWedge);
        btnDisableEnableDatawedge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_ENABLE_DATAWEDGE, checkDisableEnableDatawedge.isChecked());
            }
        });

        // Create a filter for the broadcast intent
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ENUMERATEDLIST);           //  DW 6.x
        filter.addAction(ACTION_RESULT_DATAWEDGE_FROM_6_2);//  DW 6.2
        filter.addCategory(Intent.CATEGORY_DEFAULT);    //  NOTE: this IS REQUIRED for DW6.2!
        //  Whilst we're here also register to receive broadcasts via DataWedge scanning
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        filter.addAction(getResources().getString(R.string.activity_action_from_service));
        registerReceiver(myBroadcastReceiver, filter);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_PROFILES_LIST, EXTRA_EMPTY);
        sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_ACTIVE_PROFILE, EXTRA_EMPTY);
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_ENUMERATEDLIST)) {
                Bundle b = intent.getExtras();
                for (String key : b.keySet())
                {
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
            else if (action.equals(ACTION_RESULT_DATAWEDGE_FROM_6_2))
            {
                if (intent.hasExtra(EXTRA_RESULT_GET_ACTIVE_PROFILE))
                {
                    String activeProfile = intent.getStringExtra(EXTRA_RESULT_GET_ACTIVE_PROFILE);
                    TextView txtActiveProfile = (TextView) findViewById(R.id.txtActiveProfileOutput62);
                    txtActiveProfile.setText(activeProfile);
                }
                else if (intent.hasExtra(EXTRA_RESULT_GET_PROFILE_LIST))
                {
                    String[] profilesList = intent.getStringArrayExtra(EXTRA_RESULT_GET_PROFILE_LIST);
                    Spinner profileListSpinner = (Spinner) findViewById(R.id.spinnerSelectProfile62);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, profilesList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    profileListSpinner.setAdapter(adapter);
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

    private void sendDataWedgeIntentWithExtra(String action, String extraKey, String[] extraValues)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extraValues);
        this.sendBroadcast(dwIntent);
    }

    private void sendDataWedgeIntentWithExtra(String action, String extraKey, boolean extraValue)
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
}
