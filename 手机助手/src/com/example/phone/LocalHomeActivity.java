package com.example.phone;

import java.util.List;

import com.example.phone.utils.MemoryManager;
import com.example.phone.view.Mymainbar;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LocalHomeActivity extends Activity {
    private TextView  phoneSpeed,phoneManage,phoneCheck,TelBook,phoneClean;
    public static boolean thread=false;
    private ActivityManager activityManager;
    private TextView  tv;
    private Mymainbar bll;
    private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_home);
		activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         tv=(TextView) findViewById(R.id.local_result);
        bll=(Mymainbar)findViewById(R.id.mymainbar1);
	     imageView=(ImageView)findViewById(R.id.localhome_bigButton);
	     imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			  getAutoRam();	
			}
		});
	     getAutoRam();
	}
	
	public  Boolean isback=true ;

 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
 		   if(keyCode==KeyEvent.KEYCODE_BACK){
                 if(isback){
	             Toast.makeText(getApplicationContext(), "3秒内再按一次退出可退出", 0).show();	  
	        	  isback=false;
	        	  new Thread(new Runnable() {
					
					@Override
					public void run() {
                      try {
						Thread.sleep(3000);
                   }catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						isback=true;

					}
				}).start();
	        	 return true;
	             }else {
				 finish();
			     }  
                 
	           }
		
		
		
		
		 return super.onKeyDown(keyCode, event);
	}
	public void localhomeclick(View view) {
		switch (view.getId()) {
		case R.id.phonespeed:
			Intent intent=new Intent(LocalHomeActivity.this,PhoneSpeedActivity.class);
			startActivity(intent); 
			break;
		case R.id.phonemanage:
			Intent intent1=new Intent(LocalHomeActivity.this,PhoneManageActivity.class);
			startActivity(intent1);
			break;
		case R.id.phonecheck:
			Intent intent2=new Intent(LocalHomeActivity.this,PhoneCheckActivity.class);
			startActivity(intent2);
			break;
		case R.id.phoneBook:
			Intent intent3=new Intent(LocalHomeActivity.this,TelBookActivity.class);
			startActivity(intent3);
			break;
		case R.id.phoneClean:
			Intent intent4=new Intent(LocalHomeActivity.this,PhoneCleanActivity.class);
			startActivity(intent4);			
			break;

		default:
			break;
		}
		
		
		
	}


	public void getAutoRam(){
		if(thread){
			return;
		}
		//杀死进程
		List<RunningAppProcessInfo> runnApplist=activityManager.getRunningAppProcesses();
		 //遍历所有进程
		for (RunningAppProcessInfo runningAppProcessInfo : runnApplist) {
			if(runningAppProcessInfo.importance>=RunningAppProcessInfo.IMPORTANCE_SERVICE){
				activityManager.killBackgroundProcesses(runningAppProcessInfo.processName); 
			}
			
		}
		//算法
		Long allRam=MemoryManager.getPhoneTotalRamMemory();
		Long fullRam=MemoryManager.getPhoneFreeRamMemory(this);
		final int proResult=(int)((double)fullRam/(double)allRam*100);
		final int viewResult=(int)((double)fullRam/(double)allRam*360);
           
		      runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					bll.myball(viewResult);
					tv.setText(proResult+"%");
				 	
				}
			});
		
		
		
		
	}
	
}
