package a207project.fall18.gamecenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Activity_Login";

    /**
     * Entry point of Firebase Authentication SDK.
     */
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addLoginButtonListener();
        addCreateAccountListener();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Activate Login button.
     */
    private void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = ((EditText) findViewById(R.id.userName)).getText().toString();
                    String password = ((EditText) findViewById(R.id.password)).getText().toString();
                    if (email.equals("") || password.equals("")) {
                        Toast.makeText(LoginActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    login(email, password);
                } catch (NullPointerException e) {
                    alertView("Username not found!");
                }
            }
        });
    }

    /**
     * Activate "Don't have an account?" link
     */
    private void addCreateAccountListener() {
        TextView textViewCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(tmp);
            }
        });

    }

    /**
     * Alert the user of possible error in the password or username
     *
     * @param message message to be displayed
     */
    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }

    /**
     * Authenticates the user's credentials.
     *
     * @param email    the user's email
     * @param password the user's password
     */
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(LoginActivity.this, GameCenterActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }

                });
    }

    /**
     * Read a temporary board from disk
     */
    @Override
    protected void onResume() {
        super.onResume();
        mAuth.signOut(); //ensures user is logged out on returning to the home screen
    }

    /**
     * Dispatch onPause() to fragments
     */
    protected void onPause() {
        super.onPause();
    }
}

