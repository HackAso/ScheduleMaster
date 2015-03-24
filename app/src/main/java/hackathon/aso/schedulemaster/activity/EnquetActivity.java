package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hackathon.aso.schedulemaster.R;

/**
 * Created by shouhei on 15/02/15.
 */
public class EnquetActivity extends AbstractActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquet);
        ImageView what=(ImageView)findViewById(R.id.btn_what_enque);
        ImageView when=(ImageView)findViewById(R.id.btn_when_enque);
        ImageView who=(ImageView)findViewById(R.id.btn_who_enque);
        what.setOnClickListener(this);
        when.setOnClickListener(this);
        who.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch(view.getId()){
            case R.id.btn_what_enque:
                intent = new Intent(getApplicationContext(),WhatEnqueActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_when_enque:
                intent = new Intent(getApplicationContext(),WhenEnqueActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_who_enque:
                intent = new Intent(getApplicationContext(),WhoEnqueActivity.class);
                startActivity(intent);
                break;
        }
    }
}
