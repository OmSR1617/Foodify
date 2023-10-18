package com.example.fooddeliveryapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fooddeliveryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText etName, etEmail, etPassword;
    Button signUpMain;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signUpMain = findViewById(R.id.signUpMain);

        // Get a reference to the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        signUpMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get user input from EditText fields
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Create a User object to store the data
                User user = new User(name, email, password);

                // Create a FirebaseAuth instance
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                // Create a new user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User registration success, save user data to database
                                    // Get the current user ID
                                    String userId = mAuth.getCurrentUser().getUid();

                                    // Get a reference to the users node in the Firebase Realtime Database
                                    DatabaseReference currentUserReference = databaseReference.child("users").child(userId);

                                    // Push the user object to the Firebase Realtime Database
                                    currentUserReference.setValue(user);

                                    Toast.makeText(signup.this, "Signed Up successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(signup.this, login.class);
                                    startActivity(intent);
                                } else {
                                    // User registration failed
                                    Toast.makeText(signup.this, "Sign Up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    // User model class to store the data
    public class User {
        private String name;
        private String email;
        private String password;

        // Default constructor (required for serialization)
        public User() {}

        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        // Getter and setter methods for properties
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
