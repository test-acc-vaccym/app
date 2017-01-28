package com.gitlab.PCU.PCU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitlab.PCU.PCU.helper.InputFilterMinMax;
import com.gitlab.PCU.PCU.helper.MaxLineFilter;
import com.gitlab.PCU.PCU.helper.ServerSettingsStore;

import static com.gitlab.PCU.PCU.helper.Defaults.ResultCode.RESULT_DELETE;

/**
 * Created by tim on 20.01.17.
 */

public class SingleServerSettings extends AppCompatActivity {

    protected ServerSettingsStore serverSettingsStore;
    protected boolean changed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_server_settings);
        serverSettingsStore = getIntent().getParcelableExtra("in");
        buildViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.single_server_settings);
        relativeLayout.setAlpha(0);
        relativeLayout.animate().alpha(1).setDuration(500).start();
    }

    private void buildViews() {
        EditText et = (EditText) findViewById(R.id.ss_edit_name);
        et.setFilters(new InputFilter[]{new MaxLineFilter(1)});
        et.setText(serverSettingsStore.getName());
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serverSettingsStore.setName(s.toString());
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        EditText ed = (EditText) findViewById(R.id.edit_desc);
        ed.setFilters(new InputFilter[]{new MaxLineFilter(5)});
        ed.setText(serverSettingsStore.getDesc());
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serverSettingsStore.setDesc(s.toString());
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        {
            EditText ip_a = (EditText) findViewById(R.id.ip_a);
            ip_a.setFilters(new InputFilter[]{new MaxLineFilter(1)});
            ip_a.setText(String.format("%s", serverSettingsStore.getIp().getInt()[0]));
            ip_a.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
            ip_a.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, 0);
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()));
                    }
                    changed = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        {
            EditText ip_b = (EditText) findViewById(R.id.ip_b);
            ip_b.setFilters(new InputFilter[]{new MaxLineFilter(1)});
            ip_b.setText(String.format("%s", serverSettingsStore.getIp().getInt()[1]));
            ip_b.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
            ip_b.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.B, 0);
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()));
                    }
                    changed = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        {
            EditText ip_c = (EditText) findViewById(R.id.ip_c);
            ip_c.setFilters(new InputFilter[]{new MaxLineFilter(1)});
            ip_c.setText(String.format("%s", serverSettingsStore.getIp().getInt()[2]));
            ip_c.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
            ip_c.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.C, 0);
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()));
                    }
                    changed = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        {
            EditText ip_d = (EditText) findViewById(R.id.ip_d);
            ip_d.setFilters(new InputFilter[]{new MaxLineFilter(1)});
            ip_d.setText(String.format("%s", serverSettingsStore.getIp().getInt()[3]));
            ip_d.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
            ip_d.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.D, 0);
                    } else {
                        serverSettingsStore.modifyIP(ServerSettingsStore.IPPart.A, Integer.parseInt(s.toString()));
                    }
                    changed = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        Button ba = (Button) findViewById(R.id.edit_server_accept);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(RESULT_OK);
            }
        });

        Button bd = (Button) findViewById(R.id.button_delete);
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        Button bc = (Button) findViewById(R.id.edit_server_cancel);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(RESULT_CANCELED);
            }
        });
    }

    public void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.really_delete);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                really_delete();
            }
        });
        builder.show();
    }

    private void really_delete() {
        finish(RESULT_DELETE);
    }

    public void finish(int result) {
        Intent data;
        if (result == RESULT_OK) {
            data = getIntent();
            data.putExtra("out", serverSettingsStore);
        } else if (result == RESULT_DELETE) {
            data = getIntent();
        } else {
            data = null;
        }
        setResult(result, data);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish(RESULT_CANCELED);
    }

    public void onAdvancedSettings(View v) {
        boolean isChecked = ((CheckBox) v).isChecked();

        TextView desc_port = (TextView) findViewById(R.id.desc_port);
        EditText port = (EditText) findViewById(R.id.edit_port);
        if (isChecked) {
            desc_port.setVisibility(View.VISIBLE);
            port.setVisibility(View.VISIBLE);
            desc_port.setAlpha(0);
            desc_port.animate().alpha(1).setDuration(500).start();
            port.setAlpha(0);
            port.animate().alpha(1).setDuration(500).start();
        } else {
            desc_port.setAlpha(1);
            desc_port.animate().alpha(0).setDuration(500).start();
            port.setAlpha(1);
            port.animate().alpha(0).setDuration(500).start();
            desc_port.setVisibility(View.GONE);
            port.setVisibility(View.GONE);
        }
    }
}
