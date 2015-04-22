package com.example.educonsult.activitys;

import org.jivesoftware.smack.ChatManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.example.educonsult.R;

public class ServiceCenterActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_zx_regist,ll_zx_shangjia,ll_zx_buy,ll_zx_zhuanjia,
	ll_wt_chaxun,ll_wt_order,ll_wt_regist,ll_wt_wuliu,ll_wt_qita,
	ll_show_regist,ll_show_shangjia,ll_show_buy,ll_show_zhuanjia,ll_show_wenti;
	private boolean regist,shangjia,buy,zhuanjia,wenti;
	private TextView tv_zhuce_1_online,tv_zhuce_1_qq,tv_zhuce_2_online,tv_zhuce_2_qq,
	tv_shangjia_1_online,tv_shangjia_1_qq,tv_shangjia_2_online,tv_shangjia_2_qq,
	tv_buy_1_online,tv_buy_1_qq,tv_buy_2_online,tv_buy_2_qq,
	tv_zhuanjia_1_online,tv_zhuanjia_1_qq,tv_zhuanjia_2_online,tv_zhuanjia_2_qq;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.service_center_title);
		setContentXml(R.layout.service_center_layout);
		init();
	}

	private void init() {
		regist = false;
		shangjia = false;
		buy = false;
		zhuanjia = false;
		wenti = false;
		ll_zx_regist = (LinearLayout) findViewById(R.id.service_center_ll_zx_regist);
		ll_zx_regist.setOnClickListener(this);
		ll_zx_shangjia = (LinearLayout) findViewById(R.id.service_center_ll_zx_shangjia);
		ll_zx_shangjia.setOnClickListener(this);
		ll_zx_buy = (LinearLayout) findViewById(R.id.service_center_ll_zx_buy);
		ll_zx_buy.setOnClickListener(this);
		ll_zx_zhuanjia = (LinearLayout) findViewById(R.id.service_center_ll_zx_zhuanjia);
		ll_zx_zhuanjia.setOnClickListener(this);
		ll_wt_chaxun = (LinearLayout) findViewById(R.id.service_center_ll_wenti_chaxun);
		ll_wt_chaxun.setOnClickListener(this);
		ll_wt_order= (LinearLayout) findViewById(R.id.service_center_ll_wenti_order);
		ll_wt_order.setOnClickListener(this);
		ll_wt_regist = (LinearLayout) findViewById(R.id.service_center_ll_wenti_regist);
		ll_wt_regist.setOnClickListener(this);
		ll_wt_wuliu = (LinearLayout) findViewById(R.id.service_center_ll_wenti_wuliu);
		ll_wt_wuliu.setOnClickListener(this);
		ll_wt_qita = (LinearLayout) findViewById(R.id.service_center_ll_wenti_qita);
		ll_wt_qita.setOnClickListener(this);
		ll_show_regist = (LinearLayout) findViewById(R.id.service_center_ll_show_regist);
		ll_show_shangjia = (LinearLayout) findViewById(R.id.service_center_ll_show_shangjia);
		ll_show_buy = (LinearLayout) findViewById(R.id.service_center_ll_show_buy);
		ll_show_zhuanjia = (LinearLayout) findViewById(R.id.service_center_ll_show_zhuanjia);
		ll_show_wenti = (LinearLayout) findViewById(R.id.service_center_ll_show_wenti);

		tv_zhuce_1_online = (TextView) findViewById(R.id.service_center_tv_regist_one_line);
		tv_zhuce_1_online.setOnClickListener(this);
		tv_zhuce_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuce_1_qq = (TextView) findViewById(R.id.service_center_tv_regist_one_qq);
		tv_zhuce_1_qq.setOnClickListener(this);
		tv_zhuce_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuce_2_online = (TextView) findViewById(R.id.service_center_tv_regist_two_line);
		tv_zhuce_2_online.setOnClickListener(this);
		tv_zhuce_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuce_2_qq = (TextView) findViewById(R.id.service_center_tv_regist_two_qq);
		tv_zhuce_2_qq.setOnClickListener(this);
		tv_zhuce_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		tv_shangjia_1_online = (TextView) findViewById(R.id.service_center_tv_shangjia_one_line);
		tv_shangjia_1_online.setOnClickListener(this);
		tv_shangjia_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_shangjia_1_qq = (TextView) findViewById(R.id.service_center_tv_shangjia_one_qq);
		tv_shangjia_1_qq.setOnClickListener(this);
		tv_shangjia_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_shangjia_2_online = (TextView) findViewById(R.id.service_center_tv_shangjia_two_line);
		tv_shangjia_2_online.setOnClickListener(this);
		tv_shangjia_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_shangjia_2_qq = (TextView) findViewById(R.id.service_center_tv_shangjia_two_qq);
		tv_shangjia_2_qq.setOnClickListener(this);
		tv_shangjia_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		tv_buy_1_online = (TextView) findViewById(R.id.service_center_tv_buy_one_line);
		tv_buy_1_online.setOnClickListener(this);
		tv_buy_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_buy_1_qq = (TextView) findViewById(R.id.service_center_tv_buy_one_qq);
		tv_buy_1_qq.setOnClickListener(this);
		tv_buy_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_buy_2_online = (TextView) findViewById(R.id.service_center_tv_buy_two_line);
		tv_buy_2_online.setOnClickListener(this);
		tv_buy_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_buy_2_qq = (TextView) findViewById(R.id.service_center_tv_buy_two_qq);
		tv_buy_2_qq.setOnClickListener(this);
		tv_buy_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		tv_zhuanjia_1_online = (TextView) findViewById(R.id.service_center_tv_zhuanjia_one_line);
		tv_zhuanjia_1_online.setOnClickListener(this);
		tv_zhuanjia_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuanjia_1_qq = (TextView) findViewById(R.id.service_center_tv_zhuanjia_one_qq);
		tv_zhuanjia_1_qq.setOnClickListener(this);
		tv_zhuanjia_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuanjia_2_online = (TextView) findViewById(R.id.service_center_tv_zhuanjia_two_line);
		tv_zhuanjia_2_online.setOnClickListener(this);
		tv_zhuanjia_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuanjia_2_qq = (TextView) findViewById(R.id.service_center_tv_zhuanjia_two_qq);
		tv_zhuanjia_2_qq.setOnClickListener(this);
		tv_zhuanjia_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);









	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.service_center_ll_zx_regist:
			if(!regist){
				ll_show_regist.setVisibility(View.VISIBLE);
				regist = true;
			}else{
				ll_show_regist.setVisibility(View.GONE);
				regist = false;
			}
			break;
		case R.id.service_center_ll_zx_shangjia:
			if(!shangjia){
				ll_show_shangjia.setVisibility(View.VISIBLE);
				shangjia = true;
			}else{
				ll_show_shangjia.setVisibility(View.GONE);
				shangjia = false;
			}
			break;
		case R.id.service_center_ll_zx_buy:
			if(!buy){
				ll_show_buy.setVisibility(View.VISIBLE);
				buy = true;
			}else{
				ll_show_buy.setVisibility(View.GONE);
				buy = false;
			}

			break;
		case R.id.service_center_ll_zx_zhuanjia:
			if(!zhuanjia){
				ll_show_zhuanjia.setVisibility(View.VISIBLE);
				zhuanjia = true;
			}else{
				ll_show_zhuanjia.setVisibility(View.GONE);
				zhuanjia = false;
			}
			break;
		case R.id.service_center_ll_wenti_chaxun:
			if(!wenti){
				ll_show_wenti.setVisibility(View.VISIBLE);
				wenti = true;
			}else{
				ll_show_wenti.setVisibility(View.GONE);
				wenti = false;
			}
			break;
		case R.id.service_center_tv_regist_one_line:
//			startActivity(new Intent(this,ChatManager));
//			EMChatManager.getInstance().login("051530", "000000", new EMCallBack() {
//				
//				@Override
//				public void onSuccess() {
//					EMChatManager.getInstance().loadAllConversations();
//				}
//				
//				@Override
//				public void onProgress(int arg0, String arg1) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onError(int arg0, String arg1) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			break;
			//	case R.id.service_center_ll_wenti_order:
			//		
			//		break;
			//	case R.id.service_center_ll_wenti_regist:
			//		
			//		break;
			//	case R.id.service_center_ll_wenti_wuliu:
			//		
			//		break;
			//	case R.id.service_center_ll_wenti_qita:
			//		
			//		break;









		}
	}

}
