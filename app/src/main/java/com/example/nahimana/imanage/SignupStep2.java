package com.example.nahimana.imanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupStep2 extends AppCompatActivity {
    Button btnPrev, btnNext;
    EditText pass, confirmPass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step2);
        btnPrev = findViewById(R.id.backBtn1);
        btnNext = findViewById(R.id.continueBtn2);
        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPassword);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pi = personalInfo
                Intent  pi = getIntent();

                Intent passwordIntent = new Intent(getBaseContext(), SignupStep3.class);
                passwordIntent.putExtra("names", pi.getStringExtra("names"));
                passwordIntent.putExtra("email", pi.getStringExtra("email"));
                passwordIntent.putExtra("password", pass.getText().toString());
                passwordIntent.putExtra("password_confirmation", confirmPass.getText().toString());
                startActivity(passwordIntent);

            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignupStep1.class));
            }
        });
    }
}