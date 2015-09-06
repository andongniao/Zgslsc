package com.example.educonsult.activitys;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;

/**
 * 我要提问
 * @author Qzr
 *
 */
public class PutQuestionActivity extends Activity implements OnClickListener{
	private Context context;
	private CheckBox cb;
	@SuppressWarnings("unused")
	private boolean isshow;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.know_put_question_layout);
		init();
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		findViewById(R.id.put_question_et_top).setOnClickListener(this);
		findViewById(R.id.put_question_ll_back).setOnClickListener(this);
		//		findViewById(R.id.know_put_question_et).setOnClickListener(this);
		findViewById(R.id.put_question_tv_commit).setOnClickListener(this);
		findViewById(R.id.put_question_ll_tiwen_i).setOnClickListener(this);
		cb = (CheckBox) findViewById(R.id.put_question_cb);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isshow = !isChecked;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.put_question_et_top:
			intent = new Intent(context,SearchHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("t", 1);
			startActivity(intent);
			break;
		case R.id.put_question_tv_commit:

			break;
		case R.id.put_question_ll_back:
			finish();
			break;
		case R.id.put_question_ll_tiwen_i:
			
			break;
		}
	}

}
