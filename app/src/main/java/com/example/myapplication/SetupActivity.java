package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, CountryName;
    private Button SaveInformationButton;
    private CircleImageView ProfileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;

    String currentUserID;
    final static int Galley_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        UserName = findViewById(R.id.setup_username);
        FullName = findViewById(R.id.setup_fullname);
        CountryName = findViewById(R.id.setup_country_name);
        SaveInformationButton = findViewById(R.id.setup_information_button);
        ProfileImage = findViewById(R.id.setup_profile_image);

        SaveInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        });

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Galley_Pick);

            }
        });

    }

    private void SaveAccountSetupInformation() {

            String username = UserName.getText().toString();
            String fullname = FullName.getText().toString();
            String country = CountryName.getText().toString();

            if(TextUtils.isEmpty(username)){
                Toast.makeText(getApplicationContext(), "Please write username", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(fullname)){
                Toast.makeText(getApplicationContext(), "Please write fullname", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(country)){
                Toast.makeText(getApplicationContext(), "Please write country", Toast.LENGTH_SHORT).show();
            }else {

                HashMap usermap = new HashMap();
                usermap.put("username", username);
                usermap.put("fullname", fullname);
                usermap.put("country", country);
                usermap.put("status", "Hey there, i am using Poster Social network , Gio Coser");
                usermap.put("gender", "none");
                usermap.put("dob","none" );
                usermap.put("relationshipstatus", "none");

                UserRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            SendUserToMainActivity();
                            Toast.makeText(getApplicationContext(), "Your account is created successfully", Toast.LENGTH_LONG).show();
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "Error occured"   + message, Toast.LENGTH_LONG).show();
                        }
                    }

                });


        }
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }
}