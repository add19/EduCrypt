package com.example.aadishdeshpande.educrypt;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class WalletActivity extends AppCompatActivity implements News.OnFragmentInteractionListener, Guide.OnFragmentInteractionListener, Wallet.OnFragmentInteractionListener {

    private TextView amount,holdin;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid;
    FirebaseUser user;
    //String amountValue;
    Float total_amount;
    String bal;
    String btc;
    String ltc;
    String bch;
    String eth;

    Float btcHolding;
    Float bchHolding;
    Float ltcHolding;
    Float ethHolding;

    String btc_holding;
    String ltc_holding;
    String bch_holding;
    String eth_holding;
    Float amountValue;
    ListView walletHolding;
    ArrayList<String> userWallet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        walletHolding = findViewById(R.id.lvWallet);
        holdin = findViewById(R.id.tvBalance);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(WalletActivity.this, android.R.layout.simple_list_item_1, userWallet);;

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                btc = "Holding in BitCoin                         " + dataSnapshot.child(uid).child("btc").child("quantity").getValue(String.class);
                eth = "Holding in Ethereum                     " + dataSnapshot.child(uid).child("eth").child("quantity").getValue(String.class);
                ltc = "Holding in LiteCoin                        " + dataSnapshot.child(uid).child("ltc").child("quantity").getValue(String.class);
                bch = "Holding in BitCoinCash                 " + dataSnapshot.child(uid).child("bch").child("quantity").getValue(String.class);
                //bal = dataSnapshot.child(uid).child("balance").getValue(String.class);
                //Toast.makeText(BitCoinActivity.this,bal,Toast.LENGTH_SHORT).show();
                bal = dataSnapshot.child(uid).child("balance").getValue(String.class);
                btc_holding = dataSnapshot.child(uid).child("holding_btc").getValue(String.class);
                ltc_holding = dataSnapshot.child(uid).child("holding_ltc").getValue(String.class);
                bch_holding = dataSnapshot.child(uid).child("holding_bch").getValue(String.class);
                eth_holding = dataSnapshot.child(uid).child("holding_eth").getValue(String.class);
                holdin.setText(bal);

                btcHolding = Float.parseFloat(btc_holding);
                ethHolding = Float.parseFloat(eth_holding);
                bchHolding = Float.parseFloat(bch_holding);
                ltcHolding = Float.parseFloat(ltc_holding);
                //Toast.makeText(WalletActivity.this,btc_holding,Toast.LENGTH_SHORT).show();
                //Toast.makeText(WalletActivity.this,ltc_holding,Toast.LENGTH_SHORT).show();
                //Toast.makeText(WalletActivity.this,bch_holding,Toast.LENGTH_SHORT).show();
                //Toast.makeText(WalletActivity.this,eth_holding,Toast.LENGTH_SHORT).show();
                //walletHolding.setAdapter(arrayAdapter);

                firebaseAuth = FirebaseAuth.getInstance();
                amount = findViewById(R.id.tvValue);



                amountValue = btcHolding.floatValue() + ethHolding.floatValue() + bchHolding.floatValue() + ltcHolding.floatValue();
                String v = Float.toString(amountValue);
                amount.setText(v);

                userWallet.add(btc);
                userWallet.add(eth);
                userWallet.add(bch);
                userWallet.add(ltc);
                arrayAdapter.notifyDataSetChanged();

                HashSet<String> hashSet = new HashSet<String>();
                hashSet.addAll(userWallet);
                userWallet.clear();
                userWallet.addAll(hashSet);

                Collections.sort(userWallet);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(WalletActivity.this, android.R.layout.simple_list_item_1, userWallet);;
                walletHolding.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        TabLayout tabLayout = findViewById(R.id.tlTab);
        tabLayout.addTab(tabLayout.newTab().setText("Guide"));
        tabLayout.addTab(tabLayout.newTab().setText("Wallet"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /*final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0)
                {
                    startActivity(new Intent(WalletActivity.this,ChartActivity.class));
                }
                if(tab.getPosition() == 2)
                {
                    //startActivity(new Intent (ChartActivity.this));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/



        walletHolding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    //Toast.makeText(ChartActivity.this, "Want To Purchase",Toast.LENGTH_LONG).show();
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WalletActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.chose2, null);
                    TextView mQty = mView.findViewById(R.id.tvHold);
                    TextView buy = mView.findViewById(R.id.tvBuy);
                    TextView sell = mView.findViewById(R.id.tvSell);
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, BitCoinActivity.class));
                        }
                    });

                    sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, BitCoinActivity.class));

                        }
                    });

                    String display = "Sell/Buy?";
                    mQty.setText(display);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    int displayHeight = displayMetrics.heightPixels;

                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

                    layoutParams.copyFrom(dialog.getWindow().getAttributes());

                    int dialogWindowWidth = (int) (displayWidth * 0.9f);
                    int dialogWindowHeight = (int) (displayHeight * 0.3f);
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;

                    dialog.getWindow().setAttributes(layoutParams);
                }

                if(position == 1) {
                    //Toast.makeText(ChartActivity.this, "Want To Purchase",Toast.LENGTH_LONG).show();
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WalletActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.chose2, null);
                    TextView mQty = mView.findViewById(R.id.tvHold);
                    TextView buy = mView.findViewById(R.id.tvBuy);
                    TextView sell = mView.findViewById(R.id.tvSell);
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, BitCash.class));
                        }
                    });

                    sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, BitCash.class));
                        }
                    });

                    String display = "Sell/Buy?";
                    mQty.setText(display);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    int displayHeight = displayMetrics.heightPixels;

                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

                    layoutParams.copyFrom(dialog.getWindow().getAttributes());

                    int dialogWindowWidth = (int) (displayWidth * 0.9f);
                    int dialogWindowHeight = (int) (displayHeight * 0.3f);
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;

                    dialog.getWindow().setAttributes(layoutParams);
                }

                if(position == 2) {
                    //Toast.makeText(ChartActivity.this, "Want To Purchase",Toast.LENGTH_LONG).show();
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WalletActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.chose2, null);
                    TextView mQty = mView.findViewById(R.id.tvHold);
                    TextView buy = mView.findViewById(R.id.tvBuy);
                    TextView sell = mView.findViewById(R.id.tvSell);
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, Ethereumctivity.class));
                        }
                    });

                    sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, Ethereumctivity.class));
                        }
                    });

                    String display = "Sell/Buy?";
                    mQty.setText(display);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    int displayHeight = displayMetrics.heightPixels;

                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

                    layoutParams.copyFrom(dialog.getWindow().getAttributes());

                    int dialogWindowWidth = (int) (displayWidth * 0.9f);
                    int dialogWindowHeight = (int) (displayHeight * 0.3f);
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;

                    dialog.getWindow().setAttributes(layoutParams);
                }

                if(position == 3) {
                    //Toast.makeText(ChartActivity.this, "Want To Purchase",Toast.LENGTH_LONG).show();
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WalletActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.chose2, null);
                    TextView mQty = mView.findViewById(R.id.tvHold);
                    TextView buy = mView.findViewById(R.id.tvBuy);
                    TextView sell = mView.findViewById(R.id.tvSell);
                    buy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, LiteCoinAcitvity.class));
                        }
                    });

                    sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WalletActivity.this, LiteCoinAcitvity.class));
                        }
                    });

                    String display = "Sell/Buy?";
                    mQty.setText(display);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int displayWidth = displayMetrics.widthPixels;
                    int displayHeight = displayMetrics.heightPixels;

                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

                    layoutParams.copyFrom(dialog.getWindow().getAttributes());

                    int dialogWindowWidth = (int) (displayWidth * 0.9f);
                    int dialogWindowHeight = (int) (displayHeight * 0.3f);
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;

                    dialog.getWindow().setAttributes(layoutParams);
                }
            }
        });
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


