package hackathon.aso.schedulemaster.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;

public class AnswerUsingWear extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_using_wear);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(R.string.app_name);
        Toast.makeText(getApplicationContext(), "ウォッチから回答します", Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        int flg = intent.getIntExtra("flg", -1);
        String title = intent.getStringExtra("title");
        String comment = intent.getStringExtra("comment");
        Log.i("内部", "AnswerUsingWearのタイトル、コメント、flgは" + title + comment + flg);
        if (flg == -1 || title == null || comment == null) {
            Log.i("状態", "wearからの回答に失敗しました");
        } else {
            Enquete enquete = Enquete.getEnqueteByTitleAndComment(title, comment);
            enquete.setUsrAdd(currentUser.getEmail());
            enquete.setUsrName(currentUser.getName());
            enquete.setYesNo(flg);
            enquete.setAnswer();
        }

        finish();


   }

}


