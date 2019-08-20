package com.example.todolist;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText signup_email,signup_pass;
    Button user_signup_button, back_to_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup_email = findViewById(R.id.email);
        signup_pass = findViewById(R.id.password);

        user_signup_button=findViewById(R.id.user_signup_button);
        back_to_login=findViewById(R.id.backtologin);

        firebaseAuth = FirebaseAuth.getInstance();

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtologin();
            }
        });
        user_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newuser_signup();
            }
        });
    }
    private void newuser_signup(){
        String string_email = signup_email.getText().toString().trim();
        String string_pass=signup_pass.getText().toString().trim();

        if(TextUtils.isEmpty(string_email)){
            signup_email.setError("Sign field empty");
            signup_email.requestFocus();
        }
        if(TextUtils.isEmpty(string_pass)){
            signup_pass.setError("Password Empty");
            signup_pass.requestFocus();
        }

        firebaseAuth.createUserWithEmailAndPassword(string_email,string_pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"user created Succesfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Register.this,"Some error occured",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void backtologin(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}


