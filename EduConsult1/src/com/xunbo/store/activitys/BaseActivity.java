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
 * 基本类
 * 
 * @author Qzr
 * 
 */
public abstract class BaseActivity extends Activity {

	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	public TextView titleTv,right_tv;// 标题控件
	public TextView topLeftTv;// 左上 控件（返回）
	private ImageView topright_l,topright_r;
	private LinearLayout baseContentLayout,topleftll,toprightll;// 添加内容控件
	private RelativeLayout baseTopLayout,rl_l,rl_r;

	//	private BaseLeftClickListener leftClickListener;// 左上 点击监听

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
	//	 * base页面 左上角点击效果监听
	//	 * 
	//	 * @param clickListener
	//	 *            监听
	//	 */
	//	public void setTopLeftClickListener(BaseLeftClickListener clickListener) {
	//		this.leftClickListener = clickListener;
	//	}

	/**
	 * 设置左上角背景
	 * 
	 * @param drawableid
	 *            资源id
	 */
	public void setTopLeftBackground(int drawableId) {
		topLeftTv.setBackgroundResource(drawableId);
	}

	/**
	 * 设置左上角背景
	 * 
	 * @param drawable
	 *            图片
	 */
	@SuppressWarnings("deprecation")
	public void setTopLeftBackground(Drawable drawable) {
		topLeftTv.setBackgroundDrawable(drawable);
	}

	/**
	 * 隐藏左上布局
	 */
	public void goneTopLeft() {
		topleftll.setVisibility(View.GONE);
	}

	/**
	 * 显示左上布局
	 */
	public void visibleTopLeft() {
		topleftll.setVisibility(View.VISIBLE);
	}
	/**
	 * 设置左上角文字
	 */
	public void setTopLeftTv(String s) {
		topLeftTv.setText(s);
	}

	/**
	 * 设置左上角文字
	 */
	public void setTopLeftTv(int id) {
		topLeftTv.setText(getResources().getString(id));
	}

	/**
	 * 添加子页面布局
	 * 
	 * @param contentViewId
	 *            子布局
	 */
	public void setContentXml(int contentViewId) {
		addViewXML(baseContentLayout, contentViewId, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	/**
	 * 添加子页面view
	 * 
	 * @param childView
	 *            子 View
	 */
	public void setContentChildView(View childView) {
		baseContentLayout.addView(childView, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 设置标题
	 * 
	 * @param textId
	 *            标题资源id
	 */
	public void setTitleTxt(int textId) {
		titleTv.setText(textId);
	}

	/**
	 * 设置标题
	 * 
	 * @param text
	 *            标题资源
	 */
	public void setTitleTxt(String text) {
		titleTv.setText(text);
	}

	/**
	 * 隐藏title
	 */
	public void goneTitle() {
		titleTv.setVisibility(View.GONE);
	}

	/**
	 * 显示title
	 */
	public void visibleTitle() {
		titleTv.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置顶部背景
	 * 
	 * @param drawableid
	 *            资源id
	 */
	public void setTopTitleBackground(int drawableId) {
		titleTv.setBackgroundResource(drawableId);
	}

	/**
	 * 设置顶部背景
	 * 
	 * @param drawable
	 *            图片
	 */
	@SuppressWarnings("deprecation")
	public void setTopTitleBackground(Drawable drawable) {
		titleTv.setBackgroundDrawable(drawable);
	}
	/**
	 * 获取顶部view
	 * 
	 * @return
	 */
	public View getTopTitleView() {
		return titleTv;
	}

	/**
	 * 添加view
	 * 
	 * @param group
	 *            父容器
	 * @param id
	 *            子view id
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 */
	public void addViewXML(ViewGroup group, int id, int width, int height) {
		View contentView = View.inflate(this, id, null);
		group.addView(contentView, width, height);
	}

	/**
	 * 右上左按钮显示
	 */
	public void topRightLVisible() {
		topright_l.setVisibility(View.VISIBLE);
	}

	/**
	 * 右上右按钮显示
	 */
	public void topRightRVisible() {
		topright_r.setVisibility(View.VISIBLE);
	}
	/**
	 * 右上中竖条按钮隐藏
	 */
	public void topRightTGone() {
		right_tv.setVisibility(View.INVISIBLE);
	}

	/**
	 * 设置右上右背景
	 * 
	 * @param drawable
	 *            图片
	 */
	@SuppressWarnings("deprecation")
	public void setTopRightBackground(Drawable drawable) {
		topright_r.setBackgroundDrawable(drawable);
	}

	/**
	 * 设置右上角背景
	 * 
	 * @param drawableid
	 *            资源id
	 */
	public void setTopRightBackground(int drawableId) {
		topright_r.setBackgroundResource(drawableId);
	}
	/**
	 * 获取右上角左rl
	 * 
	 * @return
	 */
	public View getTopLightRl() {
		return rl_l;
	}
	/**
	 * 获取右上角右rl
	 * 
	 * @return
	 */
	public View getTopRightRl() {
		return rl_r;
	}
	
	/**
	 * 获取右上角左view
	 * 
	 * @return
	 */
	public View getTopLightView() {
		return topright_l;
	}
	/**
	 * 获取右上角右view
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
