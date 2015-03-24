package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
public class WhenEnqueActivity extends AbstractActivity {
    Spinner day1Spinner;
    Spinner day2Spinner;
    Spinner day3Spinner;
    Spinner hour1Spinner;
    Spinner hour2Spinner;
    Spinner hour3Spinner;
    Spinner minutes1Spinner;
    Spinner minutes2Spinner;
    Spinner minutes3Spinner;
    Spinner limitSpinner;
    String[] hour;
    String[] minutes;
    List<String> sendToList = new ArrayList<String>();
    List<String> sendToEmailList = new ArrayList<String>();
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_selectfriend_when:
                    Intent intent1 = new Intent(getApplicationContext(), SelectFriendsActivity.class);
                    startActivityForResult(intent1, 0);
                    break;
                case R.id.iv_entry_when:

                    if (sendToList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "送信先メンバーを選択して下さい", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String date = limitSpinner.getSelectedItem().toString();
                    if (date == null) {
                        Toast.makeText(getApplicationContext(), "回答期限を選択して下さい", Toast.LENGTH_LONG).show();
                        return;
                    }
                    EditText et_title = (EditText) findViewById(R.id.et_title_when);
                    String title = et_title.getText().toString();
                    if (title == null) {
                        Toast.makeText(getApplicationContext(), "タイトルを入力して下さい", Toast.LENGTH_LONG).show();
                        return;
                    }

                    EditText et_comment = (EditText) findViewById(R.id.et_comment_when);
                    String comment = et_comment.getText().toString();
                    if (comment == null) {
                        Toast.makeText(getApplicationContext(), "コメントを入力して下さい", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String when1 = day1Spinner.getSelectedItem().toString() + hour1Spinner.getSelectedItem().toString() + minutes1Spinner.getSelectedItem().toString();
                    String when2 = day2Spinner.getSelectedItem().toString() + hour2Spinner.getSelectedItem().toString() + minutes2Spinner.getSelectedItem().toString();
                    String when3 = day3Spinner.getSelectedItem().toString() + hour3Spinner.getSelectedItem().toString() + minutes3Spinner.getSelectedItem().toString();
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
                    enquete.setKind("いつ");
                    enquete.setWhen1(when1);
                    enquete.setWhen2(when2);
                    enquete.setWhen3(when3);

                    Log.i("内部", enquete.getTitle() + enquete.getComment() + enquete.getManagerID() + enquete.getUserNameList().get(0) + sendToList.get(0) + date);
                    try {
                        enquete.setEnquete();
                        Toast.makeText(getApplicationContext(), "アンケートを作成しました", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "アンケートを作成に失敗しました", Toast.LENGTH_LONG).show();
                    }

                    break;

            }
        }
    };


//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imageViewEntry: {
//                //フォームからデータ取得
//                EditText et_title = (EditText) findViewById(R.id.editTextTitle);
//                String title = et_title.getText().toString();
//                EditText et_comment = (EditText) findViewById(R.id.editTextcomme);
//                String comment = et_comment.getText().toString();
//                Log.w("スピナ", spinner1.getSelectedItem().toString());
//                String date1 = spinner1.getSelectedItem().toString() + spinner2.getSelectedItem().toString() + spinner3.getSelectedItem().toString();
//                String date2 = spinner4.getSelectedItem().toString() + spinner5.getSelectedItem().toString() + spinner6.getSelectedItem().toString();
//                String date3 = spinner7.getSelectedItem().toString()
//                        + spinner8.getSelectedItem().toString() + spinner9.getSelectedItem().toString();
//                //アンケート作成
//                Enquete enquete = new Enquete();
//                enquete.setWhen1(date1);
//                enquete.setWhen2(date2);
//                enquete.setWhen3(date3);
//                enquete.setKind("いつ");
//                enquete.setTitle(title);
//                enquete.setComment(comment);
//                enquete.setManagerID(currentUser.getEmail());
//                enquete.setEnquete();
//
//
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whenenquete);
        day1Spinner = (Spinner) findViewById(R.id.sp_day1_when);
        day2Spinner = (Spinner) findViewById(R.id.sp_date2_when);
        day3Spinner = (Spinner) findViewById(R.id.sp_dasy3_when);
        hour1Spinner = (Spinner) findViewById(R.id.sp_huor1_when);
        hour2Spinner = (Spinner) findViewById(R.id.sp_hour2_when);
        hour3Spinner = (Spinner) findViewById(R.id.sp_hour3_when);
        minutes1Spinner = (Spinner) findViewById(R.id.sp_minutes1_when);
        minutes2Spinner = (Spinner) findViewById(R.id.sp_minutes2_when);
        minutes3Spinner = (Spinner) findViewById(R.id.sp_minutes3_when);
        limitSpinner = (Spinner) findViewById(R.id.sp_limit_when);

        createSpinner();
        ImageView entry = (ImageView) findViewById(R.id.iv_entry_when);
        entry.setOnClickListener(listener);
        ImageView selectFriends = (ImageView) findViewById(R.id.iv_selectfriend_when);
        selectFriends.setOnClickListener(listener);


    }

    //Spinnerに項目リストを登録する
    private void createSpinner() {
        //daySpinner作成
        ArrayList<String> dateList = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd(EE)");
        Calendar cal = Calendar.getInstance();
        for (int i = 1; i < 100; i++) {
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
            cal.get(Calendar.DAY_OF_WEEK);
            dateList.add(format.format(cal.getTime()));
        }
        //配列をウィジェットに渡す準備
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dateList);
        //アダプター経由でSpinnerにリストを登録

        day1Spinner.setAdapter(dayAdapter);
        day2Spinner.setAdapter(dayAdapter);
        day3Spinner.setAdapter(dayAdapter);
        limitSpinner.setAdapter(dayAdapter);

        //hourSpinners作成
        hour = getResources().getStringArray(R.array.hour);
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hour);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour1Spinner.setAdapter(hourAdapter);
        hour2Spinner.setAdapter(hourAdapter);
        hour3Spinner.setAdapter(hourAdapter);

        //minutesSpinner作成
        minutes = getResources().getStringArray(R.array.minute);
        ArrayAdapter<String> minutesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes);
        minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes1Spinner.setAdapter(minutesAdapter);
        minutes2Spinner.setAdapter(minutesAdapter);
        minutes3Spinner.setAdapter(minutesAdapter);
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

                    TextView selectedTv = (TextView) findViewById(R.id.tv_selected_when);
                    selectedTv.setText(selected);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "送信先メンバーを選択して下さい", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
