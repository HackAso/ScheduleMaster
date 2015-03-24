package hackathon.aso.schedulemaster.object;

import android.util.Log;

import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.NCMBQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.operation.Operation;

/**
 * Created by shouhei on 15/03/02.
 */
public class Enquete implements Serializable {
    private String enqueteID = null;
    private int receiver = 1;
    private List<String> userNameList = new ArrayList<String>();
    private List<String> userEmailList = new ArrayList<String>();
    private String title = null;
    private String managerID = null;
    private String managerNickName = null;
    private String groupName = null;
    private String date = null;
    private String time = null;
    private String place = null;
    private String comment = null;
    private String when1 = null;
    private String when2 = null;
    private String when3 = null;
    private String when4 = null;
    private String when5 = null;
    private String kind = null;
    private String usrAdd = null;
    private String usrName = null;
    private String what = null;


    private int yesNo = 0;

    //回答を取得
    public static List<Enquete> getAnswer(String enqueteId, String kind) throws NCMBException {
        //回答を保存する箱、名前と答えを入れて返す。
        List<Enquete> ansList = new ArrayList<Enquete>();
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("answer");
        List<NCMBObject> ansNCMBList = new ArrayList<NCMBObject>();
        if (kind == "なに") {
            query.whereEqualTo("enqueteID", enqueteId);
            ansNCMBList = query.find();
            for (int i = 0; i < ansNCMBList.size(); i++) {
                Enquete ans = new Enquete();
                ans.setUsrName(ansNCMBList.get(i).getString("nickName"));
                ans.setWhat(ansNCMBList.get(i).getString("what"));
                ansList.add(ans);
            }

        } else if (kind == "いつ1"||kind=="いつ2"||kind=="いつ3") {
            query.whereEqualTo("enqueteID", enqueteId);
            if (kind == "いつ1") {
                query.whereEqualTo("when1", "true");
            } else if (kind == "いつ2") {
                query.whereEqualTo("when2", "true");
            } else if (kind == "いつ3") {
                query.whereEqualTo("when3", "true");
            }
            ansNCMBList = query.find();
            Log.d("cis", "確認用");
                for (int i = 0; i < ansNCMBList.size(); i++) {
                    Enquete ans = new Enquete();
                    ans.setUsrName(ansNCMBList.get(i).getString("nickName"));
                    ansList.add(ans);
                }
                Log.i("内部", "ねむい" + ansNCMBList.size());

        } else {
            query.whereEqualTo("enqueteID", enqueteId);
            ansNCMBList = query.find();
            for (int i = 0; i < ansNCMBList.size(); i++) {
                Enquete ans = new Enquete();
                ans.setUsrName(ansNCMBList.get(i).getString("nickName"));
                ans.setYesNo(ansNCMBList.get(i).getInt("yesNo"));
                ansList.add(ans);

            }
        }
        return ansList;

    }




    //enqueteから自分の名前を探してenqueteIDを取得。その後にanswerからenqueteIDで検索をする
//    public List<Enquete> getAnswer(){
//        List<Enquete> ans = new ArrayList<Enquete>();
//        Enquete enq = new Enquete();
//        List<String> id = new ArrayList<String>();
//        List<NCMBObject> list2 = new ArrayList<NCMBObject>();
//        NCMBQuery<NCMBObject> enquete = NCMBQuery.getQuery("enquete");
//        List<NCMBObject> list = new ArrayList<NCMBObject>();
//        enquete.whereEqualTo("managerName",managerID);
//        try{
//            list = enquete.find();
//            for (int i = 0;i<list.size();i++){
//                id.add(list.get(0).getObjectId());//id配列に自分が幹事を担当したアンケートが入ってくる
//            }
//            for (int i = 0;i<id.size();i++){
//                NCMBQuery<NCMBObject> search = NCMBQuery.getQuery("answer");
//                search.whereEqualTo("enqueteID",id.get(0));
//                list2 =search.find();
//                if (list2.get(i).getString("enqueteKind").equals("なに")){
//                    if (list2.get(i).getString("what")!=null)enq.setWhat(list2.get(i).getString("what"));
//                    ans.add(enq);
//                }else if (list2.get(i).getString("enqueteKind").equals("いつ")){
//                    if (list2.get(i).getString("when1")!=null)enq.setWhen1(list2.get(i).getString("when1"));
//                    if (list2.get(i).getString("when2")!=null)enq.setWhen2(list2.get(i).getString("when2"));
//                    if (list2.get(i).getString("when3")!=null)enq.setWhen3(list2.get(i).getString("when3"));
//                    ans.add(enq);
//                }else if (list2.get(i).getString("enqueteKind").equals("だれ")){
//                    enq.setYesNo(list2.get(i).getInt("yesNo"));
//                    ans.add(enq);
//                }
//
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return ans;
//    }

    public void setEnqueteID(String enqueteID) {
        this.enqueteID = enqueteID;
    }

