package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;
import hackathon.aso.schedulemaster.object.News;

public class WhoAnswerActivity extends AbstractActivity {
    private String enqueteID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_answer);

        Intent intent = getIntent();
        enqueteID = intent.getStringExtra("id");
        if (enqueteID != null) {
            Log.i("内部", "アンケートidは" + enqueteID);
            Enquete enquete = Enquete.getEnquete(enqueteID, "だれ");
            Log.i("内部", "whoAnsのアンケのタイトルとコメントは"+enquete.getTitle() + enquete.getComment());
            TextView tv_title = (TextView) findViewById(R.id.tv_title_whoAns);
            TextView tv_comment = (TextView) findViewById(R.id.tv_comment_whoAns);
            tv_title.setText(enquete.getTitle());
            tv_comment.setText(enquete.getComment());
            ImageView send = (ImageView) findViewById(R.id.iv_send_whoAns);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_answer_whoAms);
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    int yesNo = 0;
                    Log.i("内部", "チェックされたラジオボタンのIDは" + checkedId);
                    switch (checkedId) {
                        case R.id.rb_yes_whoAns:
                            yesNo = 1;
                            break;
                        case R.id.rb_no_whoAns:
                            yesNo = 0;
                            break;
                        case -1:
                            yesNo = -1;
                    }
                    if (yesNo == -1) {
                        Toast.makeText(getApplicationContext(), "回答を選択して下さい", Toast.LENGTH_SHORT).show();
                    } else {
                        Enquete enquete = new Enquete();
                        enquete.setEnqueteID(enqueteID);
                        enquete.setUsrAdd(currentUser.getEmail());
                        enquete.setUsrName(currentUser.getName());
                        enquete.setYesNo(yesNo);
                        enquete.setKind("だれ");
                        enquete.setAnswer();

                        News news = new News();
                        news.setEnqueteID(enqueteID);
                        news.setUserName(currentUser.getName());
                        news.setFlag();

                        Toast.makeText(getApplicationContext(), "アンケートに回答しました", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }
    }
}
