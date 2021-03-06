package com.github.PCUnlocker.app

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.github.PCUnlocker.app.ServerSettings.ListActivity
import com.github.PCUnlocker.app.helper.Defaults.RequestCode
import com.github.PCUnlocker.app.helper.StaticMethods.isServiceRunning
import kotlinx.android.synthetic.main.activity_main_screen.*

/**
 * The Main Screen you see, when you are starting the app
 */

class MainScreen : AppCompatActivity() {

    /**
     * The System Keyguard Manager
     */
    private var mKeyguardManager: KeyguardManager? = null
    /**
     * The Settings
     */
    private var settings: Intent? = null

    /**
     * The Settings
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
        settings = Intent(applicationContext, SettingsActivity::class.java)
        serverSettings = Intent(applicationContext, ListActivity::class.java)
        setContentView(R.layout.activity_main_screen)
        setSupportActionBar(main_toolbar)
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
                startActivity(settings)
            }
            RequestCode.REQUEST_CODE_SERVER_SETTINGS -> if (resultCode == Activity.RESULT_OK) {
                startActivity(serverSettings)
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

    @Suppress("UNUSED_PARAMETER")
    fun onClickSettings(item: MenuItem) {
        showAuthenticationScreen(RequestCode.REQUEST_CODE_SETTINGS)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickServerSettings(item: MenuItem) {
        showAuthenticationScreen(RequestCode.REQUEST_CODE_SERVER_SETTINGS)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickFabMain(view: View) {
        //showAuthenticationScreen(RequestCode.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK);
        startActivity(Intent(applicationContext, UnlockActivity::class.java))
    }
}
