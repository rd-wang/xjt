
package com.catsic.biz.js.activity;

import net.tsz.afinal.annotation.view.ViewInject;

import android.os.Bundle;
import android.widget.EditText;

import com.catsic.R;
import com.catsic.biz.js.bean.TZljcYsd;
import com.catsic.biz.js.custom.CustomTextWatcher;
import com.catsic.core.activity.base.BaseActivity;
import com.catsic.core.tools.StringUtil;

/**
 * @author catsic-wuxianling
 * @ClassName: ZljcYsdTabActivity
 * @Description: 压实度Tab
 * @date 2015年8月20日 下午2:27:44
 */
public class ZljcYsdTabActivity extends BaseActivity {

    private
    @ViewInject(id = R.id.et_sksd)
    EditText et_sksd;
    private
    @ViewInject(id = R.id.et_gstYylsz)
    EditText et_gstYylsz;
    private
    @ViewInject(id = R.id.et_ztjccmhsz)
    EditText et_ztjccmhsz;
    private
    @ViewInject(id = R.id.et_gstSylsz)
    EditText et_gstSylsz;
    private
    @ViewInject(id = R.id.et_skhsz)
    EditText et_skhsz;
    private
    @ViewInject(id = R.id.et_lsmd)
    EditText et_lsmd;
    private
    @ViewInject(id = R.id.et_skrj)
    EditText et_skrj;
    private
    @ViewInject(id = R.id.et_ssyz)
    EditText et_ssyz;
    private
    @ViewInject(id = R.id.et_sysmd)
    EditText et_sysmd;
    private
    @ViewInject(id = R.id.et_hh)
    EditText et_hh;
    private
    @ViewInject(id = R.id.et_hz)
    EditText et_hz;
    private
    @ViewInject(id = R.id.et_hzSsyz)
    EditText et_hzSsyz;
    private
    @ViewInject(id = R.id.et_hzGsyz)
    EditText et_hzGsyz;
    private
    @ViewInject(id = R.id.et_sz)
    EditText et_sz;
    private
    @ViewInject(id = R.id.et_gsyz)
    EditText et_gsyz;
    private
    @ViewInject(id = R.id.et_syhsl)
    EditText et_syhsl;
    private
    @ViewInject(id = R.id.et_pjhsl)
    EditText et_pjhsl;
    private
    @ViewInject(id = R.id.et_zjhsl)
    EditText et_zjhsl;
    private
    @ViewInject(id = R.id.et_sygmd)
    EditText et_sygmd;
    private
    @ViewInject(id = R.id.et_zdgmd)
    EditText et_zdgmd;
    private
    @ViewInject(id = R.id.et_ysd)
    EditText et_ysd;

    private String crowid;

