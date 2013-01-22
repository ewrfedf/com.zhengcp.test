package com.deepview.penguin;

public abstract class Configuration {
 
	public static final String signature = "rtF3e/EZx1O4b9WxKklJ6UGZVRflr0fAPsY0xpfB+qg=";

	public abstract class Camera
	{
		public static final long resolutionX = 480;  	
		public static final long resolutionY = 320;
		/*
		 * 0: normal camera
		 * 1: front facing camera
		 */
		public static final int deviceId = 0;
	}
}
