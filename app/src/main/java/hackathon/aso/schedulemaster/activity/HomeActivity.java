package hackathon.aso.schedulemaster.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import hackathon.aso.schedulemaster.R;


public class
        HomeActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //通知削除
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(0);

    }

}
