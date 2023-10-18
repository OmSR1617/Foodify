package com.example.fooddeliveryapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddeliveryapp.Activity.IntroActivity;
import com.example.fooddeliveryapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button goToSignup;
    Button signInMain;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    String password;

    TextView forgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        goToSignup = findViewById(R.id.goToSignup);
        signInMain = findViewById(R.id.signInMain);
        forgetpass = findViewById(R.id.forgetpass);



        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

        signInMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                // Sign in with email and password
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();
                                    String userEmail = user.getEmail();
                                    saveUserDataToFirestore(userId, userEmail); // Save user data to Firestore
                                }

                                Toast.makeText(login.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, IntroActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(login.this, "Authentication failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();

                mAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {


                                Toast.makeText(login.this, "Email send", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void saveUserDataToFirestore(String userId, String userEmail) {
        // Create a new user document in Firestore
        DocumentReference docRef = firestore.collection("users").document(userId);

        Map<String, Object> user = new HashMap<>();
        user.put("email", userEmail);
        user.put("Password", password);

        docRef.set(user)
                .addOnSuccessListener(aVoid -> {
                    // User data saved successfully
                    Toast.makeText(login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error saving user data
                    Toast.makeText(login.this, "Login Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
