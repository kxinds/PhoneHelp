package com.example.phone.view;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoText extends TextView {

	public AutoText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AutoText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public AutoText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	Handler handler=new Handler();
	public void autoAddProgress(final int end){
		setText(0+"%");
		final Timer timer=new Timer();
		TimerTask task=new TimerTask() {
          int temp=0;			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.post(new Runnable() {
					
					@Override
					public void run() {
					  setText((++temp)+"%");
					  if(temp>=end){
						  timer.cancel();
						  setText(end+"%");
					  }
					  postInvalidate();
					}
				});
				
			}
		};
		timer.schedule(task, 40,40);
	}
	   
	public void autoDownProgress(final int max,final int end) {
		setText(max+"%");
		final Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			int temp=max;
			@Override
			public void run() {
				 handler.post(new Runnable() {
					
					@Override
					public void run() {
					 setText(temp+"%");
						if(temp>=end){
							timer.cancel();
							setText(end+"%");
						}	
						postInvalidate();
					}
				});
			}
		};
		
		timer.schedule(task, 40,40);
		
	}

	
	
	
	
	
	
	
	
}
