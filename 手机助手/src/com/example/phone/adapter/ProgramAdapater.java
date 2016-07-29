package com.example.phone.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.phone.R;
import com.example.phone.entity.ProgramEntity;
import com.example.phone.utils.CommonUtil;

public class ProgramAdapater extends MyBaseAdapter<ProgramEntity> {

	public ProgramAdapater(ArrayList<ProgramEntity> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View mygetView(final int position, View convertView, ViewGroup parent) {
		   convertView=inflater.inflate(R.layout.item_program, null);
	        CheckBox cb=(CheckBox) convertView.findViewById(R.id.item_progarm_checkBox1);
	        TextView tv1=(TextView) convertView.findViewById(R.id.item_program_info1);
	        TextView tv2=(TextView) convertView.findViewById(R.id.item_program_info2);
	        ImageView im=(ImageView)convertView.findViewById(R.id.item_progaram_im);
	        im.setImageDrawable(list.get(position).getImg());
	        tv1.setText(list.get(position).getInfo());
	        tv2.setText(list.get(position).getInfo2());

	        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	             list.get(position).setIscheck(isChecked);				
					
				}
			});
	        cb.setChecked(list.get(position).isIscheck());
	        
	        
			return convertView;	}

}
