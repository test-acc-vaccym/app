package com.gitlab.PCU.PCU;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gitlab.PCU.PCU.helper.ip;


public class MainScreen extends AppCompatActivity {

    private static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK = 1;
    private static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_SETTINGS = 2;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private KeyguardManager mKeyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!mKeyguardManager.isDeviceSecure()) {
            Intent intent = new Intent(this, DeviceNotSecure.class);
            startActivity(intent);
            System.exit(1);
        }
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_unlock);
        View.OnClickListener fab_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAuthenticationScreen(REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK);
            }
        };
        fab.setOnClickListener(fab_listener);
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(R.string.tv_default);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_SETTINGS:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void showAuthenticationScreen(int requestCode) {
        // Create the Confirm Credentials screen. You can customize the title and description. Or
        // we will provide a generic one for you if you leave it null
        Intent intent = mKeyguardManager.createConfirmDeviceCredentialIntent(null, getResources().getString(R.string.auth_text));
        if (intent != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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

    public void onClickSettings(MenuItem item) {
        showAuthenticationScreen(REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_SETTINGS);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String do_auth(ip:server_ip);
}

