package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBUser;

import hackathon.aso.schedulemaster.R;

public class PreferencesActivity extends AbstractActivity{
    public PreferencesActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ImageView logoutButton = (ImageView) findViewById(R.id.btn_logout_preferences);
        logoutButton.setOnClickListener(listener);
//        findViewById(R.id.btn_temp_preferences).setOnClickListener(listener);
        ImageView toFriendListImageButton = (ImageView) findViewById(R.id.ib_friendlist_preferences);
        toFriendListImageButton.setOnClickListener(listener);
        ImageView friendEntry = (ImageView)findViewById(R.id.btn_search_preferences);
        friendEntry.setOnClickListener(listener);
        //ログインユーザの表示
//        TextView tv1=(TextView)findViewById(R.id.tv_temp_preferences);
//        tv1.setText(currentUser.getEmail());
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_logout_preferences :
                    try {
                        NCMBUser.logOut();
                        Log.e("状態", "ログアウトしました");
                        currentUser = null;
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    } catch (NCMBException e) {
                        Toast.makeText(getApplicationContext(), "ログアウト失敗", Toast.LENGTH_LONG).show();
                        Log.e("状態", "ログアウトに失敗しました: " + e.getLocalizedMessage());
                    }
                    break;

//                case R.id.btn_temp_preferences:
//                    TextView tempTv = (TextView) findViewById(R.id.tv_temp_preferences);
//                    Intent intent1 = new Intent(getApplicationContext(),SelectFriendsActivity.class);
//                    startActivityForResult(intent1, 0);
//                    break;

                case R.id.ib_friendlist_preferences:
                    intent = new Intent(getApplicationContext(),FriendsListActivity.class);
                    startActivity(intent);
                    break;

                case R.id.btn_search_preferences:
                    intent = new Intent(getApplicationContext(),addFrindActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
//                TextView textView = (TextView) findViewById(R.id.tv_temp_preferences);
//                textView.setText(data.getExtras().getStringArrayList("result").get(0));

            break;
        }
    }
}
