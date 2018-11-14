package jmeansjustice.hackday_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jmeansjustice.hackday_final.main.AboutAppFragment;
import jmeansjustice.hackday_final.main.ContactsFragment;
import jmeansjustice.hackday_final.main.FaqFragment;
import jmeansjustice.hackday_final.main.LawListFragment;
import jmeansjustice.hackday_final.main.LawyerListFragment;
import jmeansjustice.hackday_final.main.OnlineSupportFragment;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        LoginActivity.customListener, OnLawyersLoadListener {
    public static ArrayList<Users> array = new ArrayList<>();
    private DatabaseReference myRef;
    private FirebaseDatabase mDatabase;
    private ImageView mImage;
    private static Toolbar mToolbar;
    private static ActionBar mActionbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView mOtherInfo;
    private RecyclerView.LayoutManager mLayoutManager;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LawyerListFragment()).commit();

        setContentView(R.layout.activity_main_page);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_menu_btn);
        mActionbar.setTitle("Список юристов");

        initUI();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initUI() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

//        mImage = findViewById(R.id.main_filter);
//        mImage.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.main_filter):
                Log.d("taaag", "onClick: filter is working!!!!!!!!!!!!");
                Intent intent = new Intent(this,Filter_activity.class );
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_law_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LawListFragment()).commit();
                break;
            case R.id.nav_online_support:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OnlineSupportFragment()).commit();
                break;
            case R.id.nav_contacts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactsFragment()).commit();
                break;
            case R.id.nav_about_app:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutAppFragment()).commit();
                break;
            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FaqFragment()).commit();
                break;
            case R.id.nav_lawyer_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LawyerListFragment()).commit();
                break;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onDataLoadedListener(ArrayList<Users> users) {

    }

    @Override
    public void onDataLoadedListenerRegister(ArrayList<Registrate> users) {

    }

    @Override
    public void onLawyersLoaded(int quantity) {
        getSupportActionBar().setSubtitle(String.valueOf(quantity));
    }
}