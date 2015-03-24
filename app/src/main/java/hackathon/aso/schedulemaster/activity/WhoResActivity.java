package hackathon.aso.schedulemaster.activity;

import android.os.Bundle;

import hackathon.aso.schedulemaster.R;

/**
 * Created by shouhei on 15/03/05.
 */
public class WhoResActivity extends AbstractActivity {
    private String enqueteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whores);
    }
}