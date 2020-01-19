package com.etl.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.etl.firebaseapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private EditText nameET, ageET, genderET;
    private RecyclerView recyclerView;
    private List<User> userList;
    FirebaseDatabase firebaseDatabase;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        configRecyclerView();
        getDataFromDB();
    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(this,userList));
    }

    private void init() {
       nameET = findViewById(R.id.nameET);
       ageET = findViewById(R.id.ageET);
       genderET = findViewById(R.id.genderET);
       recyclerView = findViewById(R.id.recyclerView);
       userList = new ArrayList<>();
       userAdapter = new UserAdapter(this,userList);

       //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void onClick(View view) {
        String name = nameET.getText().toString();
        String  age = ageET.getText().toString();
        String gender = genderET.getText().toString();
        saveData(new User(name,gender,age));
        nameET.setText("");
        ageET.setText("");
        genderET.setText("");

    }

    private void saveData(User user) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference userRef = databaseReference.child("user");
        String userId = userRef.push().getKey();
        user.setUserId(userId);
        userRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromDB() {
        DatabaseReference userRef = firebaseDatabase.getReference().child("user");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    userList.clear();
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        User user = data.getValue(User.class);
                        userList.add(user);
                        userAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}
