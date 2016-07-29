package com.example.phone;

import java.util.ArrayList;

import javax.net.ssl.ManagerFactoryParameters;

import com.example.phone.adapter.CheckAdapter;
import com.example.phone.adapter.MyBaseAdapter;
import com.example.phone.entity.PhoneCheckEntity;
import com.example.phone.utils.CommonUtil;
import com.example.phone.utils.MemoryManager;
import com.example.phone.utils.MobileManager;
import com.example.phone.view.AutoProgressBar;
import com.example.phone.view.AutoText;

import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PhoneCheckActivity extends Activity {
  private ImageView phone_check_back;
  private ArrayList<PhoneCheckEntity>  list;
  private ListView listView;
  private MobileManager manager;
  private   AutoProgressBar autoProgressBar;
  private AutoText autoText;
  private  MyBetteryBroadcastReceiver bett;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_check);
		phone_check_back=(ImageView)findViewById(R.id.phone_check_back);
          phone_check_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                 finish();				
			}
		});
          manager=manager.getPhoneManager(this);
      autoProgressBar=(AutoProgressBar) findViewById(R.id.phoneCheck_bar);
      autoText=(AutoText)findViewById(R.id.phoneCheck_baifen); 
//       autoProgressBar.AutoAddprogress(80);
//      autoText.autoAddProgress(80);
       listView=(ListView) findViewById(R.id.phonecheck_listview);
       list=new ArrayList<PhoneCheckEntity>();
      getData();   
       CheckAdapter adapter=new CheckAdapter(list, this);
       listView.setAdapter(adapter);
       bett=new MyBetteryBroadcastReceiver();
       IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
       registerReceiver(bett, filter); 
       
	}
	 @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
	     unregisterReceiver(bett);
	 }
	 
	 class MyBetteryBroadcastReceiver extends BroadcastReceiver{
           private int dlNum=0;
        		@Override
		public void onReceive(Context context, Intent intent) {
          if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))			
          {
        	  //取出电池信息
        	  Bundle bundle=intent.getExtras();
        	  //当前电量
        	  int num=bundle.getInt(BatteryManager.EXTRA_LEVEL);
        	  //总电量
        	  int numALL=bundle.getInt(BatteryManager.EXTRA_SCALE);
        	  //只是更新一次
        	  if(num!=dlNum){
        		  dlNum=num;
        	  //设置动画
        		autoProgressBar.AutoAddprogress(num);
                autoText.autoAddProgress(num);      		
        	  
        	  }
        	  
          }
        		}
		 
	 }
  
	
	 public void getData(){
		 PhoneCheckEntity p=new PhoneCheckEntity();
		 p.setImg(getResources().getDrawable(R.drawable.setting_info_icon_camera));
		 p.setInfo("手机分辨率"+manager.getPhoneInfo().getResolution());
		 p.setInfo2("相机分辨率"+manager.getPhoneInfo().getMaxPhotoSize());
		 list.add(p);
		 
		 PhoneCheckEntity p1=new PhoneCheckEntity();
		 p1.setImg(getResources().getDrawable(R.drawable.setting_info_icon_version));
		 p1.setInfo("设备名称"+manager.getPhoneInfo().getPhoneName1());
		 p1.setInfo2("系统版本"+manager.getSystemInfo().getPhoneSystemVersion());
		 list.add(p1);
		 PhoneCheckEntity p2=new PhoneCheckEntity();
		 String allsize=CommonUtil.getFileSize(MemoryManager.getPhoneTotalRamMemory());
         String size=CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(this));
   
		 p2.setImg(getResources().getDrawable(R.drawable.setting_info_icon_space));
		 p2.setInfo("全部运行内存:  "+allsize);
		 p2.setInfo2("剩余运行内存:  "+size);
		 list.add(p2);
		 
		 PhoneCheckEntity p3=new PhoneCheckEntity();
		 p3.setImg(getResources().getDrawable(R.drawable.setting_info_icon_cpu));
		 p3.setInfo("Cpu数量：   "+manager.getPhoneInfo().getPhoneCpuNumber());
		 p3.setInfo2("cpu名称：   "+manager.getPhoneInfo().getPhoneCpuName());
		 list.add(p3);
		 
		 PhoneCheckEntity p4=new PhoneCheckEntity();
		 p4.setImg(getResources().getDrawable(R.drawable.setting_info_icon_root));
		 p4.setInfo("基带版本：    "+manager.getSystemInfo().getPhoneSystemBasebandVersion());
		 p4.setInfo2("是否root：   "+(manager.getSystemInfo().isRoot()?"是":"否"));
		 list.add(p4);
		 
		 
		 
	 }

}
