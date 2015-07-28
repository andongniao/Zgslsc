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
 * ���ù�����
 * 
 * @author Qzr
 * 
 */
public class Util {
	private static final String ALGORITHM = "RSA";
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final int CACHE_TIME = 60*60000;//����ʧЧʱ��
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
	 * ��ȡ��Ļ���
	 * 
	 * @param activity
	 * @return ��Ļ���px��λ
	 */
	public static int getWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * ��ȡ��Ļ�߶�
	 * 
	 * @param activity
	 * @return ��Ļ�߶�px��λ
	 */
	public static int getHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeigh = dm.heightPixels;
		return screenHeigh;
	}
	
	

	/**
	 * �������Ļ���
	 */
	public void cleanCookie()
	{
		//		removeProperty(AppConfig.CONF_COOKIE);
	}

	/**
	 * �жϻ��������Ƿ�ɶ�
	 * @param cachefile
	 * @return
	 */
	public boolean isReadDataCache(String cachefile)
	{
		return readObject(cachefile) != null;
	}

	/**
	 * �жϻ����Ƿ����
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
	 * �жϻ����Ƿ�ʧЧ
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
	 * �������Ŀ¼
	 * @param dir Ŀ¼
	 * @param numDays ��ǰϵͳʱ��
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
	 * �����󱣴浽�ڴ滺����
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}

	/**
	 * ���ڴ滺���л�ȡ����
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key){
		return memCacheRegion.get(key);
	}

	/**
	 * ������̻���
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
	 * ��ȡ���̻�������
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
	 * �������
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
	 * ��ȡ����
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
			//�����л�ʧ�� - ɾ�������ļ�
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
	 * ��ȡpackage name
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
	 * ���ز�����ͼƬ������
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
	 * ��������Ƿ����
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	/**
	 * ��ȡ����ͼƬ��ַ
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
	 * ��ȡ�汾��
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
	 * ����ͼƬ������
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
			System.out.println("����ͼƬʱ���쳣��"+e);
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
				System.out.println("����ͼƬʱ���쳣��"+e);
			}
		}
		return false;
	}
	/**
	 * ʹ��MD5��ͼƬurl���б��� ��ȡͼƬ����
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
	 * ��ȡ����ͼƬ
	 */
	public Bitmap getBitmaoForCahe(String imageUrl){
		Bitmap bitmap = null;
		String key = hashKeyForDisk(imageUrl);
		try {
			File cacheDir = getDiskCacheDir(context, "bitmap");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			DiskLruCache mD = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);//ͼƬ����
			DiskLruCache.Snapshot snapShot = mD.get(key);
			if (snapShot != null) {  
				InputStream is = snapShot.getInputStream(0);  
				bitmap = BitmapFactory.decodeStream(is);  
			}  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ȡ����ͼƬʱ���쳣��");
		}
		return bitmap;
	}

	// ��ȡָ��·����ͼƬ  
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
	 * �ж��Ƿ��ǵ绰����
	 * 
	 * @param mobiles
	 * @return true�ǵ绰����
	 */
	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][3578]\\d{9}";// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * �ж�email��ʽ�Ƿ���ȷ
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
	 * ����λ�����ƣ�����6λ��С��20λ
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
	 * �û���λ��&�������ƣ�����4λ��С��20λ
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
	 * ��鿪ͷ���β�Ƿ��пո�
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
	 * �ж��ַ����Ƿ�Ϊ���в�Ϊnull
	 * 
	 * @param text
	 * @return true ��Ϊ��
	 */
	public static boolean IsNull(String text) {
		if (text != null && !text.equals("")&&!text.equals("null")) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡͼƬ��Ⱥ͸߶�
	 * 
	 * @param id
	 * @param resources
	 * @return
	 */
	public static int[] getBitmapWidth(int id, Resources resources) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// ����Ϊtureֻ��ȡͼƬ��С
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeResource(resources, id, opts);
		return new int[] { opts.outWidth, opts.outHeight };
	}

	/**
	 * �ж������Ƿ����
	 * 
	 * @param act
	 * @return ture����
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
	 * ��ʱ���ת����ʱ������
	 * 
	 * @param str
	 *            ʱ�����͸�ʽ
	 * @param time
	 *            ʱ��� ��λ����
	 * @return
	 */
	public static String getData(String str, long time) {
		// yyyy-MM-dd hh:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String date = sdf.format(new Date(time));
		return date;
	}

	/**
	 * ��ȡ��ǰʱ��
	 * 
	 * @param str
	 *            ʱ�����͸�ʽ
	 * @return
	 */
	public static String getdata(String str) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

	//	/**
	//	 * ����Loding...
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
	//	 * �ر�Loding...
	//	 */
	//	public static void stopProgressDialog() {
	//		if (progressDialog != null && progressDialog.isShowing()) {
	//			progressDialog.dismiss();
	//			progressDialog = null;
	//		}
	//	}

	/**
	 * ��ȡ����sd���ĸ�·��������]������sd�����t����null
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
		// �õ�·��
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
	 * ����Toast
	 * 
	 * @param context
	 * @param content
	 */
	public static void ShowToast(Context context, String content) {
		Toast.makeText(context, content, 1000).show();
	}

	/**
	 * ����Toast
	 * 
	 * @param context
	 * @param i
	 */
	public static void ShowToast(Context context, int i) {
		Toast.makeText(context, i, 1000).show();
	}

	/**
	 * ���¼�����listview�߶�
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// ��ȡListView��Ӧ��Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // ��������View �Ŀ��
			totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
		listView.setLayoutParams(params);
	}

	/**
	 * ��ʱ���ת����ʱ������
	 * 
	 * @param str
	 *            ʱ�����͸�ʽ
	 * @param time
	 *            ʱ��� ��λ����
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
	 *  ����ͼ�����Ͻǵ�δ����Ϣ������ʾ	 *
	 *    @param context	 *
	 *                ������	 *
	 *                 @param icon	 *
	 *                             ��Ҫ����ӵ�icon����ԴID	 *
	 *                              @param news	 *            
	 *                              δ������Ϣ����	 * @return drawable	
	 *                               */
	@SuppressWarnings("unused")	
	public static Drawable displayNewsNumber(Context context, int icon, int news) {		
		// ��ʼ������		
		int iconSize = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);		
		// Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,		
		// Config.ARGB_8888);		
		Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), icon);		
		Canvas canvas = new Canvas(iconBitmap);		
		// ����ͼƬ		
		Paint iconPaint = new Paint();		
		iconPaint.setDither(true);
		// ������		
		iconPaint.setFilterBitmap(true);
		// ������Bitmap�����˲�����		
		Rect src = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());		
		Rect dst = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());		
		canvas.drawBitmap(iconBitmap, src, dst, iconPaint);		
		// ���ÿ���ݺ�ʹ���豸���ı��־�		
		Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);		
		countPaint.setColor(Color.RED);		canvas.drawCircle(iconSize - 13, 20, 10, countPaint);		
		Paint textPaint = new Paint();		textPaint.setColor(Color.WHITE);		
		// textPaint.setTypeface(Typeface.DEFAULT_BOLD);		
		textPaint.setTextSize(19f);		
		canvas.drawText(String.valueOf(news), iconSize - 18, 27, textPaint);		
		return new BitmapDrawable(iconBitmap);	
	}
	/**
	 * �������Ͻ�С���&����
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
	 * ȡ�����Ͻ�С���&����
	 * @param context
	 * @param v
	 */
	public static void SetRedGone(Context context,View v){
		if(badge!=null){
			badge.toggle();
		}
	}
	
	// ����Դ�л�ȡBitmap
	public static Bitmap getBitmapFromResources(Context context2, int resId) {
	Resources res = context2.getResources();
	return BitmapFactory.decodeResource(res, resId);
	}


	/**
	 * �õ�������ͼƬ
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
					// ��ȡ����ͼƬ
//					if (imgURL.endsWith("portrait.gif")
//							|| StringUtils.isEmpty(imgURL)) {
//						bmp = BitmapFactory.decodeResource(
//								mImage.getResources(), R.drawable.widget_dface);
//					}
					if (bmp == null) {
						// �Ƿ��л���ͼƬ
						// Environment.getExternalStorageDirectory();����/sdcard
						String filepath = context.getFilesDir() + File.separator
								+ filename;
						File file = new File(filepath);
						if (file.exists()) {
							bmp = ImageUtils.getBitmap(context,
									filename);
							if (bmp != null) {
								// ����ͼƬ
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
								// дͼƬ���� 
								ImageUtils.saveImage(context,
										filename, bmp);
							} catch (IOException e) {
								e.printStackTrace();
							}
							// ����ͼƬ
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
	 * ����ͼƬ
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
						// дͼƬ����
						ImageUtils.saveImage(context,
								filename, bmp);
					} catch (IOException e) {
						e.printStackTrace();
					}	
					// ����ͼƬ
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
