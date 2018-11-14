package jmeansjustice.hackday_final.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jmeansjustice.hackday_final.Adapter;
import jmeansjustice.hackday_final.LoginActivity;
import jmeansjustice.hackday_final.MainPageActivity;
import jmeansjustice.hackday_final.OnLawyersLoadListener;
import jmeansjustice.hackday_final.PersonalPage;
import jmeansjustice.hackday_final.R;
import jmeansjustice.hackday_final.Registrate;
import jmeansjustice.hackday_final.Users;

public class LawyerListFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, LoginActivity.customListener, PersonalPage.itemCreatedInterface {
    public static ArrayList<Users> array = new ArrayList<>();
    private DatabaseReference myRef;
    private FirebaseDatabase mDatabase;
    private ImageView mImage;
    private static Toolbar mToolbar;
    private static ActionBar mActionbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout mDrawerLayout;

    private OnLawyersLoadListener mLawyersLoadListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLawyersLoadListener = (OnLawyersLoadListener) context;
    }

    @Nullable
@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lawyer_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        databaseConnector();

    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.RecyclerView);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();

//        ((MainPageActivity) getActivity()).getSupportActionBar().hide();


    }

    private void databaseConnector() {
        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Users> items = new ArrayList<Users>();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    if (!child.getKey().equals("user") && !child.getKey().equals("user_lawyer")){
                        Log.d("childsad", "onDataChange: "+child.toString());
                        for (DataSnapshot second_child: child.getChildren()){
                            Users childUser = second_child.getValue(Users.class);
                            if (!childUser.getmID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                items.add(childUser);
                            }
                        }
                    }
                }
                onDataLoadedListener(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.main_filter:
                Log.d("Filter", "onOptionsItemSelected: filter is working");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataLoadedListener(ArrayList<Users> arrayOfUsers) {

        array = arrayOfUsers;
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new Adapter(array);

        mLawyersLoadListener.onLawyersLoaded(array.size());

        ((Adapter) mAdapter).setItemCreatedInterface(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onDataLoadedListenerRegister(ArrayList<Registrate> users) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemCreated(String id) {
        Intent intent = new Intent(getContext(), PersonalPage.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
