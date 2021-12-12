package com.example.myfirbaseauthenticationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirbaseauthenticationproject.databinding.ActivityMainBinding;
import com.example.myfirbaseauthenticationproject.databinding.ActivityOtherBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class OtherActivity extends AppCompatActivity {
    private ActivityOtherBinding binding;
    DatabaseReference messagesRef=FirebaseDatabase.getInstance().getReference("Messages");
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.sendButton.setEnabled(false);
                    binding.sendButton.setImageResource(R.drawable.outline_send_gray_24);
                } else {
                    binding.sendButton.setEnabled(true);
                    binding.sendButton.setImageResource(R.drawable.outline_send_24);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(binding.messageEditText.getText().toString());
                binding.messageEditText.setText("");
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            gotoMainActivity();
            return;
        }

        //DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("Messages");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("OtherActivity", "onDataChange: count=" + snapshot.getChildrenCount());
                MyAdapter adapter = new MyAdapter(snapshot);
                        binding.messageRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OtherActivity", "onStart: ");
        messagesRef.addValueEventListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("OtherActivity", "onStop: ");
        messagesRef.removeEventListener(listener);
    }

    private void sendMessage(String text) {
        // build message
        Date time = Calendar.getInstance().getTime();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        ChatMessage msg = new ChatMessage(time, userId, userName, text);

        //save message to database
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("Messages");
        messagesRef.child(userId + ":" + time.toString()).setValue(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}