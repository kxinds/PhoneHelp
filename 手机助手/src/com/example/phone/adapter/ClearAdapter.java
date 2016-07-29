package com.example.phone.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import com.example.phone.R;
import com.example.phone.entity.ClearEntity;
import com.example.phone.entity.TelManagerEntity;
import com.example.phone.utils.CommonUtil;

import android.content.Context;
import android.graphics.Point;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ClearAdapter extends BaseAdapter {
 
	   ArrayList<ClearEntity> list;
	   LayoutInflater layoutInflater;
	   Context context;


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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=layoutInflater.inflate(R.layout.item_clear, null);
		TextView  name=(TextView) convertView.findViewById(R.id.item_clear_name);
		TextView size=(TextView)convertView.findViewById(R.id.item_clear_size);
		ImageView  im=(ImageView)convertView.findViewById(R.id.item_clear_img);
		CheckBox checkBox=(CheckBox) convertView.findViewById(R.id.item_clear_checkbox);
		 name.setText(list.get(position).getName());
	     size.setText(CommonUtil.getFileSize(list.get(position).getSize()));	
	     im.setImageDrawable(list.get(position).getIcon());

		 checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 	
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              list.get(position).setIschecked(isChecked);
			}
		});
		 
		 checkBox.setChecked(list.get(position).isIschecked());
		return convertView;
	}

	public ClearAdapter(ArrayList<ClearEntity> list,
		Context context) {
		super();
		this.list = list;
		this.layoutInflater = layoutInflater.from(context);

	}
	

}
