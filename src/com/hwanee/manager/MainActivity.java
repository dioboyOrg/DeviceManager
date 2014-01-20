package com.hwanee.manager;

import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;

import com.hwanee.soundmanager.R;

public class MainActivity extends Activity {

	private SeekBar mBrightnessLevelBar = null;
	private SeekBar mMediaVolumeBar = null;
	private SeekBar mRingVolumeBar = null;
	private SeekBar mNotificationVolumeBar = null;
	private SeekBar mCallVolumeBar = null;
	private SeekBar mAlarmVolumeBar = null;
	private SeekBar mSystemVolumeBar = null;
	private SeekBar mVibrateRingerLevelBar = null;
	private SeekBar mVibrateNotificationLevelBar = null;

	private Switch mBrightness = null;
	private Switch mSound = null;
	private Switch mVibrateRinger = null;
	private Switch mVibrateNotification = null;
	private Switch mSilentSwitch = null;

	private CheckBox mBrightnessMode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActivity();
	}

	@Override
	protected void onResume() {
		initSeekBar();
		initSwitch();
		initBrightnessMode();
		setSwitchState();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void initActivity() {
		initSeekBar();
		initSwitch();
		initBrightnessMode();
		setSwitchState();
	}

	private void initBrightnessMode() {
		mBrightnessMode = (CheckBox) findViewById(R.id.ControlActBrightnessMode);
		mBrightnessMode.setOnCheckedChangeListener(mBrightnessModeListener);
		mBrightnessMode.setChecked(DeviceManager.getDeviceManager(this)
				.getBrightnessMode());
	}

	private void initSwitch() {
		mBrightness = (Switch) findViewById(R.id.ControlActBrightnessOnOffSwitch);
		mBrightness.setOnCheckedChangeListener(mBrightnessSwitchListener);
		mSound = (Switch) findViewById(R.id.ControlActSoundOnOffSwitch);
		mSound.setOnCheckedChangeListener(mSoundSwitchListener);
		mVibrateRinger = (Switch) findViewById(R.id.ControlActVibrateRingerOnOffSwitch);
		mVibrateNotification = (Switch) findViewById(R.id.ControlActVibrateNotificationOnOffSwitch);
		mVibrateRinger.setOnCheckedChangeListener(mVibrateSwitchListener);
		mSilentSwitch = (Switch) findViewById(R.id.ControlActSilentSwitch);
		mVibrateNotification.setOnCheckedChangeListener(mVibrateSwitchListener);
		mSilentSwitch = (Switch) findViewById(R.id.ControlActSilentSwitch);
		mSilentSwitch.setOnCheckedChangeListener(mSilentSwitchListener);
	}

	private void initSeekBar() {
//		initBrightLevelSeekBar();
		initMediaVolumeBar();
		initRingVolumeBar();
		initNotificationBar();
		initCallVolumeBar();
		initAlarmVolumeBar();
		initSystemVolumeBar();
		initVibrateRingerLevelBar();
		initVibrateNotificationLevelBar();
	}

	private void initBrightLevelSeekBar() {
		mBrightnessLevelBar = (SeekBar) findViewById(R.id.ControlActBrightnessLevelSeekBar);
		if (mBrightnessLevelBar != null) {
			mBrightnessLevelBar.setMax(255);
			mBrightnessLevelBar.setProgress(DeviceManager
					.getDeviceManager(this).getBrightness());
			mBrightnessLevelBar
					.setOnSeekBarChangeListener(mBrightnessLevelBarListener);
		}
	}

	private void initMediaVolumeBar() {
		HashMap<String, String> data = DeviceManager.getDeviceManager(this)
				.getDeviceVolume(AudioManager.STREAM_MUSIC);
		mMediaVolumeBar = (SeekBar) findViewById(R.id.ControlActMediaVolumeSeekBar);

		if (mMediaVolumeBar == null) {
			return;
		}
		mMediaVolumeBar.setMax(Integer.valueOf(data
				.get(DeviceData.MAXIMUM_MEDIA_VOLUME)));
		mMediaVolumeBar.setProgress(Integer.valueOf(data
				.get(DeviceData.MEDIA_VOLUME)));
		mMediaVolumeBar.setOnSeekBarChangeListener(mMediaVolumeBarListener);
	}

	private void initRingVolumeBar() {
		HashMap<String, String> data = DeviceManager.getDeviceManager(this)
				.getDeviceVolume(AudioManager.STREAM_RING);
		mRingVolumeBar = (SeekBar) findViewById(R.id.ControlActRingVolumeSeekBar);
		if (mRingVolumeBar == null) {
			return;
		}
		mRingVolumeBar.setMax(Integer.valueOf(data
				.get(DeviceData.MAXIMUM_RING_VOLUME)));
		mRingVolumeBar.setProgress(Integer.valueOf(data
				.get(DeviceData.RING_VOLUME)));
		mRingVolumeBar.setOnSeekBarChangeListener(mRingVolumeBarListener);
	}

	private void initNotificationBar() {
		HashMap<String, String> data = DeviceManager.getDeviceManager(this)
				.getDeviceVolume(AudioManager.STREAM_NOTIFICATION);
		mNotificationVolumeBar = (SeekBar) findViewById(R.id.ControlActNotificationVolumeSeekBar);
		if (mNotificationVolumeBar == null) {
			return;
		}
		mNotificationVolumeBar.setMax(Integer.valueOf(data
				.get(DeviceData.MAXIMUM_NOTIFICATION_VOLUME)));
		mNotificationVolumeBar.setProgress(Integer.valueOf(data
				.get(DeviceData.NOTIFICATION_VOLUME)));
		mNotificationVolumeBar
				.setOnSeekBarChangeListener(mNotificationVolumeBarListener);
	}

	private void initCallVolumeBar() {
		HashMap<String, String> data = DeviceManager.getDeviceManager(this)
				.getDeviceVolume(AudioManager.STREAM_VOICE_CALL);
		mCallVolumeBar = (SeekBar) findViewById(R.id.ControlActCallVolumeSeekBar);
		if (mCallVolumeBar == null) {
			return;
		}
		mCallVolumeBar.setMax(Integer.valueOf(data
				.get(DeviceData.MAXIMUM_CALL_VOLUME)));
		mCallVolumeBar.setProgress(Integer.valueOf(data
				.get(DeviceData.CALL_VOLUME)));
		mCallVolumeBar.setOnSeekBarChangeListener(mCallVolumeBarListener);
	}

	private void initAlarmVolumeBar() {
		HashMap<String, String> data = DeviceManager.getDeviceManager(this)
				.getDeviceVolume(AudioManager.STREAM_ALARM);
		mAlarmVolumeBar = (SeekBar) findViewById(R.id.ControlActAlarmVolumeSeekBar);
		if (mAlarmVolumeBar == null) {
			return;
		}
		mAlarmVolumeBar.setMax(Integer.valueOf(data
				.get(DeviceData.MAXIMUM_ALARM_VOLUME)));
		mAlarmVolumeBar.setProgress(Integer.valueOf(data
				.get(DeviceData.ALARM_VOLUME)));
		mAlarmVolumeBar.setOnSeekBarChangeListener(mAlarmVolumeBarListener);
	}

	private void initSystemVolumeBar() {
		HashMap<String, String> data = DeviceManager.getDeviceManager(this)
				.getDeviceVolume(AudioManager.STREAM_SYSTEM);
		mSystemVolumeBar = (SeekBar) findViewById(R.id.ControlActSystemVolumeSeekBar);
		mSystemVolumeBar.setOnSeekBarChangeListener(mSystemVolumeBarListener);

		if (mSystemVolumeBar == null) {
			return;
		}
		mSystemVolumeBar.setMax(Integer.valueOf(data
				.get(DeviceData.MAXIMUM_SYSTEM_VOLUME)));
		mSystemVolumeBar.setProgress(Integer.valueOf(data
				.get(DeviceData.SYSTEM_VOLUME)));
	}

	private void initVibrateRingerLevelBar() {
		mVibrateRingerLevelBar = (SeekBar) findViewById(R.id.ControlActVibrateLevelSeekBar);
		mVibrateRingerLevelBar
				.setOnSeekBarChangeListener(mVibrateRingerLevelBarListener);
		if (mVibrateRingerLevelBar == null) {
			return;
		}
	}

	private void initVibrateNotificationLevelBar() {
		mVibrateNotificationLevelBar = (SeekBar) findViewById(R.id.ControlActVibrateLevelSeekBar);
		mVibrateNotificationLevelBar
				.setOnSeekBarChangeListener(mVibrateNotificationLevelBarListener);
		if (mVibrateNotificationLevelBar == null) {
			return;
		}
	}

	private void setSwitchState() {
//		setLightSwitchState();
		setSoundSwitchState();
		setVibrateRingerSwitchState();
		setVibrateNotificationSwitchState();
		setSilentSwitchState();
	}

	private void setLightSwitchState() {
		if (mBrightness == null) {
			return;
		}
		mBrightness.setChecked(DeviceManager.getDeviceManager(
				getApplicationContext()).getSwitchMode(
				DeviceData.BRIGHTNESS_ONOFF));
	}

	private void setSoundSwitchState() {
		if (mSound == null) {
			return;
		}
		mSound.setChecked(DeviceManager.getDeviceManager(
				getApplicationContext()).getSwitchMode(DeviceData.SOUND_ONOFF));
	}

	private void setVibrateRingerSwitchState() {
		if (mVibrateRinger == null) {
			return;
		}
		mVibrateRinger.setChecked(DeviceManager.getDeviceManager(
				getApplicationContext()).getSwitchMode(
				DeviceData.VIBRATE_RINGER_ONOFF));
	}

	private void setVibrateNotificationSwitchState() {
		if (mVibrateNotification == null) {
			return;
		}
		mVibrateNotification.setChecked(DeviceManager.getDeviceManager(
				getApplicationContext()).getSwitchMode(
				DeviceData.VIBRATE_NOTIFICATION_ONOFF));
	}

	private void setSilentSwitchState() {

		if (mSilentSwitch == null) {
			return;
		}
		mSilentSwitch
				.setChecked(DeviceManager.getDeviceManager(
						getApplicationContext()).getSwitchMode(
						DeviceData.SILENT_ONOFF));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	OnCheckedChangeListener mBrightnessSwitchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
		}
	};

	OnCheckedChangeListener mSoundSwitchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setSoundMode(
							isChecked ? DeviceData.SOUND : DeviceData.VIBRATE);
			if (isChecked) {
				mSilentSwitch.setChecked(!isChecked);
			}
		}
	};

	OnCheckedChangeListener mVibrateSwitchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
		}
	};

	OnCheckedChangeListener mSilentSwitchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setSoundMode(
							isChecked ? DeviceData.SILENT_ON
									: DeviceData.SILENT_OFF);
		}
	};

	OnSeekBarChangeListener mBrightnessLevelBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// DeviceManager.getDeviceManager(getApplicationContext()).setBrightness(seekBar.getProgress());
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setBrightness(progress);
		}
	};

	OnSeekBarChangeListener mMediaVolumeBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setDeviceVolume(AudioManager.STREAM_MUSIC,
							seekBar.getProgress());
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mRingVolumeBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setDeviceVolume(AudioManager.STREAM_RING,
							seekBar.getProgress());
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mNotificationVolumeBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setDeviceVolume(AudioManager.STREAM_NOTIFICATION,
							seekBar.getProgress());

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mCallVolumeBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setDeviceVolume(AudioManager.STREAM_VOICE_CALL,
							seekBar.getProgress());

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mAlarmVolumeBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setDeviceVolume(AudioManager.STREAM_ALARM,
							seekBar.getProgress());

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mSystemVolumeBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setDeviceVolume(AudioManager.STREAM_SYSTEM,
							seekBar.getProgress());

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mVibrateRingerLevelBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnSeekBarChangeListener mVibrateNotificationLevelBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

		}
	};

	OnCheckedChangeListener mBrightnessModeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			DeviceManager.getDeviceManager(getApplicationContext())
					.setBrightnessMode(
							isChecked ? System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
									: System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		}
	};
}
