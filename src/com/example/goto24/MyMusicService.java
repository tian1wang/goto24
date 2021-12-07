package com.example.goto24;

import com.example.o.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;

public class MyMusicService extends Service {

	private MediaPlayer mediaPlayer;// 声明媒体播放器
	private int pos = 0;// 声明播放进度

	/**
	 * 通过 binderService()绑定服务 启动后回调的方法
	 */
	@Override
	public IBinder onBind(Intent intent) {
		playMusic();
		return new MyBinder();

	}

	public class MyBinder extends Binder {
		public MyMusicService getMusicService() {
			return MyMusicService.this;
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if (mediaPlayer == null) {
			mediaPlayer = mediaPlayer.create(MyMusicService.this,
					R.raw.qifengle);
			mediaPlayer.setLooping(false);// 设置不需要单曲循环
		}
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mediaPlayer.release();// 释放资源

			}
		});
	}

	// 播放音乐
	public void playMusic() {
		if (mediaPlayer != null && !mediaPlayer.isLooping()) {
			try {
				if (pos != 0) {
					mediaPlayer.seekTo(pos);// 根据指定位置进行播放
					mediaPlayer.start();
				} else {
					mediaPlayer.stop();
					mediaPlayer.prepare();// 重新启动之前必须prepare
					mediaPlayer.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 暂停音乐
	public void pasueMusic() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			pos = mediaPlayer.getCurrentPosition();
			mediaPlayer.pause();
		}
	}

	// 停止音乐
	public void stopMusic() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();// 停止
			mediaPlayer.release();// 资源重置
		}
	}
}