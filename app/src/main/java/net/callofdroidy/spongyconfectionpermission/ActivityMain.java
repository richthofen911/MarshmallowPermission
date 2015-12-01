package net.callofdroidy.spongyconfectionpermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private String PERMISSION_READ_CONTACT = Manifest.permission.READ_CONTACTS;

    private WebView wv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv_test = (WebView) findViewById(R.id.wv_test);

        Log.e("checking permission", PERMISSION_READ_CONTACT + " ...");
        if(!checkPermission(PERMISSION_READ_CONTACT)){
            Log.e("Permission", "not granted, trying to request...");
            requestPermission(PERMISSION_READ_CONTACT);
        } else{
            Log.e("Permission", "granted already, visiting google.ca ...");
            Snackbar.make(wv_test, "Permission Granted, trying to visit google.ca...", Snackbar.LENGTH_LONG).show();
            wv_test.loadUrl("http://www.google.ca");
        }

    }

    private boolean checkPermission(String permissionName){
        return (ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(String permissionName){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName)){
            Log.e("request reason", "need the permission");
            Snackbar.make(wv_test, "need Permission: " + permissionName, Snackbar.LENGTH_SHORT).show();
        }else {
            Log.e("requesting Permission", permissionName);
            ActivityCompat.requestPermissions(this, new String[]{permissionName}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e("Permission", PERMISSION_READ_CONTACT + ", granted");
                    Snackbar.make(wv_test, "Permission Granted, trying to visit google.ca...", Snackbar.LENGTH_LONG).show();
                    wv_test.loadUrl("http://www.google.ca");
                }else {
                    Snackbar.make(wv_test, "Permission Denied", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
