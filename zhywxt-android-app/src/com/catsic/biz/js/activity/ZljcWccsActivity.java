package com.catsic.biz.js.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.BuildConfig;
import com.catsic.R;
import com.catsic.biz.js.bean.TZljc;
import com.catsic.biz.js.bean.TZljcWccs;
import com.catsic.biz.js.bean.TZljcWccsSub;
import com.catsic.biz.yh.utils.ShbzUtils;
import com.catsic.core.AppConstants;
import com.catsic.core.AppContext;
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

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuxianling
 * @ClassName: ZljcWccsActivity
 * @Description: 弯沉测试
 * @date 2015年8月14日 上午11:15:00
 */
public class ZljcWccsActivity extends BaseActivity implements OnTouchListener, android.view.View.OnClickListener {

    private
    @ViewInject(id = R.id.tv_xmmc)
    TextView tv_xmmc;
    private
    @ViewInject(id = R.id.tv_xzqh)
    TextView tv_xzqh;
    private
    @ViewInject(id = R.id.tv_xmlx)
    TextView tv_xmlx;

    //	private @ViewInject(id=R.id.et_bh) EditText et_bh;
//	private @ViewInject(id=R.id.et_hth) EditText et_hth;
//	private @ViewInject(id=R.id.et_csld) EditText et_csld;
//	private @ViewInject(id=R.id.et_cx) EditText et_cx;
//	private @ViewInject(id=R.id.et_dwyl) EditText et_dwyl;
//	private @ViewInject(id=R.id.et_sgdw) EditText et_sgdw;
    private
    @ViewInject(id = R.id.et_cdcc)
    EditText et_cdcc;
    //	private @ViewInject(id=R.id.et_hzz) EditText et_hzz;
    private
    @ViewInject(id = R.id.et_cdrq)
    EditText et_cdrq;
    private
    @ViewInject(id = R.id.et_zh)
    EditText et_zh;
    //	private @ViewInject(id=R.id.et_yxwcz) EditText et_yxwcz;
//	private @ViewInject(id=R.id.et_syr) EditText et_syr;
//	private @ViewInject(id=R.id.et_jsr) EditText et_jsr;
//	private @ViewInject(id=R.id.et_fhr) EditText et_fhr;
//	private @ViewInject(id=R.id.et_syrq) EditText et_syrq;
    private
    @ViewInject(id = R.id.et_yxwcz)
    EditText et_yxwcz;


    private
    @ViewInject(id = R.id.tv_l)
    TextView tv_l;
    private
    @ViewInject(id = R.id.et_Z)
    EditText et_Z;
    private
    @ViewInject(id = R.id.tv_S)
    TextView tv_S;
    private
    @ViewInject(id = R.id.tv_Lr)
    TextView tv_Lr;

    private
    @ViewInject(id = R.id.tv_result)
    TextView tv_result;


    private
    @ViewInject(id = R.id.tl_cdxx)
    TableLayout tl_cdxx;
    private
    @ViewInject(id = R.id.btn_add)
    ImageView btn_add;
    private BootstrapButton btn_save;

    private JSONObject xmjbxxJsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_js_zljc_wccs);

        String centerTitle = getResources().getString(R.string.ZljcWccsActivityTitle);
        ActionBarManager.initBackTitle(this, getActionBar(), centerTitle);

        ProgressDialogUtil.show(this, "正在加载...", true);

        btn_save = (BootstrapButton) this.findViewById(R.id.btn_save);
        et_cdrq.setOnTouchListener(this);
