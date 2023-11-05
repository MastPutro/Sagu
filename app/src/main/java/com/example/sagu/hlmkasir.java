package com.example.sagu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class hlmkasir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlmkasir);
        Intent intent = new Intent(hlmkasir.this, uploadfood.class);
        startActivity(intent);
    }
}