package com.example.phone.view;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MysoftBall extends View{
	private Paint paintbg,paintBall;
	private RectF rectF;
	private Context context;

	public MysoftBall(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MysoftBall(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MysoftBall(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	 private void init(Context context){
		  this.context=context;
		 paintBall=new Paint();
		 paintbg=new Paint();
		 paintbg.setColor(Color.YELLOW);
		 paintBall.setColor(Color.GREEN);
		 //清除锯齿效果
		 paintBall.setAntiAlias(true);
		 paintbg.setAntiAlias(true);
	 }
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int h=MeasureSpec.getSize(heightMeasureSpec);
		int w=MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(w, h);
	    rectF=new RectF(0,0,w,h);
	}
	 private int zong=0;
	 private int free;
	
	 public void  setAutoArc(final int du) {
		final Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
              if (zong<360) {
				zong++;
				postInvalidate();
			}else {
				timer.cancel();
			}
				
			}
		};
		timer.schedule(task, 10,10);
		 Timer time2=new Timer();
		 TimerTask task2=new TimerTask() {
			
			@Override
			public void run() {
	                 if (free<du) {
						free++;
						postInvalidate();
					}			
			}
		};
		time2.schedule(task2, 20,20);
		
	}
	 @Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	       //总内存
		canvas.drawArc(rectF, -90, zong, true, paintbg);
		//使用过的内存
		canvas.drawArc(rectF, -90, free, true, paintBall);
		
	 }
	
	
	

}
