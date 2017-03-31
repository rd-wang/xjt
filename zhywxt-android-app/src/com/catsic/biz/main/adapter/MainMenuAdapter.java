package com.catsic.biz.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.catsic.R;
import com.catsic.biz.main.fragment.MainSubMenuFragment;
import com.viewpagerindicator.IconPagerAdapter;

/**  
  * @Description: 主页Fragment适配器 
  * @author wuxianling  
  * @date 2014年7月29日 下午4:55:12    
  */ 
public class MainMenuAdapter extends FragmentPagerAdapter implements IconPagerAdapter{

	/**  
	  * @Fields pageTitles : 主页各Fragment 的标题 
	  */
	private static final String[] pageTitles = new String[] {"建设检查", "养护巡查","路政","数据查询"};
    private static final int[] ICONS = new int[] {
            R.color.icon_main_menu_js_selector,
            R.color.icon_main_menu_yh_selector,
            R.color.icon_main_menu_lz_selector,
            R.color.icon_main_menu_query_selector
    };

    public MainMenuAdapter(FragmentManager fm) {
		super(fm);
	}

    @Override
    public Fragment getItem(int position) {
        return  MainSubMenuFragment.newInstance(position % pageTitles.length);
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return pageTitles[position % pageTitles.length];
    }

	@Override
	public int getIconResId(int index) {
		return ICONS[index];
	}

}
