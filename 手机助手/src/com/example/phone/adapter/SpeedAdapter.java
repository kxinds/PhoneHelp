package com.example.phone.adapter;

import java.util.ArrayList;

import com.example.phone.R;
import com.example.phone.entity.SpeedEntity;
import com.example.phone.utils.CommonUtil;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeedAdapter extends MyBaseAdapter<SpeedEntity>{

	public SpeedAdapter(ArrayList<SpeedEntity> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected View mygetView(final int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.item_speed, null);
        CheckBox cb=(CheckBox) convertView.findViewById(R.id.item_speed_checkBox1);
        TextView tv1=(TextView) convertView.findViewById(R.id.item_speed_textView1);
        TextView tv2=(TextView) convertView.findViewById(R.id.item_speed_textView2);
        ImageView im=(ImageView)convertView.findViewById(R.id.item_speed_imageView1);
        im.setImageDrawable(list.get(position).getIcon());
        tv1.setText(list.get(position).getName());
        tv2.setText(CommonUtil.getFileSize(list.get(position).getSize()));

        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             list.get(position).setChecked(isChecked);				
				
			}
		});
        cb.setChecked(list.get(position).isChecked());
        
        
		return convertView;
	}

}
