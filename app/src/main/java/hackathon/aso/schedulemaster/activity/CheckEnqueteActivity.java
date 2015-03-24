package hackathon.aso.schedulemaster.activity;
/*  回答確認をするアクティビティ
*
*
* */


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.elements.EnqueteCustomAdapter;
import hackathon.aso.schedulemaster.object.Enquete;

public class CheckEnqueteActivity extends AbstractActivity {
    EnqueteCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_enquete);
        List<Enquete> enqueteList = currentUser.getAnsEnqueteList();
        ListView enqueteListview = (ListView) findViewById(R.id.lv_enquetelist_checkenquete);
        adapter = new EnqueteCustomAdapter(getApplicationContext(), 0, (ArrayList) enqueteList);
        enqueteListview.setAdapter(adapter);
        enqueteListview.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Enquete enquete = (Enquete) parent.getItemAtPosition(position);
                String enqueteID = enquete.getEnqueteID();
                Intent intent = null;
                if (enquete.getKind().equals("なに")) {
                    intent = new Intent(getApplicationContext(), WhatAnsListActivity.class);
                }else if (enquete.getKind().equals("いつ")) {
                    intent = new Intent(getApplicationContext(), WhenAnsListActivity.class);
                }else if (enquete.getKind().equals("だれ")) {
                    intent = new Intent(getApplicationContext(), WhoAnsListActivity.class);
                }

                intent.putExtra("id",enqueteID);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }


}


