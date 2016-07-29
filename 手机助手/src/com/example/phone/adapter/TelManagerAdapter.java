package com.example.phone.adapter;

import java.util.ArrayList;

import com.example.phone.R;
import com.example.phone.entity.TelManagerEntity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TelManagerAdapter extends BaseAdapter {
   ArrayList<TelManagerEntity> list=new ArrayList<TelManagerEntity>();
LayoutInflater layoutInflater;
Context context;


   public TelManagerAdapter(ArrayList<TelManagerEntity> list,
		Context context) {
	super();
	this.list = list;
	this.layoutInflater = layoutInflater.from(context) ;
}

@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	    /**
	     * 将Item填充到listView
	     */
	
		  convertView=layoutInflater.inflate(R.layout.item_telmanage, null);
		  TextView name=(TextView) convertView.findViewById(R.id.telmanager_item_name);
	      TextView phone=(TextView) convertView.findViewById(R.id.telmanager_item_phone);
		  
		/**
		 * 填充数据
		 */
	      name.setText(list.get(position).getName());
	      phone.setText(list.get(position).getPhone());
	          
		
		
		return convertView;
	}

}
