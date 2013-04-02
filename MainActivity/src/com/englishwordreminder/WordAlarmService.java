package com.englishwordreminder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class WordAlarmService  extends Service{

	 	private static int MOOD_NOTIFICATIONS = R.layout.activity_settings;
	    private PendingIntent contentIntent ;
		private NotificationManager mNM;
		private SharedPreferences sharedPrefs;

@Override
public void onCreate() {

// TODO Auto-generated method stub
	
sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
final func Func = new func(getApplicationContext());
Func.selectSettings();


}


@Override
public IBinder onBind(Intent intent) {
// TODO Auto-generated method stub
return null;
}


@Override
public void onDestroy() {

// TODO Auto-generated method stub

super.onDestroy();

}



@Override
public void onStart(Intent intent, int startId) {

// TODO Auto-generated method stub

super.onStart(intent, startId);

//Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
showNotification(R.drawable.icon_words, 0x7f040000);
}



@Override
public boolean onUnbind(Intent intent) {

// TODO Auto-generated method stub

return super.onUnbind(intent);

}

private void showNotification(int moodId, int textId) {
    
	func Func = new func(getApplicationContext());
	Func.selectNotifyWord("Words");
    
	CharSequence title = constant.NOTIFYWORD.get(0)  ; //getText(textId);
    CharSequence text = constant.NOTIFYWORD.get(1) ;
    
    Notification notification = new Notification(moodId, text, System.currentTimeMillis());

    // The PendingIntent to launch our activity if the user selects this notification
    contentIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, SettingsActivity.class), 0);

    // Buraya ingilizce Kelime gelecek.
    notification.setLatestEventInfo(this, title, text, contentIntent);
    
    if (sharedPrefs.getBoolean("prefSound",false)) {
    	 notification.defaults |= Notification.DEFAULT_SOUND;	
	}
    
    if (sharedPrefs.getBoolean("prefVibrate",false)) {
    	  notification.defaults |= Notification.DEFAULT_VIBRATE;
	}
  
    mNM.notify(MOOD_NOTIFICATIONS, notification);
}


}