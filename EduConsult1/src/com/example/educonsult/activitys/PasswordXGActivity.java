package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.SCStoreAdapter;
import com.example.educonsult.myviews.MyListview;

public class PasswordXGActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private Intent intent;
	private String str;
	private EditText ed_old,ed_new,ed_two;
	private Button button;



	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		intent=getIntent();
		str=intent.getStringExtra("possword");
		if(str.equals("1")){
			
			setTopLeftTv(R.string.possword_tvlogin);
		}else if(str.equals("2")) {
			setTopLeftTv(R.string.possword_tvzhifu);
		}
		setContentXml(R.layout.possword_xiugai);
		init();

	}
	void init(){
		ed_old=(EditText)findViewById(R.id.possword_xiugai_oldpow);
		ed_new=(EditText)findViewById(R.id.possword_xiugai_newpow);
		ed_two=(EditText)findViewById(R.id.possword_xiugai_newpowtwo);
		if(str.equals("1")){
			
		}else if(str.equals("2")) {
			ed_old.setHint(R.string.possword_tvoldzhifu);
			ed_new.setHint(R.string.possword_tvnewzhifu);
			ed_two.setHint(R.string.possword_tvnewzhifutwo);
		}
		button=(Button)findViewById(R.id.possword_xiugai_ok);
		button.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.possword_xiugai_ok:
			if(str.equals("1")){
				
			}else if(str.equals("2")) {
				
			}
			break;

		default:
			break;
		}
	}
	
}
