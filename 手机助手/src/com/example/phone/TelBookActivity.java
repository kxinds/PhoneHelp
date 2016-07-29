package com.example.phone;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TelBookActivity extends Activity {
 ImageView telBook_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel_book);
		telBook_back=(ImageView)findViewById(R.id.telBook_back);
         telBook_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//            Intent intent=new Intent(TelBookActivity.this,LocalHomeActivity.class);
//		    startActivity(intent);
				finish();
			}
		});
                    
	}
	private int id;
	private String  text;
	
	public void telclock(View view) {
		switch (view.getId()) {
		case R.id.telbook_dhdc:
			id=1;
			text="电话订餐";
			
			break;
		case R.id.telbook_ggfw:
			id=2;
			text="公共服务";
			
			break;
		case R.id.telbook_yys:
			id=3;
			text="运营商";
			
			break;
		case R.id.telbook_kdfw:
			id=4;
			text="快递服务";
			
			break;
		case R.id.telbook_jpjd:
			id=5;
			text="机票酒店";
			
			break;
		case R.id.telbook_yhzq:
			id=6;
			text="银行证券";
			
			break;
		case R.id.telbook_bxfw:
			id=7;
			text="保险服务";
			
			break;
		case R.id.telbook_ppsh:
			id=8;
			text="品牌售后";
			break;

		default:
			break;
		}
		
		Intent intent=new Intent(TelBookActivity.this,Tel_ManageActivity.class);
		intent.putExtra("text", text);
		intent.putExtra("id", id);
		startActivity(intent);
	}


}
