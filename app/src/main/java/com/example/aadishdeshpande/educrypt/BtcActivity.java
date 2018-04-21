package com.example.aadishdeshpande.educrypt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import javax.net.ssl.SSLSocketFactory;

public class BtcActivity extends AppCompatActivity {

    final String GDAX_WS_URL = "wss://ws-feed.gdax.com";
    final String TAG = "GDAX";
    TextView btcPrice;
    TextView btcr;
    //Toolbar mToolbar;
    WebSocketClient webSocketClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btc);
        Toolbar mToolbar = findViewById(R.id.tbMain1);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            mToolbar.setTitle(bundle.getString("CurrencyName"));
            if(mToolbar.getTitle().toString().equalsIgnoreCase("Ethereum(ETH)")){
                startActivity(new Intent(BtcActivity.this,Ethereumctivity.class));
            }
            if(mToolbar.getTitle().toString().equalsIgnoreCase("BitCoin(BTC)")){
                startActivity(new Intent(BtcActivity.this,BitCoinActivity.class));
            }
        }
    }

}
