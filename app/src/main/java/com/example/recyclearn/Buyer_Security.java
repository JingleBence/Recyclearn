package com.example.recyclearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Buyer_Security extends AppCompatActivity {

    CardView buyerpassword, buyeremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_security);

        buyerpassword = findViewById(R.id.changePassword);
        buyeremail = findViewById(R.id.changeEmail);

        buyerpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass = new Intent(Buyer_Security.this, Buyer_ChangePassword.class);
                startActivity(pass);
            }
        });

        buyeremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Buyer_Security.this, Buyer_ChangeEmail.class);
                startActivity(email);
            }
        });
    }
}