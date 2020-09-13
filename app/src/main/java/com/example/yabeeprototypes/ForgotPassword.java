package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private TextView email;
    private Button forgotPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = ((TextView) findViewById(R.id.forgotEmail));
        forgotPasswordButton = ((Button) findViewById(R.id.forgotPasswordButtonText));
        mAuth = FirebaseAuth.getInstance();



        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailAddress = email.getText().toString();
                if (!TextUtils.isEmpty(emailAddress)) {
                    // send reset password email to user's email address
                    mAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Email has been sent. ", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.TOP, 0, 0);
                                        toast.show();
                                    }
                                }
                            });
                } else {
                    email.setError("Please enter a valid email address.");
                }
            }
        });


    }
}