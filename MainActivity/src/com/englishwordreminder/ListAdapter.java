package com.englishwordreminder;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends BaseAdapter {

	public String title[];
	public String description[];
	ArrayList<String> arr_words = new ArrayList<String>();
	ArrayList<String> arr_wordsdesc = new ArrayList<String>();
	
	public Activity context;
	
	public LayoutInflater inflater;
	
	public ListAdapter(Activity context, ArrayList<String> arr_words) {
		super();

	
		this.context = context;
		this.arr_words = arr_words;
		
		
	    this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr_words.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder
	{
		
		TextView txtName1;
		TextView txtName2;
		Button btn;
		RelativeLayout row;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final ViewHolder holder;
		if(convertView==null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			
			
			holder.txtName1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.txtName2 = (TextView) convertView.findViewById(R.id.textView2);
			holder.btn = (Button) convertView.findViewById(R.id.button);
			holder.row = (RelativeLayout) convertView.findViewById(R.id.lineItem);
			convertView.setTag(holder);
		}
		else
			holder=(ViewHolder)convertView.getTag();
		
		String spltWord = arr_words.get(position);
		String[] separated = spltWord.split("\\:");
		
		holder.txtName1.setText(separated[0]);
		holder.txtName2.setText(separated[1]);
		
		holder.btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 	arr_words.remove(position);
			 	notifyDataSetChanged();
			// 	Toast.makeText(getApplicationContext(), "You have deleted row No. "+ position, Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.row.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 //	Toast.makeText(getApplicationContext(), "Your selected car is: "+ holder.txtName.getText(), Toast.LENGTH_SHORT).show();
			}
		});
		
		return convertView;
		
	
		
	}
}
