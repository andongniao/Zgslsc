package com.example.educonsult.tools.FileUtil;
import java.io.File;  
import java.io.FileInputStream; 
import java.io.FileOutputStream;  
import java.io.IOException; 
import java.io.OutputStream; 
import android.content.Context; 
import android.content.Intent;  
import android.net.Uri;  
import android.os.Environment;  
public class SDFileHandle {
	private static String SDPath=Environment.getExternalStorageDirectory().
			getAbsolutePath();
	/**
	 * 判断SD卡是否可�?
	 * @return
	 */
	public static boolean SDCanOperate(){
		// SD Card Ready

        if (Environment.getExternalStorageState().
        		equals(Environment.MEDIA_MOUNTED)) {
        		return true;
        }
        else{
        	return false;

        }
	}
	 /**
     * 获取程序私有路径
     * @return
     */
    public static String getDataPath(){
    	return "/data/data/com.main.jiami/temp";
    }	
	/**
	 * 判断文件路径是否在SD卡中
	 * @param filename
	 * @return
	 */
    public static boolean FileInSD(String filename){
    	return filename.startsWith(
    			Environment.getExternalStorageDirectory().
    			getAbsolutePath());
    }
    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getSDPath(){
    	return SDPath;
    }
    	
    /**
	* 
	* @param path 文件夹路�?
	* 判断文件夹是否存�?,如果不存在则创建文件�?
	*/
	public static void isExist(String path) {
	File file = new File(path);
	//判断文件夹是否存�?,如果不存在则创建文件�?
	if (!file.exists()) {
	file.mkdir();
	}
	}
	
	 /**    
	  *    * 创建文件       *   
	  *         * @throws IOException  
	  *              */     
	public static File creatFile(String fileName) throws IOException {
		File file = new File(fileName);   
		file.createNewFile();      
		return file;      
		}     
	/**    
	 *    * 删除文件       *     
	 *       * @param fileName    
	 *          */    
	public static boolean delFile(String fileName) {   
		File file = new File(fileName);   
		if (file == null || !file.exists() || file.isDirectory())
			return false;   
		file.delete();       
		return true;    
		}     
	/**      
	 *  * 创建目录       *   
	 *       * @param dirName      
	 *        */    
	public static File creatDir(String dirName) { 
		File dir = new File(dirName);  
		dir.mkdir();         
		return dir;      
		}   
	/**      
	 *  * 修改文件或目录名       * @param fileName 
	 *        */    
	public boolean renameSDFile(String oldfileName, String newFileName) {  
		File oleFile = new File(SDPath+"//"+oldfileName);      
		File newnewFile = new File(SDPath+"//"+newFileName);   
		return oleFile.renameTo(newnewFile);  
		}  
	
	 /* 删除�?个文�?       *       
	  *  * @param file   
	  *      * @return  
	  *           */     
	public boolean delSDFile(File file) {    
		if (file.isDirectory())  
			return false;      
		return file.delete();  
		}     
	/**     
	 *   * 删除�?个目录（可以是非空目录）    
	 *      * @param dir    
	 *         */      
	public boolean delSDDir(File dir) {  
		if (dir == null || !dir.exists() || dir.isFile()) {
            return false;    
            }        
		for (File file : dir.listFiles()) {
			if (file.isFile()) {     
				file.delete();         
				} else if (file.isDirectory()) { 
					delSDDir(file);
					// 递归         
					}     
					}         
			dir.delete();      
			return true;    
			}   
	
	 public static void writeFile(String fileName,byte[] data) throws IOException{ 
	        //FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);

	       FileOutputStream fout = new FileOutputStream(fileName);
	        fout.write(data); 
	         fout.close(); 
	   }
	 
	 
}
