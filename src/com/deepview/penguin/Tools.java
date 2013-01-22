package com.deepview.penguin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.metaio.unifeye.ndk.Pose;
import com.metaio.unifeye.ndk.Vector3d;
import com.metaio.unifeye.ndk.Vector4d;

public class Tools {

	public static Vector3d convetQuaternionsToEuler(Vector4d v) {
		Vector3d resValue = new Vector3d(0, 0, 0);
		try {
			double resX = Math.atan2(
					2 * (v.getW() * v.getZ() + v.getX() * v.getY()),
					1 - 2 * (v.getZ() * v.getZ() + v.getX() * v.getX()));
			double resY = Math.asin(2 * (v.getW() * v.getX() - v.getY()
					* v.getZ()));
			double resZ = Math.atan2(
					2 * (v.getW() * v.getY() + v.getZ() * v.getX()),
					1 - 2 * (v.getX() * v.getX() + v.getY() * v.getY()));
			resValue.setX(getAngleFromRad(resX));
			resValue.setY(getAngleFromRad(resY));
			resValue.setZ(getAngleFromRad(resZ));
		} catch (Exception e) {
			Logger.log("convetQuaternionsToEuler err:\t" + e.getMessage());
		}
		return resValue;
	}

	public static Vector3d convetQuaternionsToRad(Vector4d v) {
		Vector3d resValue = new Vector3d(0, 0, 0);
		try {
			double resX = Math.atan2(
					2 * (v.getW() * v.getZ() + v.getX() * v.getY()),
					1 - 2 * (v.getZ() * v.getZ() + v.getX() * v.getX()));
			double resY = Math.asin(2 * (v.getW() * v.getX() - v.getY()
					* v.getZ()));
			double resZ = Math.atan2(
					2 * (v.getW() * v.getY() + v.getZ() * v.getX()),
					1 - 2 * (v.getX() * v.getX() + v.getY() * v.getY()));
			resValue.setX((float) resX);
			resValue.setY((float) resY);
			resValue.setZ((float) resZ);
		} catch (Exception e) {
			Logger.log("convetQuaternionsToEuler err:\t" + e.getMessage());
		}
		return resValue;
	}

	public static Vector3d convetQuaternionsToEuler(Pose p) {
		Vector4d v = p.getRotation();
		return convetQuaternionsToEuler(v);
	}

	public static float getAngleFromRad(double rad) {
		return (float) (rad / Math.PI * 180);
	}

	public static void copyFile(Context context, String dstName,
			InputStream is, boolean override) throws IOException {

		String targetPath;

		File f = context.getFilesDir();
		targetPath = f.getAbsolutePath();

		byte[] buffer = new byte[4096];
		int len;
		File file;

		FileOutputStream fos = null;
		try {
			file = new File(targetPath + "/" + dstName);

			boolean copyContents = false;

			if (file.exists()) {
				// log(Log.WARN, "File already exists: "+path);

				if (override) {
					// log(Log.WARN, "Deleting existing file: "+path);
					file.delete();
					copyContents = true;
				}
			} else
				copyContents = true;

			if (copyContents) {
				file.createNewFile();

				// log("Copying contents: " + file);

				// copy the file from the assets
				fos = new FileOutputStream(file);
				while ((len = is.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}
}
