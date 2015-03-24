package hackathon.aso.schedulemaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nifty.cloud.mb.NCMBUser;

import hackathon.aso.schedulemaster.R;
import hackathon.aso.schedulemaster.object.User;

public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageButton registerButton = (ImageButton) findViewById(R.id.ib_submit_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //nameを取得
                EditText etUserName = (EditText) findViewById(R.id.et_name_register);
                String userName = etUserName.getText().toString();
                //e-mailを取得
                EditText etEmail = (EditText) findViewById(R.id.et_email_register);
                final String userEmail = etEmail.getText().toString();
                //passを取得
                EditText etPass = (EditText) findViewById(R.id.et_pass_register);
                String userPass = etPass.getText().toString();
                final NCMBUser ncmbUser = new NCMBUser();
                if (!(userName != null && userEmail != null && userPass != null)) {
                    Toast.makeText(getApplicationContext(), "全項目を入力して下さい", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    user.setEmail(userEmail);
                    user.setName(userName);
                    user.setPass(userPass);
                    try {
                        user.register(getApplicationContext());
                        AbstractActivity.currentUser = user;
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("状態", "新規会員登録に失敗しました");
                    }

                }
            }
        });
    }


}
