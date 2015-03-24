package hackathon.aso.schedulemaster.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.elements.ScheduleAdapter;
import hackathon.aso.schedulemaster.object.Plan;

public class SchedulesActivity extends AbstractActivity {
    ArrayList<Plan> list = null;
    ScheduleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);



    }


    @Override
    protected void onResume() {
        super.onResume();
        //activity新規作成時もonResume()通るからこっちに書いた、ちょっと処理重くなるけど問題ない範囲
        ListView listView = (ListView) findViewById(R.id.lv_schedules_schedules);
        try {
            list = (ArrayList)currentUser.getPlansList();
            if (list != null && list.size() != 0) {
                Log.i("状態", list.size() + "件ののスケジュールを取得しました");
            }
            // Adapterのインスタンス化
            // 第三引数にlabelListを渡す
            mAdapter = new ScheduleAdapter(getApplicationContext(), 0, list);
            // リストにAdapterをセット
            listView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("状態", "スケジュールの取得に失敗しました");
            Toast.makeText(getApplicationContext(), "ネットワークを確認して下さい", Toast.LENGTH_LONG).show();
        }

//        try {
//            list = (ArrayList)currentUser.getPlansList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mAdapter.notifyDataSetChanged();

    }

}