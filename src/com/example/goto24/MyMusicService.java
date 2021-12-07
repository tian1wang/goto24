package com.example.goto24;

import com.example.o.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;

public class MyMusicService extends Service {

	private MediaPlayer mediaPlayer;// ����ý�岥����
	private int pos = 0;// �������Ž���

	/**
	 * ͨ�� binderService()�󶨷��� ������ص��ķ���
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
			mediaPlayer.setLooping(false);// ���ò���Ҫ����ѭ��
		}
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mediaPlayer.release();// �ͷ���Դ

			}
		});
	}

	// ��������
	public void playMusic() {
		if (mediaPlayer != null && !mediaPlayer.isLooping()) {
			try {
				if (pos != 0) {
					mediaPlayer.seekTo(pos);// ����ָ��λ�ý��в���
					mediaPlayer.start();
				} else {
					mediaPlayer.stop();
					mediaPlayer.prepare();// ��������֮ǰ����prepare
					mediaPlayer.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ��ͣ����
	public void pasueMusic() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			pos = mediaPlayer.getCurrentPosition();
			mediaPlayer.pause();
		}
	}

	// ֹͣ����
	public void stopMusic() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();// ֹͣ
			mediaPlayer.release();// ��Դ����
		}
	}
}