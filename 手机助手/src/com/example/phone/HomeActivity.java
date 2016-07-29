package com.example.phone;

import android.os.Bundle;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	Animation animation;
TextView tv;
 ImageView im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	    tv=(TextView) findViewById(R.id.shuxingdonghua);
		ValueAnimator valueAnimator=ValueAnimator.ofInt(0,100);
		valueAnimator.setDuration(3000);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int i=(Integer) animation.getAnimatedValue();
				tv.setText("正在加载中"+i+"%");
				 
			}
		});
		valueAnimator.start();
		
		
		im=(ImageView) findViewById(R.id.imageView1);
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.myalpha);
		im.startAnimation(animation);
		

	    
	    
	    
	    
	    
	    
	    animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
	                     Intent intent=new Intent(HomeActivity.this,LocalHomeActivity.class);		
			               startActivity(intent);
			               finish();

			               			}
		});
	    
	}



}
