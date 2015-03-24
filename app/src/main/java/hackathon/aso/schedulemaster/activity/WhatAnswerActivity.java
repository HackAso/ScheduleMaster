package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.Enquete;
import hackathon.aso.schedulemaster.object.News;

public class WhatAnswerActivity extends AbstractActivity implements View.OnClickListener {
    private String enqueteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_answer);
        Intent intent = getIntent();
        enqueteID = intent.getStringExtra("id");
        if (enqueteID != null) {
            Log.i("内部", "アンケートidは" + enqueteID);
            Enquete enquete = Enquete.getEnquete(enqueteID, "なに");
            TextView tv_title = (TextView) findViewById(R.id.textView3);
            TextView tv_comment = (TextView) findViewById(R.id.textView4);
            tv_title.setText(enquete.getTitle());
            tv_comment.setText(enquete.getComment());
            ImageView put = (ImageView) findViewById(R.id.imageView5);
            put.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        try {
            EditText comment = (EditText) findViewById(R.id.et_answer_whatanswer);
            Enquete enquete = new Enquete();
            enquete.setEnqueteID(enqueteID);
            enquete.setUsrAdd(currentUser.getEmail());
            enquete.setUsrName(currentUser.getName());
            enquete.setWhat(comment.getText().toString());
            enquete.setKind("なに");
            enquete.setAnswer();

            News news = new News();
            news.setEnqueteID(enqueteID);
            news.setUserName(currentUser.getName());
            news.setFlag();

            Toast.makeText(getApplicationContext(), "アンケートに回答しました", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
