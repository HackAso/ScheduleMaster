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
import hackathon.aso.schedulemaster.object.Enquete;

/**
 * Created by Aki on 15/03/07.
 */
public class EnqueteCustomAdapter extends ArrayAdapter<Enquete> {

    private LayoutInflater inflater;

    // コンストラクタ
    public EnqueteCustomAdapter(Context context, int textViewResourceId, ArrayList<Enquete> enqueteList) {
        super(context, textViewResourceId, enqueteList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_enquetelist, null);
            TextView titleTv = (TextView) view.findViewById(R.id.tv_title_enquete);
            TextView dateTv = (TextView) view.findViewById(R.id.tv_date_enquete);
            holder = new ViewHolder();
            holder.titleText = titleTv;
            holder.dateText = dateTv;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 特定の行のデータを取得
        Enquete enquete = getItem(position);
        String kind = enquete.getKind();
        if (kind.equals("なに")) {
            kind = "なにする?";
        }
        else if (kind.equals("いつ") ){
            kind = "いつする?";
        }
        else if (kind.equals("だれ")) {

            kind = "だれと?";
        }

        holder.titleText.setText("["+kind+"]"+enquete.getTitle());
        holder.dateText.setText("limit: "+enquete.getDate());

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