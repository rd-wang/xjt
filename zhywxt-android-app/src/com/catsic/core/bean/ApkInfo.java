package com.catsic.core.bean;

import java.io.Serializable;

/**  
  * @Description: Apk信息 
  * @author wuxianling  
  * @date 2014年9月23日 上午11:21:53    
  */ 
public class ApkInfo implements Serializable{

	/**  
	  * @Fields serialVersionUID : TODO 
	  */
	private static final long serialVersionUID = 1L;
	
	private String lastestVersion;
	
	private long fileSize;
	
	private String fileName;

	public String getLastestVersion() {
		return lastestVersion;
	}

	public void setLastestVersion(String lastestVersion) {
		this.lastestVersion = lastestVersion;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
