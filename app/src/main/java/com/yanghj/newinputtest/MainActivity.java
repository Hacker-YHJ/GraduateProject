package com.yanghj.newinputtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity {
    private ToggleButton btn_bp;
    private ToggleButton btn_mf;
    private ToggleButton btn_dt;
    private ToggleButton btn_nl;
    private ToggleButton btn_gk;
    private ToggleButton btn_hy;
    private ToggleButton btn_jq;
    private ToggleButton btn_xw;
    private ToggleButton btn_zzh;
    private ToggleButton btn_cch;
    private ToggleButton btn_ssh;
    private ToggleButton btn_r;

    private ToggleButton[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButtons();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void addButtons() {
        btns = new ToggleButton[12];

        btns[0] = btn_bp = (ToggleButton)findViewById(R.id.btn_bp);
        btns[1] = btn_mf = (ToggleButton)findViewById(R.id.btn_mf);
        btns[2] = btn_dt = (ToggleButton)findViewById(R.id.btn_dt);
        btns[3] = btn_nl = (ToggleButton)findViewById(R.id.btn_nl);
        btns[4] = btn_gk = (ToggleButton)findViewById(R.id.btn_gk);
        btns[5] = btn_hy = (ToggleButton)findViewById(R.id.btn_hy);
        btns[6] = btn_jq = (ToggleButton)findViewById(R.id.btn_jq);
        btns[7] = btn_xw = (ToggleButton)findViewById(R.id.btn_xw);
        btns[8] = btn_zzh = (ToggleButton)findViewById(R.id.btn_zzh);
        btns[9] = btn_cch = (ToggleButton)findViewById(R.id.btn_cch);
        btns[10] = btn_ssh = (ToggleButton)findViewById(R.id.btn_ssh);
        btns[11] = btn_r = (ToggleButton)findViewById(R.id.btn_r);


        btn_bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "bp");
            }
        });
        btn_mf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "mf");
            }
        });
        btn_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "dt");
            }
        });
        btn_nl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "nl");
            }
        });
        btn_gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "gk");
            }
        });
        btn_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "hy");
            }
        });
        btn_jq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "jq");
            }
        });
        btn_xw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "xw");
            }
        });
        btn_zzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "zzh");
            }
        });
        btn_cch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "cch");
            }
        });
        btn_ssh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "ssh");
            }
        });
        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllBtnStatus();
                ((ToggleButton)v).setChecked(true);
                Log.d("BUTTON", "r");
            }
        });
    }

    private void clearAllBtnStatus() {
        for (int i = 0; i < 12; ++i) {
            btns[i].setChecked(false);
        }
    }

}
