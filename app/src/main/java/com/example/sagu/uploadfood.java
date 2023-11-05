package com.example.sagu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.util.Calendar;

import com.github.clans.fab.FloatingActionButton;

public class uploadfood extends AppCompatActivity {
    ImageView uploadImage;
    Button saveButton;
    EditText uploadT, uploadD, uploadL;
    String imageURL;
    Uri uri;

    DatabaseReference Myref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfood);
        uploadImage = findViewById(R.id.uploadImage);
//        uploadD = findViewById(R.id.uploadDesc);
//        uploadT = findViewById(R.id.uploadTopic);
//        uploadL = findViewById(R.id.uploadLang);
        EditText desc = (EditText) findViewById(R.id.uploadDesc);
        EditText topic = (EditText) findViewById(R.id.uploadTopic);
        EditText lang = (EditText) findViewById(R.id.uploadLang);
        Myref = FirebaseDatabase.getInstance().getReference("food list");
        saveButton = findViewById(R.id.saveButton);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(uploadfood.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

                String Sdesc = desc.getText().toString();
                String Stopic = topic.getText().toString();
                String Slang = lang.getText().toString();


                Myref.child(Stopic).child("Deskripsi").setValue(Sdesc);
                Myref.child(Stopic).child("Harga").setValue(Slang);
                Myref.child(Stopic).child("Image").setValue(imageURL);

            }
        });
    }
    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(uploadfood.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
//                uploadData();

                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){
        EditText desc = (EditText) findViewById(R.id.uploadDesc);
        EditText topic = (EditText) findViewById(R.id.uploadTopic);
        EditText lang = (EditText) findViewById(R.id.uploadLang);
        String Sdesc = desc.getText().toString();
        String Stopic = topic.getText().toString();
        String Slang = lang.getText().toString();
        Myref = FirebaseDatabase.getInstance().getReference("food list");

        Myref.child(Stopic).child("Deskripsi").setValue(Sdesc);
        Myref.child(Stopic).child("Harga").setValue(Slang);
        Myref.child(Stopic).child("Image").setValue(imageURL);


    }
}