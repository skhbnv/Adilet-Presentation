package jmeansjustice.hackday_final;

import android.os.Bundle;
import android.app.Activity;

public class PersonalPage extends Activity {

    private Object anExtra;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);
        setAnExtra();
    }

    public void setAnExtra() {
        Bundle extras = getIntent().getExtras();
        userID = extras.getString("UserID");
    }

    interface itemCreatedInterface{
        void onItemCreated(String id);
    }
}
