package kr.hs.emirim.dngus242.kokoalarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingAlarm extends Activity implements OnClickListener {
	protected AlarmManager am;
	protected PendingIntent pIntent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingalarm);
		
		Button btnOk = (Button) findViewById(R.id.btnOk);
		Button btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReceiver.class);
		pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

	}

	public void onClick(View v) {
		if (v.getId() == R.id.btnOk) {
			TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
			int hour = picker.getCurrentHour();// 사용자가 선택한 시간
			int minute = picker.getCurrentMinute();// 시용자가 선택한 분
			int nowHour = (new Date().getHours());// 현재시간
			int nowMinute = (new Date().getMinutes());// 현재분
			int nowSec=(new Date().getSeconds());	//현재초

			int time = Math.abs(((nowHour * 3600) + (nowMinute * 60))
					- ((hour * 3600) + (minute * 60)));

			am.set(AlarmManager.RTC_WAKEUP,
					(System.currentTimeMillis() + time * 1000)-(nowSec*1000), pIntent);
			
			Intent intent1 = new Intent();
			intent1.putExtra("hour", hour+"");
			intent1.putExtra("minute", minute+"");
			setResult(RESULT_OK, intent1);

		}

		else if (v.getId() == R.id.btnCancel) {
			setResult(RESULT_CANCELED);
		}
		finish();
	}
}
