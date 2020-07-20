package cordova.plugins.permission;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.Manifest;
import android.net.Uri;
import android.os.Build;

import java.util.HashMap;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;


class PermissionStatus{
    public static final String GRANTED = "GRANTED";
    public static final String DENIED_ONCE = "DENIED_ONCE";
    public static final String DENIED_ALWAYS = "DENIED_ALWAYS";
    public static final String NOT_REQUESTED = "NOT_REQUESTED";
}
/**
 * This class echoes a string called from JavaScript.
 */
public class permission extends CordovaPlugin {

    public static final HashMap<String, String> PermissionValues = new HashMap<>();
    static {
        PermissionValues.put("READ_CALENDAR", Manifest.permission.READ_CALENDAR);
        PermissionValues.put("WRITE_CALENDAR", Manifest.permission.WRITE_CALENDAR);
        PermissionValues.put("CAMERA", Manifest.permission.CAMERA);
        PermissionValues.put("READ_CONTACTS", Manifest.permission.READ_CONTACTS);
        PermissionValues.put("WRITE_CONTACTS", Manifest.permission.WRITE_CONTACTS);
        PermissionValues.put("GET_ACCOUNTS", Manifest.permission.GET_ACCOUNTS);
        PermissionValues.put("ACCESS_FINE_LOCATION", Manifest.permission.ACCESS_FINE_LOCATION);
        PermissionValues.put("ACCESS_COARSE_LOCATION", Manifest.permission.ACCESS_COARSE_LOCATION);
        PermissionValues.put("ACCESS_BACKGROUND_LOCATION", "android.permission.ACCESS_BACKGROUND_LOCATION");
        PermissionValues.put("RECORD_AUDIO", Manifest.permission.RECORD_AUDIO);
        PermissionValues.put("READ_PHONE_STATE", Manifest.permission.READ_PHONE_STATE);
        PermissionValues.put("CALL_PHONE", Manifest.permission.CALL_PHONE);
        PermissionValues.put("READ_CALL_LOG", Manifest.permission.READ_CALL_LOG);
        PermissionValues.put("WRITE_CALL_LOG", Manifest.permission.WRITE_CALL_LOG);
        PermissionValues.put("ADD_VOICEMAIL", Manifest.permission.ADD_VOICEMAIL);
        PermissionValues.put("USE_SIP", Manifest.permission.USE_SIP);
        PermissionValues.put("PROCESS_OUTGOING_CALLS", Manifest.permission.PROCESS_OUTGOING_CALLS);
        PermissionValues.put("BODY_SENSORS", Manifest.permission.BODY_SENSORS);
        PermissionValues.put("SEND_SMS", Manifest.permission.SEND_SMS);
        PermissionValues.put("RECEIVE_SMS", Manifest.permission.RECEIVE_SMS);
        PermissionValues.put("READ_SMS", Manifest.permission.READ_SMS);
        PermissionValues.put("RECEIVE_WAP_PUSH", Manifest.permission.RECEIVE_WAP_PUSH);
        PermissionValues.put("RECEIVE_MMS", Manifest.permission.RECEIVE_MMS);
        PermissionValues.put("READ_EXTERNAL_STORAGE", Manifest.permission.READ_EXTERNAL_STORAGE);
        PermissionValues.put("WRITE_EXTERNAL_STORAGE", Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    private static final int PERMISSION_CODE = 1;
    private static final int PERMISSIONS_CODE = 2;
    private CallbackContext callbackContext = null;
    private Context context = null;
    private Activity activity = null;
    private SharedPreferences sharedPreferences;
    private final String SHARED_PREF_FILE = "com.venkat.share.sharepreffile";
    private boolean NOT_REQUESTED = true;
    private int REQUEST_CODE = 1;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        initialization(callbackContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String message;
            switch (action){
                case "checkPermission": message = args.getString(0);
                    checkPermission(message);
                    break;
                case "checkPermissions": checkPermissions(args);
                    break;
                case "requestPermission": message = args.getString(0);
                    requestPermission(message);
                    break;
                case "requestPermissions": requestPermissions(args);
                    break;
                case "forceAskPermission": forceAskPermission();
                    break;
                default : return false;
            }
            return true;
        } else {
             return true;
        }
    }

    private void initialization(CallbackContext callbackContext){
        this.callbackContext = callbackContext;
        this.activity =  this.cordova.getActivity();
        this.context = this.cordova.getActivity().getApplicationContext();
        this.sharedPreferences = this.context.getSharedPreferences(this.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        this.NOT_REQUESTED = this.sharedPreferences.getBoolean("NOT_REQUESTED", true);
    }

    private String _checkPermission(String message) {
        if (message != null && message.length() > 0) {
            if(this.NOT_REQUESTED){
                return("Success:" + PermissionStatus.NOT_REQUESTED);
            } else{
                if (this.cordova.hasPermission(PermissionValues.get(message))){
                    return("Success:" + PermissionStatus.GRANTED);
                } else {
                    if(this.activity.shouldShowRequestPermissionRationale(PermissionValues.get(message))){
                        return("Success:" + PermissionStatus.DENIED_ONCE);
                    } else {
                        return("Success:" + PermissionStatus.DENIED_ALWAYS);
                    }
                }
            }
        } else {
            return("Error:Expected one non-empty string argument.");
        }
    }

    private void checkPermission(String message) {
        String Info = this._checkPermission(message);
        String header = Info.split(":")[0];
        switch(header){
            case "Success" : this.callbackContext.success(Info.split(":")[1]);
                break;
            case "Error" : this.callbackContext.error(Info.split(":")[1]);
                break;
        }
        
    }

    private void checkPermissions(JSONArray permissions) {
        try{
            int length = permissions.length();
            if(length <= 0){
                this.callbackContext.error("Expected one non-empty string argument.");
            }
            JSONArray array = new JSONArray();
            for(int i=0; i<length; i++){
                String Info = this._checkPermission(permissions.getString(i));
                String header = Info.split(":")[0];
                switch(header){
                    case "Success" : array.put(Info.split(":")[1]);
                        break;
                    case "Error" : this.callbackContext.error(Info.split(":")[1]);
                        break;
                }
            }
            this.callbackContext.success(array);
        } catch(JSONException e){
            this.callbackContext.error(e.getLocalizedMessage());
        }
    }

    private void requestPermission(String message) {
        if (message != null && message.length() > 0) {
            cordova.requestPermission(this, PERMISSION_CODE, PermissionValues.get(message));
        } else {
            this.callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void requestPermissions(JSONArray permissions) {
        try{
            String[] permission_list = new String[permissions.length()];
            if(permissions.length() > 0) {
                for(int i=0; i<permissions.length(); i++){
                    permission_list[i] = PermissionValues.get(permissions.getString(i));
                }
                cordova.requestPermissions(this, PERMISSIONS_CODE, permission_list);
            } else {
                this.callbackContext.error("Expected one non-empty string argument.");
            }
        }catch(Exception e){}
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        if(requestCode == PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                this.loadPreferences();
                this.callbackContext.success(PermissionStatus.GRANTED);
            }
            else{
                this.loadPreferences();
                String[] parts = permissions[0].split("\\.");
                this.checkPermission(parts[parts.length - 1]);
            }
        }
        if(requestCode == PERMISSIONS_CODE){
            int length = permissions.length;
            JSONArray permission_values = new JSONArray();
            if(length > 0){
                for(int i=0; i<length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        this.loadPreferences();
                        permission_values.put(PermissionStatus.GRANTED);
                    }else{
                        this.loadPreferences();
                        String[] parts = permissions[0].split("\\.");
                        String Info = this._checkPermission(parts[parts.length - 1]);
                        String header = Info.split(":")[0];
                        switch(header){
                            case "Success" : permission_values.put(Info.split(":")[1]);
                                break;
                            case "Error" : permission_values.put(Info.split(":")[1]);
                                break;
                        }
                    }
                }
                this.callbackContext.success(permission_values);               
            }
        }
    }

    private void loadPreferences(){
        SharedPreferences.Editor preferencesEditor = this.sharedPreferences.edit();
        preferencesEditor.putBoolean("NOT_REQUESTED", false);
        preferencesEditor.apply();
        this.NOT_REQUESTED = this.sharedPreferences.getBoolean("NOT_REQUESTED", true);
        return;
    }

    private void forceAskPermission(){
        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + this.activity.getPackageName()));
        this.cordova.startActivityForResult(this,i,REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                this.callbackContext.success("Successful");
            } else {
                this.callbackContext.error("Error in geting the result");
            }
        }
    }
}
