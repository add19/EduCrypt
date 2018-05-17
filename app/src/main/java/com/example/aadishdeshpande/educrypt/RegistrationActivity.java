package com.example.aadishdeshpande.educrypt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    String initialValue = "30000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        setupUIViews();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //Upload to Data Base
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                //DatabaseReference users = firebaseDatabase.getReference("users");
                                DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                                DatabaseReference childRef = myRef.child("balance");
                                childRef.setValue(initialValue);

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                                childRef = myRef.child("btc");
                                String init = "0";
                                DatabaseReference childofchild = childRef.child("quantity");
                                childofchild.setValue(init);
                                String holding_btc = "0";
                                DatabaseReference hold = myRef.child("holding_btc");
                                hold.setValue(holding_btc);
                                DatabaseReference buyVal = myRef.child("buyValBtc");
                                Float f = 0.f;
                                buyVal.setValue(f);

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                                childRef = myRef.child("eth");
                                childofchild = childRef.child("quantity");
                                childofchild.setValue(init);
                                String holding_eth = "0";
                                hold = myRef.child("holding_eth");
                                hold.setValue(holding_eth);
                                buyVal = myRef.child("buyValEth");
                                buyVal.setValue(f);


                                firebaseDatabase = FirebaseDatabase.getInstance();
                                myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                                childRef = myRef.child("ltc");
                                childofchild = childRef.child("quantity");
                                childofchild.setValue(init);
                                String holding_ltc = "0";
                                hold = myRef.child("holding_ltc");
                                hold.setValue(holding_ltc);
                                buyVal = myRef.child("buyValLtc");
                                buyVal.setValue(f);

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                                childRef = myRef.child("bch");
                                childofchild = childRef.child("quantity");
                                childofchild.setValue(init);
                                String holding_bch = "0";
                                hold = myRef.child("holding_bch");
                                hold.setValue(holding_bch);
                                buyVal = myRef.child("buyValBch");
                                buyVal.setValue(f);

                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }

    private void setupUIViews() {
        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);
        userEmail = findViewById(R.id.etUserEmail);
        regButton = findViewById(R.id.btnRegister);
        userLogin = findViewById(R.id.tvUserLogin);
    }

    private Boolean validate() {
        Boolean result = false;
        String name = userName.getText().toString();
        String passwword = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if ((name.isEmpty() || passwword.isEmpty()) && email.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}
