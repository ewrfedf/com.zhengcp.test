package com.deepview.penguin;

import java.io.FileNotFoundException;

import android.media.MediaPlayer;
import android.util.Log;

import com.metaio.unifeye.ndk.IUnifeyeMobileAndroid;
import com.metaio.unifeye.ndk.IUnifeyeMobileGeometry;
import com.metaio.unifeye.ndk.Vector3d;

public class PenguinActivity extends ARViewActivity{

	static {
		IUnifeyeMobileAndroid.loadNativeLibs();
	}
	private MediaPlayer song = null;
	private  IUnifeyeMobileGeometry mGeometryPenguin,mGeometryHead,mGeometryBody,mGeometryLeft,mGeometryRight,mGeometryFoot;
 
	private float rotation = 0;
	@Override
	protected void loadUnifeyeContents() {
		
		try {
			song = MediaPlayer.create(PenguinActivity.this, R.raw.penguin);
			loadTrackingData("Tracking_data1.xml");  
			mGeometryHead =loadGeometry("head.md2");
			mGeometryPenguin= loadGeometry("penguin.md2");
			mGeometryBody =loadGeometry("body.md2");
			mGeometryLeft =loadGeometry("left.md2");
			mGeometryRight =loadGeometry("right.md2");
			mGeometryFoot =loadGeometry("yingzi.md2");
			
			mGeometryPenguin.setName("penguin");
			mGeometryHead.setName("head");
			mGeometryBody.setName("body");
			mGeometryLeft.setName("left");
			mGeometryRight.setName("right");
			mGeometryFoot.setName("foot");
			
		 
			mactchTarget( mGeometryPenguin);
			mactchTarget( mGeometryHead);
			mactchTarget( mGeometryBody);
			mactchTarget( mGeometryLeft);
			mactchTarget( mGeometryRight);
			mactchTarget( mGeometryFoot); 
			
			
			
		} catch (FileNotFoundException e) {
			Log.e("mGeometryPenguin",""+e.toString() );
		}
	}
	private void mactchTarget(IUnifeyeMobileGeometry mGeometry ) {
		
		mGeometry.setMoveTranslation(new Vector3d(0,-110,0));
		mGeometry.setMoveRotation(new Vector3d(-1.57f,3.14f,0));
		mGeometry.setAnimationSpeed(130);
	}
	
	
	@Override
	protected void detectedTarget() {
		startAnimation("in");
	}
	
	
	private void startAnimation(String animationName) {
		mGeometryHead.startAnimation(animationName, false);
		mGeometryBody.startAnimation(animationName, false);
		mGeometryLeft.startAnimation(animationName, false);
		mGeometryRight.startAnimation(animationName, false);
		mGeometryFoot.startAnimation(animationName, false);
		mGeometryPenguin.startAnimation(animationName, false);
	}
	@Override
	protected void onGeometryTouched(IUnifeyeMobileGeometry geometry) {
		
//		rotation-=100;
//		Log.i("rotation", ""+rotation);
//		mGeometryPenguin.setMoveTranslation(new Vector3d(0,rotation,0));
		
		
		Log.i("touched geometry+++",geometry.getName());             
		if(!song.isPlaying()){
//			song.start();
		}
		if(geometry.getName().equals("penguin")){
			
		}else if(geometry.getName().equals("head")){
			startAnimation("head");
		}else if(geometry.getName().equals("body")){
			startAnimation("body");
		}else if(geometry.getName().equals("left")){
			startAnimation("left");
		}else if(geometry.getName().equals("right")){
			startAnimation("right");
		} else if(geometry.getName().equals("foot")){
			startAnimation("foot");
		}
		 
		 
	}
	 
	@Override
	protected void onPause() {
		super.onPause();
		song.release();
		
	}
 
	

}
