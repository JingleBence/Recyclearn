package com.example.recyclearn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Buyer_ChangePassword extends AppCompatActivity {

    private FirebaseUser user;
    FirebaseFirestore fStore;
    private String userID;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;

    EditText etnewpass, etconfirmnewpass;
    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_change_password);

        etnewpass = findViewById(R.id.newPass);
        etconfirmnewpass = findViewById(R.id.ConfirmNewPass);
        changePassword = findViewById(R.id.savePass);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = user.getUid();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = etnewpass.getText().toString();
                String pass2 = etconfirmnewpass.getText().toString();


                if(pass1.isEmpty()){
                    etnewpass.setError("This field is required!");
                    etnewpass.requestFocus();
                    return;
                }
                if(pass2.isEmpty()){
                    etconfirmnewpass.setError("This field is required!");
                    etconfirmnewpass.requestFocus();
                    return;
                }
                if(pass1.length() < 6){
                    etnewpass.setError("Must be at least 6 characters");
                    etnewpass.requestFocus();
                    return;
                }
                if(!pass1.equals(pass2)){
                    etconfirmnewpass.setError("Does not match!");
                    etconfirmnewpass.requestFocus();
                    return;
                }
                //verifies the user's new email before completely changing it in the database
                //note: the new email must be existing for it be verified
                user.updatePassword(pass1);
                Toast.makeText(Buyer_ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();

                HashMap hashMap = new HashMap();
                hashMap.put("password",pass1);


                Map<String, Object> user = new HashMap<>();
                user.put("password", pass1);
                DocumentReference documentReference = fStore.collection("Buyer_Users").document(userID);
                documentReference.update(user);

                Toast.makeText(Buyer_ChangePassword.this,"Updated",Toast.LENGTH_SHORT).show();

            }
        });
    }
}