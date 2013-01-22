package com.deepview.penguin;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.metaio.tools.io.AssetsManager;
import com.metaio.unifeye.UnifeyeDebug;

public class PenguinApplication extends Application {

	/**
	 * true if application is debuggable
	 */
	public static boolean isDebuggable;
	private String rootPath = "";

	@Override
	public void onCreate() {
		super.onCreate();

		try {
			rootPath = Environment.getExternalStorageDirectory()
					+ "/dcim/AR_PIC/";
			File f = new File(rootPath);
			if(!f.exists())
			{
				f.mkdir();
				Logger.log("创建路径：\t"+rootPath);
			}
			PackageManager pm = getPackageManager();
			ApplicationInfo ai = new ApplicationInfo();
			ai = pm.getApplicationInfo(getPackageName(), 0);
			isDebuggable = ((ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE);
			UnifeyeDebug.enableLogging(isDebuggable);
			extractAssets();
		} catch (Exception e) {

		}

		UnifeyeDebug.log("ExampleApplication.onCreate");

	}

	@Override
	public void onLowMemory() {
		UnifeyeDebug.log(Log.WARN, "ExampleApplication.onLowMemory");
	}

	/**
	 * Display log messages with debug priority 优先
	 * 
	 * @param msg
	 *            Message to display
	 * @see Log#d(String, String)
	 */
	// public static void log(String msg)
	// {
	// if (isDebuggable && msg != null)
	// Log.d(TAG, msg);
	//
	// }

	/**
	 * Display log messages with the given priority
	 * 
	 * @param priority
	 *            Priority, e.g. Log.INFO, Log.ERROR
	 * @param msg
	 *            Message to display
	 * 
	 * @see Log
	 */
	// public static void log(int priority, String msg)
	// {
	// if (isDebuggable && msg != null)
	// {
	// Log.println(priority, TAG, msg);
	// }
	// }
	//
	/**
	 * Extract all the assets.
	 * <p>
	 * If application has lots of assets, this should not be called on main
	 * thread
	 * 
	 * @return true on success
	 */
	public boolean extractAssets() {
		try {
			AssetsManager.extractAllAssets(this, true);
			return true;
		} catch (Exception e) {
			UnifeyeDebug.log(Log.ERROR,
					"Error extracting assets: " + e.getMessage());
			return false;
		}
	}

	public static void unbindDrawables(View view) {
		try {
			if (view.getBackground() != null)

				view.getBackground().setCallback(null);

			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindDrawables(((ViewGroup) view).getChildAt(i));
				}
				((ViewGroup) view).removeAllViews();
			}
		} catch (Exception e) {
		}

	}

	public String saveBitmap(Bitmap bitmap) {
		String resValue=null;
		try {
			String fullName = rootPath+"M_"+System.currentTimeMillis()+".jpg";
			File f= new File(fullName);
			FileOutputStream fos = new FileOutputStream(f);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
				fos.flush();
				fos.close();
				Logger.log("save ok");
				resValue = fullName;
			}
			if(bitmap!=null && !bitmap.isRecycled())
				bitmap.recycle();
		} catch (Exception e) {
			Logger.log("saveBitmap err:\t" + e.getMessage());
		}
		return resValue;
	}
}
