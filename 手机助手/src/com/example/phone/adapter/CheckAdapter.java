package com.example.phone.adapter;

import java.util.ArrayList;

import com.example.phone.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.phone.entity.PhoneCheckEntity;

public class CheckAdapter extends MyBaseAdapter<PhoneCheckEntity> {

	public CheckAdapter(ArrayList<PhoneCheckEntity> list, Context context) {
		super(list, context);
     
	}
	@Override
	protected View mygetView(int position, View convertView, ViewGroup parent) {
		convertView=inflater.inflate(R.layout.item_check, null);
	   ImageView im=(ImageView) convertView.findViewById(R.id.item_check_im);
		TextView info1=(TextView)convertView.findViewById(R.id.item_check_info1);
		TextView info2=(TextView)convertView.findViewById(R.id.item_check_info2);
		info1.setText(list.get(position).getInfo());
		info2.setText(list.get(position).getInfo2());
		im.setImageDrawable(list.get(position).getImg());
		if(position%3==0){
          im.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape1));
          
		}else if (position%3==1) {
			 im.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape2));
	         
		}else if (position%3==2) {
			 im.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape3));

		} 
		
		
		
		
		
		return convertView;
	}

}
