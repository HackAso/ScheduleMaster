package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hackathon.aso.schedulemaster.R;

public class ManagementActivity extends AbstractActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        ImageView enque = (ImageView) findViewById(R.id.imageViewEnque);
        ImageView reslist = (ImageView) findViewById(R.id.imageViewReslist);
        ImageView newplan = (ImageView) findViewById(R.id.imageViewNewplan);
        ImageView deletelist = (ImageView) findViewById(R.id.imageViewDeletelist);
        enque.setOnClickListener(this);
        reslist.setOnClickListener(this);
        newplan.setOnClickListener(this);
        deletelist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.imageViewEnque:
                intent = new Intent(ManagementActivity.this, EnquetActivity.class);
                break;
            case R.id.imageViewReslist:
                intent = new Intent(ManagementActivity.this, CheckEnqueteActivity.class);
                break;
            case R.id.imageViewNewplan:
                intent = new Intent(ManagementActivity.this, NewplanActivity.class);
                break;
            case R.id.imageViewDeletelist:
                intent = new Intent(getApplicationContext(),DeletePlanActivity.class);
                break;
        }
        startActivity(intent);
    }


}
