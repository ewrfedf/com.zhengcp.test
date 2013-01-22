package com.deepview.penguin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.metaio.unifeye.ndk.IUnifeyeMobileAndroid;

public class FlashActivity extends Activity {
	static {
		IUnifeyeMobileAndroid.loadNativeLibs();
	}

	
	private LinearLayout mMenuContainer;
	private LayoutParams mMenuItemLayoutParamters;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_flash); 


	}

	@Override
	protected void onStart() {
		super.onStart();

		if ( mMenuContainer == null )
		{
			mMenuContainer = (LinearLayout) findViewById(R.id.menuContainer);
			mMenuItemLayoutParamters = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			
			createMenuItem( "Penguin", PenguinActivity.class); 
		}
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
		mMenuContainer.removeAllViews();
		mMenuContainer = null;
	}
	
	private void createMenuItem(final String label, final Class<?> targetActivity )
	{
		Button button = new Button(this);
		button.setText(label);
		final Intent intent = new Intent(this, targetActivity);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				startActivity(intent);
			}
		});
		mMenuContainer.addView(button, mMenuItemLayoutParamters);
	}
}
