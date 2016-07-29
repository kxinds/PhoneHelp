package com.example.phone.view;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class AutoProgressBar extends ProgressBar {

	public AutoProgressBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AutoProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public AutoProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 *�ݼ� 
	 */
	public void AutoDwonProgress(int max,final int end){
		setProgress(max);
		final Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				 setProgress(getProgress()-1);
				 if(getProgress()<=end){
					 timer.cancel();
					 setProgress(end);
				 }
				 postInvalidate();
			}
		};
		
		timer.schedule(task, 40,40);
		
		
		
	}
	
	/*
	 * ����
	 */
public void AutoAddprogress(final int end){
	setProgress(0);
	final Timer time=new Timer();
        TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setProgress(getProgress()+1);
				if(getProgress()>=end){
					//ȡ����ʱ��
					 time.cancel();
					 setProgress(end);
				}
				//����UI
				postInvalidate();
				
				
			}
		}; 
		//��һ��40λ��һ�������ӳ�ʱ��  �ڶ���Ϊÿ�����ӵļ��
		time.schedule(task, 40,40);
}

	
	
}
