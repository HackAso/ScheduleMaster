package hackathon.aso.schedulemaster;

import android.util.Log;

import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aki on 15/02/09.
 */
public class News {
    private String title;
    private String managerName;
    private String date;
    private String flg;
    private String enqueteID;
    private String enqueteKind;




    //news用のDB取得
    public static List<News> getMyNews(String userName){
        List<News> myList = new ArrayList<News>();
        NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("news");
        query.whereEqualTo("userName",userName);
        List<NCMBObject> list =null;
        try{
            list= query.find();

            for (int i=0;i<list.size();i++){
                News searchNews = new News();
                if(list.get(i).getString("flag").equals("0")){
                    searchNews.setEnqueteID(list.get(i).getString("enqueteID"));
                    searchNews.setEnqueteKind(list.get(i).getString("enqueteKind"));
                    searchNews.setManagerName(list.get(i).getString("managerName"));
                    searchNews.setDate(list.get(i).getString("date"));
                    searchNews.setTitle(list.get(i).getString("title"));
                    myList.add(searchNews);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.w("失敗", "ニュースとれない");
        }
        return myList;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public String getEnqueteID() {
        return enqueteID;
    }

    public void setEnqueteID(String enqueteID) {
        this.enqueteID = enqueteID;
    }

    public String getEnqueteKind() {
        return enqueteKind;
    }

    public void setEnqueteKind(String enqueteKind) {
        this.enqueteKind = enqueteKind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}