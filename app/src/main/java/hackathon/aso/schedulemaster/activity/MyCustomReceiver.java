package hackathon.aso.schedulemaster.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import hackathon.aso.schedulemaster.R;

/**
 * Created by Aki on 15/03/13.
 */
public class MyCustomReceiver extends BroadcastReceiver {
    private static final String TAG = "MyCustomReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("状態", "カスタムレシーバーがプッシュを受け取ったよ");
        try {
            String action = intent.getAction();
            Log.i("状態", action);
            String title = intent.getExtras().getString("com.nifty.Title");
            Log.i("内部", title);
            String message = intent.getExtras().getString("com.nifty.Message");
            Log.i("内部", message);
            Intent intent1 = new Intent(context, MainActivity.class);
            PendingIntent pIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);
            //wearに表示する参加ボタンにセットするインテント,"flg" = 1
            Intent intent2 = new Intent(context, AnswerUsingWear.class);
            intent2.putExtra("flg", 1);
            intent2.putExtra("title", title);
            intent2.putExtra("comment", message);
            PendingIntent pIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
            //wearに表示する不参加ボタンにセットするインテント,"flg" = 0
            Intent intent3 = new Intent(context, AnswerUsingWear.class);
            intent3.putExtra("flg", 0);
            intent3.putExtra("title", title);
            intent3.putExtra("comment", message);
            PendingIntent pIntent3 = PendingIntent.getActivity(context, 0, intent3, 0);

            Notification n = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pIntent1)
                    .addAction(R.drawable.ic_home, "参加", pIntent2)   //…… 1
                    .addAction(R.drawable.ic_home, "不参加", pIntent3)   //…… 0
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(0, n);

        } catch (Exception e) {
            // エラー処理
            Log.i("状態", "カスタムレシーバーがエラーはいたよ");
        }
    }
}