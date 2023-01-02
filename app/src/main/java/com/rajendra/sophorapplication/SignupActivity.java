package com.rajendra.sophorapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rajendra.sophorapplication.databinding.ActivitySignupBinding;
import com.rajendra.sophorapplication.util.Constants;
import com.rajendra.sophorapplication.util.PreferenceManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private String userName, phone, email, password;
    private FirebaseAuth mAuth;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setListeners();


    }

    private void setListeners() {
        binding.signupButton.setOnClickListener(view -> {
            isLoading(true);
            getUserData();
        });

        binding.signupHaveAccount.setOnClickListener(view -> {
            Intent welcome3 = new Intent(SignupActivity.this,SigninActivity.class);
            startActivity(welcome3);
            finish();
        });
    }


    private void getUserData() {
        userName = binding.signupNameEditText.getText().toString().trim();
        phone = binding.signupNumberEditText.getText().toString().trim();
        email = binding.signupEmailEditText.getText().toString().trim();
        password = binding.signupPasswordEditText.getText().toString().trim();

        if(isNotEmpty()) {
            signUp();
        }
    }

    private Boolean isNotEmpty() {
        if(userName.isEmpty()) {
            binding.signupNameEditText.setError("This field is required!");
            binding.signupNameEditText.requestFocus();
            return false;
        } else if(phone.isEmpty()) {
            binding.signupNumberEditText.setError("This field is required!");
            return false;
        } else if(phone.length()<11) {
            binding.signupNumberEditText.setError("Enter valid phone number.");
            return false;
        } else if(email.isEmpty()){
            binding.signupEmailEditText.setError("This field is required!");
            return false;
        } else if(!email.matches("[a-zA-Z0-9._-]+@gmail.com+")) {
            binding.signupEmailEditText.setError("Invalid email.");
            return false;
        } else if(password.isEmpty()) {
            binding.signupPasswordEditText.setError("This field is required!");
            return false;
        }else if(password.length()<6 ) {
            binding.signupPasswordEditText.setError("password at least 6 characters");
            return false;
        }
        return true;
    }

    private void signUp() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"Successfully SignUp Complete",Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user!=null) {
                            updateDataToDatabase(user.getUid());
                        }

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                    isLoading(false);
                });
    }

    private void updateDataToDatabase(String userId) {
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_USER_NAME, userName);
        user.put(Constants.KEY_EMAIL, email);
        user.put(Constants.KEY_PHONE, phone);
        user.put(Constants.KEY_PASSWORD, password);
        user.put(Constants.KEY_USER_ID, userId);
        user.put(Constants.KEY_REG_DATE, new Date());

        db.collection(Constants.KEY_USER_DB)
                .document(userId)
                .set(user)
                .addOnSuccessListener(unused -> {
                    preferenceManager.putSting(Constants.KEY_EMAIL, email);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED, true);
                    preferenceManager.putSting(Constants.KEY_USER_NAME, userName);
                    preferenceManager.putSting(Constants.KEY_PHONE, phone);
                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void isLoading(Boolean loading) {
        if(loading) {
            binding.progress.setVisibility(View.VISIBLE);
        } else {
            binding.progress.setVisibility(View.GONE);
        }
    }
}