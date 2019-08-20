package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText login_email,login_pass;
    Button signup,login;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_email=findViewById(R.id.email);
        login_pass=findViewById(R.id.password);
        signup=findViewById(R.id.signupbutton)    ;
        login=findViewById(R.id.loginbutton);

        firebaseAuth= FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void signInUser(){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
    private void loginUser(){

        String loginEmail = login_email.getText().toString().trim();
        String loginPass = login_pass.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(loginEmail,loginPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this,Add.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"email and password is wrong, please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
