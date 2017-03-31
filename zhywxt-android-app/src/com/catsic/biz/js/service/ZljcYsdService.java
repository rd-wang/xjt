package com.catsic.biz.js.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.catsic.R;
import com.catsic.biz.js.bean.TZljc;
import com.catsic.biz.js.bean.TZljcYsd;
import com.catsic.biz.yh.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.tools.ObjectUtils;


/**
  * @ClassName: ZljcYsdService
  * @Description: 质量检测-压实度
  * @author Comsys-wuxianling
  * @date 2015年5月19日 上午11:34:23
  */
public class ZljcYsdService extends BaseService{
	
	public ZljcYsdService(Context context){
		super(context);
	}
	

	
	/**
	  * @Title: save
	  * @Description: 压实度数据保存
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	  */
	public boolean saveOrUpdate(TabHost tabHost,String xmid){
		ArrayList<TZljc> list = new ArrayList<TZljc>();
		TabWidget tabWidget =  tabHost.getTabWidget();
		FrameLayout frameLayout = tabHost.getTabContentView();
		for (int i = 0; i < tabWidget.getTabCount(); i++) {
			//1.1
			TZljc zljc = new TZljc();
			zljc.setCrowid(UUID.randomUUID().toString());
			zljc.setXmid(xmid);
			zljc.setJclb(AppConstants.ZLJC_YSD);
//			if (!ObjectUtils.isNullOrEmptyString(tv_result.getText())) {
//				zljc.setResult(tv_result.getText().toString().trim());
//			}
			JSONObject loginUser = AppContext.LOGINUSER;
			if (loginUser!=null) {
				try {
					zljc.setTbdw(loginUser.getString("orgid"));
					zljc.setTbr(loginUser.getString("username"));
					zljc.setTbsj(new Date());
					zljc.setShbz(ShbzUtils.getShbzByUser(loginUser.getString("orglevel")));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			zljc.setJcsj(new Date());
			
			FinalDb db = FinalDb.create(context,AppConstants.DB_NAME);
			db.save(zljc);
			
			//1.2
			TZljcYsd zljcYsd = new TZljcYsd();
			zljcYsd.setCrowid(UUID.randomUUID().toString());
			zljcYsd.setParentid(zljc.getCrowid());
			
			//初始化
			EditText et_sksd =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_sksd);
			EditText et_gstYylsz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_gstYylsz);
			EditText et_ztjccmhsz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_ztjccmhsz);
			EditText et_gstSylsz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_gstSylsz);
			EditText et_skhsz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_skhsz);
			EditText et_lsmd =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_lsmd);
			EditText et_skrj =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_skrj);
			EditText et_ssyz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_ssyz);
			EditText et_sysmd =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_sysmd);
			EditText et_hh =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_hh);
			EditText et_hz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_hz);
			EditText et_hzSsyz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_hzSsyz);
			EditText et_hzGsyz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_hzGsyz);
			EditText et_sz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_sz);
			EditText et_gsyz =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_gsyz);
			EditText et_syhsl =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_syhsl);
			EditText et_pjhsl =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_pjhsl);
			EditText et_zjhsl =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_zjhsl);
			EditText et_sygmd =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_sygmd);
			EditText et_zdgmd =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_zdgmd);
			EditText et_ysd =  (EditText) frameLayout.getChildAt(i).findViewById(R.id.et_ysd);
			
			if (!ObjectUtils.isNullOrEmptyString(et_sksd.getText().toString().trim())) {
				zljcYsd.setSksd(Double.parseDouble(et_sksd.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_gstYylsz.getText().toString().trim())) {
				zljcYsd.setGstYylsz(Double.parseDouble(et_gstYylsz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_ztjccmhsz.getText().toString().trim())) {
				zljcYsd.setZtjccmhsz(Double.parseDouble(et_ztjccmhsz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_gstSylsz.getText().toString().trim())) {
				zljcYsd.setGstSylsz(Double.parseDouble(et_gstSylsz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_skhsz.getText().toString().trim())) {
				zljcYsd.setSkhsz(Double.parseDouble(et_skhsz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_lsmd.getText().toString().trim())) {
				zljcYsd.setLsmd(Double.parseDouble(et_lsmd.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_skrj.getText().toString().trim())) {
				zljcYsd.setSkrj(Double.parseDouble(et_skrj.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_ssyz.getText().toString().trim())) {
				zljcYsd.setSsyz(Double.parseDouble(et_ssyz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_sysmd.getText().toString().trim())) {
				zljcYsd.setSysmd(Double.parseDouble(et_sysmd.getText().toString().trim()));
			}
			zljcYsd.setHh(et_hh.getText().toString().trim());
			if (!ObjectUtils.isNullOrEmptyString(et_hz.getText().toString().trim())) {
				zljcYsd.setHz(Double.parseDouble(et_hz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_hzSsyz.getText().toString().trim())) {
				zljcYsd.setHzSsyz(Double.parseDouble(et_hzSsyz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_hzGsyz.getText().toString().trim())) {
				zljcYsd.setHzGsyz(Double.parseDouble(et_hzGsyz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_sz.getText().toString().trim())) {
				zljcYsd.setSz(Double.parseDouble(et_sz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_gsyz.getText().toString().trim())) {
				zljcYsd.setGsyz(Double.parseDouble(et_gsyz.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_syhsl.getText().toString().trim())) {
				zljcYsd.setSyhsl(Double.parseDouble(et_syhsl.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_pjhsl.getText().toString().trim())) {
				zljcYsd.setPjhsl(Double.parseDouble(et_pjhsl.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_zjhsl.getText().toString().trim())) {
				zljcYsd.setZjhsl(Double.parseDouble(et_zjhsl.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_sygmd.getText().toString().trim())) {
				zljcYsd.setSygmd(Double.parseDouble(et_sygmd.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_zdgmd.getText().toString().trim())) {
				zljcYsd.setZdgmd(Double.parseDouble(et_zdgmd.getText().toString().trim()));
			}
			if (!ObjectUtils.isNullOrEmptyString(et_ysd.getText().toString().trim())) {
				zljcYsd.setYsd(Double.parseDouble(et_ysd.getText().toString().trim()));
			}
			
			db.save(zljcYsd);
			
		    zljc.setTZljcYsd(zljcYsd);
			list.add(zljc);
		}
		return true;
	}
}
