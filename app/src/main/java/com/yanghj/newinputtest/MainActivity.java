package com.yanghj.newinputtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;


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

    private ArrayList<ToggleButton> btns;

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
        btn_bp = (ToggleButton)findViewById(R.id.btn_bp);
        btn_mf = (ToggleButton)findViewById(R.id.btn_mf);
        btn_dt = (ToggleButton)findViewById(R.id.btn_dt);
        btn_nl = (ToggleButton)findViewById(R.id.btn_nl);
        btn_gk = (ToggleButton)findViewById(R.id.btn_gk);
        btn_hy = (ToggleButton)findViewById(R.id.btn_hy);
        btn_jq = (ToggleButton)findViewById(R.id.btn_jq);
        btn_xw = (ToggleButton)findViewById(R.id.btn_xw);
        btn_zzh = (ToggleButton)findViewById(R.id.btn_zzh);
        btn_cch = (ToggleButton)findViewById(R.id.btn_cch);
        btn_ssh = (ToggleButton)findViewById(R.id.btn_ssh);
        btn_r = (ToggleButton)findViewById(R.id.btn_r);

        btns = new ArrayList<>();
        btns.add(btn_bp);
        btns.add(btn_mf);
        btns.add(btn_dt);
        btns.add(btn_nl);
        btns.add(btn_gk);
        btns.add(btn_hy);
        btns.add(btn_jq);
        btns.add(btn_xw);
        btns.add(btn_zzh);
        btns.add(btn_cch);
        btns.add(btn_ssh);
        btns.add(btn_r);

        btn_bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "bp");
            }
        });
        btn_mf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "mf");
            }
        });
        btn_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "dt");
            }
        });
        btn_nl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "nl");
            }
        });
        btn_gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "gk");
            }
        });
        btn_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "hy");
            }
        });
        btn_jq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "jq");
            }
        });
        btn_xw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "xw");
            }
        });
        btn_zzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "zzh");
            }
        });
        btn_cch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "cch");
            }
        });
        btn_ssh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "ssh");
            }
        });
        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON", "r");
            }
        });
    }
}
