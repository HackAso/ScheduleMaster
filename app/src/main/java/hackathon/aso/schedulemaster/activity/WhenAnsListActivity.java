package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nifty.cloud.mb.NCMBException;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;

public class WhenAnsListActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_when_ans_list);
        Intent intent = getIntent();
        //アンケートIDを遷移元から取得
        String id = intent.getStringExtra("id");
        Enquete enquete = Enquete.getEnquete(id, "いつ");
        String title = enquete.getTitle();
        String date1 = enquete.getWhen1();
        String date2 = enquete.getWhen2();
        String date3 = enquete.getWhen3();
        List<Enquete> ans1 = new ArrayList<Enquete>();
        List<Enquete> ans2 = new ArrayList<Enquete>();
        List<Enquete> ans3 = new ArrayList<Enquete>();
        Log.i("ここか", ans1.size() + "");

        try {
            Log.i("内部", "いつアンケート回答の取得に失敗しtenai");
            ans1 = Enquete.getAnswer(id, "いつ1");
            ans2 = Enquete.getAnswer(id, "いつ2");
            ans3 = Enquete.getAnswer(id, "いつ3");
            Log.i("内部", ans1.size() + ans2.size()+ ans3.size() + "");
        } catch (NCMBException e) {
            e.printStackTrace();
            Log.i("内部", "いつアンケート回答の取得に失敗しました");
        }
        TextView titleTv = (TextView) findViewById(R.id.tv_title_whenans);
        titleTv.setText(title);
        TextView date1Tv = (TextView) findViewById(R.id.tv_date1_whenans);
        date1Tv.setText(date1);
        TextView date2Tv = (TextView) findViewById(R.id.tv_date2_whenans);
        date2Tv.setText(date2);
        TextView date3Tv = (TextView) findViewById(R.id.tv_date3_whenans);
        date3Tv.setText(date3);
        TextView count1Tv = (TextView) findViewById(R.id.tv_count1_whenans);
        count1Tv.setText(ans1.size() + " 人");
        TextView count2Tv = (TextView) findViewById(R.id.tv_count2_whenans);
        count2Tv.setText(ans2.size()+ " 人");
        TextView count3Tv = (TextView) findViewById(R.id.tv_count3_whenans);
        count3Tv.setText(ans3.size() + " 人");

    }

}
