package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;
import hackathon.aso.schedulemaster.object.News;

public class WhenAnswerActivity extends AbstractActivity {
    private String enqueteID;
    CheckBox date1CB;
    CheckBox date2CB;
    CheckBox date3CB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_when_answer);
        Intent intent = getIntent();
        enqueteID = intent.getStringExtra("id");
        if (enqueteID != null) {
            Enquete enquete = Enquete.getEnquete(enqueteID,"いつ");
            TextView tv_title = (TextView) findViewById(R.id.tv_title_whenans);
            TextView tv_comment = (TextView) findViewById(R.id.tv_comment_whenans);
            date1CB = (CheckBox) findViewById(R.id.cb_date1_whenans);
            date2CB = (CheckBox) findViewById(R.id.cb_date2_whenans);
            date3CB = (CheckBox) findViewById(R.id.cb_date3_whenans);
            date1CB.setText(enquete.getWhen1());
            date2CB.setText(enquete.getWhen2());
            date3CB.setText(enquete.getWhen3());
            tv_title.setText(enquete.getTitle());
            tv_comment.setText(enquete.getComment());

            ImageView send = (ImageView) findViewById(R.id.iv_send_whenans);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Log.i("内部", String.valueOf(date1CB.isChecked()));
                        Enquete enquete = new Enquete();
                        enquete.setKind("いつ");
                        enquete.setEnqueteID(enqueteID);
                        enquete.setUsrAdd(currentUser.getEmail());
                        enquete.setUsrName(currentUser.getName());
                        enquete.setWhen1(String.valueOf(date1CB.isChecked()));
                        enquete.setWhen2(String.valueOf(date2CB.isChecked()));
                        enquete.setWhen3(String.valueOf(date3CB.isChecked()));
                        Log.i("内部", "kimiya");
                        enquete.setAnswer();


                        News news = new News();
                        news.setEnqueteID(enqueteID);
                        news.setUserName(currentUser.getName());
                        news.setFlag();
                        Log.i("内部", "kimiya2");

                        Toast.makeText(getApplicationContext(), "アンケートに回答しました", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("状態", "いつアンケート回答に失敗しました");
                    }


                }
            });
        }
    }
}
