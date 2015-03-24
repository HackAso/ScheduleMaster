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
import hackathon.aso.schedulemaster.object.User;

/**
 * Created by Aki on 15/02/23.
 */
public class FriendCustomAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;

    // コンストラクタ
    public FriendCustomAdapter(Context context, int textViewResourceId, ArrayList<User> friendList) {
        super(context, textViewResourceId, friendList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_friends, null);
            TextView nameTv = (TextView) view.findViewById(R.id.tv_name_friendslist);
            TextView emailTv = (TextView) view.findViewById(R.id.tv_email_friendslist);
            holder = new ViewHolder();
            holder.nameText = nameTv;
            holder.emailText = emailTv;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 特定の行のデータを取得
        User friend = getItem(position);
        holder.nameText.setText(friend.getName());
        holder.emailText.setText(friend.getEmail());

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
        TextView emailText;
    }
}
