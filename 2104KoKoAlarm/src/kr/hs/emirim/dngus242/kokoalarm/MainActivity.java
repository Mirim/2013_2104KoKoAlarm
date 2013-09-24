package kr.hs.emirim.dngus242.kokoalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView Texe2;
	int rcount;

	ViewGroup.MarginLayoutParams margin;
	int count = 1;
	int rand;

	private MediaPlayer player;


	private void reenableLockScreen(Context context) {
		KeyguardManager manager = (KeyguardManager)context.getSystemService(Activity.KEYGUARD_SERVICE);  
		KeyguardLock lock = manager.newKeyguardLock(Activity.KEYGUARD_SERVICE);  
		lock.reenableKeyguard();;  
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("LOG", "ONRESUME");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.i("LOG", "ONStart");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		reenableLockScreen(this);
		AlarmPowerManager.release();

		player = MediaPlayer.create(this, R.raw.sundlysundlymemories);
		Log.i("LOG", "PLA : " + player);
		player.start();
		//long pattern[] = { 50, 1000, 50, 3000 };

		Texe2 = (TextView) findViewById(R.id.Text2);

		rand = (int) (Math.random() * 40) + 10;

		Button button = (Button) findViewById(R.id.btnbtn);
		ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(
				button.getLayoutParams());
		margin.setMargins(150, 150, 5, 5);
		button.setLayoutParams(new RelativeLayout.LayoutParams(margin));
		
		Button btnStop=(Button)findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(count>10){
					if (player != null) {
						player.stop();
					}
					Toast.makeText(MainActivity.this, "일어나셨쎄요?", 3000)
					.show();
					finish();
				}
				else{
					Toast.makeText(MainActivity.this, "아직 10번 안누르신거 같은데?", 3000)
					.show();
				}
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				rcount = (rand - count) - 1;
				Texe2.setText(rcount + "번 남았습니다!");
				count++;
				if (count == rand) {
					
					if (player != null) {
						player.stop();
					}
					
					Toast.makeText(MainActivity.this, "일어나!!!!!!!", 3000)
					.show();
					finish();
				} else {
					Log.d("LOG", "Main.onClick");
					Button button = (Button) v;
					ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(
							button.getLayoutParams());
					MainActivity.this.margin = margin;
					// margin.setMargins(250, 5, 5, 5);
					RandomState();
					button.setLayoutParams(new RelativeLayout.LayoutParams(
							margin));
					button.invalidate();

				}
			}
		});
	}

	public void RandomState() {
		int left = (int) (Math.random() * 500);
		int top = (int) (Math.random() * 900);
		this.margin.setMargins(left, top, 0, 0);
	}

	@Override
	protected void onPause() {
		if(player!=null){
			player.stop();
			player.release();
			player=null;
		}
		super.onPause();
	}
}