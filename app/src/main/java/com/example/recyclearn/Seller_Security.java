package com.example.recyclearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Seller_Security extends AppCompatActivity {

    CardView sellerpassword, selleremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_security);

        sellerpassword = findViewById(R.id.changePassword);
        selleremail = findViewById(R.id.changeEmail);

        sellerpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pass = new Intent(Seller_Security.this, Seller_ChangePassword.class);
                startActivity(pass);
            }
        });

        selleremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Seller_Security.this, Seller_ChangeEmail.class);
                startActivity(email);
            }
        });
    }
}