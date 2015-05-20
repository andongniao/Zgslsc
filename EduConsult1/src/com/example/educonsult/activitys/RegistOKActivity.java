package com.example.educonsult.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;

public class RegistOKActivity extends Activity{
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	private Handler hand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setContentView(R.layout.tiaozhuan_layout);
		hand = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1) {
					Intent intent = new Intent(RegistOKActivity.this,
							LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			}
		};
		new Thread() {
			public void run() {
				try {
					sleep(2000);
					Message msg = hand.obtainMessage();
					msg.what = 1;
					hand.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}
}
