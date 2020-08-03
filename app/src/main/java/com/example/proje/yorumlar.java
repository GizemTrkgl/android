package com.example.proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class yorumlar extends AppCompatActivity {

    TextView yorum;
    String yorumTxt;
    Button gonder;
    String film, deneme;
    ListView liste;
    DatabaseReference myRef, myRef1;
    FirebaseDatabase database;
    ArrayList<String> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorumlar);

        database = FirebaseDatabase.getInstance();
        myRef =database.getReference("filmler1");
        myRef1 = database.getReference("filmler1");
        Intent intent = getIntent();

        film = intent.getStringExtra("film");

        yorum = findViewById(R.id.yorum);
        gonder = findViewById(R.id.bttn);
        liste = findViewById(R.id.yorumlar);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    deneme = ds.getKey().toString();
                    if (deneme.equals(film))
                    {
                        for (DataSnapshot ds1 : ds.getChildren()){
                            arrayList.add(String.valueOf(ds1.getValue()));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        liste.setAdapter(adapter);

    }

    public void gonder_Click(View view) {
        yorumTxt = yorum.getText().toString();
        if (yorum != null && !yorum.equals(" ")){
            Integer i = arrayList.size();
            myRef1.child(film).child(String.valueOf(i + 1)).setValue(yorumTxt);

        }
    }
}