package hackathon.aso.schedulemaster.operation;

import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBInstallation;
import com.nifty.cloud.mb.NCMBPush;
import com.nifty.cloud.mb.NCMBQuery;
import com.nifty.cloud.mb.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Aki on 15/02/24.
 */

public class Operation {
    private String title;
    private String message;
    private String receiver;//HomeActivity = 1, ScheduleActivity = 2 MycustomAdapter = 3
    private List<String> sendTo;

    public void sendPush() throws JSONException {
        NCMBPush push = new NCMBPush();
//        JSONObject data = new JSONObject("{\"action\": \"com.google.android.c2dm.intent.RECEIVE\", \"title\": \"test title\", \"target\": [android]}");
        JSONObject data = new JSONObject("{\"action\": \""+receiver+"\", \"title\": \"" + title + "\", \"target\": [android]}");
        push.setData(data);
        push.setMessage(message);
        push.setImmediateDeliveryFlag(true);
        push.setDialog(true);
        //配信端末の絞り込み
        NCMBQuery<NCMBInstallation> query = NCMBQuery.getQuery("installation");
        query.whereContainedInArray("email",sendTo);
        push.setSearchCondition(query);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    // エラー処理
                } else {
                    // プッシュ通知登録後の操作
                }
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public List<String> getSendTo() {
        return sendTo;
    }

    public void setSendTo(List<String> sendTo) {
        this.sendTo = sendTo;
    }

    public void setReceiver(int receiver) {
        switch (receiver) {
            case 1:
                this.receiver = "hackathon.aso.schedulemaster.activity.HomeActivity";
                break;
            case 2:
                this.receiver = "hackathon.aso.schedulemaster.activity.ScheduleActivity";
                break;
            case 3:
                this.receiver = "hackathon.aso.schedulemaster.activity.MyCustomReceiver";
                break;
            case 4:
                this.receiver = "hackathon.aso.schedulemaster.activity.MyCustomReceiver2";
                break;

        }
    }
}