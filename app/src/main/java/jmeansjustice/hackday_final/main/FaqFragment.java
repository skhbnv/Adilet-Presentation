package jmeansjustice.hackday_final.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jmeansjustice.hackday_final.ClientsAdapter;
import jmeansjustice.hackday_final.LoginActivity;
import jmeansjustice.hackday_final.PersonalPage;
import jmeansjustice.hackday_final.R;
import jmeansjustice.hackday_final.Registrate;
import jmeansjustice.hackday_final.Users;


public class FaqFragment extends Fragment implements LoginActivity.customListener {
    private DatabaseReference myRef;
    private FirebaseDatabase mDatabase;
    private RecyclerView mRecyclerView;

    private ClientsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.faq_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();

        mRecyclerView = getView().findViewById(R.id.clients_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ClientsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        super.onViewCreated(view, savedInstanceState);
        myRef.child("user_lawyer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Registrate> items = new ArrayList<Registrate>();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                            Registrate newU;
                            newU = child.getValue(Registrate.class);
                            FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
                            String usID = us.getUid();
                    Log.d("usersss", "onDataChange: " + usID);
                    Log.d("usersss", "onDataChange: " + newU.getLawyerID());

                            if(newU.getLawyerID().equals(usID)){
                                items.add(child.getValue(Registrate.class));
                        }
                    }
                onDataLoadedListenerRegister(items);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onDataLoadedListener(ArrayList<Users> users) {

    }

    @Override
    public void onDataLoadedListenerRegister(ArrayList<Registrate> users) {
        for (Registrate registrate: users) {
            databaseConnector(registrate.getUserID());
        }
    }

    private void databaseConnector(final String uid) {
        Log.d("taaag", uid);
        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Users> items = new ArrayList<Users>();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Log.d("childsad", "onDataChange: "+child.toString());
                    for (DataSnapshot second_child: child.getChildren()){
                        Users infoUsers = second_child.getValue(Users.class);
                        if (infoUsers.getmID().equals(uid)) {
                            mAdapter.addUser(infoUsers);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
