package com.example.proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    Button buton;
    TextView film;

    String filmTxt, deneme;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buton = findViewById(R.id.button);
        film = findViewById(R.id.film);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("filmler1");
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        filmTxt = film.getText().toString();

                        for (DataSnapshot ds: snapshot.getChildren() ){
                            deneme = ds.getValue().toString();
                            if(deneme.equals(filmTxt)){
                                Intent intent = new Intent(getApplicationContext(),yorumlar.class);
                                intent.putExtra("film", deneme);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        });

    }
}