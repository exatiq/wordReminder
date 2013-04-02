
package com.englishwordreminder;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.view.LayoutInflater;
import android.view.View;

public class WordActivity extends TabActivity {
	
	ListView listViewTemel;
	ListView listViewIleri;
	ListAdapter listAdapterTemel;
	ListAdapter listAdapterIleri;
	
	AlertDialog.Builder alertDialogBuilder;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             
        ArrayList<String> aListTemel = new ArrayList<String>();
        ArrayList<String> aListIleri = new ArrayList<String>();
        
        
        
        final func Func = new func(getApplicationContext());
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_words, tabHost.getTabContentView(), true);
        listViewTemel = (ListView)findViewById(R.id.listView1);
        listViewIleri = (ListView)findViewById(R.id.listView2);
        
       
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Temel Kelimeler")
                .setContent(R.id.listView1));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Ileri Kelimeler")
                .setContent(R.id.listView2));
        
     //   tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#77aaff"));
     //   tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#6b99e5"));
        
        aListTemel = Func.getWords("Words");
        aListIleri = Func.getWords("Words2");
        
        listAdapterTemel = new ListAdapter(WordActivity.this,  aListTemel);
        listViewTemel.setSelector(R.drawable.list_selector);
        listViewTemel.setAdapter(listAdapterTemel);
        
        listAdapterIleri = new ListAdapter(WordActivity.this,  aListIleri);
        listViewIleri.setSelector(R.drawable.list_selector);
        listViewIleri.setAdapter(listAdapterIleri);
        
        listViewTemel.setOnItemLongClickListener(new OnItemLongClickListener() {
          	 public boolean onItemLongClick(AdapterView<?> av, View v, final int pos, long id) 
               { 
          		 Object listItem = listViewTemel.getItemAtPosition(pos);
                   Toast.makeText(WordActivity .this, "The number of the long clicked item is " + pos, Toast.LENGTH_LONG).show();
                  
                alertDialogBuilder = new AlertDialog.Builder(WordActivity.this);
           	 	alertDialogBuilder.setTitle("Delete item");
           	 	alertDialogBuilder.setMessage("Are you sure?");
           	 	alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
           			public void onClick(DialogInterface dialog,int id) {
           			//	aListTemel.remove(pos);
           	             listAdapterTemel.notifyDataSetChanged();
          				}
          			  });
          			alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
          				public void onClick(DialogInterface dialog,int id) {
          						dialog.cancel();
          				}
          			});
          	 
          			AlertDialog alertDialog = alertDialogBuilder.create();
          			alertDialog.show();
                  
   				return true; //false will also trigger OnItemClick!
               } 
          });
        
        
        
        
        
    }
}
