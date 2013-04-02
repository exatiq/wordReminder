/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.englishwordreminder;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

import com.englishwordreminder.R;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.TextView;
import android.widget.Toast;



/**
 * This is an example of service that will update its status bar balloon 
 * every 5 seconds for a minute.
 * 
 */
public class NotifyingService extends Service {
	
    // Use a layout id for a unique identifier
    private static int MOOD_NOTIFICATIONS = R.layout.activity_settings;
    private Integer selectedTime ;
	private Integer notifyTime ;
	private SettingsActivity settingsActivity;
	private PendingIntent contentIntent ;
	
    // variable which controls the notification thread 
    private ConditionVariable mCondition;
    private boolean stopService = false;
    
    @Override
    public void onCreate() {
    	 mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	 Thread notifyingThread = new Thread(null, mTask, "NotifyingService");
    	 if (!notifyingThread.isAlive())
         {        

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.
        final func Func = new func(getApplicationContext());
        Func.selectSettings();
        
        selectedTime = constant.SETTINGS.get(3);
        notifyTime = func.NotifyTime(selectedTime);
        
       
     //   Thread timerThread = new Thread(null, timerTask, "NotifyingService");
        mCondition = new ConditionVariable(false);
       
        notifyingThread.start();
       // timerThread.start();
       
        	
        }
      
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(MOOD_NOTIFICATIONS);
        stopService = true;     
                
        // Stop the thread from generating further notifications
        // Tell the user we stopped.
        Toast.makeText(this, "Servis kapatýldý", Toast.LENGTH_SHORT).show();
       
       
    }

/*    private Runnable mTask = new Runnable() {
        @Override
		public void run() {
            for (int i = 0; i < 10; ++i) {
                showNotification(R.drawable.icon_words, 0x7f040000);
            	if (mCondition.block(notifyTime)){
            		incomingHandler.sendEmptyMessage(notifyTime -2000);
            	}
                   // break;
                
            }
            // Done with our work...  stop the service!
            NotifyingService.this.stopSelf();
        }
    };
    
    
        private Runnable timerTask = new Runnable() {
        @Override
		public void run() {
            for (int i = 0; i < 100000000; ++i) {
            	 notifyTime -= 1000;
            	 
                //showNotification(R.drawable.icon_words, 0x7f040000);
            	incomingHandler.sendEmptyMessage(notifyTime);
            	
                if (mCondition.block(1000)) 
                    break;
                
            }
            // Done with our work...  stop the service!
            NotifyingService.this.stopSelf();
        }
    };
    
    
    */
    
    Runnable mTask = new Runnable() {
        public void run() {
            // Normally we would do some work here...  for our sample, we will
            // just sleep for 30 seconds.
        	
            long endTime = System.currentTimeMillis() +notifyTime * 10;
            long Timer ;
            int	tmpTimer ;
            while (System.currentTimeMillis() < endTime) {
            	Timer = System.currentTimeMillis() + notifyTime;
            	tmpTimer = notifyTime;
                synchronized (mBinder) {
                    try {
                    	showNotification(R.drawable.icon_words, 0x7f040000);
                    	
                    	while (System.currentTimeMillis() < Timer && !stopService) {
                    	tmpTimer -= 1000 ;
                    		incomingHandler.sendEmptyMessage(tmpTimer);
                    		mBinder.wait(1000);	
                    	}
                    	
                    //    mBinder.wait(notifyTime);
                        if (stopService) {
                        	incomingHandler.sendEmptyMessage(0);
                        	break;
						}
                        
                    } catch (Exception e) {
                    }
                }
            }

            // Done with our work...  stop the service!
            NotifyingService.this.stopSelf();
        }
    };
    
    
    
    

  

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
        
        if (func.SettingsControl(constant.SETTINGS.get(0))) {
        	 notification.defaults |= Notification.DEFAULT_SOUND;	
		}
        
        if (func.SettingsControl(constant.SETTINGS.get(1))) {
        	  notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
       
      
        mNM.notify(MOOD_NOTIFICATIONS, notification);
    }

    
     
    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };
    
    private final Handler incomingHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
        	  //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
        	 
        	if (msg.what != 0)
        	{
        		 int seconds = (int) (msg.what / 1000);
                 int minutes = seconds / 60;
                 int hours = minutes / 60 ;
                 seconds = seconds % 60;
                 minutes = minutes % 60;
                 String tmpHours = Integer.toString(hours);
                 String tmpMinutes = Integer.toString(minutes);
                 if (hours == 0){
                	 tmpHours ="00";
                 }else if(hours < 10) {
                	 tmpHours = "0" + Integer.toString(hours);
                 }
                 
                 if (minutes < 10) {
                	 tmpMinutes = "0"+Integer.toString(minutes);
                 }
                 
                 String tmp = (tmpHours + ":" + tmpMinutes + ":" + String.format("%02d", seconds));
            	 settingsActivity.timerTV.setText(tmp);	
        	}else{
        		settingsActivity.timerTV.setText("00:00:00");	
        	}
          
        }
        
    };
    

    private NotificationManager mNM;
}
