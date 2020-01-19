package com.etl.firebaseapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthActivity extends AppCompatActivity {
    private Button signInBtn, signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signInBtn  = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        replaceFragment(new SignInFragment());

        signInBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        signInBtn.setTextColor(getResources().getColor(R.color.white));
        signUpBtn.setBackgroundColor(getResources().getColor(R.color.white));

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignInFragment());
                signInBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                signInBtn.setTextColor(getResources().getColor(R.color.white));
                signUpBtn.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignUpFragment());
                signInBtn.setBackgroundColor(getResources().getColor(R.color.white));
                signUpBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                signUpBtn.setTextColor(getResources().getColor(R.color.white));
            }
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}
