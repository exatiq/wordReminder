package com.englishwordreminder;

import java.io.IOException;
import com.englishwordreminder.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "Extra mesaj";
	private SQLiteDatabase db;	
	private PendingIntent pendingIntent;
	private Integer selectedTime ;
	private Integer notifyTime ;
	private SharedPreferences sharedPrefs;
	public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
	  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        pendingIntent = PendingIntent.getService(MainActivity.this,
                0, new Intent(MainActivity.this, WordAlarmService.class), 0);
        
        setContentView(R.layout.activity_main);
      
        
        //**************db iþlemleri from assets***************
        
        DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
		 myDbHelper = new DataBaseHelper(getApplicationContext());
		 
	        try {
	        	myDbHelper.createDataBase();
	        	} catch (IOException ioe) {
	        		throw new Error("database yaratýlamadý..");
	        	}
	 	try {
	 		myDbHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
	 	 	db = myDbHelper.getdb();
        //*************db iþlemleri from assets****************
        
   	 	 	
	 	 	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Called when the user clicks the Kelimeler button */
    public void wordsActivity(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, WordActivity.class);
       	startActivity(intent);
    	
    }
    
    /** Called when the user clicks the Bugün button */
    public void dailyActivity(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, DailyActivity.class);
    	startActivity(intent);
    }
    
    
    /** Called when the user clicks the Ayarlar button */
    public void settingsActivity(View view) {
        // Do something in response to button
     	Intent intent = new Intent(this, UserSettingActivity.class);
    	startActivity(intent);
    }
    
    /** Called when the user clicks the Hakkýnda button */
    public void infoActivity(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, InfoActivity.class);
    	startActivity(intent);
    	
    }
    
    public void StartListener(View view) {
        // Do something in response to button
    	
    	selectedTime = Integer.parseInt(sharedPrefs.getString("prefTime","1"));
    	notifyTime = func.NotifyTime(selectedTime);
        	
        // We want the alarm to go off 30 seconds from now.
        long firstTime = SystemClock.elapsedRealtime();

        // Schedule the alarm!
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        firstTime, notifyTime.longValue(), pendingIntent);
        
        showToast("Servis Baþladý.");
    	
    }
    
    
    public void StopListener(View view) {
        // Do something in response to button
    	 AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
         am.cancel(pendingIntent);
    	showToast("Servis Durduruldu.");
    }

    
}
