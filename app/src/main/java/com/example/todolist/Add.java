package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Add extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatbutton;
    MyAdapter myAdapter;

    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Model> retrieveDataList = new ArrayList<>();
    List<Model> lm = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        recyclerView = findViewById(R.id.recycle);
        floatbutton = findViewById(R.id.floatbutton);


        myRef = FirebaseDatabase.getInstance().getReference("Notes");

        floatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

    }
    private void  dialog(){

        View view = LayoutInflater.from(this).inflate(R.layout.dialog,null);
        final EditText editTitle = view.findViewById(R.id.editTitle);
        final EditText editDesc = view.findViewById(R.id.editDesc);
        Button button = view.findViewById(R.id.submit);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= editTitle.getText().toString();
                String desc= editDesc.getText().toString();
                Model model = new Model();

                model.setTitle(title);
                model.setDesc(desc);

                lm.add(model);

                String id = myRef.push().getKey();
                myRef.child(id).setValue(model);

                MyAdapter myAdapter= new MyAdapter(lm,getApplicationContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(myAdapter);
                alertDialog.dismiss();
            }

        });
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retrieveDataList.clear();
                for(DataSnapshot notesShot : dataSnapshot.getChildren()) {
                    Model model = notesShot.getValue(Model.class);
                    retrieveDataList.add(model);
                }
                MyAdapter myAdapter = new MyAdapter(retrieveDataList,getApplicationContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


