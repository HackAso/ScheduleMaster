package hackathon.aso.schedulemaster.object;

import android.content.Context;
import android.util.Log;

import com.nifty.cloud.mb.FindCallback;
import com.nifty.cloud.mb.NCMBACL;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBInstallation;
import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.NCMBPush;
import com.nifty.cloud.mb.NCMBQuery;
import com.nifty.cloud.mb.NCMBUser;
import com.nifty.cloud.mb.RegistrationCallback;
import com.nifty.cloud.mb.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

import hackathon.aso.schedulemaster.activity.MainActivity;

/**
 * Created by Aki on 15/02/08.
 */
public class User {
    private static final String TAG = User.class.getSimpleName();
    private final User self = this;

    private String name;
    private String email;
    private String pass;

    //ユーザの検索
    public static User findUser(String email) throws Exception {
        User search = new User();
        NCMBQuery<NCMBUser> query = NCMBQuery.getQuery("user");

        query.whereEqualTo("userName", email);//今はニックネームで検索してる。アドレスに変えるかも。
        List<NCMBUser> ncmbSearchList = null;
        ncmbSearchList = query.find();
        search.setEmail(ncmbSearchList.get(0).getUsername());
        search.setName(ncmbSearchList.get(0).getString("nickName"));
        Log.i("検索されたアドレスは:", search.getEmail());
        return search;
    }

