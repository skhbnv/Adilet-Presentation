package jmeansjustice.hackday_final;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class RegistrationTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_type);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new UserRegistrationFragment(), "As user");
        adapter.addFragment(new LawyerRegistrationFragment(), "As lawyer");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    public interface CustomListener{
        void customMethod(String id, Object user);
    }
}
