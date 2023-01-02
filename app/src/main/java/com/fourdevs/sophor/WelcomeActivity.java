package com.fourdevs.sophor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fourdevs.sophor.util.Constants;
import com.fourdevs.sophor.util.PreferenceManager;


public class WelcomeActivity extends AppCompatActivity {

    Button welcome_button;
    TextView welcome_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        PreferenceManager preferenceManager = new PreferenceManager(this);

        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        welcome_button = findViewById(R.id.welcome_button);
        welcome_have_account = findViewById(R.id.welcome_have_account);

        welcome_button.setOnClickListener(view -> {
            Intent welcome = new Intent(WelcomeActivity.this,SignupActivity.class);
            startActivity(welcome);
            finish();
        });

        welcome_have_account.setOnClickListener(view -> {
            Intent welcome2 = new Intent(WelcomeActivity.this,SigninActivity.class);
            startActivity(welcome2);
            finish();
        });

    }


}