    //フレンド削除 引:削除するUserオブジェクト
    public void removeFriend(User user) {
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("friends");
        query.whereEqualTo("myEmail", email);
        query.whereEqualTo("friendEmail", user.getEmail());
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> ncmbFriend, NCMBException e) {
                if (e == null) {
                    ncmbFriend.get(0).deleteInBackground();
                }
            }
        });
    }

    //新着情報取得
    public List<News> getMyNews() throws Exception {
        List<News> myNews = News.getMyNews(name);
        return myNews;
    }

    public List<Plan> getPlanslist() throws Exception {
        List<Plan> plansList = Plan.getPlansList(email);
        return plansList;
    }

    public List<User> getFriendList() {
        List<User> friedList = new ArrayList<User>();
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("friends");
        query.whereEqualTo("myEmail", email);
        List<NCMBObject> ncmbFriendList = null;
        try {
            ncmbFriendList = query.find();
            Log.i("内部", ncmbFriendList.size() + "件のユーザーを取得しました");
            for (int i = 0; i < ncmbFriendList.size(); i++) {
                User friend = new User();
                friend.setName(ncmbFriendList.get(i).getString("friendName"));
                friend.setEmail(ncmbFriendList.get(i).getString("friendEmail"));
                friedList.add(friend);
            }
        } catch (NCMBException e) {
            e.printStackTrace();
            Log.i("内部えらー", "フレンドリストの取得に失敗しました");
        }

        return friedList;
    }

    //会員登録 引:コンテキスト 会員登録失敗で例外をスロー
    public void register(final Context context) throws Exception {
        final int[] errorFlg = {0};
        if (email == null || pass == null || name == null) {
            errorFlg[0] = 1;
        } else {
            NCMBUser ncmbUser = new NCMBUser();
            ncmbUser.setUsername(email);
            ncmbUser.setEmail(email);
            ncmbUser.setPassword(pass);
            ncmbUser.put("nickName", name);
            NCMBACL ncmbacl = new NCMBACL();
            ncmbacl.setPublicReadAccess(true);
            ncmbUser.setACL(ncmbacl);

            ncmbUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(NCMBException e) {
                    if (e == null) {
                        Log.i(TAG, "新規会員登録に成功");
                        //push配信端末の登録
                        if (install(context) == false) {
                            errorFlg[0] = 1;
                        }

                    } else {
                        Log.e(TAG, "新規登録に失敗: " + e.getLocalizedMessage());
                        errorFlg[0] = 1;
                    }
                }
            });
        }
        if (errorFlg[0] == 1) {
            throw new Exception();
        }
    }

    //PUSH配信端末に登録 引:コンテキスト
    public boolean install(Context context) {
        final boolean[] errorFlg = {true};
        final NCMBInstallation instllation = NCMBInstallation.getCurrentInstallation();
        instllation.put("email", email);
        instllation.getRegistrationIdInBackground("1051974565427", new RegistrationCallback() {
            @Override
            public void done(NCMBException e) {
                if (e == null) {
                    // 成功

                    try {
                        instllation.save();
                        Log.i(TAG, "配信端末登録に成功");
                    } catch (NCMBException e1) {
                        e1.printStackTrace();
                        Log.i(TAG, "配信端末登録に失敗");
                        errorFlg[0] = false;
                    }

                } else {
                    // エラー
                    Log.i(TAG, "配信端末登録に失敗");
                    errorFlg[0] = false;
                }
            }
        });
        NCMBPush.setDefaultPushCallback(context, MainActivity.class);
        return errorFlg[0];
    }

    //フレンドを追加 引:追加したいUserオブジェクト
    public void addfriend(User friend) throws NCMBException {
        NCMBObject friendNCMBObject = new NCMBObject("friends");
        friendNCMBObject.put("myEmail", email);
        friendNCMBObject.put("friendEmail", friend.getEmail());
        friendNCMBObject.put("friendName", friend.getName());
        friendNCMBObject.save();


    }

    //フレンドに追加可能か検証するメソッド 引:チェックしたいUserオブジェクト 戻:O=可能 1=不可能
    public int checkFriend(User friend) {
        int flg = 0;        //O:可能 1:不可能
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("friends");
        query.whereEqualTo("myEmail", email);
        query.whereEqualTo("friendEmail", friend.getEmail());
        try {
            if (query.find().size() != 0) {
                flg = 1;
            }
        } catch (NCMBException e) {
            e.printStackTrace();
        }
        return flg;
    }

    //enqueteから自分の名前を探してenqueteIDを取得。幹事が自分に対する回答を検索
    public List<Enquete> getAnsEnqueteList() {
        List<Enquete> whatList = new ArrayList<Enquete>();
//        Enquete enq = new Enquete();
//        List<String> id = new ArrayList<String>();
//        List<NCMBObject> list2 = new ArrayList<NCMBObject>();
        NCMBQuery<NCMBObject> enquete = NCMBQuery.getQuery("enquete");
        //自分が幹事を担当したアンケートを検索
        enquete.whereEqualTo("managerName", email);
        try {
            //自分が幹事を担当したアンケートのオブジェクトのリストを作成
            List<NCMBObject> list = enquete.find();
            for (int i = 0; i < list.size(); i++) {
                Enquete enq = new Enquete();
                enq.setEnqueteID(list.get(i).getObjectId());//id配列に自分が幹事を担当したアンケートが入ってくる
                enq.setTitle(list.get(i).getString("title"));
                enq.setDate(list.get(i).getString("date"));
                enq.setKind(list.get(i).getString("enqueteKind"));

                whatList.add(enq);
            }
//            for (int i = 0;i<id.size();i++){
//                NCMBQuery<NCMBObject> search = NCMBQuery.getQuery("answer");
//                search.whereEqualTo("enqueteID",id.get(0));
//                list2 =search.find();
//                if (list2.get(i).getString("enqueteKind").equals("どこ")){
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

//            }
//
//            if (kind.equals("どこ")){
//
//            }else if (kind.equals("いつ")){
//
//            }else if (kind.equals("だれ")){
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return whatList;
    }

    public List<Plan> getPlansList() throws Exception {
        List<Plan> schedules = Plan.getPlansList(email);
        return schedules;
    }
    public List<Plan> getHostingList() throws Exception {
        List<Plan> plansList = Plan.getHostingList(email);
        return plansList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


}