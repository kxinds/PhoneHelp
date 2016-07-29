package com.example.phone;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.phone.adapter.TelManagerAdapter;
import com.example.phone.entity.TelManagerEntity;

import android.R.integer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tel_ManageActivity extends Activity {
/*
 * (non-Javadoc)
 *  1.单行布局
 *  2.实体类
 *  3.适配器
 * @see android.app.Activity#onCreate(android.os.Bundle)
 */ 
   private ListView listView;
   ArrayList<TelManagerEntity> list;
	private ImageView telmanage_back;
    private  TextView textTop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel__manage);
		listView=(ListView) findViewById(R.id.telmanage_listview);
		textTop=(TextView)findViewById(R.id.textTop);
		
		list=new ArrayList<TelManagerEntity>();

//		for (int i = 0; i <10; i++) {
//			  TelManagerEntity tEntity=new TelManagerEntity();
//              tEntity.setName("你好"+i);
//              tEntity.setPhone("12356"+i);
//	        list.add(tEntity);
//		}
		 
		Intent intent=getIntent();
		int id=intent.getIntExtra("id", 0);
		String text=intent.getStringExtra("text");
		Log.i("oracle", text);
		textTop.setText(text);
		list=getData(this,id);
		TelManagerAdapter adapter=new TelManagerAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String phone=list.get(position).getPhone();

			    AlertDialog.Builder  builder=new AlertDialog.Builder(Tel_ManageActivity.this);
			    builder.setIcon(R.drawable.icon_phonemgr);
			    builder.setMessage("是否需要拨号");
			    builder.setTitle("拨号");
			    builder.setPositiveButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
                         Toast.makeText(getApplicationContext(), "你点击了取消", 0).show();
					}
				});
			    builder.setNegativeButton("确定",new  OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {

						Log.i("oracle", phone );
						Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
		                startActivity(intent);
					}
				});
			    
			    builder.show();
			
			
			}
		});
		telmanage_back=(ImageView) findViewById(R.id.telmanage_back);
		telmanage_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                finish();				
			}
		});
	}
	
	
	
	//获取真数据 赋值给手机
	public ArrayList<TelManagerEntity> getData(Context context,int table) {
		//目标文件夹的路径
		String datapath="data"+File.separator+"data"+File.separator+"com.example.phone";
		//原路径文件
		String frompath="db"+File.separator+"commonnum.db";
		//创建文件  数据库文件
		File file=new File(datapath,"commonnum.db");
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
		
		if (file.exists()) {
			SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(file,null);
			String sql="select * from table"+table;
			//执行SQL脚本并返回结果集
			Cursor cursor=database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				TelManagerEntity telManagerEntity=new TelManagerEntity();
				telManagerEntity.setId(cursor.getInt(0));
				telManagerEntity.setPhone(cursor.getString(1));
				telManagerEntity.setName(cursor.getString(2));
				list.add(telManagerEntity);
			}
			cursor.close();
			database.close();
		}
		
		
		
		
		return list;
		
	}
	
}
