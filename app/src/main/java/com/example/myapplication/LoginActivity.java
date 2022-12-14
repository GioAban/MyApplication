package com.example.myapplication;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText UserEmail, UserPassword;
    private TextView NeedNewAccountLink;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        NeedNewAccountLink = findViewById(R.id.needNewAccountLink);
        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginBtn);

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendUserToRegisterActivity();
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AllowingUserToLogin();

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if user is already login send user to main acticity
        if (currentUser != null) {
            SendUserToMainActivity();
        }

    }


    private void AllowingUserToLogin() {

        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please write your email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please write your password", Toast.LENGTH_SHORT).show();
        }else{

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SendUserToMainActivity();
                        Toast.makeText(getApplicationContext(), "You are logged in successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), "Error occured: " + message, Toast.LENGTH_SHORT).show();
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

    private void SendUserToRegisterActivity() {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }
}