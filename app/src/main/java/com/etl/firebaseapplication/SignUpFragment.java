package com.etl.firebaseapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private EditText nameET, genderET, mobileNumberET,emaiET,passwordET, confirmPasswordET;
    private Button signUpBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        nameET = view.findViewById(R.id.nameET);
        genderET = view.findViewById(R.id.genderET);
        mobileNumberET = view.findViewById(R.id.mobileNumberET);
        emaiET = view.findViewById(R.id.emailET);
        passwordET = view.findViewById(R.id.passwordET);
        confirmPasswordET = view.findViewById(R.id.confirmPasswordET);
        signUpBtn = view.findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String gender = genderET.getText().toString();
                String mobileNumber = mobileNumberET.getText().toString();
                String email = emaiET.getText().toString();
                String password = passwordET.getText().toString();
                String confirmPassword = confirmPasswordET.getText().toString();

                if (password.contains(confirmPassword)){
                    signInWithEmailAndPassword(name,gender,mobileNumber,email,password);
                }
            }
        });

        return view;
    }

    private void signInWithEmailAndPassword(String name, String gender, String mobileNumber, String email, String password) {

       final HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("name",name);
        userMap.put("gender",gender);
        userMap.put("mobileNumber",mobileNumber);
        userMap.put("email",email);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    String userId = firebaseAuth.getCurrentUser().getUid();

                    userMap.put("userId",userId);

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                    DatabaseReference userRef = firebaseDatabase.getReference().child("userList").child(userId);

                    userRef.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

}
