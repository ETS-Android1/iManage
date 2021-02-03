package com.example.nahimana.imanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupStep1 extends AppCompatActivity {
    Button continueBtn;
    EditText names, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step1);
        continueBtn = findViewById(R.id.continueBtn);
        names= findViewById(R.id.names);
        email = findViewById(R.id.userEmail);
        
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getBaseContext(), SignupStep2.class);
                intent.putExtra("names", names.getText().toString());
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);




            }
        });
    }

}