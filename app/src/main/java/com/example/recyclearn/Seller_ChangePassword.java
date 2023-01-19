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

public class Seller_ChangePassword extends AppCompatActivity {

    EditText etnewpass, etconfirmnewpass;
    Button changePassword;

    private FirebaseUser user;
    FirebaseFirestore fStore;
    private String userID;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;

    /*//BIOMETRIC
    androidx.biometric.BiometricPrompt biometricPrompt;
    androidx.biometric.BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout mMainLayout;
    //BIOMETRIC*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_change_password);

       /* //BIOMETRIC
        mMainLayout = findViewById(R.id.main_layout);
        //BIOMETRIC*/
        
        etnewpass = findViewById(R.id.newPass);
        etconfirmnewpass = findViewById(R.id.ConfirmNewPass);
        changePassword = findViewById(R.id.savePass);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userID = user.getUid();

       /* //BIOMETRIC
        BiometricManager biometricManager =BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Device doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Not working. SAD :(", Toast.LENGTH_SHORT).show();
                break;


            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No FingerPrint Assigned", Toast.LENGTH_SHORT).show();
                break;

        }
        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(Seller_ChangePassword.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Seller_ChangePassword.this, "Success", Toast.LENGTH_SHORT).show();
                mMainLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("RecyclEarn Projects ")
                        .setDescription("Use Fingerprint to Access").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
        //BIOMETRIC*/
        
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
                //updates the password based on the new password
                user.updatePassword(pass1);
                Toast.makeText(Seller_ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();

                HashMap hashMap = new HashMap();
                hashMap.put("password",pass1);

                Map<String, Object> user = new HashMap<>();
                user.put("password", pass1);
                DocumentReference documentReference = fStore.collection("Seller_Users").document(userID);
                documentReference.update(user);

                Toast.makeText(Seller_ChangePassword.this,"Updated",Toast.LENGTH_SHORT).show();


            }
        });
    }
}