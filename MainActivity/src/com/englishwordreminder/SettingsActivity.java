package com.englishwordreminder;

import java.util.Calendar;

import com.englishwordreminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.Spinner;


public class SettingsActivity extends Activity {

	public static TextView timerTV;
	private PendingIntent pendingIntent;
	private Integer selectedTime ;
	private Integer notifyTime ;
		
	public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		final func Func = new func(getApplicationContext());
		
        Button buttonStart = (Button) findViewById(R.id.notifyStart);
        buttonStart.setOnClickListener(mStartListener);
        Button buttonStop = (Button) findViewById(R.id.notifyStop);
        buttonStop.setOnClickListener(mStopListener);
       
        
        final CheckBox chkSes = (CheckBox) findViewById(R.id.chkSes);
        final CheckBox chkVib = (CheckBox) findViewById(R.id.chkVibrate);
        Spinner spinner = (Spinner) findViewById(R.id.spnTime);
		
        timerTV = (TextView)findViewById(R.id.timer);
        
        String[] uyariZamani={"Her Saat","3 Saat Arayla","6 Saat Arayla","12 Saat Arayla","Günde Bir kere"};
		ArrayAdapter<String> spinnerContent = new ArrayAdapter<String>
		       (this, android.R.layout.simple_spinner_item,uyariZamani);
		spinnerContent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerContent);
		
		
		Func.selectSettings();
		
		chkSes.setChecked(func.SettingsControl(constant.SETTINGS.get(0)));
		chkVib.setChecked(func.SettingsControl(constant.SETTINGS.get(1)));
		spinner.setSelection(constant.SETTINGS.get(3));
 
        chkSes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	 
            @Override
			public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                // TODO Auto-generated method stub
                if (chkSes.isChecked()) {
                	Func.updateSettings("Audio", 1);
                    //showToast(cc);
                } else {
                	Func.updateSettings("Audio", 0);
                	
                }
 
            }
        });
        
       
        chkVib.setOnCheckedChangeListener(new OnCheckedChangeListener() {
       	 
            @Override
			public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                // TODO Auto-generated method stub
                if (chkVib.isChecked()) {
                	Func.updateSettings("Vibrate", 1);
                } else {
                	Func.updateSettings("Vibrate", 0);
 
                }
 
            }
        }); 
	
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
		int pos, long id) {
		//showToast("Spinner:  " + pos + "    id :  " + id);
			
			Func.updateSettings("NotificationTime", pos);	
			
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		//	Func.showToast("Spinner unselected  ");
		
		}
		});
	
	
	}	
	
	
    private OnClickListener mStartListener = new OnClickListener() {
        @Override
		public void onClick(View arg0) {
        	showToast("Servis Baþladý.");
        	selectedTime = constant.SETTINGS.get(3);
        	notifyTime = func.NotifyTime(selectedTime);
        	Intent myIntent = new Intent(SettingsActivity.this, WordAlarmService.class);
  			pendingIntent = PendingIntent.getService(SettingsActivity.this, 0, myIntent, 0);
  			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
  			Calendar calendar = Calendar.getInstance();
  			calendar.setTimeInMillis(System.currentTimeMillis());
  			calendar.add(Calendar.SECOND, 10);
	        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5 * 1000, pendingIntent);
        	
        	
        	//startService(new Intent(SettingsActivity.this, 
            //        NotifyingService.class));
        }
    };

    private OnClickListener mStopListener = new OnClickListener() {
        @Override
		public void onClick(View arg0) {
        	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        	alarmManager.cancel(pendingIntent);
        	showToast("Servis Durduruldu.");
        	
        	//stopService(new Intent(SettingsActivity.this, 
            //        NotifyingService.class));
        }
    };

    
/*	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	            NavUtils.navigateUpFromSameTask(this);
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }*/

}
