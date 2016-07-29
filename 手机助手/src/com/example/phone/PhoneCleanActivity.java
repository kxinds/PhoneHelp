package com.example.phone;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.phone.adapter.ClearAdapter;
import com.example.phone.entity.ClearEntity;
import com.example.phone.entity.TelManagerEntity;
import com.example.phone.utils.CommonUtil;

import android.os.Bundle;
import android.os.Environment;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Size;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneCleanActivity extends Activity {
	private  ImageView  Clean_back;
	private  TextView allsize;
	private  ListView  listView;
	private  ProgressBar bar;
	private  Button   clean;
	private  CheckBox allCheckBox;
	private  ArrayList<ClearEntity> list;
	private long allsize1;
	private ClearAdapter clearAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_clean);
		init();
		asynClean();
		
		
	 
	}
	
	
	public void getData(Context context,int table) {
		 list.clear();
		//Ŀ���ļ��е�·��
		String datapath="data"+File.separator+"data"+File.separator+"com.example.phone";
		//ԭ·���ļ�
		String frompath="db"+File.separator+"clearpath.db";
		//�����ļ�  ���ݿ��ļ�
		File file=new File(datapath,"clearpath.db");
		AssetManager assetManager=context.getAssets();
		InputStream in;
		if(!file.exists()){
		try {
			in=assetManager.open(frompath);
			BufferedInputStream input=new BufferedInputStream(in);
			BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(file));
			int length=0;
			byte[] buffer=new byte[1024];
			while ((length=input.read(buffer))!=-1) {
				  out.write(buffer,0,length);
				  out.flush();
			}
            		
			out.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		if(file.exists()){
			SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(file,null);
			//��sqliteDatabase  
			String sql="select * from softdetail";
			//ִ��sql��ý����
			Cursor cursor=database.rawQuery(sql, null);
			
			while (cursor.moveToNext()) {
               ClearEntity clearEntity=new ClearEntity();
               clearEntity.setSize(cursor.getInt(0));
               clearEntity.setName(cursor.getString(2));
               clearEntity.setIschecked(false);
                clearEntity.setApkName(cursor.getString(3));
              //sdk��·��
                clearEntity.setFilePath(Environment.getExternalStorageDirectory()+File.separator+cursor.getString(4));
                File file1=new File(clearEntity.getFilePath());
                 Drawable icon=null;
                 if(file1.exists()){
                	 try {
    					icon=getPackageManager().getApplicationIcon(clearEntity.getApkName());
    	                 clearEntity.setIcon(icon);
    	 				 list.add(clearEntity);
    				} catch (NameNotFoundException e) {
    					e.printStackTrace();
//    					icon=getResources().getDrawable(R.drawable.ic_r);
//    					 clearEntity.setIcon(icon);
//    	 				 list.add(clearEntity);

    				} 
                 }
                
			}
			cursor.close();
			database.close();	

		}			
		
		
	}
	

	
	//ɾ���ļ�
	public static void delfile(File file) {
		if (!file.isDirectory()) {
			boolean b = file.delete();
		} else {
			File files[] = file.listFiles();
			if (file != null) {
				for (int i = 0; i < files.length; i++) {
					delfile(files[i]);
				}
				file.delete();
			}
		}
	}

	//ȡ���ļ����ļ��д�С
	public static long getFileSize(File file){
		long size=0;
		if(!file.isDirectory()){
			//�ļ�
			return file.length();
		}
		File files[]=file.listFiles();//�ļ��еݹ�
		if(file!=null){
			for (int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()){
					//�ݹ�
	                    size=size+getFileSize(files[i]);				
				}else {
					size=size+files[i].length();
				}
			}
		}
		
		
		return size;	
	}
	/**
	 * �첽���񷽷�  ��������
	 */
	public void asynClean(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
                  //�������� ��ȡ���ݷ���
			  getData(PhoneCleanActivity.this, 1);
			  allsize1=0;
			  for (int i = 0; i < list.size(); i++) {
				//�ļ�·��
				  File ftemp=new File(list.get(i).getFilePath());
			   //����ÿ���ļ�·���õ��ļ���С
			     Long ltemp=getFileSize(ftemp);
			   //�ܼ���  �����ļ���С
			    list.get(i).setSize(ltemp);
				//�ܴ�С  ����
			    allsize1+=ltemp;
			  }
			  //�ص����߳�
			  runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
			        clearAdapter=new ClearAdapter(list, PhoneCleanActivity.this);
			        listView.setAdapter(clearAdapter);
				    allsize.setText(CommonUtil.getFileSize(allsize1));
			        bar.setVisibility(View.GONE);         
			      
					
				}
			});
			  
			
			}
		}).start();
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	//���ݳ�ʼ��
	public void init(){
		allsize=(TextView) findViewById(R.id.phoneclear_all);
	    listView=(ListView)findViewById(R.id.phoneclear_listview);
	    bar=(ProgressBar)findViewById(R.id.phoneclear_progressBar);
	    allCheckBox=(CheckBox)findViewById(R.id.phoneclean_checkBox);
	    clean=(Button)findViewById(R.id.phoneclean_Oneclean);
	    list=new ArrayList<ClearEntity>();
//        for (int i = 1; i <20; i++) {
//
//	    ClearEntity clearEntity=new ClearEntity();
//	    clearEntity.setApkName("�θ��ͼ"+"�汾"+i);
//	    clearEntity.setName("�θ��ͼ�汾"+i);
//	    clearEntity.setIcon(getResources().getDrawable(R.drawable.icon_c_h));
//        clearEntity.setIschecked(false);
//         list.add(clearEntity); 
//        }
//		list=getData(this, 10);
//        ClearAdapter clearAdapter=new ClearAdapter(list, this);
//        listView.setAdapter(clearAdapter);
//  
		Clean_back=(ImageView) findViewById(R.id.phoneClean_back);
	    Clean_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
        finish();
	}
	});
		   allCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					   
					//  ClearAdapter clearAdapter=new ClearAdapter(list, PhoneCleanActivity.this);
					      for (int i = 0; i <list.size(); i++) {
					    	  list.get(i).setIschecked(isChecked);
							
						}
					  //����������
					  clearAdapter.notifyDataSetChanged();
				        		   
					   
				   
				}
			});
		   clean=(Button) findViewById(R.id.phoneclean_Oneclean);
			    clean.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
					for (int i = 0; i <list.size(); i++) {
						  if(list.get(i).isIschecked()){
							  //ɾ������
							  File file=new File(list.get(i).getFilePath());
							  delfile(file);
						  }
					}	
					  asynClean();

					}
				});
			    
			    
			    
			    
			   
	     
	    
	    
	    
	}



}
