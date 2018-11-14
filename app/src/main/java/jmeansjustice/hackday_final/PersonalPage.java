package jmeansjustice.hackday_final;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonalPage extends Activity implements LoginActivity.customListener, View.OnClickListener {

    private String userID;
    private TextView mName;
    private TextView mExperience;
    private TextView mCategory;
    private Button mRegistrate;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private Button registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_page);
        initUI();
        databasePuller();
    }

    private void databasePuller() {
        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Users> items = new ArrayList<Users>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (DataSnapshot second_child : child.getChildren()) {

                        Users u = new Users();
                        u = second_child.getValue(Users.class);

                        if (u.getmID().equals(userID)) {
                            Log.d("Uid getter", "onDataChange: " + u.getmID());
                            items.add(u);
                        } else {
                            Log.d("Uid getter", "onDataChange: users is null");
                        }
                    }
                }

                onDataLoadedListener(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(this, databaseError.toString(),
//                            Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initUI() {
        mName = findViewById(R.id.personal_page_name);
        mExperience = findViewById(R.id.personal_page_stazh);
        mCategory = findViewById(R.id.personal_page_category);

        mRegistrate = findViewById(R.id.registrate);
        mRegistrate.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();

        setAnExtra();
    }

    public void setAnExtra() {
        Bundle extras = getIntent().getExtras();
        userID = getIntent().getStringExtra("id");

    }

    public void onDataLoadedListener(ArrayList<Users> users) {
        String userName = users.get(0).getmName() + " " + users.get(0).getmSurname();

        mName.setText(userName);
        mCategory.setText(users.get(0).getmCategory());
        mExperience.setText("5");

    }

    @Override
    public void onDataLoadedListenerRegister(ArrayList<Registrate> users) {

    }

    @Override
    public void onClick(View v) {
        FirebaseUser use = FirebaseAuth.getInstance().getCurrentUser();

        Bundle extras = getIntent().getExtras();
        String userid = getIntent().getStringExtra("id");

        String currentUserID = use.getUid().toString();
        Registrate r = new Registrate(currentUserID, userID);


        myRef.child("user_lawyer").child(currentUserID).setValue(r).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(PersonalPage.this, "success!",
                                    Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(getContext(),LoginActivity.class );
//                            startActivity(intent);
                        }else{
                            Toast.makeText(PersonalPage.this,
                                    task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public interface itemCreatedInterface {
        void onItemCreated(String id);
    }
}