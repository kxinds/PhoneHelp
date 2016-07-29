package com.example.phone;

import com.example.phone.utils.CommonUtil;
import com.example.phone.utils.MemoryManager;
import com.example.phone.view.AutoProgressBar;
import com.example.phone.view.MysoftBall;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneManageActivity extends Activity {
	private MysoftBall mysoftBall;
	private AutoProgressBar autoProgressBar;
	private TextView textView;
	 
ImageView phonemanage_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_manage);
        phonemanage_back=(ImageView) findViewById(R.id.phonemanage_back);
        phonemanage_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
        init();
	}
	
	//初始化方法
	private void init() {
    mysoftBall=(MysoftBall)findViewById(R.id.mysoftBall1);
    long num=MemoryManager.getPhoneFreeRamMemory(PhoneManageActivity.this);
    long all=MemoryManager.getPhoneTotalRamMemory();
    int a=(int)(((double)num/all)*100);
    mysoftBall.setAutoArc(a);
    autoProgressBar=(AutoProgressBar) findViewById(R.id.phoneManage_bar);
    int b=(int)(((double)num/all)*100);
    autoProgressBar.AutoAddprogress(b);
    textView=(TextView)findViewById(R.id.phonemanage_bili);
    textView.setText("空闲容量/总容量:  "+CommonUtil.getFileSize(num)+"/"+CommonUtil.getFileSize(all));
	}
	String name;
	public void softClick(View view){
		switch (view.getId()) {
		case R.id.allprogaram:
			name="全部软件";
			
			break;
       case R.id.sysprogaram:
    	   name="系统软件";
			break;
        case R.id.userprogaram:
	       name="用户软件";
	      break;

		}
		Intent intent=new Intent(PhoneManageActivity.this,ProgramMainActivity.class);
		intent.putExtra("name",name);
		startActivity(intent);
	}

	

}
