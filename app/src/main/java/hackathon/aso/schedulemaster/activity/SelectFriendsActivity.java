package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.User;

public class SelectFriendsActivity extends AbstractActivity {
    SparseBooleanArray checkedItemPositions = null;
    List<String> friendStringList = new ArrayList<String>();
    List<String> friendStringList2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);
        final ListView list = (ListView) findViewById(R.id.lv_friendslist_selectfriends);
        // リストアイテムのラベルを格納するArrayListをインスタンス化
        final ArrayList<User> userList = (ArrayList) currentUser.getFriendList();


        if (userList == null || userList.size() == 0) {
            Toast.makeText(getApplicationContext(), "フレンド登録を行って下さい", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), addFrindActivity.class);
            startActivity(intent);
        } else {

            for (int i = 0; i < userList.size(); i++) {
                friendStringList.add(userList.get(i).getName() + "   " + userList.get(i).getEmail());
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, friendStringList);
            list.setAdapter(adapter);
            friendStringList = null;
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checkedItemPositions = list.getCheckedItemPositions();
                    Log.i("内部", String.format("position:%d checked:%b", position, checkedItemPositions.get(position)));

                }
            });
            Button select = (Button) findViewById(R.id.btn_select_selectFriends);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendStringList = new ArrayList<String>();
                    for (int i = 0; i < userList.size(); i++) {
                        if (checkedItemPositions.get(i) == true) {
                            friendStringList.add(userList.get(i).getName() );
                            friendStringList2.add(userList.get(i).getEmail() );
                            Log.i("内部", "ユーザー" + userList.get(i).getName()+ " " + userList.get(i).getEmail() + "が選択されています");
                        }
                    }
                    Intent result = new Intent();
                    result.putStringArrayListExtra("result", (ArrayList)friendStringList);
                    result.putStringArrayListExtra("result2", (ArrayList) friendStringList2);
                    setResult(RESULT_OK, result);
                    friendStringList = null;
                    friendStringList2 = null;
                    finish();


                }
            });

        }
    }
}
