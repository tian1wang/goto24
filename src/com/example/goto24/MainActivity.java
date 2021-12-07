package com.example.goto24;

import com.example.goto24.MyMusicService.MyBinder;
import com.example.o.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {

	private ServiceConnection conn;
	private MyMusicService musicService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		conn = new Myconn();// 建立服务连接对象
	}

	public void g(View view) {

		Intent intent = new Intent(this, RulesActivity.class);
		this.startActivity(intent);
		this.finish();

	}

	public void j(View view) {

		Intent intent = new Intent(this, PlayActivity.class);
		this.startActivity(intent);
		this.finish();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:// 启动音乐
			if (musicService == null) {
				Intent intent = new Intent(MainActivity.this,
						MyMusicService.class);
				bindService(intent, conn, Context.BIND_AUTO_CREATE);// 绑定服务,后台也能播放
			} else {
				musicService.playMusic();
			}
			break;
		case R.id.btn2:// 暂停音乐
			if (musicService != null) {
				musicService.pasueMusic();
			}
			break;
		case R.id.btn3:// 关闭音乐
			if (musicService != null) {
				musicService.stopMusic();
				musicService = null;
				unbindService(conn);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 服务连接
	 */
	public class Myconn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyBinder myBinder = (MyBinder) service;
			musicService = myBinder.getMusicService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			conn = null;

		}

	}
}
