package com.example.datademo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class FileUtil {
	private static int bufferd = 1024;

	private FileUtil() {
	}

	/*
	 * <!-- 在SDCard中创建与删除文件权限 --> <uses-permission
	 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/> <!--
	 * 往SDCard写入数据权限 --> <uses-permission
	 * android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	 */

	// =================get SDCard information===================
	public static boolean isSdcardAvailable() {
		String status = Environment.getExternalStorageState();
		//Environment.MEDIA_MOUNTED表示SD卡正常挂载
		//sd卡   http://blog.csdn.net/yuzhiboyi/article/details/8645730
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	public static long getSDAllSizeKB() {
		// get path of sdcard
		File path = Environment.getExternalStorageDirectory();
		//StatFs获取的都是以block为单位的   http://blog.csdn.net/pang3510726681/article/details/6969557
		StatFs sf = new StatFs(path.getPath());
		// get single block size(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		long allBlocks = sf.getBlockCount();
		// 返回SD卡大小
		return (allBlocks * blockSize) / 1024; // KB
	}

	/**
	 * free size for normal application
	 * 
	 * @return
	 */
	public static long getSDAvalibleSizeKB() {
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		long blockSize = sf.getBlockSize();
		long avaliableSize = sf.getAvailableBlocks();
		return (avaliableSize * blockSize) / 1024;// KB
	}

	// =====================File Operation==========================
	/**
	 * @param director 文件夹名称
	 * @return
	 */
	public static boolean isFileExist(String director) {
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + director);
		return file.exists();
	}

	/**
	 * create multiple director
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createFile(String director) {
		if (isFileExist(director)) {
			return true;
		} else {
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + director);
			if (!file.mkdirs()) {
				return false;
			}
			return true;
		}
	}

	public static File writeToSDCardFile(String directory, String fileName,
			String content, boolean isAppend) {
		return writeToSDCardFile(directory, fileName, content, "", isAppend);
	}

	/**
	 * 
	 * @param director
	 *            (you don't need to begin with
	 *            Environment.getExternalStorageDirectory()+File.separator)
	 * @param fileName
	 * @param content
	 * @param encoding
	 *            (UTF-8...)
	 * @param isAppend
	 *            : Context.MODE_APPEND
	 * @return
	 */
	public static File writeToSDCardFile(String directory, String fileName,
			String content, String encoding, boolean isAppend) {
		// mobile SD card path +path
		File file = null;
		OutputStream os = null;
		try {
			if (!createFile(directory)) {
				return file;
			}
			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + directory + File.separator + fileName);
			os = new FileOutputStream(file, isAppend);
			if (encoding.equals("")) {
				os.write(content.getBytes());
			} else {
				os.write(content.getBytes(encoding));
			}
			os.flush();
		} catch (IOException e) {
			Log.e("FileUtil", "writeToSDCardFile:" + e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * write data from inputstream to SDCard
	 */
	public static File writeToSDCardFromInput(String directory, String fileName,
			InputStream input) {
		File file = null;
		OutputStream os = null;
		try {
			if (createFile(directory)) {
				return file;
			}
			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + directory + File.separator + fileName);
			os = new FileOutputStream(file);
			byte[] data = new byte[bufferd];
			int length = -1;
			while ((length = input.read(data)) != -1) {
				os.write(data, 0, length);
			}
			// clear cache
			os.flush();
		} catch (Exception e) {
			Log.e("FileUtil", "" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static String ReadFromSDCardFile(String directory,String fileName){
		String res=""; 
		File file = null;
		file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + directory + File.separator + fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();
			byte [] buffer = new byte[length]; 
			fis.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fis.close();
			return res;
		}catch (FileNotFoundException  e) {
			// TODO Auto-generated catch block
			Log.d("TEST", "FileNotFound");
			e.printStackTrace();
		}catch (Exception  e) {
			Log.d("TEST", "Can Not Open File");
			e.printStackTrace();
		}
		return null; 	
	}
}
