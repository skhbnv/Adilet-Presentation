package jmeansjustice.hackday_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class UserRegistrationFragment extends Fragment implements UserRegistrationActivity.forRegistrationListener {
    private static final String TAG = "UserRegistrationFragment";
    private Button mButton;
    EditText mName;
    EditText mSurname;
    EditText mGender;
    EditText mAge;
    EditText mPhone;
    EditText mLogin;
    EditText mPassword;
    EditText mConfPassword;
    Button mSave;
    String category;
    private FirebaseAuth mAuth;


    FirebaseDatabase database;
    DatabaseReference myRef;

    String[] data = {"Гражданское право", "Семейное право", "Уголовное право", "Налоговое право"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_registration_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }
    private void initUI(View view) {
        mAuth = FirebaseAuth.getInstance();
        mName = getView().findViewById(R.id.user_name);
        mSurname = getView().findViewById(R.id.user_surname);
        mLogin = getView().findViewById(R.id.user_registration_login);
        mPassword = getView().findViewById(R.id.user_registration_password);
        mConfPassword = getView().findViewById(R.id.user_confirm_password);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();

        mSave = getView().findViewById(R.id.reg_but);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDataLoadedForRegistration();
            }
        });
    }

    @Override
    public void onDataLoadedForRegistration() {
        if(mPassword.getText().toString().equals(mConfPassword.getText().toString())){
            mAuth.createUserWithEmailAndPassword(mLogin.getText().toString(),
                    mPassword.getText().toString()).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser registeredUSer = task.getResult().getUser();
                                String userID = registeredUSer.getUid();

                                final Users newUser = new Users(mName.getText().toString(),
                                        mSurname.getText().toString(), userID);

                                customDataLoader(userID, newUser);

                                Toast.makeText(getContext(), "Success!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(),
                                        task.getException().toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
        }else {
            Toast.makeText(getContext(),
                    "Passwords does not pass",
                    Toast.LENGTH_LONG).show();
        }
        }

    @Override
    public void customDataLoader(String userID, Users user) {
        myRef.child("users").child("user").child(userID).setValue(user).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "success!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(),LoginActivity.class );
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(),
                                    task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}