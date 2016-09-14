# DataWedge-API-Exerciser

This application has been written to exercise the Zebra DataWedge Data Capture API (http://techdocs.zebra.com/datawedge/5-0/guide/api/).

###This application is provided without guarantee or warranty

Zebra DataWedge is a 'zero code' solution to capture barcode, magnetic stripe and OCR data on Zebra devices.  DataWedge is a profile-based service running on Zebra mobile computers and offers an intent based API for user applications to interact and control.  The intent based API offers limited functionality for controlling the scanning and profile aspects of DataWedge

Application to exercise the DataWedge Intent API for testing purposes
##APIs:
* SoftScanTrigger - used to start, stop or toggle a software scanning trigger
* ScannerInputPlugin - enable/disable the scanner Plug-in used by the active Profile
* enumerateScanners - returns a list of scanners available on the device
* setDefaultProfile - sets the specified Profile as the default Profile
* resetDefaultProfile - resets the default Profile to Profile0
* switchToProfile - switches to the specified Profile

##Device Configuration:
1. Set up a Datawedge profile that will be in effect when this application is run [to get started easily, just modify 'Profile0 (default)].  
2. Ensure Datawedge is enabled and the configured profile has enabled the 'Barcode input' plugin.  
  * To test steps 1 & 2 launch any app and press the barcode trigger, you should see a beam.
3. Configure the datawedge output plugin as follows.
  * Intent Output: Enabled
  * Intent action: com.zebra.dwapiexerciser.ACTION
  * Intent category: leave blank

##Use:
Hopefully the UI is self explanatory.  Returned barcode data is shown at the top of the view with some indication whether the intent (from Datawedge) was invoked through startActivity(), sendBroadcast() or startService().

To mimic DataWedge on a non-Zebra device you can use adb to send an intent of the same format that DataWedge would usually send on scan:
```
adb shell am start -a com.zebra.dwapiexerciser.ACTION -e com.symbol.datawedge.data_string 0123456789 -e com.symbol.datawedge.source scanner -e com.symbol.datawedge.label_type EAN13
```
