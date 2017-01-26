package com.gitlab.PCU.PCU;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gitlab.PCU.PCU.helper.InputFilterMinMax;
import com.gitlab.PCU.PCU.helper.ServerSettingsStore;

/**
 * Created by tim on 20.01.17.
 */

public class SingleServerSettings extends AppCompatActivity {

    protected ServerSettingsStore serverSettingsStore;
    protected boolean changed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_server_settings);
        serverSettingsStore = getIntent().getParcelableExtra("in");
        buildViews();
    }

    @SuppressLint("SetTextI18n")
    private void buildViews() {
        EditText et = (EditText) findViewById(R.id.edit_name);
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
            ip_a.setText(Integer.toString(serverSettingsStore.getIp().getInt()[0]));
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
            ip_b.setText(Integer.toString(serverSettingsStore.getIp().getInt()[1]));
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
            ip_c.setText(Integer.toString(serverSettingsStore.getIp().getInt()[2]));
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
            ip_d.setText(Integer.toString(serverSettingsStore.getIp().getInt()[3]));
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

        Button bc = (Button) findViewById(R.id.edit_server_cancel);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(RESULT_CANCELED);
            }
        });
    }

    public void finish(int result) {
        Intent data;
        if (result == RESULT_OK) {
            data = getIntent();
            data.putExtra("id", getIntent().getStringExtra("id"));
            data.putExtra("out", serverSettingsStore);
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
}
