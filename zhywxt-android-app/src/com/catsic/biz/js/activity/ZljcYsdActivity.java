
package com.catsic.biz.js.activity;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.js.service.ZljcYsdService;
import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.service.CatsicCodeService;
import com.catsic.core.service.XzqhService;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.DateTimePickerUtil;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Comsys-wuxianling
 * @ClassName: ZljcYsdActivity
 * @Description: 质量检测-压实度
 * @date 2015年5月18日 下午1:47:41
 */
public class ZljcYsdActivity extends BaseActivity implements OnTouchListener, android.view.View.OnClickListener {

    /**
     * Tab标题
     */
    private static ArrayList<String> TITLE = new ArrayList<String>();

    private String xmid;

    private JSONObject xmjbxxJsonObject;

    private
    @ViewInject(id = R.id.tv_xmmc)
    TextView tv_xmmc;
    private
    @ViewInject(id = R.id.tv_xzqh)
    TextView tv_xzqh;
    private
    @ViewInject(id = R.id.tv_xmlx)
    TextView tv_xmlx;

    private
    @ViewInject(id = R.id.et_cdcc)
    EditText et_cdcc;
    private
    @ViewInject(id = R.id.et_cdrq)
    EditText et_cdrq;
    private
    @ViewInject(id = R.id.et_zh)
    EditText et_zh;

    private
    @ViewInject(id = R.id.et_zh)
    EditText et_zdhsl;

    private
    @ViewInject(id = R.id.et_ysdsjz)
    EditText et_ysdsjz;

    private
    @ViewInject(id = R.id.iv_add)
    ImageView iv_add;
    private BootstrapButton btn_js_zljc_ysd_save;

    private
    @ViewInject(id = R.id.tabHost)
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_js_zljc_ysd);
        LocalActivityManager localActivityManager = new LocalActivityManager(this, true);
        localActivityManager.dispatchCreate(savedInstanceState);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup(localActivityManager);

        String centerTitle = getResources().getString(R.string.ZljcYsdActivityTitle);
        ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);

        initEvent();
        initViewData();
        super.onCreate(savedInstanceState);

    }

    public void initEvent() {
        btn_js_zljc_ysd_save = (BootstrapButton) this.findViewById(R.id.btn_js_zljc_ysd_save);

        iv_add.setOnClickListener(this);
        btn_js_zljc_ysd_save.setOnClickListener(this);
        et_cdrq.setOnTouchListener(this);
    }

    private void initViewData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("xmid")) {
            xmid = bundle.getString("xmid");
            //项目信息
            findXmjbxxById(xmid);
        }
    }

    /**
     * @param @param xmid    设定文件
     * @return void    返回类型
     * @throws
     * @Title: findXmjbxxById
     * @Description: 初始化项目信息
     */
    private void findXmjbxxById(String xmid) {
        ProgressDialogUtil.show(this, "正在加载...", true);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("params", xmid);
        SoapUtil.callService(AppUrls.getServiceURL(AppUrls.JSGL_XMXX_URL), AppUrls.JSGL_XMXX_NAMESPACE, "findById", paramMap, new SoapUtil.WebServiceCallBack() {

            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        xmjbxxJsonObject = jsonObject;
                        if (jsonObject != null && jsonObject.getString("xmmc") != null) {
                            ZljcYsdActivity.this.tv_xmmc.setText(jsonObject.getString("xmmc"));
                        }
                        if (jsonObject != null && jsonObject.getString("xzqh") != null) {
                            String xzqhmc = new XzqhService(ZljcYsdActivity.this).translate(jsonObject.getString("xzqh"));
                            ZljcYsdActivity.this.tv_xzqh.setText(xzqhmc);
                        }
                        if (jsonObject != null && jsonObject.getString("xmlxdm") != null) {
                            String xmlxmc = new CatsicCodeService(ZljcYsdActivity.this).translate("tc_xmlx", jsonObject.getString("xmlxdm"));
                            ZljcYsdActivity.this.tv_xmlx.setText(xmlxmc);
                        }
                    } catch (JSONException e) {
                        ToastUtil.showShortToast(ZljcYsdActivity.this, "加载失败");
                    }
                }
            }

            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                ToastUtil.showShortToast(ZljcYsdActivity.this, "加载失败");
            }
        });
    }

    private String zh;

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws
     * @Title: inputDialog
     * @Description: 添加检测桩号
     */
    private void inputDialog() {
        final EditText inputEt = new EditText(this);
        inputEt.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入检测桩号")
                .setIcon(android.R.drawable.ic_menu_add)
                .setView(inputEt)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        zh = inputEt.getText().toString();
                        if (!ObjectUtils.isNullOrEmptyString(zh)) {
                            addTab(zh);
                        }
                    }
                });
        builder.show();
    }

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws
     * @Title: addTab
     * @Description: TODO
     */
    public void addTab(String title) {
        TITLE.add(title);

        Intent intent = new Intent(this, ZljcYsdTabActivity.class);
        tabHost.addTab(tabHost.newTabSpec("tab" + TITLE.size()).setIndicator(title).setContent(intent));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                inputDialog();
                break;
            case R.id.btn_js_zljc_ysd_save:

                if (TextUtils.isEmpty(zh)) {
                    ToastUtil.showShortToast(getApplication(), "保存失败，请输入桩号信息...");
                    return;
                }


                if (ObjectUtils.isNullOrEmptyString(xmid)) {
                    ToastUtil.showShortToast(this, "质量检测有误！");
                    Log.d("tag", "btn_js_zljc_ysd_save");
                    break;
                }
                ProgressDialogUtil.show(this, "正在处理中...", true);
                //数据保存到本地
                if (new ZljcYsdService(this).saveOrUpdate(tabHost, xmid)) {
                    try {
                        Intent intent = new Intent(this, ZljcListActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("jclb", AppConstants.ZLJC_YSD);
                        if (xmjbxxJsonObject != null) {
                            bundle2.putString("xmid", xmjbxxJsonObject.getString("xmid"));
                            bundle2.putString("xmmc", xmjbxxJsonObject.getString("xmmc"));
                            bundle2.putString("xmlxdm", xmjbxxJsonObject.getString("xmlxdm"));
                            bundle2.putString("xzqh", xmjbxxJsonObject.getString("xzqh"));
                        }
                        bundle2.putString("title", "压实度测试");

                        intent.putExtras(bundle2);
                        startActivity(intent);
                    } catch (Exception e) {
                        ToastUtil.showLongToast(this, "保存失败！");
                    }
                }
                ProgressDialogUtil.dismiss();
                break;
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.et_cdrq) {
                    ProgressDialogUtil.show(this, "正在加载...", true);
                    DateTimePickerUtil.showDatePicker(this, et_cdrq);
                    ProgressDialogUtil.dismiss();
                }
                break;
        }
        return false;
    }

}
