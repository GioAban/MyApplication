package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText UserName, FullName, CountryName;
    private Button SaveInformationButton;
    private CircleImageView ProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        UserName = findViewById(R.id.setup_username);
        FullName = findViewById(R.id.setup_fullname);
        CountryName = findViewById(R.id.setup_country_name);
        SaveInformationButton = findViewById(R.id.setup_information_button);
        ProfileImage = findViewById(R.id.setup_profile_image);



    }
}