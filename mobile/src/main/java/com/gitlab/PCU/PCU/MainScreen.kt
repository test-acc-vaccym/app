package com.gitlab.PCU.PCU

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.gitlab.PCU.PCU.helper.Defaults.RequestCode
import com.gitlab.PCU.PCU.helper.StaticMethods.isServiceRunning

/**
 * The Main Screen you see, when you are starting the app
 */

class MainScreen : AppCompatActivity() {

    /**
     * The System Keyguard Manager
     */
    private var mKeyguardManager: KeyguardManager? = null
    /**
     * The Server Settings
     */
    private var serverSettings: Intent? = null

    /**
     * {@inheritDoc}
     *
     *
     * Also gets the keyguard manager, checks if device is secure (if not @link {@docRoot}/com/gitlab/PCU/PCU/DeviceNotSecure.html ist started)
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mKeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!mKeyguardManager!!.isDeviceSecure) {
            val intent = Intent(this, DeviceNotSecure::class.java)
            startActivity(intent)
        }
        serverSettings = Intent(this, SettingsActivity::class.java)
        setContentView(R.layout.activity_main_screen)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        if (!isServiceRunning(applicationContext, BackgroundService::class.java)) {
            //start the service
            val service = Intent(applicationContext, BackgroundService::class.java)
            application.startService(service)
        }
    }

    /**

     * @param requestCode Code for the request
     * *
     * @param resultCode Resulted Code
     * *
     * @param data intent with is done
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            RequestCode.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show()
            }
            RequestCode.REQUEST_CODE_SETTINGS -> if (resultCode == Activity.RESULT_OK) {
                startActivity(serverSettings)
            }
            RequestCode.REQUEST_CODE_SERVER_SETTINGS -> if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(this, ServerSettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showAuthenticationScreen(requestCode: Int) {
        // Create the Confirm Credentials screen. You can customize the title and description. Or
        // we will provide a generic one for you if you leave it null
        val intent = mKeyguardManager!!.createConfirmDeviceCredentialIntent(null, resources.getString(R.string.auth_text))
        if (intent != null) {
            startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun onClickSettings(item: MenuItem) {
        showAuthenticationScreen(RequestCode.REQUEST_CODE_SETTINGS)
    }

    fun onClickServerSettings(item: MenuItem) {
        showAuthenticationScreen(RequestCode.REQUEST_CODE_SERVER_SETTINGS)
    }

    fun onClickFabMain(view: View) {
        //showAuthenticationScreen(RequestCode.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK);
        startActivity(Intent(applicationContext, UnlockActivity::class.java))
    }
}
