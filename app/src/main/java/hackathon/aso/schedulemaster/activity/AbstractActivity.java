package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nifty.cloud.mb.NCMB;
import com.nifty.cloud.mb.NCMBUser;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.User;


public abstract class AbstractActivity extends ActionBarActivity {
    protected static User currentUser = null;
    protected String applicationKey = "3f054d7a8769bdac50d30b3eb2f49d4165134683a538b69fbb7af68cc4ac8024";
    protected String clientKey = "4cd39cfc2cc57db844aa9f4676e598e528273ff22b0b93e392fa0699f4db46b2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NCMB.initialize(getApplicationContext(), applicationKey, clientKey);

        NCMBUser ncmbUser = NCMBUser.getCurrentUser();
        if (!(ncmbUser == null)) {
            currentUser = new User();
            currentUser.setEmail(ncmbUser.getUsername());
            currentUser.setName(ncmbUser.getString("nickName"));
        } else {
            Log.i("状態", "ログインしてません");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NCMB.initialize(getApplicationContext(), applicationKey, clientKey);

        NCMBUser ncmbUser = NCMBUser.getCurrentUser();
        if (!(ncmbUser == null)) {
            currentUser = new User();
            currentUser.setEmail(ncmbUser.getUsername());
            currentUser.setName(ncmbUser.getString("nickName"));
        } else {
            Log.i("状態", "ログインしてません");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Class cls = null;
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_home:
                cls = HomeActivity.class;
                break;
            case R.id.menu_schedule:
                cls = SchedulesActivity.class;
                break;
            case R.id.menu_management:
                cls = ManagementActivity.class;
                break;
            case R.id.menu_preferences:
                cls = PreferencesActivity.class;
                break;
        }
        if (cls != null) {
            Intent intent = new Intent(getApplicationContext(), cls);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
