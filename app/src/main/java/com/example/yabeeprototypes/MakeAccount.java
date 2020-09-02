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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MakeAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText username;
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);

        signUpButton = (Button)findViewById(R.id.signUpButtonText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        username = (EditText) findViewById(R.id.usernameText);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        name = (EditText) findViewById(R.id.nameText);
        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(email.getText().toString(), password.getText().toString());
            }
        });
    }

    // checks if user is signed in and updates UI accordingly
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    // create user with email, password
    private void createAccount(final String email, String password) {
        if (true) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Register user for wishlists
                                final DatabaseHelper databaseHelper = new DatabaseHelper();
                                databaseHelper.getUsers(new UserCallback() {
                                    @Override
                                    public void onCallback(List<User> users) {
                                        User user = databaseHelper.getUserByEmail(email, users);
                                        if (user == null) {
                                            databaseHelper.databaseReference.child("Users").child(mAuth.getUid()).setValue(new User(email, mAuth.getUid()));
                                        }
                                    }
                                });

                                Toast accountSuccessToast = Toast.makeText(getApplicationContext(), "Account creation successful.", Toast.LENGTH_SHORT);
                                accountSuccessToast.show();
                                finish();
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

//    private boolean validateForm() {
//        boolean valid = true;
//
//        String name = this.name.getText().toString();
//        if (TextUtils.isEmpty(name)) {
//            this.name.setError("Required.");
//            valid = false;
//        } else
//            this.name.setError(null);
//
//        String email = this.email.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            this.email.setError("Required.");
//            valid = false;
//        } else
//            this.email.setError(null);
//
//        String password = this.password.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            this.password.setError("Required.");
//            valid = false;
//        } else
//            this.password.setError(null);
//
//        return valid;
//    }


}