//		et_syrq.setOnTouchListener(this);

        btn_add.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        //填写后计算
        et_Z.addTextChangedListener(new EditChangedListener());

        initData();

        //默认显示一行
        addRow();

        super.onCreate(savedInstanceState);
    }

    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("xmid")) {
            String xmid = bundle.getString("xmid");
            //项目信息
            findXmjbxxById(xmid);


        }
    }

    private void findXmjbxxById(String xmid) {
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
                            ZljcWccsActivity.this.tv_xmmc.setText(jsonObject.getString("xmmc"));
                        }
                        if (jsonObject != null && jsonObject.getString("xzqh") != null) {
                            String xzqhmc = new XzqhService(ZljcWccsActivity.this).translate(jsonObject.getString("xzqh"));
                            ZljcWccsActivity.this.tv_xzqh.setText(xzqhmc);

                        }
                        if (jsonObject != null && jsonObject.getString("xmlxdm") != null) {
                            String xmlxmc = new CatsicCodeService(ZljcWccsActivity.this).translate("tc_xmlx", jsonObject.getString("xmlxdm"));
                            ZljcWccsActivity.this.tv_xmlx.setText(xmlxmc);
                        }
                    } catch (JSONException e) {
                        ToastUtil.showShortToast(ZljcWccsActivity.this, "加载失败");
                    }
                }
            }

            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                ToastUtil.showShortToast(ZljcWccsActivity.this, "加载失败");
            }
        });
    }

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws
     * @Title: addRow
     * @Description: 添加行
     */
    private void addRow() {
        View view = View.inflate(this, R.layout.activity_js_zljc_wccs_tablelayout_row, null);
        TableRow tableRow = (TableRow) view.findViewById(R.id.tr_wccs);
        ViewGroup parent = (ViewGroup) tableRow.getParent();
        tableRow.setTag("tr_" + tl_cdxx.getChildCount());

        if (parent != null) {
            parent.removeAllViews();
        }
        //左
        EditText et_leftZds = (EditText) tableRow.findViewWithTag("leftZds");
        et_leftZds.addTextChangedListener(new EditChangedListener(tableRow.getTag().toString(), et_leftZds.getTag().toString()));
        //右
        EditText et_rightZds = (EditText) tableRow.findViewWithTag("rightZds");
        et_rightZds.addTextChangedListener(new EditChangedListener(tableRow.getTag().toString(), et_rightZds.getTag().toString()));

        //删除
        ImageView iv_delete = (ImageView) tableRow.findViewWithTag("btn_delete");
        iv_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewGroup parent = (ViewGroup) v.getParent();
                parent.removeAllViews();

                //计算
                calcu();
            }
        });
        tl_cdxx.addView(tableRow);
    }

    /**
     * @param @return 设定文件
     * @return Double    返回类型
     * @throws
     * @Title: calcuL
     * @Description: 平均数计算
     */
    public Double calcuL() {
        Double sum = 0.0d;
        for (int i = 1; i < tl_cdxx.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tl_cdxx.getChildAt(i);
            EditText et_left = (EditText) tableRow.findViewWithTag("left");
            EditText et_right = (EditText) tableRow.findViewWithTag("right");
            if (et_left == null || et_right == null) {
                continue;
            }
            if (!ObjectUtils.isNullOrEmptyString(et_left.getText().toString())) {
                sum = sum + Double.parseDouble(et_left.getText().toString());
            }
            if (!ObjectUtils.isNullOrEmptyString(et_right.getText().toString())) {
                sum = sum + Double.parseDouble(et_right.getText().toString());
            }
        }
        if (sum != 0.0d) {
            return sum / ((tl_cdxx.getChildCount() - 1) * 2);
        }
        return 0.0d;
    }

    /**
     * @param @return 设定文件
     * @return Double    返回类型
     * @throws
     * @Title: calcuS
     * @Description: 标准差
     */
    public Double calcuS() {
        //方差
        Double sum = 0.0d;
        Double l = calcuL();
        if (!ObjectUtils.isNullOrEmptyString(l)) {
            for (int i = 1; i < tl_cdxx.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tl_cdxx.getChildAt(i);
                EditText et_left = (EditText) tableRow.findViewWithTag("left");
                EditText et_right = (EditText) tableRow.findViewWithTag("right");
                if (et_left == null || et_right == null) {
                    continue;
                }
                if (!ObjectUtils.isNullOrEmptyString(et_left.getText().toString())) {
                    sum = sum + Math.pow(Double.parseDouble(et_left.getText().toString()) - l, 2);
                }
                if (!ObjectUtils.isNullOrEmptyString(et_right.getText().toString())) {
                    sum = sum + Math.pow(Double.parseDouble(et_right.getText().toString()) - l, 2);
                }
            }
        }
        if (sum != 0.0d) {
            return Math.sqrt(sum / (tl_cdxx.getChildCount() - 1) * 2);
        }
        return 0.0d;
    }

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws
     * @Title: calcu
     * @Description: 计算
     */
    public void calcu() {
        Double l = calcuL();
        if (l != null) {
            tv_l.setText(String.format("%.3f", l));
        }
        Double s = calcuS();
        if (s != null) {
            tv_S.setText(String.format("%.3f", s));
        }
        String et_ZText = et_Z.getText().toString();
        String et_yxwczText = et_yxwcz.getText().toString();
        if (BuildConfig.DEBUG) Log.d("ZljcWccsActivity", et_ZText);
        if (l != null && s != null && !et_ZText .equals("")&&et_ZText!=null ) {
            Double Lr = l + Double.parseDouble(et_ZText) * s;
            tv_Lr.setText(String.format("%.3f", Lr));

            if (TextUtils.isEmpty(et_yxwczText)) {
                Toast.makeText(this, "弯沉值为填写", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Lr <= Double.parseDouble(et_yxwczText)) {
                tv_result.setText("   合格");
            } else {
                tv_result.setText("   不合格");
            }

        }
    }


    /**
     * @author catsic-wuxianling
     * @ClassName: EditChangedListener
     * @Description: 文本内容改变Listener
     * @date 2015年8月18日 下午3:30:01
     */
    class EditChangedListener implements TextWatcher {

        String trTag;
        String viewTag;

        EditChangedListener() {

        }

        EditChangedListener(String trTag, String viewTag) {
            this.trTag = trTag;
            this.viewTag = viewTag;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            TableRow tableRow = null;
            EditText editText = null;
            if (!ObjectUtils.isNullOrEmptyString(trTag) && !ObjectUtils.isNullOrEmptyString(viewTag)) {
                tableRow = (TableRow) tl_cdxx.findViewWithTag(trTag);
                editText = (EditText) tableRow.findViewWithTag(viewTag);
            }

            //左
            if ("leftZds".equals(viewTag)) {
                if (tableRow != null) {
                    EditText et_left = (EditText) tableRow.findViewWithTag("left");
                    try {
                        Double leftZds = Double.parseDouble(s.toString());
                        et_left.setText((leftZds * 2) + "");
                    } catch (Exception e) {

                    }
                }
            }

            //右
            if ("rightZds".equals(viewTag)) {
                if (tableRow != null) {
                    EditText et_right = (EditText) tableRow.findViewWithTag("right");
                    try {
                        Double rightZds = Double.parseDouble(s.toString());
                        et_right.setText((rightZds * 2) + "");
                    } catch (Exception e) {

                    }
                }
            }


            //计算
            calcu();

        }

    }

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws JSONException
     * @throws
     * @Title: save
     * @Description: 数据保存
     */
    public void save() throws JSONException {
        //1.1
        TZljc zljc = new TZljc();
        zljc.setCrowid(UUID.randomUUID().toString());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("xmid")) {
            String xmid = bundle.getString("xmid");
            zljc.setXmid(xmid);
        }
        zljc.setJclb(AppConstants.ZLJC_WCCS);
        if (!ObjectUtils.isNullOrEmptyString(tv_result.getText())) {
            zljc.setResult(tv_result.getText().toString().trim());
        }
        JSONObject loginUser = AppContext.LOGINUSER;
        if (loginUser != null) {
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

        FinalDb db = FinalDb.create(this, AppConstants.DB_NAME);
        db.save(zljc);

        //1.2
        TZljcWccs zljcWccs = new TZljcWccs();
        zljcWccs.setCrowid(UUID.randomUUID().toString());
        zljcWccs.setParentid(zljc.getCrowid());
        zljcWccs.setCdcc(et_cdcc.getText().toString());
        if (!ObjectUtils.isNullOrEmptyString(et_zh.getText().toString())) {
            zljcWccs.setZh(Double.parseDouble(et_zh.getText().toString()));
        }
        if (!ObjectUtils.isNullOrEmptyString(et_yxwcz.getText().toString())) {
            zljcWccs.setYxwcz(Double.parseDouble(et_yxwcz.getText().toString()));
        }
        if (!ObjectUtils.isNullOrEmptyString(et_Z.getText().toString())) {
            zljcWccs.setBzxs(Double.parseDouble(et_Z.getText().toString()));
        }

        db.save(zljcWccs);

        //1.3
        List<TZljcWccsSub> zljcWccsSubs = new ArrayList<TZljcWccsSub>();
        for (int i = 1; i < tl_cdxx.getChildCount(); i++) {
            TZljcWccsSub sub = new TZljcWccsSub();
            TableRow tableRow = (TableRow) tl_cdxx.getChildAt(i);
            EditText et_cdbh = (EditText) tableRow.findViewWithTag("cdbh");
            EditText et_leftZds = (EditText) tableRow.findViewWithTag("leftZds");
            EditText et_rightZds = (EditText) tableRow.findViewWithTag("rightZds");
            EditText et_left = (EditText) tableRow.findViewWithTag("left");
            EditText et_right = (EditText) tableRow.findViewWithTag("right");

            sub.setParentid(zljcWccs.getCrowid());
            if (!ObjectUtils.isNullOrEmptyString(et_cdbh.getText().toString())) {
                sub.setCdbh(et_cdbh.getText().toString());
            }
            if (!ObjectUtils.isNullOrEmptyString(et_leftZds.getText().toString())) {
                sub.setLeftZds(Double.parseDouble(et_leftZds.getText().toString()));
            }
            if (!ObjectUtils.isNullOrEmptyString(et_rightZds.getText().toString())) {
                sub.setRightZds(Double.parseDouble(et_rightZds.getText().toString()));
            }
            if (!ObjectUtils.isNullOrEmptyString(et_left.getText().toString())) {
                sub.setLeft(Double.parseDouble(et_left.getText().toString()));
            }
            if (!ObjectUtils.isNullOrEmptyString(et_right.getText().toString())) {
                sub.setRight(Double.parseDouble(et_right.getText().toString()));
            }

            db.save(sub);
            zljcWccsSubs.add(sub);
        }
        //暂时保存在本地
//		zljcWccs.setZljcWccsSubs(zljcWccsSubs);
//		zljc.setTZljcWccs(zljcWccs);
//		//WS
//		
//		saveOrUpdate(zljc);

        Intent intent = new Intent(this, ZljcListActivity.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("jclb", AppConstants.ZLJC_WCCS);
        if (xmjbxxJsonObject != null) {
            bundle2.putString("xmid", xmjbxxJsonObject.getString("xmid"));
            bundle2.putString("xmmc", xmjbxxJsonObject.getString("xmmc"));
            bundle2.putString("xmlxdm", xmjbxxJsonObject.getString("xmlxdm"));
            bundle2.putString("xzqh", xmjbxxJsonObject.getString("xzqh"));
        }
        bundle2.putString("title", "弯沉测试");


        intent.putExtras(bundle2);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addRow();
                break;
            case R.id.btn_save:
                try {
                    save();
                } catch (JSONException e) {
                    ToastUtil.showLongToast(this, "保存失败");
                }
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
//				else if (v.getId() == R.id.et_syrq) {
//					ProgressDialogUtil.show(this, "正在加载...", true);
//					DateTimePickerUtil.showDatePicker(this,et_syrq);
//					ProgressDialogUtil.dismiss();
//				}
                break;
        }
        return false;
    }
}
