package com.example.myfirbaseauthenticationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirbaseauthenticationproject.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    //private EditText userName;
    //private EditText password;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //userName = findViewById(R.id.username);
        // = findViewById(R.id.password);

        if (auth.getCurrentUser() == null) {
            // no connected user
            Log.d(TAG, "onCreate: No valid user");
        } else {
            Log.d(TAG, "onCreate: valid user is connected");
            if (auth.getCurrentUser().getDisplayName().length()>0) {
                gotoOtherActivity();
                //auth.signOut();
            } else {
                finish();
                updateUserDisplayName("user");
            }
        }
    }

    private void updateUserDisplayName(String displayName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            gotoOtherActivity();
                        } else
                        {
                            Toast.makeText(MainActivity.this, "Faild to update Display name", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void gotoOtherActivity() {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginUser(View view) {
        //String usernameText = userName.getText().toString();
        //String passwordText = password.getText().toString();
        String usernameText = binding.username.getText().toString();
        String passwordText = binding.password.getText().toString();

        if (usernameText.length() > 0 && passwordText.length() > 0) {
            // try user login
            auth.signInWithEmailAndPassword(usernameText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: login completed successfully");
                        gotoOtherActivity();
                    } else {
                        Log.d(TAG, "onComplete: login failure: " + task.getException().getMessage());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Make sure to fill username and password!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(View view) {
        // String usernameText = userName.getText().toString();
        // String passwordText = password.getText().toString();
        String usernameText = binding.username.getText().toString();
        String passwordText = binding.password.getText().toString();


        if (usernameText.length() > 0 && passwordText.length() > 0) {
            // try user login
            auth.createUserWithEmailAndPassword(usernameText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: new user created successfully: " + auth.getCurrentUser().getUid());
                        gotoOtherActivity();
                    } else {
                        Toast.makeText(MainActivity.this, "Fail to create new user", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: Fail to create new user: " + task.getException().getMessage());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Make sure to fill username and password!!", Toast.LENGTH_SHORT).show();
        }
    }
}