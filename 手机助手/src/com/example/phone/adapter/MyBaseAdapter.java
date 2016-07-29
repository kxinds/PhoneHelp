package com.example.phone.adapter;
import java.util.ArrayList;

import com.example.phone.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	  protected ArrayList<T> list;
	  protected LayoutInflater inflater;
	  protected  Context context;
	public MyBaseAdapter(ArrayList<T> list,Context context) {
		super();
		this.list = list;
		this.context=context;
		this.inflater=LayoutInflater.from(context);
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
		return  mygetView( position,  convertView, parent);
	}
	
	protected abstract View mygetView(int position, View convertView, ViewGroup parent);
	
	
	

}
