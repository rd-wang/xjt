package com.catsic.biz.yh.service;

import android.content.Context;
import android.util.Log;

import com.catsic.biz.yh.activity.QljcEditListActivity;
import com.catsic.biz.yh.activity.QljcJEditeQlActivity;
import com.catsic.biz.yh.bean.QlcxListBean;
import com.catsic.biz.yh.bean.TQljc;
import com.catsic.biz.yh.bean.TQljcmx;
import com.catsic.core.AppContext;
import com.catsic.core.AppUrls;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Litao-pc on 2016/4/29.
 */
public class QljcUpdateAndDeleteService {
    public static void saveQljcmx(final Context mContext, final TQljcmx obj) {
        LogUtils.outString("start");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", GsonUtils.toJson(obj));
        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, "saveOrUpdateQljcmx", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                LogUtils.outString(result + "QljcUpdateServiceOnSucced");
                if (result != null) {
                    QljcJEditeQlActivity activity = (QljcJEditeQlActivity) mContext;
                    QlcxListBean.QljcmxSetBean bean = new QlcxListBean.QljcmxSetBean();
                    bean.setQljcCrowid(obj.getQljcCrowid());
                    bean.setQsfw(obj.getQsfw());
                    bean.setQslx(obj.getQslx());
                    bean.setBycsyj(obj.getBycsyj());
                    bean.setJcbj(obj.getJcbj());
                    activity.myAdapter.mData.add(bean);
                    activity.myAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showShortToast(mContext, "加载失败！");
                }
            }

            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                ToastUtil.showShortToast(mContext, "加载失败");
            }
        });
    }

    /**
     * @param mContext
     * @param position
     * @param mId      桥梁检测明细id
     */
    public static void deleteQljcmx(final Context mContext, final int position, String mId, String methodName) {


        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", mId);

        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, methodName, paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                LogUtils.outString("result " + result);
                if (result != null) {
                    if (mContext instanceof QljcEditListActivity) {
                        QljcEditListActivity activity = (QljcEditListActivity) mContext;
                        activity.adapter.listItems.remove(position);
                        activity.adapter.notifyDataSetChanged();
                    } else if (mContext instanceof QljcJEditeQlActivity) {
                        QljcJEditeQlActivity activity = (QljcJEditeQlActivity) mContext;
                        activity.myAdapter.mData.remove(position);
                        activity.myAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onFailure(String result) {
                LogUtils.outString("onFailure " + result);
            }
        });
    }

    /**
     * 添加桥梁检测信息id
     *
     * @param parentId 桥梁卡片id
     * @param UUId     桥梁检测id
     */
    public static void savaNewQljcQlmx(final Context c, String parentId, String UUId,SoapUtil.WebServiceCallBack callBack) {
        Map<String, String> paramMap = new HashMap<String, String>();
        //创建主表对象
        TQljc obj = new TQljc();
        obj.setCrowid(UUId);
        obj.setParentCrowid(parentId);
        JSONObject loginUser = AppContext.LOGINUSER;
        if (loginUser != null) {
            try {
                obj.setTbdwdm(loginUser.getString("orgid"));
            } catch (JSONException e) {
                Log.e("ZlxcActivity", "JSONException");
                e.printStackTrace();
            }
        }
        paramMap.put("params", GsonUtils.toJson(obj));
        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, "saveOrUpdate", paramMap, callBack);

    }

    /**
     * 更新桥梁检测信息
     */
    public static void upDatas(TQljc obj,SoapUtil.WebServiceCallBack callBack) {
        Map<String, String> paramMap = new HashMap<String, String>();
        //创建主表对象


        paramMap.put("params", GsonUtils.toJson(obj));
        SoapUtil.callService(AppUrls.TESTServerUrl, AppUrls.TESTSpaceName, "saveOrUpdate", paramMap, callBack);

    }
}
