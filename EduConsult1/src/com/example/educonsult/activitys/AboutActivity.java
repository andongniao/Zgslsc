package com.example.educonsult.activitys;

import com.example.educonsult.R;
import com.example.educonsult.myviews.BadgeView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class AboutActivity extends BaseActivity{
	private long exitTime = 0;
	private View tv_top_tight;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		topRightRVisible();
		setTitleTxt(R.string.about_title);
		setContentXml(R.layout.about_layout);
		init();
		
	}

	private void init() {
		tv_top_tight =  getTopRightView();
		BadgeView badge = new BadgeView(this, tv_top_tight);
		badge.setText("1");
		badge.show();
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
