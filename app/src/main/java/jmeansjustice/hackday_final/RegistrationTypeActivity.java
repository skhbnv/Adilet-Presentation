package jmeansjustice.hackday_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class RegistrationTypeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout asUser;
    LinearLayout asLawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_type);
        getSupportActionBar().hide();

        asLawer = findViewById(R.id.as_lawyer_btn);
        asUser = findViewById(R.id.as_user_btn);

        asLawer.setOnClickListener(this);
        asUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.as_user_btn:
                Intent intent = new Intent(this, UserRegistrationActivity.class );
                startActivity(intent);
                break;
            case R.id.as_lawyer_btn:
                Intent inten = new Intent(this, LawerRegistrationActivity.class );
                startActivity(inten);
        }
    }

    public interface CustomListener{
        void customMethod(String id, Object user);
    }
}
