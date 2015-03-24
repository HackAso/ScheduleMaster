package hackathon.aso.schedulemaster.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nifty.cloud.mb.NCMBException;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;

public class WhatAnsListActivity extends AbstractActivity {
    WhatAnsCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_ans_list);
        ListView ansListView = (ListView) findViewById(R.id.lv_anslist_whatanslist);
        Intent intent = getIntent();
        //アンケートIDを遷移元から取得
        String id = intent.getStringExtra("id");
        Log.i("内部", "ねむい" + id);
        List<Enquete> ansList =null;
        try {
            ansList = Enquete.getAnswer(id,"なに");
            if (ansList.size() == 0) {
                TextView textView = (TextView) findViewById(R.id.tv_nodata_whatans);
                textView.setText("まだ回答がありません");
            }
        } catch (NCMBException e) {
            e.printStackTrace();
        }
        adapter = new WhatAnsCustomAdapter(getApplicationContext(), 0, (ArrayList)ansList );
        ansListView.setAdapter(adapter);
        
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}

class WhatAnsCustomAdapter extends ArrayAdapter<Enquete> {

    private LayoutInflater inflater;

    // コンストラクタ
    public WhatAnsCustomAdapter(Context context, int textViewResourceId, ArrayList<Enquete> whatAnsList) {
        super(context, textViewResourceId, whatAnsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_whatlist, null);
            TextView titleTv = (TextView) view.findViewById(R.id.tv_name_what);
            TextView dateTv = (TextView) view.findViewById(R.id.tv_ans_what);
            holder = new ViewHolder();
            holder.nameText = titleTv;
            holder.ansText = dateTv;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 特定の行のデータを取得
        Enquete whatAnsList = getItem(position);
        holder.nameText.setText("●" +whatAnsList.getUsrName());
        holder.ansText.setText("\"" + whatAnsList.getWhat()+"\"");

        // 行毎に背景色を変える
        if (position % 2 == 0) {

        } else {

        }

        // XMLで定義したアニメーションを読み込む
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion_friendslist);
        // リストアイテムのアニメーションを開始
        view.startAnimation(anim);

        return view;
    }

    static class ViewHolder {
        TextView nameText;
        TextView ansText;
    }
}