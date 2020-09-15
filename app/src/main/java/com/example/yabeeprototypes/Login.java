package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private TextView createAccount;
    private TextView forgotPassword;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.loadingCircle);
        createAccount = ((TextView) findViewById(R.id.createAnAccountText));
        forgotPassword = ((TextView) findViewById(R.id.forgotPasswordText));

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeAccount.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });


        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        Button signInButtonText = (Button)findViewById(R.id.signInButtonText);
        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.passwordLoginText);
        signInButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void loginUser(final String email, String password)
    {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Toast telling user that they've successfully logged in
                            Toast toast = Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                            //Register user for wishlists
                            final DatabaseHelper databaseHelper = new DatabaseHelper();
                            databaseHelper.getUsers(new UserCallback() {
                                @Override
                                public void onCallback(List<User> users) {
                                    User user = databaseHelper.getUserByEmail(email, users);
                                    if(user == null)
                                    {
                                        databaseHelper.databaseReference.child("Users").child(mAuth.getUid()).setValue(new User(email, mAuth.getUid()));
                                    }
                                }
                            });

                            // go to home page
                            finish();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            // prompt them to re-try
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(getApplicationContext(), "Unsuccessful login attempt, ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                        }
                    }
                });
    }

}
