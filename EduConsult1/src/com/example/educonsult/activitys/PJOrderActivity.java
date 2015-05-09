package com.example.educonsult.activitys;

import com.example.educonsult.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PJOrderActivity extends BaseActivity implements OnClickListener {
	private LinearLayout ll_l,ll_t,ll_r; 
	private EditText ed;
	private Button button;
	private ImageView imaone,imatwo,imathree;
	private int isgood;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.pingjiadingdan_title);
		setContentXml(R.layout.pjorder);
		init();
	}
	void init(){
		ll_l=(LinearLayout)findViewById(R.id.pjorder_one_lin);
		ll_l.setOnClickListener(this);
		ll_t=(LinearLayout)findViewById(R.id.pjorder_two_lin);
		ll_t.setOnClickListener(this);
		ll_r=(LinearLayout)findViewById(R.id.pjorder_three_lin);
		ll_r.setOnClickListener(this);
		ed=(EditText)findViewById(R.id.pjorder_ed);
		button=(Button)findViewById(R.id.pjorder_ok);
		button.setOnClickListener(this);
		imaone=(ImageView)findViewById(R.id.pjorder_one);
		imathree=(ImageView)findViewById(R.id.pjorder_three);
		imatwo=(ImageView)findViewById(R.id.pjorder_two);
		isgood=1;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.pjorder_one_lin:
			imaone.setBackgroundResource(R.drawable.pingjiatwo);
			imatwo.setBackgroundResource(R.drawable.pingjiaone);
			imathree.setBackgroundResource(R.drawable.pingjiaone);
			isgood=1;
			break;
		case R.id.pjorder_two_lin:
			imatwo.setBackgroundResource(R.drawable.pingjiatwo);
			imaone.setBackgroundResource(R.drawable.pingjiaone);
			imathree.setBackgroundResource(R.drawable.pingjiaone);
			isgood=2;
			break;
		case R.id.pjorder_three_lin:
			imathree.setBackgroundResource(R.drawable.pingjiatwo);
			imatwo.setBackgroundResource(R.drawable.pingjiaone);
			imaone.setBackgroundResource(R.drawable.pingjiaone);
			isgood=3;
			break;
		case R.id.pjorder_ok:
			if(isgood==1){
				
			}else if(isgood==2){
				
			}else if(isgood==3){
				
			}
			String edStr=ed.getText().toString().trim();
			
			break;

		default:
			break;
		}
	}

}
