package hackathon.aso.schedulemaster.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nifty.cloud.mb.NCMBException;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.User;

/**
 * Created by shouhei on 15/02/25.
 */
public class addFrindActivity extends AbstractActivity implements View.OnClickListener {
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        ImageView findButton = (ImageView) findViewById(R.id.btn_find_addfriend);
        ImageView addButton = (ImageView) findViewById(R.id.btn_add_addfriend);
        findButton.setOnClickListener(this);
        addButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_find_addfriend:
                EditText friendEmailEt = (EditText) findViewById(R.id.et_searchname_addfriend);
                String friendEmail = friendEmailEt.getText().toString();
                try {
                    user = User.findUser(friendEmail);
                    TextView eAddress = (TextView) findViewById(R.id.tv_email_addfriend);
                    TextView nickName = (TextView) findViewById(R.id.tv_name_addfriend);
                    eAddress.setText(user.getEmail());
                    nickName.setText(user.getName());
                    //検索したユーザーが自分の場合
                    if (user.getEmail() == currentUser.getEmail()) {
                        Toast.makeText(getApplicationContext(),"自分を検索することはできません",Toast.LENGTH_LONG).show();
                        nickName.setText("");
                        eAddress.setText("");
                    }
                } catch (Exception e) {
                    Log.i("内部", "存在しないユーザーです");
                    Toast.makeText(getApplicationContext(), "ユーザーの検索に失敗しました", Toast.LENGTH_LONG).show();
                }

                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
            case R.id.btn_add_addfriend:
                //フレンド登録

                if (user != null) {
                    Log.i("内部", "登録するユーザーの名前、アドレスは" + user.getName() + user.getEmail());
                    try {
                        //検索されたユーザーが登録済みでない場合
                        if (currentUser.checkFriend(user) == 0) {
                            currentUser.addfriend(user);
                            Toast.makeText(getApplicationContext(), "フレンドを登録しました", Toast.LENGTH_SHORT).show();
                            Log.i("内部", "フレンド登録しました");

                        } else {
                            Toast.makeText(getApplicationContext(), "すでに登録済みです", Toast.LENGTH_LONG).show();
                            Log.i("内部", "フレンド登録済み");
                        }
                    } catch (NCMBException e) {
                        Toast.makeText(getApplicationContext(), "フレンド登録に失敗しました", Toast.LENGTH_SHORT).show();
                        Log.i("内部", "フレンド登録失敗");
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "ユーザーを検索して下さい", Toast.LENGTH_LONG).show();
                }
                this.recreate();
                break;
        }
    }
}
