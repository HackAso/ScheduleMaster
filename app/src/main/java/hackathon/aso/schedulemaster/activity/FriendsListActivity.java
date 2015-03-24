package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.elements.FriendCustomAdapter;
import hackathon.aso.schedulemaster.object.User;

public class FriendsListActivity extends AbstractActivity {
    ListView list;
    FriendCustomAdapter mAdapter;
    ArrayList<User> UserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendslist);
        list = (ListView) findViewById(R.id.lv_friendslist_friendslist);
        // リストアイテムのラベルを格納するArrayListをインスタンス化
        UserList = (ArrayList) currentUser.getFriendList();
        if (UserList == null || UserList.size() == 0) {
            Toast.makeText(getApplicationContext(), "フレンド登録を行って下さい", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), addFrindActivity.class);
            startActivity(intent);
        } else {
            // Adapterのインスタンス化
            // 第三引数にlabelListを渡す
            mAdapter = new FriendCustomAdapter(this, 0, UserList);
            // リストにAdapterをセット
            list.setAdapter(mAdapter);

        }

        //フレンド追加画面に遷移
        ImageView addFriendIv = (ImageView) findViewById(R.id.iv_register_friendslist);
        addFriendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addFrindActivity.class);
                startActivity(intent);
            }
        });
        //削除モードに切替。ロングタップでフレンドを削除できるように
        ImageView delete = (ImageView) findViewById(R.id.ib_delete_friendslist);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "削除したいフレンドをロングタップして下さい", Toast.LENGTH_SHORT).show();
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        User user = (User) parent.getItemAtPosition(position);
                        String name = user.getName();
                        Log.i("内部", "ロングクリックされたリストアイテムは" + name);
                        currentUser.removeFriend(user);
                        Toast.makeText(getApplicationContext(), name + "さんをフレンドから削除しました。", Toast.LENGTH_LONG).show();
                        //リストビューから要素削除
                        UserList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
            }

        });


    }
}
