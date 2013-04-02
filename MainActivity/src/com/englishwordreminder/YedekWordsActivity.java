package com.englishwordreminder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.englishwordreminder.R;

import android.R.color;
import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.TabHost.TabSpec;

public class YedekWordsActivity extends TabActivity {

	private SQLiteDatabase db;
	private EditText txtSearch;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_words);
		
	    // Get the message from the intent
	  //  Intent intent = getIntent();
	   // String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
		try {
			db = myDbHelper.getReadableDatabase();
	    	Cursor c = db.rawQuery("SELECT * FROM Words", null);
	        //  " where Age > 10 LIMIT 5", null);
		
				if (c != null ) {    				
					if  (c.moveToFirst()) {
						do {						
						constant.ID.add(c.getString(c.getColumnIndex("wordsID")));
						constant.ENGLISHWORDS.add(c.getString(c.getColumnIndex("EnglishWords")));
						constant.WORDSREAD.add(c.getString(c.getColumnIndex("ReadWords")));
						constant.WORDDESC.add(c.getString(c.getColumnIndex("DescWords")));
					
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
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<constant.ENGLISHWORDS.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            String tmp = englishWord[i]  + "  ( "+ wordRead[i] + " ) ";
            hm.put("txt",  tmp );            
            hm.put("flag", wordDesc[i]) ;
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "txt","flag"};

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt};

        
        
        
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
//        TabHost tabHost = getTabHost();
//        
//        LayoutInflater.from(this).inflate(R.layout.activity_words, tabHost.getTabContentView(), true);
//
//        tabHost.addTab(tabHost.newTabSpec("tab1")
//                .setIndicator("tab1")
//                .setContent(R.id.view1));
//        tabHost.addTab(tabHost.newTabSpec("tab2")
//                .setIndicator("tab2")
//                .setContent(R.id.view2));    
        
        //SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.gridview_layout, from, to);        
        
        // Getting a reference to gridview of MainActivity
   //     GridView gridView = (GridView) findViewById(R.id.gridView1);
        
        // Setting an adapter containing images to the gridview
    //    gridView.setAdapter(adapter);
			
			
    //    addKeyListener();    
		
		   
		
	}

	
//	 public void addKeyListener() {
//		 
//			// get edittext component
//			txtSearch = (EditText) findViewById(R.id.txtSearch);
//		 
//			txtSearch.addTextChangedListener(new  TextWatcher() { 
//                public  void  afterTextChanged(Editable s) { 
//                } 
//                public  void  beforeTextChanged(CharSequence s, int  start, int  count, int  after) { 
//                } 
//                public  void  onTextChanged(CharSequence s, int  start, int  before, int  count) { 
//                    
//                	Toast.makeText(WordsActivity.this, s , Toast.LENGTH_SHORT).show();     
//                    	
//                    
//                } 
//            }); 
//		}
	
}
