package com.catsic.core.custom;

/**  
  * @Description: 菜单项 
  * @author wuxianling  
  * @date 2014年7月3日 上午10:28:45    
  */ 
public class FooterMenuItem {
	
	private int imgNormal;//正常情况下 图片ID
	private int imgPress;//鼠标聚焦下图片ID
	private AppFooterView.OnMenuItemClickListener listener;

	public FooterMenuItem() {
	}
	
	public FooterMenuItem(AppFooterView mFooter) {
	}

	public int getImgNormal() {
		return this.imgNormal;
	}

	public int getImgPress() {
		return this.imgPress;
	}

	public AppFooterView.OnMenuItemClickListener getListener() {
		return this.listener;
	}

	public void setImgNormal(int paramInt) {
		this.imgNormal = paramInt;
	}

	public void setImgPress(int paramInt) {
		this.imgPress = paramInt;
	}

	public void setImgRes(int paramInt1, int paramInt2) {
		setImgNormal(paramInt1);
		setImgPress(paramInt2);
	}

	public void setListener(AppFooterView.OnMenuItemClickListener paramOnMenuItemClickListener) {
		this.listener = paramOnMenuItemClickListener;
	}

}
