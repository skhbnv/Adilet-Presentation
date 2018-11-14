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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LawyerRegistrationFragment extends Fragment implements UserRegistrationActivity.forRegistrationListener {
    private static final String TAG = "UserRegistrationFragment";
    private Button mButton;
    EditText mName;
    EditText mSurname;
    EditText mLogin;
    EditText mPassword;
    EditText mConfPassword;
    EditText mSertificate;
    Button mSave;

    Spinner mSpinner;

    String category;
    private FirebaseAuth mAuth;


    FirebaseDatabase database;
    DatabaseReference myRef;

    String[] data = {"Гражданское право", "Семейное право", "Уголовное право", "Налоговое право"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_registration_layout, container, false);


        return view;
    }

//    private void  spinnerAdapter() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getView().getContext(),
//                android.R.layout.simple_spinner_item, data);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        mSpinner.setAdapter(adapter);
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (data[position]){
//                    case "Гражданское право":
//                        category = "Citizen law";
//                        break;
//                    case "Семейное право":
//                        category = "Family law";
//                        break;
//                    case "Уголовное право":
//                        category = "Criminal law";
//                        break;
//                    case "Налоговое право":
//                        category = "Tax law";
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                category = "user";
//            }
//        });
//    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mName = getView().findViewById(R.id.lawyer_name);
        mSurname = getView().findViewById(R.id.lawyer_surname);
        mLogin = getView().findViewById(R.id.lawyer_email);
        mSertificate = getView().findViewById(R.id.lawyer_certificate_number);
        mPassword = getView().findViewById(R.id.lawyer_password);
        mConfPassword = getView().findViewById(R.id.laywer_password_confirm);

        mSpinner  = view.findViewById(R.id.spinner);

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
//        spinnerAdapter();
    }
//    private void initUI(View view) {
//
//    }

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

                                Toast.makeText(getContext(), "success!",
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