    @Override
    protected void onCreate(Bundle bundle) {
        setContentView(R.layout.fragment_js_zljc_ysd_item);

        initEvent();

        super.onCreate(bundle);
    }

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws
     * @Title: initEvent
     * @Description: 事件初始化
     */
    public void initEvent() {
        et_gstYylsz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "skhsz"));
        et_ztjccmhsz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "skhsz"));
        et_gstSylsz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "skhsz"));
        et_skhsz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "skrj"));
        et_lsmd.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "skrj"));
        et_skrj.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sysmd"));
        et_ssyz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sysmd"));
        et_sysmd.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sygmd"));
        et_hz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "gsyz"));
        et_hzSsyz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sz"));
        et_hzGsyz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sz"));
        et_hzGsyz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "gsyz"));
        et_sz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "syhsl"));
        et_gsyz.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "syhsl"));
        et_syhsl.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "pjhsl"));
        et_syhsl.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sygmd"));
        et_pjhsl.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "sygmd"));
        et_sygmd.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "ysd"));
        et_zdgmd.addTextChangedListener(new CustomTextWatcher(this, null, "zljcYsd", "ysd"));
    }

    /**
     * @param @param obj    设定文件
     * @return void    返回类型
     * @throws
     * @Title: initViewData
     * @Description: 初始化视图数据
     */
    public void initViewData(TZljcYsd obj) {
        crowid = obj.getCrowid();

        et_sksd.setText(String.valueOf(obj.getSksd()));
        et_gstYylsz.setText(String.valueOf(obj.getGstYylsz()));
        et_ztjccmhsz.setText(String.valueOf(obj.getZtjccmhsz()));
        et_gstSylsz.setText(String.valueOf(obj.getGstSylsz()));
        et_skhsz.setText(String.valueOf(obj.getSkhsz()));
        et_lsmd.setText(String.valueOf(obj.getLsmd()));
        et_skrj.setText(String.valueOf(obj.getSkrj()));
        et_ssyz.setText(String.valueOf(obj.getSsyz()));
        et_sysmd.setText(String.valueOf(obj.getSysmd()));
        et_hh.setText(obj.getHh());
        et_hz.setText(String.valueOf(obj.getHz()));
        et_hzSsyz.setText(String.valueOf(obj.getHzSsyz()));
        et_hzGsyz.setText(String.valueOf(obj.getHzGsyz()));
        et_sz.setText(String.valueOf(obj.getSz()));
        et_gsyz.setText(String.valueOf(obj.getGsyz()));
        et_syhsl.setText(String.valueOf(obj.getSyhsl()));
        et_pjhsl.setText(String.valueOf(obj.getPjhsl()));
        et_zjhsl.setText(String.valueOf(obj.getZjhsl()));
        et_sygmd.setText(String.valueOf(obj.getSygmd()));
        et_zdgmd.setText(String.valueOf(obj.getZdgmd()));
        et_ysd.setText(String.valueOf(obj.getYsd()));

        //计算
        initFunComp();
    }

    /**
     * @param 设定文件
     * @return void    返回类型
     * @throws
     * @Title: initFunComp
     * @Description: 初始化计算数据
     */
    private void initFunComp() {
        funComp("skhsz");
        funComp("skrj");
        funComp("sysmd");
        funComp("sz");
        funComp("gsyz");
        funComp("syhsl");
        funComp("pjhsl");
        funComp("sygmd");
        funComp("ysd");
    }

    /**
     * @param @param cType    设定文件
     * @return void    返回类型
     * @throws
     * @Title: funComp
     * @Description: 自动计算
     */
    public void funComp(String cType) {
        if ("skhsz".equals(cType)) {
            if (StringUtil.isDecimal(et_gstYylsz.getText().toString()) && StringUtil.isDecimal(et_ztjccmhsz.getText().toString())
                    && StringUtil.isDecimal(et_gstSylsz.getText().toString())) {
                Double val = Double.parseDouble(et_gstYylsz.getText().toString()) * 1.0 - Double.parseDouble(et_ztjccmhsz.getText().toString()) * 1.0
                        - Double.parseDouble(et_gstSylsz.getText().toString()) * 1.0;
                et_skhsz.setText(String.format("%.3f", val));
            }
        } else if ("skrj" == cType) {
            if (StringUtil.isDecimal(et_skhsz.getText().toString()) && StringUtil.isDecimal(et_lsmd.getText().toString())) {
                Double val = Double.parseDouble(et_skhsz.getText().toString()) * 1.0 / Double.parseDouble(et_lsmd.getText().toString()) * 1.0;
                et_skrj.setText(String.format("%.3f", val));
            }
        } else if ("sysmd" == cType) {
            if (StringUtil.isDecimal(et_ssyz.getText().toString()) && StringUtil.isDecimal(et_skrj.getText().toString())) {
                Double val = Double.parseDouble(et_ssyz.getText().toString()) * 1.0 / Double.parseDouble(et_skrj.getText().toString()) * 1.0;
                et_sysmd.setText(String.format("%.3f", val));


                funComp("sygmd");
                funComp("ysd");
            }
        } else if ("sz" == cType) {
            if (StringUtil.isDecimal(et_hzSsyz.getText().toString()) && StringUtil.isDecimal(et_hzGsyz.getText().toString())) {
                Double val = Double.parseDouble(et_hzSsyz.getText().toString()) * 1.0 - Double.parseDouble(et_hzGsyz.getText().toString()) * 1.0;
                et_sz.setText(String.format("%.3f", val));


                funComp("syhsl");
                funComp("pjhsl");
                funComp("sygmd");
                funComp("ysd");
            }
        } else if ("gsyz" == cType) {
            if (StringUtil.isDecimal(et_hzGsyz.getText().toString()) && StringUtil.isDecimal(et_hz.getText().toString())) {
                Double val = Double.parseDouble(et_hzGsyz.getText().toString()) * 1.0 - Double.parseDouble(et_hz.getText().toString()) * 1.0;
                et_gsyz.setText(String.format("%.3f", val));


                funComp("syhsl");
                funComp("pjhsl");
                funComp("sygmd");
                funComp("ysd");
            }
        } else if ("syhsl" == cType || "pjhsl" == cType) {
            if (StringUtil.isDecimal(et_sz.getText().toString()) && StringUtil.isDecimal(et_gsyz.getText().toString())) {
                Double val = 100 * Double.parseDouble(et_sz.getText().toString()) * 1.0 / Double.parseDouble(et_gsyz.getText().toString()) * 1.0;
                if ("syhsl" == cType) {
                    et_syhsl.setText(String.format("%.3f", val));
                } else {
                    et_pjhsl.setText(String.format("%.3f", val));
                }
            }
        } else if ("sygmd" == cType) {
            if (StringUtil.isDecimal(et_sysmd.getText().toString()) && StringUtil.isDecimal(et_pjhsl.getText().toString())) {
                Double val = Double.parseDouble(et_sysmd.getText().toString()) * 1.0 / (1 + 0.01 * Double.parseDouble(et_pjhsl.getText().toString()) * 1.0);
                et_sygmd.setText(String.format("%.3f", val));
            }
        } else if ("ysd" == cType) {
            if (StringUtil.isDecimal(et_sygmd.getText().toString()) && StringUtil.isDecimal(et_zdgmd.getText().toString())) {
                Double val = Double.parseDouble(et_sygmd.getText().toString()) * 1.0 / Double.parseDouble(et_zdgmd.getText().toString()) * 1.0;
                et_ysd.setText(String.format("%.3f", val));
            }
        }
    }

}
