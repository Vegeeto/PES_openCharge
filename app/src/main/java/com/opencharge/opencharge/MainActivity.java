package com.opencharge.opencharge;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message2");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String elvalor = (String)dataSnapshot.getValue();
                //showMessage(elvalor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        android.support.v4.app.FragmentTransaction FragmentTransaction = getSupportFragmentManager().beginTransaction();
        MapsFragment mapsFragment = new MapsFragment();
        FragmentTransaction.replace(R.id.map, mapsFragment).commit();

    }
}
