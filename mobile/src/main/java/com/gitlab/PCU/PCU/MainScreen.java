package com.gitlab.PCU.PCU;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static com.gitlab.PCU.PCU.helper.Defaults.RequestCode;

/**
 * The Main Screen you see, when you are starting the app
 */

public class MainScreen extends AppCompatActivity {

    /**
     * The System Keyguard Manager
     */
    private KeyguardManager mKeyguardManager;
    /**
     * The Server Settings
     */
    private Intent serverSettings;

    /**
     * {@inheritDoc}
     * <p>
     * Also gets the keyguard manager, checks if device is secure (if not @link {@docRoot}/com/gitlab/PCU/PCU/DeviceNotSecure.html ist started)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!mKeyguardManager.isDeviceSecure()) {
            Intent intent = new Intent(this, DeviceNotSecure.class);
            startActivity(intent);
        }
        serverSettings = new Intent(this, SettingsActivity.class);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
                }
                break;
            case RequestCode.REQUEST_CODE_SETTINGS:
                if (resultCode == RESULT_OK) {
                    startActivity(serverSettings);
                }
                break;
            case RequestCode.REQUEST_CODE_SERVER_SETTINGS:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, ServerSettingsActivity.class);
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
        showAuthenticationScreen(RequestCode.REQUEST_CODE_SETTINGS);
    }

    public void onClickServerSettings(MenuItem item) {
        showAuthenticationScreen(RequestCode.REQUEST_CODE_SERVER_SETTINGS);
    }

    public void onClickFabMain(View view) {
        showAuthenticationScreen(RequestCode.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK);
    }
}
