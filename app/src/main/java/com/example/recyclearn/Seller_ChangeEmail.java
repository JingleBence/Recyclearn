package com.example.recyclearn;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Seller_ChangeEmail extends AppCompatActivity {

    EditText etnewEmail;
    Button changeEmailAddress;

    private FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_change_email);

        etnewEmail = findViewById(R.id.newEmail);
        changeEmailAddress = findViewById(R.id.saveEmail);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Seller_Users");

        changeEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etnewEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    etnewEmail.setError("Email is required!");
                    etnewEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etnewEmail.setError("Please provide valid email");
                    etnewEmail.requestFocus();
                    return;
                }
                //verifies the user's new email before completely changing it in the database
                //note: the new email must be existing for it be verified
                user.verifyBeforeUpdateEmail(email);
                Toast.makeText(Seller_ChangeEmail.this, "Email Changed!", Toast.LENGTH_SHORT).show();

                HashMap hashMap = new HashMap();
                hashMap.put("email",email);


                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(Seller_ChangeEmail.this,"Updated",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}