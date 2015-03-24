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
import hackathon.aso.schedulemaster.object.Plan;

/**
 * Created by shouhei on 15/02/15.
 */
public class NewplanActivity extends AbstractActivity implements AdapterView.OnItemSelectedListener {

    Spinner stateSpinner;
    Spinner stateSpinner4;
    String[] states;
    String[] states2;
    String date = null;
    List<String> sendToName = new ArrayList<String>();
    List<String> sendToEmail = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplan);

        createSpinner();

        stateSpinner = (Spinner) findViewById(R.id.spinnerPlanHour);
        stateSpinner4 = (Spinner) findViewById(R.id.spinnerPlanMinute);

        states = getResources().getStringArray(R.array.hour);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, states);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(this);


        states2 = getResources().getStringArray(R.array.minute);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, states2);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);


        stateSpinner4.setAdapter(adapter2);
        stateSpinner4.setOnItemSelectedListener(this);

        ImageView selectFriends = (ImageView) findViewById(R.id.iv_selectfriend_newplan);
        selectFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectFriendsActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageView entry = (ImageView) findViewById(R.id.imageViewPlantouroku);
        entry.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "予定を追加します", Toast.LENGTH_SHORT).show();
                EditText planTitle = (EditText) findViewById(R.id.tv_name_newplan);
                String title = planTitle.getText().toString();
                //入力チェック
                if (date == null) {
                    Toast.makeText(getApplicationContext(), "日付を選択して下さい", Toast.LENGTH_LONG).show();
                }else if (title == null) {
                    Toast.makeText(getApplicationContext(), "予定名を入力して下さい", Toast.LENGTH_LONG).show();
                }else if (sendToEmail == null) {
                    Toast.makeText(getApplicationContext(), "送信先メンバーを選択して下さい", Toast.LENGTH_LONG).show();
                }else{
                    Plan plan = new Plan();
                    plan.setTitle(title);
                    plan.setDate(date);
                    plan.setManagerID(currentUser.getEmail());
                    plan.setSendToEmail(sendToEmail);
                    plan.setPlan();
                    Toast.makeText(getApplicationContext(), "新規予定を登録しました", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SchedulesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            //        NCMBQuery query  = NCMBQuery.getQuery("test");
//        try {
//            List<NCMBObject> ncmbObjectList = query.find();
//
//            Toast.makeText(getApplicationContext(), ncmbObjectList.get(0).getString("item"), Toast.LENGTH_LONG).show();
//        } catch (NCMBException e) {
//            e.printStackTrace();
//        }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                try {
                    sendToName = data.getExtras().getStringArrayList("result");
                    sendToEmail = data.getExtras().getStringArrayList("result2");
                    String selected = null;
                    for (int i = 0; i < sendToName.size(); i++) {
                        if (selected == null) {
                            selected = sendToName.get(0) + ",";
                        } else {
                            selected += " , " + sendToName.get(i);
                        }
                    }

                    TextView selectedtv = (TextView) findViewById(R.id.tv_selected_newplan);
                    selectedtv.setText(selected);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "送信先メンバーを選択して下さい", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(
            AdapterView<?> arg0,
            View arg1,
            int arg2,
            long arg3) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    //Spinnerに項目リストを登録する
    private void createSpinner() {
        ArrayList<String> list = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd(EEEE)");
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
        Spinner spn = (Spinner) this.findViewById(R.id.spinnerPlanDay);
        spn.setAdapter(adapter);


        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner spn = (Spinner) parent;
                date = spn.getItemAtPosition(position).toString();
                Log.i("内部", "もう疲れたよ" + date);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}