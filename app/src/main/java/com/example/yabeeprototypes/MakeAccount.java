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

import org.w3c.dom.Text;

import java.util.List;


public class MakeAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signUpButton;
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);

        signUpButton = (Button) findViewById(R.id.signUpButtonText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordText);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        name = (EditText) findViewById(R.id.nameText);
        mAuth = FirebaseAuth.getInstance();


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    createAccount(email.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    // check that passwords match
    private boolean passwordsMatch() {
        String pwd = this.password.getText().toString();
        String confirmPwd = this.confirmPassword.getText().toString();
        return (pwd.equals(confirmPwd));
    }

    // checks if the fields are empty
    private boolean isInvalid(String item) {
        return (TextUtils.isEmpty(item));
    }

    // validates email, password, confirmPassword, username, phoneNumber and name fields
    private boolean validateForm() {
        boolean valid = false;

        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String confirmPassword = this.confirmPassword.getText().toString();
        String phoneNumber = this.phoneNumber.getText().toString();

        if (isInvalid(name)) {
            this.name.setError("Please enter a valid name.");
        }
        if (isInvalid(email)) {
            this.email.setError("Please enter a valid email address.");
        }
        if (isInvalid(password)) {
            this.password.setError("Please enter a valid password.");
        }
        if (isInvalid(confirmPassword)) {
            this.confirmPassword.setError("Please enter a valid password.");
        }
        if (!passwordsMatch()) {
            this.confirmPassword.setError("Passwords don't match");
        }
        if (isInvalid(phoneNumber)) {
            this.phoneNumber.setError("Please enter a valid phone number.");
        } else {
            valid = true;
        }
        return valid;
    }

    // checks if user is signed in and updates UI accordingly
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    // create user with email, password
    private void createAccount(final String email, String password) {
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
                            // after successful account creation, send verification email
                            FirebaseUser newUser = mAuth.getInstance().getCurrentUser();
                            newUser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                String errorMessage = "";
                                                try {
                                                    throw task.getException();
                                                }
                                                catch(Exception e)
                                                {
                                                    errorMessage = e.toString();
                                                }
                                                Toast verificationToast = Toast.makeText(getApplicationContext(), "Error sending verification email: " + errorMessage, Toast.LENGTH_SHORT);
                                                verificationToast.show();
                                            }
                                        }
                                    });
                            finish();
                        } else {
                            String errorMessage = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                errorMessage = "Your password is too weak. Please make sure you use a variety of lowercase and uppercase letters, numbers and special characters.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                errorMessage = e.toString();
                            } catch (FirebaseAuthUserCollisionException e) {
                                errorMessage = "An account exists under the same email address, sign in instead.";
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
