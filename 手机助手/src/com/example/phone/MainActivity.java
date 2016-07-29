package com.example.phone;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
   private ViewPager viewPager;
	List<View> list;
	ImageView imgV;
	String s;
	SharedPreferences sharedPreferences;
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
          viewPager=(ViewPager) findViewById(R.id.vierpager);
          list=new ArrayList<View>();
          list.add(getLayoutInflater().inflate(R.layout.a, null));
          list.add(getLayoutInflater().inflate(R.layout.b, null));
          list.add(getLayoutInflater().inflate(R.layout.c, null));
          list.add(getLayoutInflater().inflate(R.layout.c, null));
  
         sharedPreferences=getSharedPreferences("data", MODE_PRIVATE);   
         Editor editor = sharedPreferences.edit();
         
          if(sharedPreferences.getBoolean("a",true)){
        	  editor.putBoolean("a", false);
         	  editor.commit();
	       viewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
                       container.removeView(list.get(position));
			}
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				   View view=list.get(position);      
				container.addView(view);
				 
				return view;
			}
		});
          }else {
  			Intent  intent=new Intent(MainActivity.this,LocalHomeActivity.class);
  			startActivity(intent);
  			finish();
  			 
  		}
	       viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			    
			@Override
			public void onPageSelected(int arg0) {
             if(arg0==list.size()-1){
           	      
            	 
            	 Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            	 startActivity(intent);
            	 finish();//¹Ø±Õviewpager

             }				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	
        
          
          
          }

	

}
