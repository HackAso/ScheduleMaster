package hackathon.aso.schedulemaster.object;

import android.util.Log;

import com.nifty.cloud.mb.FindCallback;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.NCMBQuery;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.operation.Operation;

/**
 * Created by Aki on 15/02/09.
 */
public class Plan {
    private String planID = null;
    private String title = null;
    private String managerID = null;
    private String managerNickName = null;
    private String date = null;
    private String time = null;
    private String place = null;
    private String comment = null;
    private String groupName = null;
    private List<String> sendToEmail = null;
    private List<Object> when = null;

    public static List<Plan> getTodays() {
        List<Plan> schedule = new ArrayList<Plan>();

        return schedule;
    }

    public static List<Plan> getPlansList(String userId) throws Exception {
        List<Plan> plansList = getSchedules(userId);
        Log.i("内部", "プランリストのサイズは : " + plansList.size());
        List<NCMBObject> ncmbPlanList = null;
        NCMBQuery<NCMBObject> planQuery = NCMBQuery.getQuery("plan");
        for (int i = 0; i < plansList.size(); i++) {
            planQuery.whereEqualTo("objectId", plansList.get(i).planID);
            ncmbPlanList = planQuery.find();
            if (ncmbPlanList.size() == 0) {
                Log.i("内部", "getPlansList()内でデータベース検索に失敗しました");
            }

            String title = ncmbPlanList.get(0).getString("title");
            String date = ncmbPlanList.get(0).getString("date");
            String time = ncmbPlanList.get(0).getString("time");
            String comment = ncmbPlanList.get(0).getString("comment");
            String managerID = ncmbPlanList.get(0).getString("managerID");
            String managerNickName = ncmbPlanList.get(0).getString("managerNickName");
//            String groupName = ncmbPlanList.get(0).getString("groupName");
            String place = ncmbPlanList.get(0).getString("place");
            if (title != null) {
                plansList.get(i).setTitle(title);
            }
            if (comment != null) {
                plansList.get(i).setComment(comment);
            }
            if (date != null) {
                plansList.get(i).setDate(date);
            }
            if (time != null) {
                plansList.get(i).setTime(time);
            }
            if (managerID != null) {
                plansList.get(i).setManagerID(managerID);
            }
            if (managerNickName != null) {
                plansList.get(i).setManagerNickName(managerNickName);
            }
//            if (groupName != null) {
//                plansList.get(i).setGroupName(groupName);
//            }
            if (place != null) {
                plansList.get(i).setPlace(place);
            }
        }

        return plansList;
    }

    //  ユーザーが参加予定のプランを取得 プランIDのみセットされたプラン一覧を返す
    public static List<Plan> getSchedules(String userID) throws NCMBException {
        List<Plan> schedules = new ArrayList<Plan>();
        List<NCMBObject> ncmbScheduleList = null;
        NCMBQuery<NCMBObject> schedule = NCMBQuery.getQuery("schedule");
        schedule.whereEqualTo("userID", userID);
        ncmbScheduleList = schedule.find();
        for (int i = 0; i < ncmbScheduleList.size(); i++) {
            Plan plan = new Plan();
            plan.setPlanID(ncmbScheduleList.get(i).getString("planID"));
            schedules.add(plan);
        }
        return schedules;
    }

    //  ユーザーが幹事として担当している予定のリストを取得
    public static List<Plan> getHostingList(String managerID) throws NCMBException {
        List<Plan> plansList = new ArrayList<Plan>();
        NCMBQuery<NCMBObject> planQuery = NCMBQuery.getQuery("plan");
        planQuery.whereEqualTo("managerID", managerID);
        List<NCMBObject> hostingList = planQuery.find();
        for (int i = 0; i < hostingList.size(); i++) {
            Plan plan = new Plan();
            plan.setTitle(hostingList.get(i).getString("title"));
            plan.setDate(hostingList.get(i).getString("date"));
            plan.setPlanID(hostingList.get(i).getObjectId());
            plansList.add(plan);
        }

        return plansList;
    }


    public void setPlan() {
        NCMBObject plan = new NCMBObject("plan");
        plan.put("title", title);
        plan.put("date", date);
        plan.put("managerID", managerID);
        if (comment != null) {
            plan.put("comment", comment);
        }
        if (place != null) {
            plan.put("place", place);
        }
        if (time != null) {
            plan.put("time", time);
        }
        try {
            plan.save();
        } catch (NCMBException e) {
            e.printStackTrace();
        }
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("plan");
        List<NCMBObject> sch = null;
        try {
            for (int i = 0; i < sendToEmail.size(); i++) {
                sch = query.find();
                NCMBObject schedule = new NCMBObject("schedule");
                schedule.put("planID", sch.get(sch.size() - 1).getObjectId());
                schedule.put("userID", sendToEmail.get(i));
                schedule.save();
            }
            Operation push = new Operation();
            push.setTitle("予定");    ///push通知のタイトルをセット
            push.setMessage(title);       //push通知のメッセージにアンケートのタイトルをセット
            push.setReceiver(3);
            push.setSendTo(sendToEmail);
            try {
                push.sendPush();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("状態", "PUSH通知処理で例外発生");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        plan.put();
//        plan.put();
//        plan.put();
    }

    public String getManagerNickName() {
        return managerNickName;
    }

    public void setManagerNickName(String managerNickName) {
        this.managerNickName = managerNickName;
    }

    public void setWhen(List<Object> when) {
        this.when = when;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getSendToEmail() {
        return sendToEmail;
    }

    public void setSendToEmail(List<String> sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    public void delete() {
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("plan");
        query.whereEqualTo("objectId", planID);
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> ncmbObjects, NCMBException e) {
                if (e == null) {
                    Log.i("内部", ncmbObjects.get(0).getString("title") + "を削除しますう");
                    ncmbObjects.get(0).deleteInBackground();
                }
            }
        });

        NCMBQuery<NCMBObject> query2 = NCMBQuery.getQuery("schedule");
        query2.whereEqualTo("planID", planID);
        query2.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> ncmbObjects, NCMBException e) {
                if (e == null) {
                    Log.i("内部", ncmbObjects.size() + "件をDB\"schedule\"から削除しますう");
                    for (int i = 0; i < ncmbObjects.size(); i++) {
                        ncmbObjects.get(i).deleteInBackground();
                    }
                }
            }
        });


    }
}