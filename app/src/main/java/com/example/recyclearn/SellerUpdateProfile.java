package com.example.recyclearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class SellerUpdateProfile extends AppCompatActivity {

    EditText etusername, etfullname, etnewpass, etconfirmnewpass;
    Button button;

    private FirebaseUser user;
    FirebaseFirestore fStore;
    private String userID;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_update_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = user.getUid();

        etusername = findViewById(R.id.editUsername);
        etfullname = findViewById(R.id.editFullname);
        etnewpass = findViewById(R.id.newPass);
        etconfirmnewpass = findViewById(R.id.ConfirmNewPass);
        button = findViewById(R.id.save);

        //displayname
        etusername = findViewById(R.id.editUsername);
        etfullname = findViewById(R.id.editFullname);
        button = findViewById(R.id.save);

        final TextView usernameTextView = (EditText) findViewById(R.id.editUsername);
        final TextView fullnameTextView = (EditText) findViewById(R.id.editFullname);

        DocumentReference documentReference = fStore.collection("Seller_Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String username = value.getString("username");
                String fullname = value.getString("fullName");
                usernameTextView.setText(username);
                fullnameTextView.setText(fullname);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = etusername.getText().toString();
                String fname = etfullname.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("username", uname);
                user.put("fullName", fname);
                DocumentReference documentReference = fStore.collection("Seller_Users").document(userID);
                documentReference.update(user);

                Toast.makeText(SellerUpdateProfile.this,"Updated",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent balik = new Intent(SellerUpdateProfile.this, SellerProfile.class);
        startActivity(balik);
        super.onBackPressed();

    }

}