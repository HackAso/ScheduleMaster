package hackathon.aso.schedulemaster.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.elements.ScheduleAdapter;
import hackathon.aso.schedulemaster.object.Plan;

public class DeletePlanActivity extends AbstractActivity {
    List<Plan> list;
    ScheduleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plan);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.lv_plansList_deleteplan);

        try {
            list = currentUser.getHostingList();
            if (list != null && list.size() != 0) {
                Log.i("状態", list.size() + "件ののスケジュールを取得しました");

                // Adapterのインスタンス化
                // 第三引数にlabelListを渡す
                mAdapter = new ScheduleAdapter(getApplicationContext(), 0, (ArrayList) list);
                // リストにAdapterをセット
                listView.setAdapter(mAdapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Plan plan = (Plan) parent.getItemAtPosition(position);
                        Log.i("内部", "ロングクリックされたリストアイテムはid: "+plan.getPlanID() + "タイトル: "+plan.getTitle());
                        plan.delete();
                        Toast.makeText(getApplicationContext(), plan.getTitle() + "を予定から削除しました。", Toast.LENGTH_LONG).show();
                        //リストビューから要素削除
                        list.remove(position);
                        mAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("状態", "スケジュールの取得に失敗しました");
            Toast.makeText(getApplicationContext(), "ネットワークを確認して下さい", Toast.LENGTH_LONG).show();
        }
    }
}
