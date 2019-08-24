package a207project.fall18.gamecenter;
/*alertView taken from https://stackoverflow.com/questions/26097513/android-simple-alert-dialog*/

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "Activity_SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addCreateAccountButtonListener();
        addHaveAccountTextListener();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Create's a new account for the new user.
     *
     * @param email    the new user's email
     * @param password the new user's password
     */
    void createAccount(String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        startActivity(new Intent(SignUpActivity.this, GameCenterActivity.class));
                    } else {
                        Exception e = task.getException();
                        Log.w(TAG, "createUserWithEmail:failure", e);
                        if (e instanceof FirebaseAuthUserCollisionException){
                            toastShortMessage("That email is already in use.");
                        }
                        else if (e instanceof FirebaseAuthWeakPasswordException){
                            toastShortMessage("Please choose a stronger password.");
                        }
                        else if (e instanceof FirebaseAuthInvalidCredentialsException){
                            toastShortMessage("Please enter a proper email.");
                        }
                        else{
                            toastShortMessage("Account could not be made.");
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Activate Create Account button.
     */
    private void addCreateAccountButtonListener() {
        Button buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.editText_username_signup)).getText().toString();
                String password = ((EditText) findViewById(R.id.editText_password_signup)).getText().toString();
                if (username.equals("") || password.equals("")) {
                    toastShortMessage("Please enter a username and password.");
                    return;
                }
                createAccount(username, password);
            }
        });

    }

    /**
     * Activate "Already have an account?" link
     */
    private void addHaveAccountTextListener() {
        TextView textViewHaveAccount = findViewById(R.id.textViewHaveAccount);
        textViewHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Creates a message alert
     */
    void toastShortMessage(String message){
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Dispatch onPause() to fragment
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }
}
