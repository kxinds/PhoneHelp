package com.example.phone;

import java.util.ArrayList;

import com.example.phone.adapter.ProgramAdapater;
import com.example.phone.entity.ProgramEntity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.*;

import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProgramMainActivity extends Activity {
	private PackageManager prackManager;
	private String name;
	private ProgressBar bar;
	private ListView listView;
    private ArrayList<ProgramEntity> list;
    private ProgramAdapater adapater;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program_main);
		
		Intent intent=getIntent();
		name=intent.getStringExtra("name");
		init();
		 
	    
	}
    
    
    
    
    public Handler handler=new Handler();
    public ArrayList<ProgramEntity> GetDataSoft(){
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
            
 ArrayList<PackageInfo> applist=(ArrayList<PackageInfo>)prackManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

				
				if("全部软件".equals(name)){
					for (PackageInfo packageInfo : applist) {
						 ProgramEntity entity=new ProgramEntity();
						 entity.setImg(packageInfo.applicationInfo.loadIcon(prackManager));
						 entity.setInfo(packageInfo.applicationInfo.loadLabel(prackManager)+"");
						 entity.setInfo2(packageInfo.packageName);
						 Intent intent=prackManager.getLaunchIntentForPackage(entity.getInfo2());
						 entity.setIntent(intent);
						 list.add(entity);
					}  
              }else if ("系统软件".equals(name)) {
					//系统
            	  for (PackageInfo packageInfo : applist) {
					//每个app都能得到
            		  int currentFlag=packageInfo.applicationInfo.flags;
            		  //用来判断是不是系统
            		  //2进制编码
            		  int sysFlag=ApplicationInfo.FLAG_SYSTEM|ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
            		  
            		  if((sysFlag&currentFlag)!=0){  
            			 ProgramEntity entity=new ProgramEntity();
 						 entity.setImg(packageInfo.applicationInfo.loadIcon(prackManager));
 						 entity.setInfo(packageInfo.applicationInfo.loadLabel(prackManager)+"");
 						 entity.setInfo2(packageInfo.packageName);
 						 Intent intent=prackManager.getLaunchIntentForPackage(entity.getInfo2());
 						 entity.setIntent(intent);
 						 list.add(entity); 
            			  
            			  
            		  }
            		  
            		  
				}
					
				}else if ("用户软件".equals(name)) {
					//用户
					for (PackageInfo packageInfo : applist) {
						//每个app都能得到
	            		  int currentFlag=packageInfo.applicationInfo.flags;
	            		  //用来判断是不是系统
	            		  //2进制编码
	            		  int sysFlag=ApplicationInfo.FLAG_SYSTEM|ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
	            		  
	            		  if((sysFlag&currentFlag)==0){  
	            			 ProgramEntity entity=new ProgramEntity();
	 						 entity.setImg(packageInfo.applicationInfo.loadIcon(prackManager));
	 						 entity.setInfo(packageInfo.applicationInfo.loadLabel(prackManager)+"");
	 						 entity.setInfo2(packageInfo.packageName);
	 						 Intent intent=prackManager.getLaunchIntentForPackage(entity.getInfo2());
	 						 entity.setIntent(intent);
	 						 list.add(entity); 
	            			  
	            			  
	            		  }
					}
					
					
					
				}
			handler.post(new Runnable() {
				
				@Override
				public void run() {
         adapater=new ProgramAdapater(list, ProgramMainActivity.this);
					listView.setAdapter(adapater);
					bar.setVisibility(View.GONE);
				}
			});
				
				
				
				
				
			}
		}).start();
    	
    	
    	
    	
    	
    	
    	return list;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

	private void init() {
		Button clearButton=(Button) findViewById(R.id.program_button1);
		ImageView im=(ImageView)findViewById(R.id.program_back);
		  im.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();	
			}
		});
		  bar=(ProgressBar)findViewById(R.id.program_progressBar);
		  listView=(ListView) findViewById(R.id.program_listview);
		  list=new ArrayList<ProgramEntity>();
		  prackManager=getPackageManager();
//          for (int i = 0; i < 20; i++) {
//        	  ProgramEntity entity=new ProgramEntity();
//		  entity.setImg(getResources().getDrawable(R.drawable.icon_c));
//		   entity.setInfo("你好");
//		   entity.setInfo2("世界"+2);
//		   entity.setIscheck(false);
//		   list.add(entity);
//          }
//               adapater=new ProgramAdapater(list, this);
//             listView.setAdapter(adapater);
//             bar.setVisibility(View.GONE);
		  GetDataSoft();
		  clearButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	for (int i = 0; i <list.size(); i++) {
					if(list.get(i).isIscheck()){
						Toast.makeText(getApplicationContext(), "你好"+list.get(i).getInfo(), 0).show();
					  Uri uri=Uri.parse("package:"+list.get(i).getInfo2());
					  Intent intent=new Intent(Intent.ACTION_DELETE,uri);
						startActivityForResult(intent, 111);

					}
				}	
				 

			}
		});
		
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==111){
			init();
			
		}
	}

	

}
