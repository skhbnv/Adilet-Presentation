package jmeansjustice.hackday_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener,
        RegistrationTypeActivity.CustomListener{


    EditText mName;
    EditText mSurname;
    EditText mGender;
    EditText mAge;
    EditText mPhone;
    EditText mLogin;
    EditText mPassword;
    EditText mConfPassword;
    Button mSave;
    Spinner mSpinner;
    String category;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;

    String[] data = {"Гражданское право", "Семейное право", "Уголовное право", "Налоговое право"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_layout);
        getSupportActionBar().hide();

        initUI();
        spinnerAdapter();

    }

    private void spinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (data[position]){
                    case "Гражданское право":
                        category = "Citizen law";
                        break;
                    case "Семейное право":
                        category = "Family law";
                        break;
                    case "Уголовное право":
                        category = "Criminal law";
                        break;
                    case "Налоговое право":
                        category = "Tax law";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "user";
            }
        });
    }

    private void initUI() {
        mAuth = FirebaseAuth.getInstance();
        mSpinner = findViewById(R.id.spinner);
        mName = findViewById(R.id.user_name);
        mSurname = findViewById(R.id.user_surname);
        mGender = findViewById(R.id.user_gender);
        mAge = findViewById(R.id.user_age);
        mPhone = findViewById(R.id.user_phone);
        mLogin = findViewById(R.id.user_login);
        mPassword = findViewById(R.id.user_password);
        mConfPassword = findViewById(R.id.user_confirm_password);

        mSave = findViewById(R.id.reg_but);
        mSave.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_but:
                String conf_password = mConfPassword.getText().toString();
                final String password = mPassword.getText().toString();
                final String login = mLogin.getText().toString();
                final String phone = mPhone.getText().toString();
                final String age = mAge.getText().toString();
                final String gender = mGender.getText().toString();
                final String name = mName.getText().toString();
                final String surname = mSurname.getText().toString();
                final String id;


                if(passwordsMatch(password, conf_password) && inputNotNull(phone, age, gender, name, surname)) {

                    mAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(
                            UserRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser registeredUSer = task.getResult().getUser();
                                        String userID = registeredUSer.getUid();

                                        final Users newUser = new Users(name, surname, gender, age, phone, category, userID);

                                        customMethod(userID, newUser);

                                        Toast.makeText(UserRegistrationActivity.this, "success!",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(UserRegistrationActivity.this,
                                                task.getException().toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
                }else {
                    Toast.makeText(UserRegistrationActivity.this,
                            "Passwords does not pass",
                            Toast.LENGTH_LONG).show();
                }
        }
    }

    private boolean inputNotNull(String phone, String age, String gender, String name, String surname) {
        return phone != null && age != null && gender != null && name != null && surname != null;
    }

    private boolean passwordsMatch(String pass, String conf_pass) {
        if(pass.equals(conf_pass)){
            return true;
        }else{
        return false;
        }
    }

    @Override
    public void customMethod(String id, Object newUser) {
        myRef.child("users").child(category).child(id).setValue(newUser).
                addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserRegistrationActivity.this, "success!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UserRegistrationActivity.this ,LoginActivity.class );
                            startActivity(intent);
                        }else{
                            Toast.makeText(UserRegistrationActivity.this,
                                    task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}