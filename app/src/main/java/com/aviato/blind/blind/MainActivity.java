package com.aviato.blind.blind;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvIp;
    private TextView tvPort;
    private Button bnConnect;
    private ImageButton ibDisconnect;
    private TextToSpeech textToSpeech;
    private ClientHandlerThread clientHandlerThread;
    private ArrayList<RoadInfo> roadInfoArrayList = new ArrayList<RoadInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
        prepareSocketConnection();
    }

    private void prepareSocketConnection() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.setSpeechRate((float) 1.5);
                    textToSpeech.setPitch((float) 0.6);
                }
            }
        });
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        bnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvIp.getText().toString().isEmpty() || tvPort.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Enter the ip and port", Toast.LENGTH_SHORT).show();
                else {
                    bnConnect.setClickable(false);
                    clientHandlerThread = new ClientHandlerThread(roadInfoArrayList);
                    clientHandlerThread.execute();
                }
            }
        });
        ibDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientHandlerThread.disconnectSocketConnection();
                ibDisconnect.setVisibility(View.GONE);
                bnConnect.setClickable(true);
                bnConnect.setText("Connect");
            }
        });
    }

    private void initVar() {
        tvIp = findViewById(R.id.tv_ip);
        tvPort = findViewById(R.id.tv_port);
        bnConnect = findViewById(R.id.bn_connect);
        ibDisconnect = findViewById(R.id.ib_disconnect);
    }

    private class ClientHandlerThread extends AsyncTask<Void, String, Void> {
        private Socket socket;
        private ArrayList<RoadInfo> roadInfoArrayList;

        public ClientHandlerThread(ArrayList<RoadInfo> roadInfoArrayList) {
            this.roadInfoArrayList = roadInfoArrayList;
        }

        protected void disconnectSocketConnection() {
            try {
                socket.close();
                Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(tvIp.getText().toString(), Integer.parseInt(tvPort.getText().toString())));
                bnConnect.setText("Connected");
                ibDisconnect.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
            textToSpeech.speak(values[0], TextToSpeech.QUEUE_ADD, null);
            roadInfoArrayList.add(new RoadInfo(values[0]));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    if (reader != null) {
                        String msg = reader.readLine();
                        Log.e("String Recieved", msg);
                        publishProgress(msg);
                    } else
                        break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
