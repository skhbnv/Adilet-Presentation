package jmeansjustice.hackday_final;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageActivity extends Activity implements View.OnClickListener, LoginActivity.customListener {
    static ArrayList<Users> array = new ArrayList<>();
    private DatabaseReference myRef;
    private FirebaseDatabase mDatabase;
    private ImageView mImage;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initUI();

        databaseConnector();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    return true;
                }
            });
    }

    private void databaseConnector() {
        myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Users> items = new ArrayList<Users>();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    for (DataSnapshot second_child: child.getChildren()){
                        items.add(second_child.getValue(Users.class));
                    }
                }
                onDataLoadedListener(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainPageActivity.this, databaseError.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initUI() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mRecyclerView = findViewById(R.id.RecyclerView);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();
        mImage = findViewById(R.id.main_filter);
        mImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.main_filter):
                Log.d("taaag", "onClick: filter is working");
                Intent intent = new Intent(this,Filter_activity.class );
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDataLoadedListener(ArrayList<Users> arrayOfUsers) {

        array = arrayOfUsers;
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(array);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}