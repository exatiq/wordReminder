package com.englishwordreminder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.impl.EnglishReasonPhraseCatalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class func {
	
	private SQLiteDatabase db;
	  
	public func(Context applicationContext) {
			// TODO Auto-generated constructor stub
			   DataBaseHelper myDbHelper = new DataBaseHelper(applicationContext);
		     	db = myDbHelper.getWritableDatabase();
		}
	
	public  void  createItem(Integer audio, Integer vibrate , Integer led ,Integer notificationtime) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(constant.TABLE_SETTINGS_COL_AUDIO, audio);
        initialValues.put(constant.TABLE_SETTINGS_COL_VIBRATE, vibrate);
        initialValues.put(constant.TABLE_SETTINGS_COL_LED, led);
        initialValues.put(constant.TABLE_SETTINGS_COL_NOTIFICATIONTIME, notificationtime);
        
        db.insert(constant.TABLE_SETTINGS, null, initialValues);
    }

    public void  updateSettings(String columnName, Integer deger) {
        ContentValues args = new ContentValues();
        args.put(columnName, deger);
        db.update(constant.TABLE_SETTINGS, args, constant.TABLE_SETTINGS_COL_ID + "= 1" , null) ;
        
    }
	
    public ArrayList<Integer>  selectSettings () {
    	String query = "SELECT * FROM " +constant.TABLE_SETTINGS + " LIMIT 1 ";
    	Cursor cur = db.rawQuery(query, null);
    	constant.SETTINGS.clear();
    	
		if (cur != null ) {    				
			if  (cur.moveToFirst()) {
				do {
					constant.SETTINGS.add(cur.getInt(cur.getColumnIndex("Audio")));
					constant.SETTINGS.add(cur.getInt(cur.getColumnIndex("Vibrate")));
					constant.SETTINGS.add(cur.getInt(cur.getColumnIndex("Led")));
					constant.SETTINGS.add(cur.getInt(cur.getColumnIndex("NotificationTime")));
			}
				while (cur.moveToNext());
			} 
		}
    	
		
		return (ArrayList<Integer>) constant.SETTINGS ;
    	
    }
    
    public ArrayList<String> selectNotifyWord (String tableName) {
		
    	Integer randomNumber = randomNumber(tableName);
    	
    	String query = "SELECT * FROM " +constant.TABLE_WORDS + " where WordsID= "+ randomNumber;
    	Cursor cur = db.rawQuery(query, null);
    	constant.NOTIFYWORD.clear();
    	
    	if (cur != null ) {    				
			if  (cur.moveToFirst()) {
				do {
					constant.NOTIFYWORD.add(cur.getString(cur.getColumnIndex("EnglishWords")));
					constant.NOTIFYWORD.add(cur.getString(cur.getColumnIndex("DescWords")));
					constant.NOTIFYWORD.add(cur.getString(cur.getColumnIndex("ReadWords")));
			}
				while (cur.moveToNext());
			} 
		}
    	
    	return (ArrayList<String>) constant.NOTIFYWORD ;
    	
    	
    }
    
    public Integer tableCount (String tableName){
    	
    	String query = "SELECT * FROM " + tableName ;
    	Cursor cur = db.rawQuery(query, null);
    	return cur.getCount();
    }
    
    public Integer randomNumber (String tableName) {
    	int min = 1;
    	int max = tableCount(tableName);

    	Random r = new Random();
    	int result = r.nextInt(max - min + 1) + min;
    	return result ;
    }
    
    public static boolean SettingsControl (Integer Deger) {
    	
    	if (Deger > 0) {
    		return true;
    	} else {
    		return false;
		}
    	
    }
    
    public static Integer NotifyTime (Integer deger) {
    Integer result = -1;
    	
    	switch (deger) {
		case  1 :
			result = 60 * 1000;  // 1 saat 3600000
			break;
		case 2 :
			result = 10800 * 1000; // 3 saat
			break;
		case  3 :
			result = 21600 * 1000; // 6 saat
			break;
		case 4 :
			result = 43200 * 1000; // 12 saat
			break;	
		case 5 :
			result = 86400 * 1000; // 24 saat
			break;	
			
		}
    		
    	
    	return result;
    }
    
    public  ArrayList<String> getWords (String word)
    {
    	constant.ID.clear();
    	constant.ENGLISHWORDS.clear();
    	constant.WORDSREAD.clear();
    	constant.WORDDESC.clear();
    	
    	
    	try {
			Cursor c = db.rawQuery("SELECT * FROM " + word , null);
	        //  " where Age > 10 LIMIT 5", null);
		
				if (c != null ) {    				
					if  (c.moveToFirst()) {
						do {				
								if (c.getString(c.getColumnIndex("EnglishWords"))!= null)
								{
									constant.ID.add(c.getString(c.getColumnIndex("wordsID")));
									constant.ENGLISHWORDS.add(c.getString(c.getColumnIndex("EnglishWords")));
									constant.WORDSREAD.add(c.getString(c.getColumnIndex("ReadWords")));
									constant.WORDDESC.add(c.getString(c.getColumnIndex("DescWords")));
								}
						   }
						
						while (c.moveToNext());
					} 
				}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String[] englishWord = new String[constant.ENGLISHWORDS.size()];constant.ENGLISHWORDS.toArray(englishWord);
		String[] wordRead = new String[constant.WORDSREAD.size()];constant.WORDSREAD.toArray(wordRead);
		String[] wordDesc = new String[constant.WORDDESC.size()];constant.WORDDESC.toArray(wordDesc);
			
		// Each row in the list stores country name, currency and flag
		 ArrayList<String> aList = new  ArrayList<String>();
       
        
        for(int i=0;i<englishWord.length ;i++){
        	String tmp = englishWord[i]  + "  ( "+ wordRead[i] + " ) :" +  wordDesc[i];
            
            aList.add(tmp);
        }
    
       
        return aList;
    }
    
    

}
