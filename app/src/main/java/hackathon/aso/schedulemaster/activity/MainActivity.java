package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AbstractActivity {

    private String TAG = "きみや";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
        finish();

    }


}
