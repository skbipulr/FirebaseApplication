package com.etl.firebaseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneET;
    private Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneET = findViewById(R.id.phoneET);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneET.getText().toString().trim();
                if (phone.length() == 11){
                    Intent intent = new Intent(LoginActivity.this,VerifyActivity.class);
                    intent.putExtra("phone",phone);
                   startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please enter valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
