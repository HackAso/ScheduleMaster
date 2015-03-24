package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;

/**
 * Created by shouhei on 15/02/25.
 */
public class WhatEnqueActivity extends AbstractActivity implements View.OnClickListener {
    List<String> sendToList = new ArrayList<String>();
    List<String> sendToEmailList = new ArrayList<String>();
    String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatenque);
        ImageView friendselect = (ImageView) findViewById(R.id.iv_selectfriend_what);
        ImageView entry = (ImageView) findViewById(R.id.iv_entry_what);
        friendselect.setOnClickListener(this);
        entry.setOnClickListener(this);
        createSpinner();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_selectfriend_what:
                Intent intent1 = new Intent(getApplicationContext(), SelectFriendsActivity.class);
                startActivityForResult(intent1, 0);
                break;

            case R.id.iv_entry_what:
                if (sendToList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "送信先メンバーを選択して下さい", Toast.LENGTH_LONG).show();
                    return;
                }
                if (date == null) {
                    Toast.makeText(getApplicationContext(), "回答期限を選択して下さい", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText et_title = (EditText) findViewById(R.id.et_title_what);
                String title = et_title.getText().toString();
                if (title == null) {
                    Toast.makeText(getApplicationContext(), "タイトルを入力して下さい", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText et_comment = (EditText) findViewById(R.id.et_comment_what);
                String comment = et_comment.getText().toString();

                Enquete enquete = new Enquete();
                enquete.setKind("なに");
                enquete.setTitle(title);                    //nullでないこと確認済み
                enquete.setComment(comment);
                enquete.setManagerID(currentUser.getEmail());
                enquete.setManagerNickName(currentUser.getName());
                enquete.setUserNameList(sendToList);        //nullでないこと確認済み
                enquete.setUserEmailList(sendToEmailList);
                enquete.setDate(date);                      //nullでないこと確認済み
                enquete.setReceiver(4);

                Log.i("内部", enquete.getTitle() + enquete.getComment() + enquete.getManagerID() + enquete.getUserNameList().get(0) + sendToList.get(0) + date);
                try {
                    enquete.setEnquete();
                    Toast.makeText(getApplicationContext(), "アンケートを作成しました", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "アンケートを作成に失敗しました", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
                startActivity(intent);
                finish();
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                try {
                    sendToList = data.getExtras().getStringArrayList("result");
                    sendToEmailList = data.getExtras().getStringArrayList("result2");

                    String selected = null;
                    for (int i = 0; i < sendToList.size(); i++) {
                        if (selected == null) {
                            selected = sendToList.get(0);
                        } else {
                            selected += " , " + sendToList.get(i);
                        }
                    }

                    TextView selectedTv = (TextView) findViewById(R.id.tv_selected_whatenquete);
                    selectedTv.setText(selected);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "送信先メンバーを選択して下さい", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    //Spinnerに項目リストを登録する
    private void createSpinner() {
        ArrayList<String> list = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd(EE)");
        Calendar cal = Calendar.getInstance();
        for (int i = 1; i < 100; i++) {
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
            cal.get(Calendar.DAY_OF_WEEK);
            list.add(format.format(cal.getTime()));
        }

        //配列をウィジェットに渡す準備
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, list);
        //アダプター経由でSpinnerにリストを登録
        Spinner spn = (Spinner) this.findViewById(R.id.spinnerWhat);
        spn.setAdapter(adapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner spn = (Spinner) parent;
                Log.i("内部", "選択された日時は" + parent.getItemAtPosition(position));
                date = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
