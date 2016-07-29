package com.example.phone.view;

import java.util.Timer;
import java.util.TimerTask;

import com.example.phone.LocalHomeActivity;
import com.example.phone.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Mymainbar  extends View{
    private Paint paint;//画笔
    private RectF rectF;//画布
    private int sweeAngle;
	
	public Mymainbar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();

	}

	public Mymainbar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();

	}

	public Mymainbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	private void startball(final int sw){
		sweeAngle=0;//开始角度
		final Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
            if (sweeAngle<sw) {
				sweeAngle++;
				postInvalidate();
			}else {
				timer.cancel();
                 LocalHomeActivity.thread=false;    
			}
				
			}
		};
		timer.schedule(task, 10,10);
	}
	/***
	 * 先退后进
	 */
	public void myball(final int sw){
		this.sweeAngle=sw;
		LocalHomeActivity.thread=true;
		final Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				if (sweeAngle>=0) {
					sweeAngle--;
					postInvalidate();
				}else {
					timer.cancel();
					startball(sw);
				}
			}
		};
		timer.schedule(task, 5,5);
		
		
		
	}
	
	void init() {
	  paint=new Paint(); //new一个对象
	  paint.setTextSize(10);//设置画笔大小
	  paint.setColor(Color.GREEN);//设置画笔颜色
	  paint.setAntiAlias(true);//消除锯齿
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int w=MeasureSpec.getSize(widthMeasureSpec);
	    int h=MeasureSpec.getSize(heightMeasureSpec);
	    setMeasuredDimension(w, h);
	    rectF=new RectF(0,0,w,h);//绘制矩形
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawArc(rectF, -90, sweeAngle, true, paint);
	}
	
	
	

}
