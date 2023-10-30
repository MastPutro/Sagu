package com.example.sagu;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        EditText txtemail = (EditText) findViewById(R.id.email);
        EditText txtpassword = (EditText) findViewById(R.id.password);
        ImageButton daftarbtn = (ImageButton) findViewById(R.id.mendaftarbtn);
        ProgressBar progres = (ProgressBar) findViewById(R.id.progresbar);
        TextView masukbtn = (TextView) findViewById(R.id.txtmasuk);

        daftarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progres.setVisibility(View.VISIBLE);
                String mail, pass;
                mail = String.valueOf(txtemail.getText());
                pass = String.valueOf(txtpassword.getText());
                if (TextUtils.isEmpty(mail)){
                    Toast.makeText(register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progres.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(register.this, "Account created",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        masukbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });

    }
}