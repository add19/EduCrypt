package com.example.aadishdeshpande.educrypt;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Aadish Deshpande on 3/26/2018.
 */

public class EduCrypt2 extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://educrypt2.firebaseio.com/");
    }
}
