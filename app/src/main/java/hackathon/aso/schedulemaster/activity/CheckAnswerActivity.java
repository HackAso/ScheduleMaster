package hackathon.aso.schedulemaster.activity;

/*
* 回答確認の際、この画面を経由してアンケートごとにグループ分けして表示する予定だったが変更
*
*
* */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hackathon.aso.schedulemaster.R;

public class CheckAnswerActivity extends AbstractActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answer);
        ImageView what = (ImageView) findViewById(R.id.btn_what_checkAnswer);
        ImageView when = (ImageView) findViewById(R.id.btn_when_checkAnswer);
        ImageView who = (ImageView) findViewById(R.id.btn_who_chekAnswer);
        what.setOnClickListener(this);
        when.setOnClickListener(this);
        who.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_what_checkAnswer:
                //
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                break;
            case R.id.btn_when_checkAnswer:
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                break;
            case R.id.btn_who_chekAnswer:
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                break;
        }
        startActivity(intent);
    }
}
