package com.example.chitchat;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

// register by users' email, password and username

public class RegisterActivity extends AppCompatActivity {

    // declare widgets
    EditText user_name, pass_word, email_address;
    Button registerButton;

    // create firebase Auth and database system
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialized widgets
        user_name = findViewById(R.id.userName);
        pass_word = findViewById(R.id.passWord);
        email_address = findViewById(R.id.emailAddress);
        registerButton = findViewById(R.id.register);

        // initialized FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // adding listener to the button "register"
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_text = user_name.getText().toString();
                String password_text = pass_word.getText().toString();
                String email_text = email_address.getText().toString();

                // if username, password, email are empty
                if (TextUtils.isEmpty(username_text) || TextUtils.isEmpty(password_text) || TextUtils.isEmpty(email_text))
                {
                    Toast.makeText(RegisterActivity.this, "please fill all three fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Register(username_text, password_text, email_text);
                }
            }
        });
    }



    // identification of firebase by creating user with username, password, email
    // to allow registration and authentication
    // https://firebase.google.com/docs/auth/android/password-auth?hl=zh-cn
    private void Register(final String username, String password, String email){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            // getReference make a new column in DB
                            databaseReference = FirebaseDatabase.getInstance()
                                    .getReference("MyUsers").child(userid);

                            // put id, username, imageURL into DB
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);

                            // TODO imageURL is a experimental idea, not compulsory in the designing plan
                            hashMap.put("imageURL", "default");

                            // enter to MainActivity if registration is successful
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}