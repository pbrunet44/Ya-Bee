package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;


public class MakeAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);

        signUpButton = (Button)findViewById(R.id.signUpButtonText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        name = (EditText) findViewById(R.id.nameText);
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Here's the email I'm going to sign up with:" + email.getText().toString());
                System.out.println("Here the password I'm going to sign up with: " + password.getText().toString());
                createAccount(email.getText().toString(), password.getText().toString());
            }
        });
    }

    // checks if user is signed in and updates UI accordingly
    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println("I am currentUser in onStart(): " + currentUser);
        //updateUI(currentUser);
    }


    // create user with email, password
    private void createAccount(String email, String password)
    {
        if (validateForm()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update appropriate information
                                System.out.println("createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user == null)
                                    System.out.println("User is null");
                                else {
                                    System.out.println("User's email:" + user.getEmail());
                                }
                                Toast accountSuccessToast = Toast.makeText(getApplicationContext(), "Account creation successful.", Toast.LENGTH_SHORT);
                                accountSuccessToast.show();
                                finish();

                                // updateUI(user);
                            } else {
                                String errorMessage = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    errorMessage = e.toString();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    errorMessage = e.toString();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    errorMessage = e.toString();
                                } catch (Exception e) {
                                    errorMessage = e.toString();
                                }
                                Toast accountFailToast = Toast.makeText(getApplicationContext(), "Account creation failed." + errorMessage, Toast.LENGTH_SHORT);
                                accountFailToast.show();
                            }
                        }
                    });
        }
    }

    private boolean validateForm()
    {
        boolean valid = true;

        String name = this.name.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            this.name.setError("Required.");
            valid = false;
        }
        else
            this.name.setError(null);

        String email = this.email.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            this.email.setError("Required.");
            valid = false;
        }
        else
            this.email.setError(null);

        String password = this.password.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            this.password.setError("Required.");
            valid = false;
        }
        else
            this.password.setError(null);

        return valid;
    }



}
