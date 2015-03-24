package hackathon.aso.schedulemaster.elements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Plan;

/**
 * Created by Aki on 15/02/23.
 */
public class ScheduleAdapter extends ArrayAdapter<Plan> {

    private LayoutInflater inflater;

    // コンストラクタ
    public ScheduleAdapter(Context context, int textViewResourceId, ArrayList<Plan> plansList) {
        super(context, textViewResourceId, plansList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_schedule, null);
            TextView titleTv = (TextView) view.findViewById(R.id.tv_title_schedules);
            TextView dateTv = (TextView) view.findViewById(R.id.tv_date_schedules);
            holder = new ViewHolder();
            holder.titleText = titleTv;
            holder.dateText = dateTv;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 特定の行のデータを取得
        Plan plan = getItem(position);
        holder.titleText.setText(plan.getTitle());
        holder.dateText.setText(plan.getDate());

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
        TextView titleText;
        TextView dateText;
    }
}
