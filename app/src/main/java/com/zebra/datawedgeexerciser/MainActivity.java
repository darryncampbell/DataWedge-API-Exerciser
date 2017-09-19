package com.zebra.datawedgeexerciser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //  6.0 API and up Actions sent to DataWedge
    private static final String ACTION_SOFTSCANTRIGGER = "com.symbol.datawedge.api.ACTION_SOFTSCANTRIGGER";
    private static final String ACTION_SCANNERINPUTPLUGIN = "com.symbol.datawedge.api.ACTION_SCANNERINPUTPLUGIN";
    private static final String ACTION_ENUMERATESCANNERS = "com.symbol.datawedge.api.ACTION_ENUMERATESCANNERS";
    private static final String ACTION_SETDEFAULTPROFILE = "com.symbol.datawedge.api.ACTION_SETDEFAULTPROFILE";
    private static final String ACTION_RESETDEFAULTPROFILE = "com.symbol.datawedge.api.ACTION_RESETDEFAULTPROFILE";
    private static final String ACTION_SWITCHTOPROFILE = "com.symbol.datawedge.api.ACTION_SWITCHTOPROFILE";
    //  6.0 API and up Extras sent to DataWedge
    private static final String EXTRA_PARAMETER = "com.symbol.datawedge.api.EXTRA_PARAMETER";
    private static final String EXTRA_PROFILENAME = "com.symbol.datawedge.api.EXTRA_PROFILENAME";
    //  6.0 API and up Actions received from DataWedge
    private static final String ACTION_ENUMERATEDLIST = "com.symbol.datawedge.api.ACTION_ENUMERATEDSCANNERLIST";
    //  6.0 API and up Extras received from DataWedge
    private static final String KEY_ENUMERATEDSCANNERLIST = "DWAPI_KEY_ENUMERATEDSCANNERLIST";

    //  6.2 API and up Actions sent to DataWedge
    private static final String ACTION_DATAWEDGE_FROM_6_2 = "com.symbol.datawedge.api.ACTION";
    //  6.2 API and up Extras sent to DataWedge
    private static final String EXTRA_GET_ACTIVE_PROFILE = "com.symbol.datawedge.api.GET_ACTIVE_PROFILE";
    private static final String EXTRA_GET_PROFILES_LIST = "com.symbol.datawedge.api.GET_PROFILES_LIST";
    private static final String EXTRA_DELETE_PROFILE = "com.symbol.datawedge.api.DELETE_PROFILE";
    private static final String EXTRA_CLONE_PROFILE = "com.symbol.datawedge.api.CLONE_PROFILE";
    private static final String EXTRA_RENAME_PROFILE = "com.symbol.datawedge.api.RENAME_PROFILE";
    private static final String EXTRA_ENABLE_DATAWEDGE = "com.symbol.datawedge.api.ENABLE_DATAWEDGE";
    private static final String EXTRA_EMPTY = "";
    //  6.2 API and up Actions received from DataWedge
    private static final String ACTION_RESULT_DATAWEDGE_FROM_6_2 = "com.symbol.datawedge.api.RESULT_ACTION";
    //  6.2 API and up Extras received from DataWedge
    private static final String EXTRA_RESULT_GET_ACTIVE_PROFILE = "com.symbol.datawedge.api.RESULT_GET_ACTIVE_PROFILE";
    private static final String EXTRA_RESULT_GET_PROFILE_LIST = "com.symbol.datawedge.api.RESULT_GET_PROFILES_LIST";

    //  6.3 API and up Extras sent to DataWedge
    private static final String EXTRA_SOFTSCANTRIGGER_FROM_6_3 = "com.symbol.datawedge.api.SOFT_SCAN_TRIGGER";
    private static final String EXTRA_SCANNERINPUTPLUGIN_FROM_6_3 = "com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN";
    private static final String EXTRA_ENUMERATESCANNERS_FROM_6_3 = "com.symbol.datawedge.api.ENUMERATE_SCANNERS";
    private static final String EXTRA_SETDEFAULTPROFILE_FROM_6_3 = "com.symbol.datawedge.api.SET_DEFAULT_PROFILE";
    private static final String EXTRA_RESETDEFAULTPROFILE_FROM_6_3 = "com.symbol.datawedge.api.RESET_DEFAULT_PROFILE";
    private static final String EXTRA_SWITCHTOPROFILE_FROM_6_3 = "com.symbol.datawedge.api.SWITCH_TO_PROFILE";
    private static final String EXTRA_GET_VERSION_INFO = "com.symbol.datawedge.api.GET_VERSION_INFO";
    private static final String EXTRA_REGISTER_NOTIFICATION = "com.symbol.datawedge.api.REGISTER_FOR_NOTIFICATION";
    private static final String EXTRA_UNREGISTER_NOTIFICATION = "com.symbol.datawedge.api.UNREGISTER_FOR_NOTIFICATION";
    private static final String EXTRA_CREATE_PROFILE = "com.symbol.datawedge.api.CREATE_PROFILE";
    private static final String EXTRA_SET_CONFIG = "com.symbol.datawedge.api.SET_CONFIG";
    private static final String EXTRA_RESTORE_CONFIG = "com.symbol.datawedge.api.RESTORE_CONFIG";
    //  6.3 API and up Actions received from DataWedge
    private static final String ACTION_RESULT_NOTIFICATION = "com.symbol.datawedge.api.NOTIFICATION_ACTION";
    //  6.3 API and up Extras received from DataWedge
    private static final String EXTRA_RESULT_GET_VERSION_INFO = "com.symbol.datawedge.api.RESULT_GET_VERSION_INFO";
    private static final String EXTRA_RESULT_NOTIFICATION = "com.symbol.datawedge.api.NOTIFICATION";
    private static final String EXTRA_RESULT_ENUMERATE_SCANNERS = "com.symbol.datawedge.api.RESULT_ENUMERATE_SCANNERS";
    //  6.3 API and up Parameter keys and values associated with extras received from DataWedge
    private static final String EXTRA_KEY_APPLICATION_NAME = "com.symbol.datawedge.api.APPLICATION_NAME";
    private static final String EXTRA_KEY_NOTIFICATION_TYPE = "com.symbol.datawedge.api.NOTIFICATION_TYPE";
    private static final String EXTRA_RESULT_NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    private static final String EXTRA_KEY_VALUE_SCANNER_STATUS = "SCANNER_STATUS";
    private static final String EXTRA_KEY_VALUE_PROFILE_SWITCH = "PROFILE_SWITCH";
    private static final String EXTRA_KEY_VALUE_CONFIGURATION_UPDATE = "CONFIGURATION_UPDATE";
    private static final String EXTRA_KEY_VALUE_NOTIFICATION_STATUS = "STATUS";
    private static final String EXTRA_KEY_VALUE_NOTIFICATION_PROFILE_NAME = "PROFILE_NAME";

    //  6.4 API and up Extras sent to Datawedge
    private static final String EXTRA_GET_DATAWEDGE_STATUS = "com.symbol.datawedge.api.GET_DATAWEDGE_STATUS";
    //  6.4 API and up Parameter keys and values associated with extras received from Datawedge
    private static final String EXTRA_RESULT_GET_DATAWEDGE_STATUS = "com.symbol.datawedge.api.RESULT_GET_DATAWEDGE_STATUS";

    //  private variables not related to the intent API
    private String mActiveProfile = "";
    private static final String EXTRA_PROFILE_NAME = "DW API Exerciser Profile";
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
            public void onNothingSelected(AdapterView<?> parentView) {}
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

        //  GetVersionInfo (6.3 API)
        final Button btnGetVersionInfo = (Button) findViewById(R.id.btnGetVersionInfo63);
        btnGetVersionInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_VERSION_INFO, EXTRA_EMPTY);
            }
        });

        //  RegisterForNotification / UnregisterForNotification(6.3 API)
        final Button btnRegisterUnregisterForNotifications = (Button) findViewById(R.id.btnRegisterUnregisterNotifications63);
        final CheckBox checkRegisterForNotifications = (CheckBox) findViewById(R.id.checkRegisterUnregisterScannerNotifications);
        btnRegisterUnregisterForNotifications.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString(EXTRA_KEY_APPLICATION_NAME, getPackageName()); //  The package name of this application
                extras.putString(EXTRA_KEY_NOTIFICATION_TYPE, EXTRA_KEY_VALUE_SCANNER_STATUS);  //  Register for changes to scanner status.  Could also register for profile switches
                if (checkRegisterForNotifications.isChecked())
                {
                    Toast.makeText(getApplicationContext(), "Changes to scanner status will be shown in toasts", Toast.LENGTH_SHORT).show();
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_REGISTER_NOTIFICATION, extras);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Changes to scanner status stopped", Toast.LENGTH_SHORT).show();
                    sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_UNREGISTER_NOTIFICATION, extras);
                }
            }
        });

        //  CreateProfile (6.3 API)
        final Button btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile63);
        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String profileName = EXTRA_PROFILE_NAME;
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_CREATE_PROFILE, profileName);

                //  Now configure that created profile to apply to our application
                Bundle profileConfig = new Bundle();
                profileConfig.putString("PROFILE_NAME", EXTRA_PROFILE_NAME);
                profileConfig.putString("PROFILE_ENABLED", "true"); //  Seems these are all strings
                profileConfig.putString("CONFIG_MODE", "UPDATE");
                Bundle barcodeConfig = new Bundle();
                barcodeConfig.putString("PLUGIN_NAME", "BARCODE");
                barcodeConfig.putString("RESET_CONFIG", "true"); //  This is the default but never hurts to specify
                Bundle barcodeProps = new Bundle();
                barcodeConfig.putBundle("PARAM_LIST", barcodeProps);
                profileConfig.putBundle("PLUGIN_CONFIG", barcodeConfig);
                Bundle appConfig = new Bundle();
                appConfig.putString("PACKAGE_NAME", getPackageName());      //  Associate the profile with this app
                appConfig.putStringArray("ACTIVITY_LIST", new String[]{"*"});
                profileConfig.putParcelableArray("APP_LIST", new Bundle[]{appConfig});
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SET_CONFIG, profileConfig);
                //  You can only configure one plugin at a time, we have done the barcode input, now do the intent output
                profileConfig.remove("PLUGIN_CONFIG");
                Bundle intentConfig = new Bundle();
                intentConfig.putString("PLUGIN_NAME", "INTENT");
                intentConfig.putString("RESET_CONFIG", "true");
                Bundle intentProps = new Bundle();
                intentProps.putString("intent_output_enabled", "true");
                intentProps.putString("intent_action", "com.zebra.dwapiexerciser.ACTION");
                intentProps.putString("intent_delivery", "2");
                intentConfig.putBundle("PARAM_LIST", intentProps);
                profileConfig.putBundle("PLUGIN_CONFIG", intentConfig);
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SET_CONFIG, profileConfig);

                Toast.makeText(getApplicationContext(), "Profile create command sent.  Check DataWedge application UI", Toast.LENGTH_LONG).show();
            }
        });

        //  Set Config (6.3 API)
        Button btnSetConfig = (Button) findViewById(R.id.btnConfigureProfile63);
        btnSetConfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final CheckBox checkEnableCommonDecoders = (CheckBox) findViewById(R.id.checkEnableCommonDecoders);
                boolean checkValue = checkEnableCommonDecoders.isChecked();
                Spinner spinnerScannerForSetConfig = (Spinner) findViewById(R.id.spinnerScannerForSetConfig);
                int selectedScanner = spinnerScannerForSetConfig.getSelectedItemPosition();
                String currentDeviceId = "" + selectedScanner;  //  Should really get this from enumerate_scanners but I just assume a contiguous ID is assigned
                String decoderValue = "false";
                String status = "disabled";
                if (checkValue)
                {
                    decoderValue = "true";
                    status = "enabled";
                }
                //  Note, another example of SetConfig can be found under the CreateProfile test
                Bundle profileConfig = new Bundle();
                profileConfig.putString("PROFILE_NAME", mActiveProfile);
                profileConfig.putString("PROFILE_ENABLED", "true");
                profileConfig.putString("CONFIG_MODE", "UPDATE");
                Bundle barcodeConfig = new Bundle();
                barcodeConfig.putString("PLUGIN_NAME", "BARCODE");
                barcodeConfig.putString("RESET_CONFIG", "true");
                Bundle barcodeProps = new Bundle();
                barcodeProps.putString("current-device-id", currentDeviceId);
                barcodeProps.putString("scanner_input_enabled", "true");
                barcodeProps.putString("decoder_ean8", decoderValue);
                barcodeProps.putString("decoder_ean13", decoderValue);
                barcodeProps.putString("decoder_upca", decoderValue);
                barcodeConfig.putBundle("PARAM_LIST", barcodeProps);
                profileConfig.putBundle("PLUGIN_CONFIG", barcodeConfig);
                Bundle appConfig = new Bundle();
                appConfig.putString("PACKAGE_NAME", getPackageName());
                appConfig.putStringArray("ACTIVITY_LIST", new String[]{"*"});
                profileConfig.putParcelableArray("APP_LIST", new Bundle[]{appConfig});
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SET_CONFIG, profileConfig);
                Toast.makeText(getApplicationContext(), "EAN8, EAN13 and UPCA are now " + status + " in profile " + mActiveProfile, Toast.LENGTH_LONG).show();
            }
        });

        //  RestoreConfig (6.3 API)
        Button btnRestoreConfig = (Button) findViewById(R.id.btnRestoreConfig63);
        btnRestoreConfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_RESTORE_CONFIG, EXTRA_EMPTY);
                Toast.makeText(getApplicationContext(), "DataWedge reset.  Use CreateProfile button & close / relaunch to get this app working again", Toast.LENGTH_LONG).show();
            }
        });

        //  SoftScanTrigger Intent (6.3 API)
        final Spinner spinnerSoftScanTrigger63 = (Spinner) findViewById(R.id.spinnerSoftScanTrigger63);
        final Button btnSoftScanTrigger63 = (Button) findViewById(R.id.btnSoftScanTrigger63);
        btnSoftScanTrigger63.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Send SoftScanTriggerIntent for the 6.3 API
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SOFTSCANTRIGGER_FROM_6_3, spinnerSoftScanTrigger63.getSelectedItem().toString());
            }
        });

        //  ScannerInputPlugin (6.3 API)
        final CheckBox checkScannerInputPlugin63 = (CheckBox) findViewById(R.id.checkScannerInputPlugin63);
        final Button btnScannerInputPlugin63 = (Button) findViewById(R.id.btnScannerInputPlugin63);
        btnScannerInputPlugin63.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String parameter = "DISABLE_PLUGIN";
                if (checkScannerInputPlugin63.isChecked())
                    parameter = "ENABLE_PLUGIN";
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SCANNERINPUTPLUGIN_FROM_6_3, parameter);
            }
        });

        //  EnumerateScanners (6.3 API)
        final Button btnEnumerateScanners63 = (Button) findViewById(R.id.btnEnumerateScaners63);
        btnEnumerateScanners63.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_ENUMERATESCANNERS_FROM_6_3, EXTRA_EMPTY);
                Toast.makeText(getApplicationContext(), "Enumerated scanners are shown in the 'Set Config' section dropdown", Toast.LENGTH_LONG).show();
            }
        });

        //  SetDefaultProfile (6.3 API)
        final Spinner spinnerSetDefaultProfile63 = (Spinner) findViewById(R.id.spinnerSetDefaultProfile63);
        final Button btnSetDefaultProfile63 = (Button) findViewById(R.id.btnSetDefaultProfile63);
        btnSetDefaultProfile63.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Note by default on my device (all devices?) the initial default profile is named 'Profile0 (default)'.
                //  Those parentheses are PART of the name, they aren't to denote which profile is the default profile!
                //  Easiest way to confirm the default profile has updated is to use `$adb logcat -s DWAPI` and observe the messages
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SETDEFAULTPROFILE_FROM_6_3, spinnerSetDefaultProfile63.getSelectedItem().toString());
            }
        });

        //  ResetDefaultProfile (6.3 API)
        final Button btnResetDefaultProfile63 = (Button) findViewById(R.id.btnResetDefaultProfile63);
        btnResetDefaultProfile63.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_RESETDEFAULTPROFILE_FROM_6_3, EXTRA_EMPTY);
            }
        });

        //  SwitchToProfile (6.3 API)
        final Spinner spinnerSwitchToProfile63 = (Spinner) findViewById(R.id.spinnerSwitchToProfile63);
        final Button btnSwitchToProfile63 = (Button) findViewById(R.id.btnSwitchToProfile63);
        btnSwitchToProfile63.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SWITCHTOPROFILE_FROM_6_3, spinnerSwitchToProfile63.getSelectedItem().toString());
            }
        });

        //  Set Config (6.4 API)
        Button btnSetConfig64 = (Button) findViewById(R.id.btnSetConfig64);
        btnSetConfig64.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final CheckBox checkEnableCommonDecoders64 = (CheckBox) findViewById(R.id.checkSetConfig64EnableCommonDecoders);
                boolean checkValue = checkEnableCommonDecoders64.isChecked();
                String decoderValue = "false";
                String status = "disabled";
                if (checkValue)
                {
                    decoderValue = "true";
                    status = "enabled";
                }
                //  Note, another example of SetConfig can be found under the CreateProfile test
                Bundle profileConfig = new Bundle();
                profileConfig.putString("PROFILE_NAME", mActiveProfile);
                profileConfig.putString("PROFILE_ENABLED", "true");
                profileConfig.putString("CONFIG_MODE", "UPDATE");
                Bundle barcodeConfig = new Bundle();
                barcodeConfig.putString("PLUGIN_NAME", "BARCODE");
                barcodeConfig.putString("RESET_CONFIG", "true");
                Bundle barcodeProps = new Bundle();
                //barcodeProps.putString("current-device-id", currentDeviceId);  //  current-device-id is DW 6.3 only, replaced by scanner_selection
                barcodeProps.putString("scanner_selection", "auto"); //  Could also specify a number here, the id returned from ENUMERATE_SCANNERS.
                                                                     //  Do NOT use "Auto" here (with a capital 'A'), it must be lower case.
                barcodeProps.putString("scanner_input_enabled", "true");
                barcodeProps.putString("decoder_ean8", decoderValue);
                barcodeProps.putString("decoder_ean13", decoderValue);
                barcodeProps.putString("decoder_upca", decoderValue);
                barcodeConfig.putBundle("PARAM_LIST", barcodeProps);
                profileConfig.putBundle("PLUGIN_CONFIG", barcodeConfig);
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_SET_CONFIG, profileConfig);
                Toast.makeText(getApplicationContext(), "EAN8, EAN13 and UPCA are now " + status + " in profile " + mActiveProfile, Toast.LENGTH_LONG).show();
            }
        });

        //  GetDatawedgeStatus (6.4 API)
        final Button btnGetDatawedgeStatus = (Button) findViewById(R.id.btnGetDatawedgeStatus);
        btnGetDatawedgeStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_DATAWEDGE_STATUS, EXTRA_EMPTY);
            }
        });


        // Create a filter for the broadcast intent
        //  Not ideal to put this here but we want to receive broadcast intents from the ZXing activity
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ENUMERATEDLIST);           //  DW 6.x
        filter.addAction(ACTION_RESULT_DATAWEDGE_FROM_6_2);//  DW 6.2
        filter.addAction(ACTION_RESULT_NOTIFICATION);      //  DW 6.3 for notifications
        filter.addCategory(Intent.CATEGORY_DEFAULT);    //  NOTE: this IS REQUIRED for DW6.2 and up!
        //  Whilst we're here also register to receive broadcasts via DataWedge scanning
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        filter.addAction(getResources().getString(R.string.activity_action_from_service));
        registerReceiver(myBroadcastReceiver, filter);

        sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_VERSION_INFO, EXTRA_EMPTY);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //  The active profile will not have been updated when the application is first launched, it takes
                //  a short amount of time for DataWedge to catch up with the current active application
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_PROFILES_LIST, EXTRA_EMPTY);
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_GET_ACTIVE_PROFILE, EXTRA_EMPTY);
                sendDataWedgeIntentWithExtra(ACTION_DATAWEDGE_FROM_6_2, EXTRA_ENUMERATESCANNERS_FROM_6_3, EXTRA_EMPTY);
            }
        });
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
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            //  This is useful for debugging to verify the format of received intents from DataWedge
            for (String key : b.keySet())
            {
                Log.v(LOG_TAG, key);
            }
            if (action.equals(ACTION_ENUMERATEDLIST))
            {
                //  6.0 API Enumerate Scanners
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
                //  Received a barcode scan
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
                //  Received a barcode scan
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
                    //  6.2 API to GetActiveProfile
                    String activeProfile = intent.getStringExtra(EXTRA_RESULT_GET_ACTIVE_PROFILE);
                    TextView txtActiveProfile = (TextView) findViewById(R.id.txtActiveProfileOutput62);
                    txtActiveProfile.setText(activeProfile);
                    TextView txtActiveProfile63 = (TextView) findViewById(R.id.txtActiveProfile63);
                    txtActiveProfile63.setText("Act on Profile: " + activeProfile);
                    TextView txtSetConfig64ActiveProfile = (TextView) findViewById(R.id.txtSetConfig64ActiveProfile);
                    txtSetConfig64ActiveProfile.setText("Act on Profile: " + activeProfile);
                    mActiveProfile = activeProfile;
                }
                else if (intent.hasExtra(EXTRA_RESULT_GET_PROFILE_LIST))
                {
                    //  6.2 API to GetProfileList
                    String[] profilesList = intent.getStringArrayExtra(EXTRA_RESULT_GET_PROFILE_LIST);
                    //  Profile list for 6.2 APIs
                    Spinner profileListSpinner = (Spinner) findViewById(R.id.spinnerSelectProfile62);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, profilesList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    profileListSpinner.setAdapter(adapter);

                    //  Profile list spinner for 6.3 SetDefaultProfile APIs
                    Spinner spinnerSetDefaultProfile63 = (Spinner) findViewById(R.id.spinnerSetDefaultProfile63);
                    spinnerSetDefaultProfile63.setAdapter(adapter);

                    //  Profile list spinner for 6.3 SwitchToProfile APIs
                    Spinner spinnerSwitchToProfile63 = (Spinner) findViewById(R.id.spinnerSwitchToProfile63);
                    spinnerSwitchToProfile63.setAdapter(adapter);

                }
                else if (intent.hasExtra(EXTRA_RESULT_GET_VERSION_INFO))
                {
                    //  6.3 API for GetVersionInfo
                    String SimulScanVersion  = "Not supported";
                    String[] ScannerFirmware = {"Not available"};
                    Bundle versionInformation = intent.getBundleExtra(EXTRA_RESULT_GET_VERSION_INFO);
                    String DWVersion = versionInformation.getString("DATAWEDGE");
                    String BarcodeVersion = versionInformation.getString("BARCODE_SCANNING");
                    String DecoderVersion = versionInformation.getString("DECODER_LIBRARY");
                    if(versionInformation.containsKey("SCANNER_FIRMWARE")){
                        ScannerFirmware = versionInformation.getStringArray("SCANNER_FIRMWARE");
                    }
                    if(versionInformation.containsKey("SIMULSCAN")) {
                        SimulScanVersion = versionInformation.getString("SIMULSCAN");
                    }
                    String userReadableVersion = "DataWedge: " + DWVersion + ", Barcode: " + BarcodeVersion +
                            ", DecoderVersion: " + DecoderVersion + ", SimulScan: " + SimulScanVersion +
                            ", Scanner Firmware: ";
                    for (int i = 0; i < ScannerFirmware.length; i++)
                        userReadableVersion += ScannerFirmware[i] + " ";
                    TextView txtReceivedVersions = (TextView) findViewById(R.id.txtReceivedVersions);
                    txtReceivedVersions.setText(userReadableVersion);
                    Log.i(LOG_TAG, "DataWedge Version info: " + userReadableVersion);
                    if (DWVersion.compareTo("6.3.0") >= 1)
                        enableUiFor63();
                    if (DWVersion.compareTo("6.4.0") >= 1)
                        enableUiFor64();
                    if (DWVersion.compareTo("6.5.0") >= 1)
                        enableUiFor65();
                }
                else if (intent.hasExtra(EXTRA_RESULT_ENUMERATE_SCANNERS))
                {
                    //  6.3 API to EnumerateScanners.  Note the format is very different from 6.0.
                    ArrayList scanner_list_arraylist = b.getParcelableArrayList(EXTRA_RESULT_ENUMERATE_SCANNERS);
                    //  Enumerate Scanners (6.3) returns a bundle array.  Each bundle has the following keys:
                    //  SCANNER_CONNECTION_STATE
                    //  SCANNER_NAME
                    //  SCANNER_INDEX
                    String[] scanner_list = new String[scanner_list_arraylist.size()];
                    String userFriendlyScanners = "";
                    for (int i = 0; i < scanner_list_arraylist.size(); i++)
                    {
                        String scannerName = (String)((Bundle)scanner_list_arraylist.get(i)).get("SCANNER_NAME");
                        //  Should really store this and pass it during SetConfig.  I just assume the indices are contiguous which is probably not too smart.
                        Integer scannerIndex = (Integer)((Bundle)scanner_list_arraylist.get(i)).get("SCANNER_INDEX");
                        scanner_list[i] = scannerName;
                        userFriendlyScanners += "{" + scannerName + "} ";
                    }

                    //  Update the UI which is used for SetConfig (6.3)
                    Spinner spinnerScannerForSetConfig = (Spinner) findViewById(R.id.spinnerScannerForSetConfig);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, scanner_list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerScannerForSetConfig.setAdapter(adapter);
                    Log.i(LOG_TAG, "Scanners on device: " + userFriendlyScanners);
                }
                else if(intent.hasExtra(EXTRA_RESULT_GET_DATAWEDGE_STATUS))
                {
                    String datawedgeStatus = intent.getStringExtra(EXTRA_RESULT_GET_DATAWEDGE_STATUS);
                    Log.i(LOG_TAG, "Datawedge status is: " + datawedgeStatus);
                    Toast.makeText(getApplicationContext(), "Datawedge status is: " + datawedgeStatus, Toast.LENGTH_LONG).show();
                }
            }
            else if (action.equals(ACTION_RESULT_NOTIFICATION))
            {
                //  6.3 API for RegisterForNotification
                if (intent.hasExtra(EXTRA_RESULT_NOTIFICATION))
                {
                    Bundle extras = intent.getBundleExtra(EXTRA_RESULT_NOTIFICATION);
                    String notificationType = extras.getString(EXTRA_RESULT_NOTIFICATION_TYPE);
                    if (notificationType != null && notificationType.equals(EXTRA_KEY_VALUE_SCANNER_STATUS))
                    {
                        //  We have received a change in Scanner status
                        String userReadableScannerStatus = "Scanner status: " + extras.getString(EXTRA_KEY_VALUE_NOTIFICATION_STATUS) +
                                ", profile: " + extras.getString(EXTRA_KEY_VALUE_NOTIFICATION_PROFILE_NAME);
                        Toast.makeText(getApplicationContext(), userReadableScannerStatus, Toast.LENGTH_SHORT).show();
                        Log.i(LOG_TAG, userReadableScannerStatus);
                    }
                    else if (notificationType != null && notificationType.equals(EXTRA_KEY_VALUE_PROFILE_SWITCH))
                    {
                        //  The profile has changed (Note, this example app does NOT register for this)
                    }
                    else if (notificationType != null && notificationType.equals(EXTRA_KEY_VALUE_CONFIGURATION_UPDATE))
                    {
                        //  Future enhancement only
                    }
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

    private void sendDataWedgeIntentWithExtra(String action, String extraKey, Bundle extras)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extras);
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

    private void enableUiFor63()
    {
        TextView txtHeading63Description = (TextView) findViewById(R.id.txtHeading63Description);
        txtHeading63Description.setText("These will work on any Zebra device running DataWedge 6.3 or higher");

        //  Enable the UI elements which are specific to the DataWedge 6.3 UI
        Button btnGetVersionInfo = (Button) findViewById(R.id.btnGetVersionInfo63);
        btnGetVersionInfo.setEnabled(true);
        Button btnRegisterUnregisterNotification = (Button) findViewById(R.id.btnRegisterUnregisterNotifications63);
        btnRegisterUnregisterNotification.setEnabled(true);
        Button btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile63);
        btnCreateProfile.setEnabled(true);
        Button btnSetConfig = (Button) findViewById(R.id.btnConfigureProfile63);
        btnSetConfig.setEnabled(true);
        Button btnRestoreConfig = (Button) findViewById(R.id.btnRestoreConfig63);
        btnRestoreConfig.setEnabled(true);
        Button btnSoftScanTrigger = (Button) findViewById(R.id.btnSoftScanTrigger63);
        btnSoftScanTrigger.setEnabled(true);
        Button btnScannerInputPlugin = (Button) findViewById(R.id.btnScannerInputPlugin63);
        btnScannerInputPlugin.setEnabled(true);
        Button btnEnumerateScanners = (Button) findViewById(R.id.btnEnumerateScaners63);
        btnEnumerateScanners.setEnabled(true);
        Button btnSetDefaultProfile = (Button) findViewById(R.id.btnSetDefaultProfile63);
        btnSetDefaultProfile.setEnabled(true);
        Button btnResetDefaultProfile = (Button) findViewById(R.id.btnResetDefaultProfile63);
        btnResetDefaultProfile.setEnabled(true);
        Button btnSwitchToProfile = (Button) findViewById(R.id.btnSwitchToProfile63);
        btnSwitchToProfile.setEnabled(true);
    }

    private void enableUiFor64()
    {
        TextView txtHeading64Description = (TextView) findViewById(R.id.txtHeading64Description);
        txtHeading64Description.setText("These will work on any Zebra device running DataWedge 6.4 or higher");

        Button btnSetConfig64 = (Button) findViewById(R.id.btnSetConfig64);
        btnSetConfig64.setEnabled(true);
        Button btnGetDatawedgeStatus = (Button) findViewById(R.id.btnGetDatawedgeStatus);
        btnGetDatawedgeStatus.setEnabled(true);
    }

    private void enableUiFor65()
    {
        //TextView txtHeading65Description = (TextView) findViewById(R.id.txtHeading65Description);
        //txtHeading65Description.setText("These will work on any Zebra device running DataWedge 6.5 or higher");

    }
}
