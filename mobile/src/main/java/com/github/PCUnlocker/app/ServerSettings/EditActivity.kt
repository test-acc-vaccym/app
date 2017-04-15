package com.github.PCU.android.ServerSettings

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
import android.view.View.VISIBLE
import android.view.View.GONE
import android.view.animation.Animation
import android.widget.CheckBox
import com.github.PCU.android.R
import com.github.PCU.android.helper.Defaults.ResultCode.RESULT_DELETE
import com.github.PCU.android.helper.ExpandCollapseAnimation
import com.github.PCU.android.helper.InputFilterMinMax
import com.github.PCU.android.helper.MaxLineFilter
import com.github.PCU.android.helper.ServerSettingsStore
import kotlinx.android.synthetic.main.activity_edit.*


/**
 * Created by tim on 20.01.17.
 */

class EditActivity : AppCompatActivity() {

    protected var serverSettingsStore: ServerSettingsStore? = null
    protected var changed = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        serverSettingsStore = intent.getParcelableExtra<ServerSettingsStore>("in")
        if (serverSettingsStore != null) {
            buildViews(serverSettingsStore!!)
        }
    }

    public override fun onResume() {
        super.onResume()
        single_server_settings.alpha = 0f
        single_server_settings.animate().alpha(1f).setDuration(500).start()
    }

    private fun buildViews(lServerSettingsStore: ServerSettingsStore) {
        ss_edit_name.filters = arrayOf<InputFilter>(MaxLineFilter(1))
        ss_edit_name.setText(lServerSettingsStore.name)
        ss_edit_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                lServerSettingsStore.name = s.toString()
                changed = true
            }

            override fun afterTextChanged(s: Editable) {}
        })

        edit_desc.filters = arrayOf<InputFilter>(MaxLineFilter(5))
        edit_desc.setText(lServerSettingsStore.desc)
        edit_desc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                lServerSettingsStore.desc = s.toString()
                changed = true
            }

            override fun afterTextChanged(s: Editable) {}
        })

        run {
            ip_a.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_a.setText(String.format("%s", lServerSettingsStore.ip.int[0]))
            ip_a.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_a.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, 0)
                    } else {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        run {
            ip_b.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_b.setText(String.format("%s", lServerSettingsStore.ip.int[1]))
            ip_b.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_b.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.B, 0)
                    } else {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.B, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        run {
            ip_c.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_c.setText(String.format("%s", lServerSettingsStore.ip.int[2]))
            ip_c.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_c.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.C, 0)
                    } else {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.C, Integer.parseInt(s.toString()))
                    }
                    changed = true
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

        run {
            ip_d.filters = arrayOf<InputFilter>(MaxLineFilter(1))
            ip_d.setText(String.format("%s", lServerSettingsStore.ip.int[3]))
            ip_d.filters = arrayOf<InputFilter>(InputFilterMinMax(0, 255))
            ip_d.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.D, 0)
                    } else {
                        lServerSettingsStore.modifyIP(ServerSettingsStore.IPPart.D, Integer.parseInt(s.toString()))
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
            data.putExtra("out", serverSettingsStore)
        } else if (result == RESULT_DELETE) {
            data = intent
            data.putExtra("out", serverSettingsStore)
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
        if (isChecked) {
            val animation_l: Animation = ExpandCollapseAnimation(port_layout, 500, ExpandCollapseAnimation.Type.SHOW, this)
            port_layout.startAnimation(animation_l)
        } else {
            val animation_l: Animation = ExpandCollapseAnimation(port_layout, 500, ExpandCollapseAnimation.Type.HIDE, this)
            port_layout.startAnimation(animation_l)
        }
    }

    private inner class ClosEndAnimListener internal constructor(private val action: (Animator) -> Unit) : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {}

        override fun onAnimationEnd(animation: Animator) {
            action(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {}

        override fun onAnimationCancel(animation: Animator) {}
    }
}
