package com.xunbo.store.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.educonsult.R;
import com.xunbo.store.adapters.TextItemCenterListAdapter;
import com.xunbo.store.adapters.TextItemListAdapter;
import com.xunbo.store.myviews.BadgeView;

/**
 * 公用工具类
 * 
 * @author Qzr
 * 
 */
public class Util {
	private static final String ALGORITHM = "RSA";
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int CACHE_TIME = 60*60000;//缓存失效时间
	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();
	private static Context context;
	private static BadgeView badge;
	private static ArrayList<String> list;
	private static PopupWindow popu;
	private static LayoutInflater inflater;
	private static View v_fenlei;
	private static ListView list_2,lv_l;
	private static TextItemListAdapter adapter_r;
	private static TextItemCenterListAdapter textItemCenterListAdapter;
	private static int postion;

	public Util(Context context){
		this.context = context;
	}


	/**
	 * 获取屏幕宽度
	 * 
	 * @param activity
	 * @return 屏幕宽度px单位
	 */
	public static int getWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param activity
	 * @return 屏幕高度px单位
	 */
	public static int getHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeigh = dm.heightPixels;
		return screenHeigh;
	}
	
	

	/**
	 * 清除保存的缓存
	 */
	public void cleanCookie()
	{
		//		removeProperty(AppConfig.CONF_COOKIE);
	}

	/**
	 * 判断缓存数据是否可读
	 * @param cachefile
	 * @return
	 */
	public boolean isReadDataCache(String cachefile)
	{
		return readObject(cachefile) != null;
	}

	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	public boolean isExistDataCache(String cachefile)
	{
		boolean exist = false;
		File data = context.getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}

	/**
	 * 判断缓存是否失效
	 * @param cachefile
	 * @return
	 */
	public boolean isCacheDataFailure(String cachefile)
	{
		boolean failure = false;
		File data = context.getFileStreamPath(cachefile);
		if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
			failure = true;
		else if(!data.exists())
			failure = true;
		return failure;
	}

	/**
	 * 清除缓存目录
	 * @param dir 目录
	 * @param numDays 当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {          
		int deletedFiles = 0;         
		if (dir!= null && dir.isDirectory()) {             
			try {                
				for (File child:dir.listFiles()) {    
					if (child.isDirectory()) {              
						deletedFiles += clearCacheFolder(child, curTime);          
					}  
					if (child.lastModified() < curTime) {     
						if (child.delete()) {                   
							deletedFiles++;           
						}    
					}    
				}             
			} catch(Exception e) {       
				e.printStackTrace();    
			}     
		}       
		return deletedFiles;     
	}

	/**
	 * 将对象保存到内存缓存中
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}

	/**
	 * 从内存缓存中获取对象
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key){
		return memCacheRegion.get(key);
	}

	/**
	 * 保存磁盘缓存
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setDiskCache(String key, String value) throws IOException {
		FileOutputStream fos = null;
		try{
			fos = context.openFileOutput("cache_"+key+".data", Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.flush();
		}finally{
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * 获取磁盘缓存数据
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getDiskCache(String key) throws IOException {
		FileInputStream fis = null;
		try{
			fis = context.openFileInput("cache_"+key+".data");
			byte[] datas = new byte[fis.available()];
			fis.read(datas);
			return new String(datas);
		}finally{
			try {
				fis.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			File files = Environment.getExternalStorageDirectory();
			//fos=new FileOutputStream(files);
			fos = context.openFileOutput(file, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}

	/**
	 * 读取对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = context.openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if(e instanceof InvalidClassException){
				File data = context.getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}

	/**
	 * 获取package name
	 * @return
	 */
	private String getAppInfo() {
		try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(
					pkName, 0).versionName;
			int versionCode = context.getPackageManager()
					.getPackageInfo(pkName, 0).versionCode;
			return pkName + "   " + versionName + "  " + versionCode;
		} catch (Exception e) {
		}
		return null;
	}


	/**
	 * 下载并缓存图片到磁盘
	 */
	public void saveiamgetoload(final String url,final DiskLruCache mDiskLruCache){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String key = hashKeyForDisk(url);
					DiskLruCache.Editor editor = mDiskLruCache.edit(key);
					if (editor != null) {
						OutputStream outputStream = editor.newOutputStream(0);
						if (downloadUrlToStream(url, outputStream)) {
							editor.commit();
						} else {
							editor.abort();
						}
					}
					mDiskLruCache.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	/**
	 * 获取缓存图片地址
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	@SuppressLint("NewApi")
	public File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}
	/**
	 * 获取版本号
	 * @param context
	 * @return
	 */
	public int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**
	 * 缓存图片到本地
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
			out = new BufferedOutputStream(outputStream, 8 * 1024);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
			System.out.println("下载图片时出异常了"+e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
				System.out.println("下载图片时出异常了"+e);
			}
		}
		return false;
	}
	/**
	 * 使用MD5对图片url进行编码 获取图片名称
	 * @param key
	 * @return
	 */
	public String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	/**
	 * 读取缓存图片
	 */
	public Bitmap getBitmaoForCahe(String imageUrl){
		Bitmap bitmap = null;
		String key = hashKeyForDisk(imageUrl);
		try {
			File cacheDir = getDiskCacheDir(context, "bitmap");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			DiskLruCache mD = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);//图片缓存
			DiskLruCache.Snapshot snapShot = mD.get(key);
			if (snapShot != null) {  
				InputStream is = snapShot.getInputStream(0);  
				bitmap = BitmapFactory.decodeStream(is);  
			}  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取缓存图片时出异常了");
		}
		return bitmap;
	}

	// 获取指定路径的图片  
	public static Bitmap getBitmapForNet(String urlpath)  
			throws Exception {  
		URL url = new URL(urlpath);  
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
		conn.setRequestMethod("GET");  
		conn.setConnectTimeout(5 * 1000);  
		Bitmap bitmap = null;  
		if (conn.getResponseCode() == 200) {  
			InputStream inputStream = conn.getInputStream();  
			bitmap = BitmapFactory.decodeStream(inputStream);  
		}  
		return bitmap;  
	}

	public static Bitmap drawableToBitmap(Drawable drawable){     
		int width = drawable.getIntrinsicWidth();     
		int height = drawable.getIntrinsicHeight();     
		Bitmap bitmap = Bitmap.createBitmap(width, height,     
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888     
						: Bitmap.Config.RGB_565);     
		Canvas canvas = new Canvas(bitmap);     
		drawable.setBounds(0,0,width,height);     
		drawable.draw(canvas);     
		return bitmap;     
	}  
	public void saveMyBitmap(String Ffile,String bitName,Bitmap mBitmap) throws IOException {
		File f = new File(Ffile +"/"+ bitName + ".png");
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Bitmap getbitmapforcahe(String fname){

		Bitmap bt = BitmapFactory.decodeFile(fname);
		return bt;
	}



	/**
	 * 判断是否是电话号码
	 * 
	 * @param mobiles
	 * @return true是电话号码
	 */
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * 判断email格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * 密码位数限制，大于6位，小于20位
	 * 
	 * @param password
	 * @return
	 */
	public static boolean ispassword(String password) {
		String str = "[0-9A-Za-z]{6,20}";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(password);

		return m.matches();
	}
	/**
	 * 用户名位数&输入限制，大于4位，小于20位
	 * @param username
	 * @return
	 */
	public static boolean isusername(String username) {
		String str = "[0-9A-Za-z_-]{4,20}";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(username);

		return m.matches();
	}



	/**
	 * 检查开头或结尾是否有空格
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isnullstring(String password) {
		String str = "\\s+";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(password);

		return m.matches();
	}

	/**
	 * 判断字符串是否为空切不为null
	 * 
	 * @param text
	 * @return true 不为空
	 */
	public static boolean IsNull(String text) {
		if (text != null && !text.equals("")&&!text.equals("null")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取图片宽度和高度
	 * 
	 * @param id
	 * @param resources
	 * @return
	 */
	public static int[] getBitmapWidth(int id, Resources resources) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeResource(resources, id, opts);
		return new int[] { opts.outWidth, opts.outHeight };
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param act
	 * @return ture可用
	 */
	public static boolean detect(Context act) {
		ConnectivityManager manager = (ConnectivityManager) act
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 把时间戳转化成时间类型
	 * 
	 * @param str
	 *            时间类型格式
	 * @param time
	 *            时间戳 单位毫秒
	 * @return
	 */
	public static String getData(String str, long time) {
		// yyyy-MM-dd hh:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String date = sdf.format(new Date(time));
		return date;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param str
	 *            时间类型格式
	 * @return
	 */
	public static String getdata(String str) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

	//	/**
	//	 * 启动Loding...
	//	 * 
	//	 * @param context
	//	 */
	//	public static void startProgressDialog(Context context) {
	//		if (progressDialog == null) {
	//			progressDialog = CustomProgressDialog.createDialog(context);
	//		}
	//
	//		progressDialog.show();
	//	}

	//	/**
	//	 * 关闭Loding...
	//	 */
	//	public static void stopProgressDialog() {
	//		if (progressDialog != null && progressDialog.isShowing()) {
	//			progressDialog.dismiss();
	//			progressDialog = null;
	//		}
	//	}

	/**
	 * 获取外置sd卡的根路径，如果沒有外置sd卡，則返回null
	 * 
	 * @return
	 */
	public String getSdPath() {
		String sdcard_path = null;
		String sd_default = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (sd_default.endsWith("/")) {
			sd_default = sd_default.substring(0, sd_default.length() - 1);
		}
		// 得到路径
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;
				if (line.contains("fat") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				} else if (line.contains("fuse") && line.contains("/mnt/")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						if (sd_default.trim().equals(columns[1].trim())) {
							continue;
						}
						sdcard_path = columns[1];
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdcard_path;
	}

	/**
	 * 呼出Toast
	 * 
	 * @param context
	 * @param content
	 */
	public static void ShowToast(Context context, String content) {
		Toast.makeText(context, content, 1000).show();
	}

	/**
	 * 呼出Toast
	 * 
	 * @param context
	 * @param i
	 */
	public static void ShowToast(Context context, int i) {
		Toast.makeText(context, i, 1000).show();
	}

	/**
	 * 重新计算子listview高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * 把时间戳转化成时间类型
	 * 
	 * @param str
	 *            时间类型格式
	 * @param time
	 *            时间戳 单位毫秒
	 * @return
	 */
	public static String getTimeForData(String str, long time) {
		// yyyy-MM-dd hh:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String date = sdf.format(new Date(time));
		return date;
	}

	//	public static String sign(String content, String privateKey) {
	//		try {
	//			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
	//					Base64.decode(privateKey));
	//			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
	//			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
	//
	//			java.security.Signature signature = java.security.Signature
	//					.getInstance(SIGN_ALGORITHMS);
	//
	//			signature.initSign(priKey);
	//			signature.update(content.getBytes(DEFAULT_CHARSET));
	//
	//			byte[] signed = signature.sign();
	//
	//			return Base64.encode(signed);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//
	//		return null;
	//	}
	/**	 *
	 *  绘制图标右上角的未读消息数量显示	 *
	 *    @param context	 *
	 *                上下文	 *
	 *                 @param icon	 *
	 *                             需要被添加的icon的资源ID	 *
	 *                              @param news	 *            
	 *                              未读的消息数量	 * @return drawable	
	 *                               */
	@SuppressWarnings("unused")	
	public static Drawable displayNewsNumber(Context context, int icon, int news) {		
		// 初始化画布		
		int iconSize = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);		
		// Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,		
		// Config.ARGB_8888);		
		Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), icon);		
		Canvas canvas = new Canvas(iconBitmap);		
		// 拷贝图片		
		Paint iconPaint = new Paint();		
		iconPaint.setDither(true);
		// 防抖动		
		iconPaint.setFilterBitmap(true);
		// 用来对Bitmap进行滤波处理		
		Rect src = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());		
		Rect dst = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());		
		canvas.drawBitmap(iconBitmap, src, dst, iconPaint);		
		// 启用抗锯齿和使用设备的文本字距		
		Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);		
		countPaint.setColor(Color.RED);		canvas.drawCircle(iconSize - 13, 20, 10, countPaint);		
		Paint textPaint = new Paint();		textPaint.setColor(Color.WHITE);		
		// textPaint.setTypeface(Typeface.DEFAULT_BOLD);		
		textPaint.setTextSize(19f);		
		canvas.drawText(String.valueOf(news), iconSize - 18, 27, textPaint);		
		return new BitmapDrawable(iconBitmap);	
	}
	/**
	 * 设置右上角小红点&数量
	 * @param context
	 * @param v
	 * @param num
	 */
	public static void SetRedNum(Context context,View v,int num){
		BadgeView badge = new BadgeView(context, v);
		badge.setText(""+num);
		badge.show();
	}
	/**
	 * 取消右上角小红点&数量
	 * @param context
	 * @param v
	 */
	public static void SetRedGone(Context context,View v){
		if(badge!=null){
			badge.toggle();
		}
	}
	
	// 从资源中获取Bitmap
	public static Bitmap getBitmapFromResources(Context context2, int resId) {
	Resources res = context2.getResources();
	return BitmapFactory.decodeResource(res, resId);
	}


	/**
	 * 得到并缓存图片
	 */
	public static void Getbitmap(final ImageView v,final String url){
		Thread thread;
		final Handler handler;
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(v!=null){
					v.setTag(url);
				if (msg.what == 1) {
					v.setTag(url);
					if(msg.obj != null){
						v.setImageBitmap((Bitmap) msg.obj);
					}else{
//						 Bitmap bitmap = getBitmapFromResources(context, R.drawable.default_bg);
//						 v.setImageBitmap(bitmap);
						v.setBackgroundResource(R.drawable.default_bg);
					}
					//					mViewSwitcher.showNext();
				} else {
					//yToastMessage(ImageDialog.this, ErrMsg);
					//finish();
					v.setBackgroundResource(R.drawable.default_bg);
//					Resources r = context.getResources();
//					InputStream is = r.openRawResource(R.drawable.default_bg);
//					BitmapDrawable  bmpDraw = new BitmapDrawable(is);
//					Bitmap bitmap = bmpDraw.getBitmap();
////					 Bitmap bitmap = getBitmapFromResources(context, R.drawable.default_bg);
//					 v.setImageBitmap(bitmap);
				}
			}
			}
		};
		thread = new Thread() {
			public void run() {
				Message msg = handler.obtainMessage();
				Bitmap bmp = null;
//				if (!StringUtils.isEmpty(url)) {
//					bmp = BitmapFactory.decodeFile(url);
//				}
				String filename = FileUtils.getFileName(url);
				try {
					// 读取本地图片
//					if (imgURL.endsWith("portrait.gif")
//							|| StringUtils.isEmpty(imgURL)) {
//						bmp = BitmapFactory.decodeResource(
//								mImage.getResources(), R.drawable.widget_dface);
//					}
					if (bmp == null) {
						// 是否有缓存图片
						// Environment.getExternalStorageDirectory();返回/sdcard
						String filepath = context.getFilesDir() + File.separator
								+ filename;
						File file = new File(filepath);
						if (file.exists()) {
							bmp = ImageUtils.getBitmap(context,
									filename);
							if (bmp != null) {
								// 缩放图片
								bmp = ImageUtils.reDrawBitMap((Activity) context,bmp);
							}
						}
					}
					if (bmp == null) {
						if(Util.detect(context)){
							bmp = Util.getBitmapForNet(url);
						}
						//						bmp = ApiClient.getNetBitmap(imgURL);
						if (bmp != null) {
							try {
								// 写图片缓存 
								ImageUtils.saveImage(context,
										filename, bmp);
							} catch (IOException e) {
								e.printStackTrace();
							}
							// 缩放图片
							bmp = ImageUtils.reDrawBitMap((Activity) context, bmp);
						}
					}
					if(bmp!=null){
						msg.what = 1;
						msg.obj = bmp;
					}else{
						msg.what=2;
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				if (handler != null && !isInterrupted())
					handler.sendMessage(msg);
			}
		};
		thread.start();

	}
	/**
	 * 保存图片
	 * @param url
	 */
	public static void saveBitmap(String url){
		Bitmap bmp = null;
		//		if (!StringUtils.isEmpty(url)) {
		//			bmp = BitmapFactory.decodeFile(url);
		//		}
		String filename = FileUtils.getFileName(url);
		if(Util.detect(context)){
			try {
				bmp = Util.getBitmapForNet(url);
				if (bmp != null) {
					try {
						// 写图片缓存
						ImageUtils.saveImage(context,
								filename, bmp);
					} catch (IOException e) {
						e.printStackTrace();
					}	
					// 缩放图片
					bmp = ImageUtils.reDrawBitMap((Activity) context, bmp);
					//						return true;
				}else{
					//						return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//					return false;
			}
		}
		//		return false;

	}


}
