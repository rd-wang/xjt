
package com.catsic.core.conf;

import com.catsic.R;
import com.catsic.biz.js.activity.XmjbxxListActivity;
import com.catsic.biz.js.activity.ZlxcListActivity;
import com.catsic.biz.lz.activity.LzXcjlListActivity;
import com.catsic.biz.query.activity.LsListActivity;
import com.catsic.biz.task.activity.TaskListActivity;
import com.catsic.biz.yh.activity.LSListActivity;
import com.catsic.biz.yh.activity.QljcEditListActivity;
import com.catsic.biz.yh.activity.YhXcjlListActivity;
import com.catsic.core.bean.Menu;

import java.util.HashMap;

/**
  * @ClassName: MenuEnum
  * @Description: 系统菜单配置
  * @author wuxianling
  * @date 2015年6月26日 下午4:15:06
  */
public enum MenuEnum {

	@SuppressWarnings("serial")
	ALLEnum("0"
			,new Menu[][]{{
				  new Menu(R.drawable.menu_taskcenter,"待办任务",TaskListActivity.class,null),
				  new Menu(R.drawable.menu_xmjc,"项目检查",XmjbxxListActivity.class,new HashMap<String, String>(){{ put("tag", "xmjc");put("title","项目检查");}}),
		          new Menu(R.drawable.menu_zljc,"质量检测",XmjbxxListActivity.class,new HashMap<String, String>(){{put("tag", "zljc");put("title","质量检测");}})
				  }
				 ,{
				   new Menu(R.drawable.menu_taskcenter,"待办任务",TaskListActivity.class,null),
				   new Menu(R.drawable.menu_ls,"养护路损",LSListActivity.class,new HashMap<String, String>(){{put("tag", "zljc");put("title","质量检测");}}),
				   new Menu(R.drawable.menu_yh_xcjl,"养护巡查",YhXcjlListActivity.class,null)
				 }
				 ,{
				   new Menu(R.drawable.menu_lz_xcjl,"路政巡查",LzXcjlListActivity.class,null)
				 }
				 ,{
				   new Menu(R.drawable.menu_jhcx,"计划查询",XmjbxxListActivity.class,new HashMap<String, String>(){{put("title","计划查询");}}),
				   new Menu(R.drawable.menu_jscx,"建设查询",XmjbxxListActivity.class,new HashMap<String, String>(){{put("title","建设查询");}}),
				   new Menu(R.drawable.menu_yhcx,"养护查询",LsListActivity.class,new HashMap<String, String>(){{put("title","养护查询");}}),
				   new Menu(R.drawable.menu_lzcx,"路政查询",XmjbxxListActivity.class,new HashMap<String, String>(){{put("title","路政查询");}})
				 }}),
	@SuppressWarnings("serial")
	LYEnum("4103"
			,new Menu[][]{{
				          new Menu(R.drawable.menu_zljc,"质量检测",XmjbxxListActivity.class,new HashMap<String, String>(){{put("tag", "zljc");put("title","质量检测");}}),
				          new Menu(R.drawable.menu_zlxc,"质量巡查",ZlxcListActivity.class,new HashMap<String, String>(){{put("title","质量巡查");}}),
						  }
						 ,{
						   new Menu(R.drawable.menu_ls,"养护路损",LSListActivity.class,new HashMap<String, String>(){{put("tag", "zljc");put("title","养护路损列表");}}),
						   new Menu(R.drawable.menu_yh_xcjl,"养护巡查",YhXcjlListActivity.class,null),
                           new Menu(R.drawable.menu_lz_xcjl,"桥梁检测",QljcEditListActivity.class,new HashMap<String, String>(){{put("title","桥梁检测");}})
						 }
						 ,{
						   new Menu(R.drawable.menu_lz_xcjl,"路政巡查",LzXcjlListActivity.class,null)
						 }
						 ,{
						   new Menu(R.drawable.menu_jscx,"建设查询",XmjbxxListActivity.class,new HashMap<String, String>(){{put("title","建设查询");}})
						 }}),
	GZEnum("52");
	private String sysCode ;//系统代码
	private Menu [][] menus;
	private MenuEnum(String sysCode){
		this.sysCode = sysCode;
	}
	private MenuEnum(String sysCode,Menu [][] menus){
		this.sysCode = sysCode;
		this.menus = menus;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Menu[][] getMenus() {
		return menus;
	}

	public void setMenus(Menu[][] menus) {
		this.menus = menus;
	}

}
