package com.example.aadishdeshpande.educrypt;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import javax.net.ssl.SSLSocketFactory;

public class BitCash extends AppCompatActivity {

    final String GDAX_WS_URL = "wss://ws-feed.gdax.com";
    final String TAG = "GDAX";
    TextView btcPrice;
    TextView btcr;
    FirebaseUser user;
    Integer total;
    Button buy,sell;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    static String total_value_bch;
    String bal;
    Integer total_value_eth;
    private Button wallet;
    static float value;
    float val_coin;
    Float balance;
    String uid;
    float buyValBch;
    TextView Inf;

    //Toolbar mToolbar;
    private FirebaseAuth firebaseAuth;
    WebSocketClient webSocketClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bit_coin);
        initWebsocket();
        wallet = findViewById(R.id.btnwall);
        Inf = findViewById(R.id.tvInf);


        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar mToolbar = findViewById(R.id.tbMain1);
        Bundle bundle = getIntent().getExtras();
        buy = findViewById(R.id.buybtn);
        sell = findViewById(R.id.sellbtn);
        /*if(bundle != null)
        {
            mToolbar.setTitle(bundle.getString("CurrencyName"));
        }*/

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total_value_bch = dataSnapshot.child(uid).child("bch").child("quantity").getValue(String.class);
                bal = dataSnapshot.child(uid).child("balance").getValue(String.class);
                //Toast.makeText(BitCoinActivity.this,bal,Toast.LENGTH_SHORT).show();
                buyValBch = dataSnapshot.child(uid).child("buyValBch").getValue(Float.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(BitCash.this);
                View mView = getLayoutInflater().inflate(R.layout.chose,null);
                final TextView mQty = mView.findViewById(R.id.etQty);
                Button mPurchase = mView.findViewById(R.id.purcahse);


                mPurchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mQty.getText().toString().isEmpty()){
                            Toast.makeText(BitCash.this,"Please Enter a valid quantity",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Integer tot = Integer.parseInt(total_value_bch);
                            String n = mQty.getText().toString();
                            Integer N = Integer.parseInt(n);
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            myRef = firebaseDatabase.getReference(firebaseAuth.getUid());

                            DatabaseReference buyVal = myRef.child("buyValBch");
                            buyVal.setValue(val_coin);

                            DatabaseReference childRef = myRef.child("bch");
                            DatabaseReference childofchild = childRef.child("quantity");

                            balance = Float.parseFloat(bal);

                            value = balance - val_coin * N.intValue();
                            if (value < 0) {
                                Toast.makeText(BitCash.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                            } else {

                                total = Integer.valueOf(N.intValue() + tot.intValue());
                                childofchild.setValue(total.toString());

                                Float new_balance = new Float(value);
                                String newBalance = Float.toString(new_balance);
                                DatabaseReference holding = myRef.child("balance");
                                holding.setValue(newBalance);
                                Toast.makeText(BitCash.this, "Purchased", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

                layoutParams.copyFrom(dialog.getWindow().getAttributes());

                int dialogWindowWidth = (int) (displayWidth * 0.7f);
                int dialogWindowHeight = (int) (displayHeight * 0.5f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;

                dialog.getWindow().setAttributes(layoutParams);

            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(BitCash.this);
                View mView = getLayoutInflater().inflate(R.layout.chose1,null);
                final TextView mQty = mView.findViewById(R.id.etQty);
                Button mPurchase = mView.findViewById(R.id.purcahse);


                mPurchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mQty.getText().toString().isEmpty()){
                            Toast.makeText(BitCash.this,"Please Enter a valid quantity",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Integer tot = Integer.parseInt(total_value_bch);
                            String n = mQty.getText().toString();
                            Integer N = Integer.parseInt(n);
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                            DatabaseReference childRef = myRef.child("bch");
                            DatabaseReference childofchild = childRef.child("quantity");

                            balance = Float.parseFloat(bal);

                            value = balance + val_coin * N.intValue();
                            if (N.intValue() > tot) {
                                Toast.makeText(BitCash.this, "Please Enter a valid quantity to sell", Toast.LENGTH_SHORT).show();
                            } else {

                                total = Integer.valueOf(tot.intValue() - N.intValue());
                                childofchild.setValue(total.toString());

                                if(N.intValue() == tot){
                                    buyValBch = 0.f;
                                    DatabaseReference buyVal = myRef.child("buyValBch");
                                    buyVal.setValue(buyValBch);
                                }

                                Float new_balance = new Float(value);
                                String newBalance = Float.toString(new_balance);
                                DatabaseReference holding = myRef.child("balance");
                                holding.setValue(newBalance);
                                Toast.makeText(BitCash.this, "Sold", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayWidth = displayMetrics.widthPixels;
                int displayHeight = displayMetrics.heightPixels;

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

                layoutParams.copyFrom(dialog.getWindow().getAttributes());

                int dialogWindowWidth = (int) (displayWidth * 0.7f);
                int dialogWindowHeight = (int) (displayHeight * 0.5f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;

                dialog.getWindow().setAttributes(layoutParams);
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BitCash.this,AssistBchActivity.class));
            }
        });
    }

    public void initWebsocket(){

        URI gdaxURI = null;
        try {
            gdaxURI = new URI(GDAX_WS_URL);
        }
        catch(URISyntaxException e){
            e.printStackTrace();
        }
        webSocketClient = new WebSocketClient(gdaxURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG,"onOpen");
                subscribe();
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG,"onOpen" + message);
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(message);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }

                if(jsonObject != null){
                    try {
                        final String price = jsonObject.getString("price");
                        final Float p = Float.parseFloat(price);
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        final String d_price = df.format(p) + "$";
                        val_coin = p;
                        Integer tot = Integer.parseInt(total_value_bch);
                        value = val_coin * tot.intValue();
                        /**/
                        final String ago_price = jsonObject.getString("open_24h");

                        final Float price_ago = Float.parseFloat(ago_price);

                        float r = ((price_ago - p) * 100)/price_ago;
                        float abs_r = Math.abs(r);
                        Float classR = new Float(abs_r);
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        df1.setMaximumFractionDigits(2);
                        final String btc = df1.format(classR) + "%";



                        if(price_ago < p) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.price_text)).setText(d_price);
                                    btcPrice = findViewById(R.id.price_text);
                                    btcPrice.setTextColor(Color.GREEN);
                                    final String b = "+" + btc;
                                    ((TextView)findViewById(R.id.tvBCHRise)).setText(b);
                                    btcr = findViewById(R.id.tvBCHRise);
                                    btcr.setTextColor(Color.GREEN);

                                    if(buyValBch == 0){
                                        Inf.setText("0.0");
                                    }else {
                                        String profit = (p - buyValBch) + "";
                                        Inf.setText(profit);
                                    }
                                }
                            });
                        }

                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.price_text)).setText(d_price);
                                    btcPrice = findViewById(R.id.price_text);
                                    btcPrice.setTextColor(Color.RED);
                                    final String b = "-" + btc;
                                    ((TextView)findViewById(R.id.tvBCHRise)).setText(b);
                                    btcr = findViewById(R.id.tvBCHRise);
                                    btcr.setTextColor(Color.RED);

                                    if(buyValBch == 0){
                                        Inf.setText("0.0");
                                    }else {
                                        String profit = (p - buyValBch) + "";
                                        Inf.setText(profit);
                                    }
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String holding_btc = Float.toString(value);
                    DatabaseReference hold = myRef.child("holding_bch");
                    hold.setValue(holding_btc);

                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG,"onClose");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };

        SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        try {
            webSocketClient.setSocket(factory.createSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }

        webSocketClient.connect();
    }





    public void subscribe(){
        webSocketClient.send("{\n" +
                "    \"type\": \"subscribe\",\n" +
                "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BCH-USD\"] }]\n" +
                "}");

    }
}
