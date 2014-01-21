package com.hwanee.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hwanee.devicemanager.R;
import com.hwanee.manager.DeviceData;
import com.hwanee.manager.DeviceManager;

public class MannerModeWidget extends AppWidgetProvider {
	public static final String ACTION_BTN = "com.hwanee.appwiget.MannerModeWidget.ACTION_BTN";
	public Context mContext;
	public static int mMediaVolume = 0;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		mContext = context;

		for (int i = 0; i < appWidgetIds.length; i++) {
			int widgetId = appWidgetIds[i];
			RemoteViews remoteView = new RemoteViews(context.getPackageName(),
					R.layout.silent_widget_layout);
			appWidgetManager.updateAppWidget(widgetId, remoteView);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		String action = intent.getAction();
		RemoteViews remoteView = new RemoteViews(context.getPackageName(),
				R.layout.silent_widget_layout);
		Log.i("device", "onReceive() action = " + action);

		// Default Recevier
		if (AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action)) {

		} else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			initUI(context, manager, manager.getAppWidgetIds(new ComponentName(
					context, getClass())));
		} else if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {

		} else if (AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action)) {

		}

		if (action.equals(ACTION_BTN)) {
			Log.i("device", "onReceive() action.equals(ACTION_BTN) = true");
			boolean mode = DeviceManager.getDeviceManager(context)
					.getSwitchMode(DeviceData.SOUND_ONOFF);
			Log.i("device", "onReceive() mode = " + mode);
			if (mode) {
				mMediaVolume = Integer.valueOf(DeviceManager
						.getDeviceManager(context)
						.getDeviceVolume(AudioManager.STREAM_MUSIC)
						.get(DeviceData.MEDIA_VOLUME));
				DeviceManager.getDeviceManager(context).setDeviceVolume(
						AudioManager.STREAM_MUSIC, 0);
				DeviceManager.getDeviceManager(context).setSoundMode(
						DeviceData.VIBRATE);

				Toast.makeText(context, "Vibrate Mode", Toast.LENGTH_SHORT)
						.show();
			} else {
				DeviceManager.getDeviceManager(context).setSoundMode(
						DeviceData.SOUND);
				DeviceManager.getDeviceManager(context).setDeviceVolume(
						AudioManager.STREAM_MUSIC, mMediaVolume);
				Toast.makeText(context, "Sound Mode", Toast.LENGTH_SHORT)
						.show();
			}
			Bitmap bitmap = getBitmap(context);
			if (bitmap != null) {
				remoteView.setImageViewBitmap(R.id.SilentWidgetButton, bitmap);
				ComponentName silentBtn = new ComponentName(context,
						MannerModeWidget.class);
				(AppWidgetManager.getInstance(context)).updateAppWidget(
						silentBtn, remoteView);
			}
		}
	}

	/**
	 * UI 설정 이벤트 설정
	 */
	public void initUI(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i("device",
				"======================= initUI() =======================");
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.silent_widget_layout);
		Intent intent = new Intent(ACTION_BTN);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		views.setOnClickPendingIntent(R.id.SilentWidgetButton, pendingIntent);

		for (int appWidgetId : appWidgetIds) {
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		Bitmap bitmap = getBitmap(context);
		if (bitmap != null) {
			views.setImageViewBitmap(R.id.SilentWidgetButton, bitmap);
			ComponentName silentBtn = new ComponentName(context,
					MannerModeWidget.class);
			(AppWidgetManager.getInstance(context)).updateAppWidget(silentBtn,
					views);
		}
	}

	public Bitmap getBitmap(Context context) {
		int id = R.drawable.on_button;
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				60, context.getResources().getDisplayMetrics());
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				65, context.getResources().getDisplayMetrics());
		if (!DeviceManager.getDeviceManager(context).getSwitchMode(
				DeviceData.SOUND_ONOFF)) {
			id = R.drawable.off_button;
		}
		Bitmap bitmap = BitmapFactory
				.decodeResource(context.getResources(), id);
		bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
		return bitmap;
	}
}
