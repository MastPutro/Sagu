package com.example.sagu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class landpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landpage);
        ImageButton masukbtn = (ImageButton) findViewById(R.id.masukbtn);
        masukbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landpage.this, login.class);
                startActivity(intent);

            }
        });
        ImageButton regisbtn = (ImageButton) findViewById(R.id.daftarbtn);
        regisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landpage.this, register.class);
                startActivity(intent);

            }
        });
    }
}