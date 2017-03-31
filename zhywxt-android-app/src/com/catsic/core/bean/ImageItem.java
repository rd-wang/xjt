package com.catsic.core.bean;

import java.io.Serializable;

/**
 * @Description: 图片项
 * @author wuxianling
 * @date 2014年8月22日 下午2:54:03
 */
public class ImageItem implements Serializable {
	/**  
	  * @Fields serialVersionUID : TODO 
	  */
	private static final long serialVersionUID = 1L;
	public String imageId;//图片ID
	public String thumbnailPath;//缩略图路径
	public String imagePath;//图片路径
	public boolean isSelected = false;//是否选中
}
