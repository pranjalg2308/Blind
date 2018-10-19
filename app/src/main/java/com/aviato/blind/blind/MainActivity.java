package com.aviato.blind.blind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvIp;
    private TextView tvPort;
    private Button bnConnect;
    private ProgressBar pbConnecting;
    private ImageButton ibDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
    }

    private void initVar() {
        tvIp = findViewById(R.id.tv_ip);
        tvPort = findViewById(R.id.tv_port);
        bnConnect = findViewById(R.id.bn_connect);
        pbConnecting = findViewById(R.id.connection_load);
        ibDisconnect = findViewById(R.id.ib_disconnect);
    }
}
