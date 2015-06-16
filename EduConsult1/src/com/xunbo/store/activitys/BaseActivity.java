package com.xunbo.store.activitys;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.testin.agent.TestinAgent;
import com.xunbo.store.R;

/**
 * ������
 * 
 * @author Qzr
 * 
 */
public abstract class BaseActivity extends Activity {

	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	public TextView titleTv,right_tv;// ����ؼ�
	public TextView topLeftTv;// ���� �ؼ������أ�
	private ImageView topright_l,topright_r;
	private LinearLayout baseContentLayout,topleftll,toprightll;// ������ݿؼ�
	private RelativeLayout baseTopLayout,rl_l,rl_r;

	//	private BaseLeftClickListener leftClickListener;// ���� �������

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		TestinAgent.init(this);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setContentView(R.layout.base_layout);
		baseTopLayout = (RelativeLayout) findViewById(R.id.Base_Layout);
		titleTv = (TextView) findViewById(R.id.base_title_tv);
		topLeftTv = (TextView) findViewById(R.id.base_top_left_tv_text);
		topleftll = (LinearLayout) findViewById(R.id.base_top_left_tv_ll);
		topleftll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//				if (leftClickListener != null) {
				//					leftClickListener.onTopLeftClickListener();
				//				} else {
				//					finish();
				//				}
				finish();
			}
		});
		rl_l = (RelativeLayout) findViewById(R.id.base_top_right_rl_l);
		rl_r = (RelativeLayout) findViewById(R.id.base_top_right_rl_r);
		toprightll = (LinearLayout) findViewById(R.id.base_top_right_ll);
		topright_l = (ImageView) findViewById(R.id.base_top_right_image_l);
		topright_r = (ImageView) findViewById(R.id.base_top_right_image_r);
		right_tv = (TextView) findViewById(R.id.base_top_right_tv);
		//		topRightTv = (TextView) findViewById(R.id.base_top_right_tv);
		//		topRightTv.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//
		//			}
		//		});
		baseContentLayout = (LinearLayout) findViewById(R.id.base_content_layout);

	}

	//	/**
	//	 * baseҳ�� ���Ͻǵ��Ч������
	//	 * 
	//	 * @param clickListener
	//	 *            ����
	//	 */
	//	public void setTopLeftClickListener(BaseLeftClickListener clickListener) {
	//		this.leftClickListener = clickListener;
	//	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawableid
	 *            ��Դid
	 */
	public void setTopLeftBackground(int drawableId) {
		topLeftTv.setBackgroundResource(drawableId);
	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawable
	 *            ͼƬ
	 */
	@SuppressWarnings("deprecation")
	public void setTopLeftBackground(Drawable drawable) {
		topLeftTv.setBackgroundDrawable(drawable);
	}

	/**
	 * �������ϲ���
	 */
	public void goneTopLeft() {
		topleftll.setVisibility(View.GONE);
	}

	/**
	 * ��ʾ���ϲ���
	 */
	public void visibleTopLeft() {
		topleftll.setVisibility(View.VISIBLE);
	}
	/**
	 * �������Ͻ�����
	 */
	public void setTopLeftTv(String s) {
		topLeftTv.setText(s);
	}

	/**
	 * �������Ͻ�����
	 */
	public void setTopLeftTv(int id) {
		topLeftTv.setText(getResources().getString(id));
	}

	/**
	 * �����ҳ�沼��
	 * 
	 * @param contentViewId
	 *            �Ӳ���
	 */
	public void setContentXml(int contentViewId) {
		addViewXML(baseContentLayout, contentViewId, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	/**
	 * �����ҳ��view
	 * 
	 * @param childView
	 *            �� View
	 */
	public void setContentChildView(View childView) {
		baseContentLayout.addView(childView, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * ���ñ���
	 * 
	 * @param textId
	 *            ������Դid
	 */
	public void setTitleTxt(int textId) {
		titleTv.setText(textId);
	}

	/**
	 * ���ñ���
	 * 
	 * @param text
	 *            ������Դ
	 */
	public void setTitleTxt(String text) {
		titleTv.setText(text);
	}

	/**
	 * ����title
	 */
	public void goneTitle() {
		titleTv.setVisibility(View.GONE);
	}

	/**
	 * ��ʾtitle
	 */
	public void visibleTitle() {
		titleTv.setVisibility(View.VISIBLE);
	}

	/**
	 * ���ö�������
	 * 
	 * @param drawableid
	 *            ��Դid
	 */
	public void setTopTitleBackground(int drawableId) {
		titleTv.setBackgroundResource(drawableId);
	}

	/**
	 * ���ö�������
	 * 
	 * @param drawable
	 *            ͼƬ
	 */
	@SuppressWarnings("deprecation")
	public void setTopTitleBackground(Drawable drawable) {
		titleTv.setBackgroundDrawable(drawable);
	}
	/**
	 * ��ȡ����view
	 * 
	 * @return
	 */
	public View getTopTitleView() {
		return titleTv;
	}

	/**
	 * ���view
	 * 
	 * @param group
	 *            ������
	 * @param id
	 *            ��view id
	 * @param width
	 *            ��
	 * @param height
	 *            ��
	 */
	public void addViewXML(ViewGroup group, int id, int width, int height) {
		View contentView = View.inflate(this, id, null);
		group.addView(contentView, width, height);
	}

	/**
	 * ������ť��ʾ
	 */
	public void topRightLVisible() {
		topright_l.setVisibility(View.VISIBLE);
	}

	/**
	 * �����Ұ�ť��ʾ
	 */
	public void topRightRVisible() {
		topright_r.setVisibility(View.VISIBLE);
	}
	/**
	 * ������������ť����
	 */
	public void topRightTGone() {
		right_tv.setVisibility(View.INVISIBLE);
	}

	/**
	 * ���������ұ���
	 * 
	 * @param drawable
	 *            ͼƬ
	 */
	@SuppressWarnings("deprecation")
	public void setTopRightBackground(Drawable drawable) {
		topright_r.setBackgroundDrawable(drawable);
	}

	/**
	 * �������ϽǱ���
	 * 
	 * @param drawableid
	 *            ��Դid
	 */
	public void setTopRightBackground(int drawableId) {
		topright_r.setBackgroundResource(drawableId);
	}
	/**
	 * ��ȡ���Ͻ���rl
	 * 
	 * @return
	 */
	public View getTopLightRl() {
		return rl_l;
	}
	/**
	 * ��ȡ���Ͻ���rl
	 * 
	 * @return
	 */
	public View getTopRightRl() {
		return rl_r;
	}
	
	/**
	 * ��ȡ���Ͻ���view
	 * 
	 * @return
	 */
	public View getTopLightView() {
		return topright_l;
	}
	/**
	 * ��ȡ���Ͻ���view
	 * 
	 * @return
	 */
	public View getTopRightView() {
		return topright_r;
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

}
