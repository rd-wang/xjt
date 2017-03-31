package com.catsic.core.bean;

import java.util.Map;

/**
  * @ClassName: Menu
  * @Description: 系统菜单
  * @author wuxianling
  * @date 2015年6月26日 下午4:22:54
  */
public class Menu {
	private int image;
	private String title;
	private Class clazz;
	private Map param;
	
	public Menu(){
		
	}
	
	public Menu(int image, String title, Class clazz, Map param) {
		super();
		this.image = image;
		this.title = title;
		this.clazz = clazz;
		this.param = param;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

}
