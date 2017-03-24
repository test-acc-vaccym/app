package com.gitlab.PCU.PCU

import android.animation.Animator
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.gitlab.PCU.PCU.helper.Defaults
import com.gitlab.PCU.PCU.helper.Defaults.ResultCode.RESULT_DELETE
import com.gitlab.PCU.PCU.helper.InputFilterMinMax
import com.gitlab.PCU.PCU.helper.MaxLineFilter
import com.gitlab.PCU.PCU.helper.ServerSettingsStore
import kotlinx.android.synthetic.main.single_server_settings.*

/**
 * Created by tim on 20.01.17.
 */

class SingleServerSettings : AppCompatActivity() {

    protected var serverSettingsStore: ServerSettingsStore = Defaults.ServerStatic.SERVER_SETTINGS_STORE
    protected var changed = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(single_server_settings)
        serverSettingsStore = intent.getParcelableExtra<ServerSettingsStore>("in")
        buildViews()
    }

    public override fun onResume() {
        super.onResume()
        single_server_settings.alpha = 0f
        single_server_settings.animate().alpha(1f).setDuration(500).start()
    }

    private fun buildViews() {
        ss_edit_name.filters = arrayOf<InputFilter>(MaxLineFilter(1))
        ss_edit_name.setText(serverSettingsStore.name)
        ss_edit_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                serverSettingsStore.name = s.toString()
                changed = true
            }

            override fun afterTextChanged(s: Editable) {}
        })


        edit_desc.filters = arrayOf<InputFilter>(MaxLineFilter(5))
        edit_desc.setText(serverSettingsStore.desc)
        edit_desc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                serverSettingsStore.desc = s.toString()
                changed = true
            }

            override fun afterTextChanged(s: Editable) {}
        })

        run {
            ip_a.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_a.setText(String.format("%s", serverSettingsStore.ip.int[0]))
            ip_a.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_a.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, 0)
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        run {
            ip_b.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_b.setText(String.format("%s", serverSettingsStore.ip.int[1]))
            ip_b.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_b.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.B, 0)
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        run {
            ip_c.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_c.setText(String.format("%s", serverSettingsStore.ip.int[2]))
            ip_c.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_c.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.C, 0)
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        run {
            ip_d.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_d.setText(String.format("%s", serverSettingsStore.ip.int[3]))
            ip_d.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_d.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.D, 0)
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        edit_server_accept.setOnClickListener { finish(Activity.RESULT_OK) }

        button_delete.setOnClickListener { delete() }

        edit_server_cancel.setOnClickListener { finish(Activity.RESULT_CANCELED) }
    }

    fun delete() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.really_delete)
        builder.setNegativeButton(R.string.cancel) { dialog, which -> }
        builder.setPositiveButton(R.string.delete) { dialog, which -> really_delete() }
        builder.show()
    }

    private fun really_delete() {
        finish(RESULT_DELETE)
    }

    fun finish(result: Int) {
        val data: Intent?
        if (result == Activity.RESULT_OK) {
            data = intent
            data!!.putExtra("out", serverSettingsStore)
        } else if (result == RESULT_DELETE) {
            data = intent
        } else {
            data = null
        }
        setResult(result, data)
        super.finish()
    }

    override fun onBackPressed() {
        finish(Activity.RESULT_CANCELED)
    }

    fun onAdvancedSettings(v: View) {
        val isChecked = (v as CheckBox).isChecked

        val desc_port = findViewById(R.id.desc_port) as TextView
        val port = findViewById(R.id.edit_port) as EditText
        if (isChecked) {
            desc_port.visibility = View.VISIBLE
            port.visibility = View.VISIBLE
            desc_port.alpha = 0f
            desc_port.animate().alpha(1f).setDuration(500).setListener(SAnimListener(null)).start()
            port.alpha = 0f
            port.animate().alpha(1f).setDuration(500).setListener(SAnimListener(null)).start()
        } else {
            desc_port.alpha = 1f
            desc_port.animate().alpha(0f).setDuration(500).setListener(SAnimListener(desc_port)).start()
            port.alpha = 1f
            port.animate().alpha(0f).setDuration(500).setListener(SAnimListener(port)).start()
        }
    }

    private inner class SAnimListener internal constructor(private val view: View?) : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {
            if (view != null) {
                view.visibility = View.GONE
            }
        }

        override fun onAnimationRepeat(animation: Animator) {}

        override fun onAnimationCancel(animation: Animator) {
            if (view != null) {
                view.visibility = View.GONE
            }
        }
    }
}
