package com.hwanee.manager;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;

public class DeviceManager {
	private static DeviceManager mManager = null;
	private AudioManager mAudioManager = null;
	private Context mContext = null;

	private int mMaximumBrightness = 0;
	private int mMaximumMusicVolume = 0;
	private int mMaximumRingVolume = 0;
	private int mMaximumNotificationVolume = 0;
	private int mMaximumCallVolume = 0;
	private int mMaximumAlarmVolume = 0;
	private int mMaximumSystemVolume = 0;
	private int mMaximumDTMFVolume = 0;
	private int mMaximumVibrateLevel = 0;

	private int mCurBrightness = 0;
	private int mCurMusicVolume = 0;
	private int mCurRingVolume = 0;
	private int mCurNotificationVolume = 0;
	private int mCurCallVolume = 0;
	private int mCurAlarmVolume = 0;
	private int mCurSystemVolume = 0;
	private int mCurDTMFVolume = 0;
	private int mCurVibrateLevel = 0;

	public static DeviceManager getDeviceManager(Context context) {

		if (mManager == null) {
			mManager = new DeviceManager(context);
		}

		return mManager;
	}

	private DeviceManager(Context context) {
		mContext = context;
		mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		initMaximunVolume();
		initCurVolume();
	}

	private void initMaximunVolume() {
		if (mAudioManager != null) {
			mMaximumMusicVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			mMaximumRingVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_RING);
			mMaximumNotificationVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
			mMaximumCallVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
			mMaximumAlarmVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_ALARM);
			mMaximumSystemVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
			mMaximumDTMFVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_DTMF);
		}
	}

	private void initCurVolume() {
		if (mAudioManager != null) {
			mCurMusicVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			mCurRingVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_RING);
			mCurNotificationVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
			mCurCallVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
			mCurAlarmVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_ALARM);
			mCurSystemVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
			mCurDTMFVolume = mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_DTMF);
		}
	}

	public boolean getBrightnessMode() {
		try {
			return android.provider.Settings.System.getInt(
					mContext.getContentResolver(),
					System.SCREEN_BRIGHTNESS_MODE) == System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC ? true
					: false;
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public void setBrightnessMode(int mode) {
		System.putInt(mContext.getContentResolver(),
				System.SCREEN_BRIGHTNESS_MODE, mode);
	}

	public int getBrightness() {
		try {
			return System.getInt(mContext.getContentResolver(),
					System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public void setBrightness(int value) {
		System.putInt(mContext.getContentResolver(), System.SCREEN_BRIGHTNESS,
				value);
	}

	public HashMap<String, String> getDeviceVolume(int id) {
		HashMap<String, String> data = new HashMap<String, String>();

		if (data != null) {
			data.put(getMaximumVolumeKey(id),
					String.valueOf(mAudioManager.getStreamMaxVolume(id)));
			data.put(getVolumeKey(id),
					String.valueOf(mAudioManager.getStreamVolume(id)));
		}

		return data;
	}

	public void setDeviceVolume(int id, int value) {
		mAudioManager.setStreamVolume(id, value, 0);
	}

	public HashMap<String, String> getVibrateLevel() {
		HashMap<String, String> data = new HashMap<String, String>();

		if (data != null) {
		}

		return data;
	}

	public void setVibrateLevel(int value) {
	}

	public boolean getSwitchMode(int position) {
		boolean result = false;
		switch (position) {
		case 0:
			result = isBrightnessOn();
			break;
		case 1:
			result = isSoundOn();
			break;
		case 2:
			result = isVibrateRingerOn();
			break;
		case 3:
			result = isVibrateNotificationOn();
			break;
		case 4:
			result = isSilentMode();
			break;
		}

		return result;
	}

	private boolean isBrightnessOn() {
		return false;
	}

	private boolean isSoundOn() {
		if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
			return true;
		}
		return false;
	}

	private boolean isVibrateRingerOn() {
		if (mAudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER) == AudioManager.VIBRATE_SETTING_ON) {
			return true;
		}
		return false;
	}

	private boolean isVibrateNotificationOn() {
		if (mAudioManager
				.getVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION) == AudioManager.VIBRATE_SETTING_ON) {
			return true;
		}
		return false;
	}

	public boolean isSilentMode() {
		if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
			return true;
		}
		return false;
	}

	public void setSoundMode(int position) {
		switch (position) {
		case 0:
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			break;
		case 1:
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			break;
		case 2:
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			break;
		case 3:
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			break;
		}
	}

	private String getMaximumVolumeKey(int id) {
		String key = DeviceData.MAXIMUM_MEDIA_VOLUME;
		switch (id) {
		case AudioManager.STREAM_RING:
			key = DeviceData.MAXIMUM_RING_VOLUME;
			break;

		case AudioManager.STREAM_NOTIFICATION:
			key = DeviceData.MAXIMUM_NOTIFICATION_VOLUME;
			break;
		case AudioManager.STREAM_VOICE_CALL:
			key = DeviceData.MAXIMUM_CALL_VOLUME;
			break;
		case AudioManager.STREAM_ALARM:
			key = DeviceData.MAXIMUM_ALARM_VOLUME;
			break;
		case AudioManager.STREAM_SYSTEM:
			key = DeviceData.MAXIMUM_SYSTEM_VOLUME;
			break;
		}

		return key;
	}

	private String getVolumeKey(int id) {
		String key = DeviceData.MEDIA_VOLUME;
		switch (id) {
		case AudioManager.STREAM_RING:
			key = DeviceData.RING_VOLUME;
			break;

		case AudioManager.STREAM_NOTIFICATION:
			key = DeviceData.NOTIFICATION_VOLUME;
			break;
		case AudioManager.STREAM_VOICE_CALL:
			key = DeviceData.CALL_VOLUME;
			break;
		case AudioManager.STREAM_ALARM:
			key = DeviceData.ALARM_VOLUME;
			break;
		case AudioManager.STREAM_SYSTEM:
			key = DeviceData.SYSTEM_VOLUME;
			break;
		}

		return key;
	}

	public void setScreenTimeOut(int value) {
		System.putInt(mContext.getContentResolver(), System.SCREEN_OFF_TIMEOUT,
				value);
	}

	public int getScreenTimeOut() {
		int time = 0;
		try {
			time = System.getInt(mContext.getContentResolver(),
					System.SCREEN_OFF_TIMEOUT);
		} catch (SettingNotFoundException e) {
			return time;
		}
		return time;
	}

}
