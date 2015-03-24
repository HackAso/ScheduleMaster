package hackathon.aso.schedulemaster;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nifty.cloud.mb.LogInCallback;
import com.nifty.cloud.mb.NCMB;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBUser;

import java.util.List;

public class MainActivity extends Activity {

    private TextView mTextView;
    NCMBUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String applicationKey = "3f054d7a8769bdac50d30b3eb2f49d4165134683a538b69fbb7af68cc4ac8024";
        String clientKey = "4cd39cfc2cc57db844aa9f4676e598e528273ff22b0b93e392fa0699f4db46b2";
        NCMB.initialize(getApplicationContext(), applicationKey, clientKey);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        user = NCMBUser.getCurrentUser();
        NCMBUser.logInInBackground("333@gmail.com", "000", new LogInCallback() {
            @Override
            public void done(NCMBUser ncmbUser, NCMBException e) {
                if (e == null) {
                    user = NCMBUser.getCurrentUser();

                } else {
                    Log.i("状態", "ログイン失敗: " + e.getLocalizedMessage());
                    Toast.makeText(getApplication(), "IDまたはパスワードが違います", Toast.LENGTH_LONG).show();
                }
            }
        });



        final String userName = user.getUsername();
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("はろー");

            }
        });
    }
}

class NewsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    List<News> newsList;

    public NewsAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setnewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listitem_news, parent, false);

        final WatchViewStub stub = (WatchViewStub)convertView.findViewById(R.id.watch_view_stub);
        ((TextView) stub.findViewById(R.id.tv_title_newsList)).setText("●" + newsList.get(position).getTitle());
        ((TextView) stub.findViewById(R.id.tv_from_newsList)).setText("from : " + newsList.get(position).getManagerName());
        ((TextView) stub.findViewById(R.id.tv_received_newsList)).setText("date : " + newsList.get(position).getDate());

        return convertView;
    }
}
