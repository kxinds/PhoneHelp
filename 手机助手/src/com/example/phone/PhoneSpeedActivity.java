package com.example.phone;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.commons.logging.Log;

import com.example.phone.adapter.SpeedAdapter;
import com.example.phone.entity.SpeedEntity;
import com.example.phone.utils.CommonUtil;
import com.example.phone.utils.MemoryManager;
import com.example.phone.utils.MobileManager;
import com.example.phone.view.AutoProgressBar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Debug.MemoryInfo;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhoneSpeedActivity extends Activity {
ImageView imageView;
private TextView tv1,tv2,tv3;
private Button bt1,bt2;
private ListView lv1;
private CheckBox cb;
private MobileManager manager;
private ArrayList<SpeedEntity> list;
private AutoProgressBar progressBar;
private   SpeedAdapter adapter;
private ActivityManager activityManager;//�����
private PackageManager packageManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_speed);
       // tv1=findViewById();
		
	imageView=(ImageView) findViewById(R.id.phoneSpeed_back);
	 imageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
             finish();
		}
	});
	  //

	  init();
	 
	 
	 
	 
	}

	private void init() {
		
            manager=MobileManager.getPhoneManager(this);
            tv1=(TextView)findViewById(R.id.speed_tv_phonename);
            tv1.setText("�ֻ�Ʒ��: "+manager.getPhoneInfo().getPhoneName1());
            tv2=(TextView)findViewById(R.id.speed_tv_banben);
            tv2.setText("�ֻ��汾: "+manager.getPhoneInfo().getPhoneCpuName());
            tv3=(TextView)findViewById(R.id.speed_tv_neicun);
            long sum=MemoryManager.getPhoneTotalRamMemory();
            long free=MemoryManager.getPhoneFreeRamMemory(this);
            long used=sum-free;
            tv3.setText(CommonUtil.getFileSize(used)+"/"+CommonUtil.getFileSize(sum));
            progressBar=(AutoProgressBar)findViewById(R.id.speed_bar);
            int i=(int)(((double)used/sum)*100);
            progressBar.AutoAddprogress(i);
            bt1=(Button) findViewById(R.id.speed_button1);
            lv1=(ListView)findViewById(R.id.speed_listview);
            list=new ArrayList<SpeedEntity>();
            activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);//���ϵͳ�������е�APP
            packageManager=getPackageManager();//��������ֻ�APP
//            for (int j = 0; j < 20; j++) {
//				SpeedEntity s=new SpeedEntity("app"+i,false,getResources().getDrawable(R.drawable.icon_c_h),99*i);
//			     list.add(s);
//            } ������
            ansyGetData();
            adapter=new SpeedAdapter(list, this);
            lv1.setAdapter(adapter);
            //

            cb=(CheckBox)findViewById(R.id.phoneclean_checkBox);
            cb.setOnCheckedChangeListener(new OnCheckedChangeListener( ) {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int j = 0; j <list.size(); j++) {
						list.get(j).setChecked(isChecked);
					}
                    adapter.notifyDataSetChanged();
					
				}
			});   
            bt1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				 for (int j = 0; j <list.size(); j++) {
					if(list.get(j).isChecked()){
						android.util.Log.i("oracle",list.get(j).getPackagename() );
						activityManager.killBackgroundProcesses(list.get(j).getPackagename());
					}
				}
				 ansyGetData();
				 init();

				}
			});
            
            
            
            
            
	}
	Handler handler=new Handler();
	
	public void ansyGetData(){
		
		 new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				//�ֻ����������еĽ��̵ļ���
				List<RunningAppProcessInfo> runApplist=activityManager.getRunningAppProcesses();
				//�ֻ���������app�ļ���
				List<PackageInfo> appList=packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
				//���еļ���
				for (PackageInfo packageInfo : appList) {
					//�������еļ���
					for (int i = 0; i < runApplist.size(); i++) {
						 //���еĽ��̺����н�����Ƚ�
						if(packageInfo.packageName.equals(runApplist.get(i).processName)){
                             //�������Լ��Ľ��̣� ���ҽ��̵����ȼ����ڵ�300
							if(!packageInfo.packageName.equals("com.example.phone")&&runApplist.get(i).importance>=300){
								SpeedEntity  entity=new SpeedEntity();
								entity.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
								entity.setName(packageInfo.applicationInfo.loadLabel(packageManager)+"");
								entity.setPackagename(packageInfo.packageName);
		 						//�ڴ�
      MemoryInfo[] memoryInfos=activityManager.getProcessMemoryInfo(new int[]{ runApplist.get(i).pid});
								//ֻҪ��һ��Ԫ��
                              MemoryInfo memoryInfo=memoryInfos[0];
                              //�õ��ڴ��С��λΪkb*1024b
                              int memory=memoryInfo.getTotalPrivateDirty()*1024;
                              entity.setSize(memory);
                              list.add(entity);
                              break;
							}
						}
					}
				}
				
				
				
				
	 handler.post(new Runnable() {
		
		@Override
		public void run() {
			adapter=new SpeedAdapter(list, PhoneSpeedActivity.this);
		    lv1.setAdapter(adapter);
			progressBar.setVisibility(View.GONE);
			
			
		}
	});
				
				
			}
		} ).start();


	}
	

	

}