    //アンケート登録
    public void setEnquete() {
        //テーブルenqueteにデータを登録
        NCMBObject enquete = new NCMBObject("enquete");
        if (managerID != null) enquete.put("managerName", managerID);
        if (kind != null) enquete.put("enqueteKind", kind);
        if (date != null) enquete.put("date", date);
        if (title != null) enquete.put("title", title);
        if (comment != null) enquete.put("comment", comment);

        if (when1 != null) {
            enquete.put("when1", when1);
        }
        if (when2 != null) {
            enquete.put("when2", when2);
        }
        if (when3 != null) {
            enquete.put("when3", when3);
        }
        try {
            enquete.save();

        } catch (NCMBException e) {
            e.printStackTrace();
        }
        //ユーザがアンケートを検索するためにnewsでenqueteIDとaddressをひもづける

        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("enquete");
        List<NCMBObject> list = new ArrayList<NCMBObject>();
        try {
            list = query.find();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userNameList != null) {

            //ユーザがいるだけ登録
            Log.i("ユーザ数", userNameList.size() + "");
            for (int i = 0; i < userNameList.size(); i++) {
                NCMBObject news = new NCMBObject("news");//newsに追加
                if (list != null) {
                    news.put("enqueteID", list.get(list.size() - 1).getObjectId());
                }
                news.put("userName", userNameList.get(i));
                news.put("managerName", managerID);
                news.put("managerNickName", managerNickName);
                news.put("date", date);
                news.put("title", title);
                news.put("enqueteKind", kind);
                news.put("flag", "0");
                try {
                    news.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            Log.i("内部", "PUSHさくせいするよ" + title + receiver + userEmailList.get(0));
            Operation operation = new Operation();
            operation.setTitle("アンケート");    ///push通知のタイトルをセット
            operation.setMessage(title);       //push通知のメッセージにアンケートのタイトルをセット
            operation.setReceiver(receiver);
            operation.setSendTo(userEmailList);
            try {
                operation.sendPush();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("状態", "PUSH通知処理で例外発生");
            }


        }
    }


    public static Enquete getEnqueteByTitleAndComment(String title, String comment) {
        Enquete enquete = new Enquete();
        NCMBQuery query = NCMBQuery.getQuery("enquete");
        query.whereEqualTo("title", title);
        query.whereEqualTo("comment", comment);
        try {
            List<NCMBObject> list = query.find();
            enquete.setEnqueteID(list.get(0).getObjectId());
            enquete.setKind(list.get(0).getString("enqueteKind"));
        } catch (NCMBException e) {
            e.printStackTrace();
        }
        return enquete;
    }

    //アンケート回答登録
    public void setAnswer() {
        NCMBObject schedule = new NCMBObject("answer");
        schedule.put("enqueteID", enqueteID);
        schedule.put("userName", usrAdd);
        schedule.put("nickName", usrName);
        schedule.put("enquetKind", kind);
        if (kind.equals("なに")) {
            schedule.put("what", what);
        } else if ((kind.equals("いつ"))) {
            schedule.put("when1", when1);
            schedule.put("when2", when2);
            schedule.put("when3", when3);
        } else if (kind.equals("だれ")) {
            schedule.put("yesNo", yesNo);
        }
        try {
            schedule.save();
        } catch (NCMBException e) {
            e.printStackTrace();
        }
    }

    //アンケート取得
    public static Enquete getEnquete( String enqueteID, String kind) {

        Enquete contents = new Enquete();
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("enquete");
        query.whereEqualTo("objectId", enqueteID);
        List<NCMBObject> list = new ArrayList<NCMBObject>();
        try {
            list = query.find();
            contents.setTitle(list.get(0).getString("title"));//タイトル
            contents.setComment(list.get(0).getString("comment"));//作成者コメント
            if (kind.equals("なに")) {
//                contents.setDate(list.get(0).getString("date"));//回答期限
            } else if (kind.equals("いつ")) {
                if (list.get(0).getString("when1") != null)
                    contents.setWhen1(list.get(0).getString("when1"));
                if (list.get(0).getString("when2") != null)
                    contents.setWhen2(list.get(0).getString("when2"));
                if (list.get(0).getString("when3") != null)
                    contents.setWhen3(list.get(0).getString("when3"));
                contents.setTitle(list.get(0).getString("title"));
                contents.setComment(list.get(0).getString("comment"));
            } else if (kind.equals("だれ")) {

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("だめ", "誰アンケート取得エラー");
        }
        return contents;

    }


    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getUsrAdd() {
        return usrAdd;
    }

    public void setUsrAdd(String usrAdd) {
        this.usrAdd = usrAdd;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public int getYesNo() {
        return yesNo;
    }

    public void setYesNo(int yesNo) {
        this.yesNo = yesNo;
    }

    public String getWhen1() {
        return when1;
    }

    public void setWhen1(String when1) {
        this.when1 = when1;
    }

    public String getWhen2() {
        return when2;
    }

    public void setWhen2(String when2) {
        this.when2 = when2;
    }

    public String getWhen3() {
        return when3;
    }

    public void setWhen3(String when3) {
        this.when3 = when3;
    }

    public String getWhen4() {
        return when4;
    }

    public void setWhen4(String when4) {
        this.when4 = when4;
    }

    public String getWhen5() {
        return when5;
    }

    public void setWhen5(String when5) {
        this.when5 = when5;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getManagerNickName() {
        return managerNickName;
    }

    public void setManagerNickName(String managerNickName) {
        this.managerNickName = managerNickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userName) {
        this.userNameList = userName;
    }

    public List<String> getUserEmailList() {
        return userEmailList;
    }

    public void setUserEmailList(List<String> userEmailList) {
        this.userEmailList = userEmailList;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEnqueteID() {
        return enqueteID;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }


}
