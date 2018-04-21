package com.example.aadishdeshpande.educrypt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

public class ChartActivity extends AppCompatActivity implements News.OnFragmentInteractionListener, Guide.OnFragmentInteractionListener, Wallet.OnFragmentInteractionListener {





    WebSocketClient webSocketClient;
    WebSocketClient webSocketClient1;
    float mp = 0;
    private DatabaseReference mRootRef;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    TextView btcPrice;
    TextView ethPrice;

    Toolbar toolbar;
    ListView listView;


    TextView btcr;
    TextView ethr;
    String prev_string;
    final String GDAX_WS_URL = "wss://ws-feed.gdax.com";
    final String TAG = "GDAX";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Toolbar toolbar = findViewById(R.id.tbMain);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        listView = findViewById(R.id.lvMain);
        adapter = new ArrayAdapter<String>(ChartActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Currencies));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ChartActivity.this, "Want To Purchase",Toast.LENGTH_LONG).show();
                if(position == 0){
                    Intent intent = new Intent(ChartActivity.this,BitCoinActivity.class);
                    startActivityForResult(intent,0);
                }
                if(position == 1){
                    Intent intent = new Intent(ChartActivity.this,Ethereumctivity.class);
                    startActivityForResult(intent,1);
                }
                if(position == 2){
                    Intent intent = new Intent(ChartActivity.this, LiteCoinAcitvity.class);
                    startActivityForResult(intent,2);
                }
                if(position == 3){

                    Intent intent = new Intent(ChartActivity.this, BitCash.class);
                    startActivityForResult(intent,3);
                }

            }
        });
        listView.setAdapter(adapter);

        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tlTab);
        tabLayout.addTab(tabLayout.newTab().setText("Guide"));
        tabLayout.addTab(tabLayout.newTab().setText("Wallet"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 1)
                {
                    startActivity(new Intent(ChartActivity.this,WalletActivity.class));
                }
                if(tab.getPosition() == 2)
                {
                    startActivity(new Intent (ChartActivity.this,MainNews.class));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
/*
    uid{
       "balance":"30000",
       "bch":{quantity:"0"},
       "btc":{quantity:"0"},
       "eth":{quantity:"0"},
       "holding_bch":"0",
       "holding_btc":"0",
       "holding_eth":"0",
       "ltc":{quantity:"0"},
       "holding_ltc":"0"
    }
 */