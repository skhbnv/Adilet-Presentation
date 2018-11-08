package jmeansjustice.hackday_final;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView firstTime;

    EditText mLogin;
    EditText mPassword;

    FirebaseAuth mAuth;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initUI();
    }

    private void initUI() {
        firstTime = findViewById(R.id.login_first_time);
        firstTime.setOnClickListener(this);

        mLogin = findViewById(R.id.user_login);
        mPassword = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_first_time:
                Intent intent = new Intent(this,RegistrationTypeActivity.class );
                startActivity(intent);
                break;
            case R.id.login_button:
                String login = mLogin.getText().toString();
                String password = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(login, password).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "successfully logged in!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,
                                            MainPageActivity.class );
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(LoginActivity.this,"erroes"+ task.getException()
                                                    .toString(),
                                            Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }
    interface customListener{
        public void onDataLoadedListener(ArrayList<Users> users);
    }
}
