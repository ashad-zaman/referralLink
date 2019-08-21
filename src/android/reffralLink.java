package generate.referrallin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class reffralLink extends CordovaPlugin {
    private BroadcastReceiver mReceiver = null;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        mReceiver = new BarcodeReceiver(callbackContext);
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
public class BarcodeReceiver extends BroadcastReceiver {

    private CallbackContext callbackContext;

    public BarcodeReceiver (CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    public void onReceive(Context ctx, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            String referrerString = extras.getString("referrer");
            if (referrerString != null) {
                callbackContext.success(referrerString);
                // SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

                // Editor edit = sharedPreferences.edit();
                // edit.putString("referrer", referrerString);
                // edit.commit();
            }
        }

        // if (intent.getAction().equals(ACTION_BARCODE_SERVICE_BROADCAST)) {
        //     strBarcode = intent.getExtras().getString(KEY_BARCODE_STR);
        //     callbackContext.success(strBarcode);
        // }
    }
} 
