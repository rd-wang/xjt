package com.catsic.biz.yh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.catsic.R;
import com.catsic.biz.yh.adapter.QljcAddListAdapter;
import com.catsic.biz.yh.adapter.QljcEditSwipeAdapter;
import com.catsic.biz.yh.bean.QlAddCardBean;
import com.catsic.biz.yh.bean.QlcxListBean;
import com.catsic.biz.yh.bean.TQljc;
import com.catsic.biz.yh.popupwindow.PartPopupwindows;
import com.catsic.biz.yh.service.QljcUpdateAndDeleteService;
import com.catsic.core.AppConstants;
import com.catsic.core.activity.PhotoActivity;
import com.catsic.core.activity.PhotoAlbumActivity;
import com.catsic.core.activity.base.Base2Activity;
import com.catsic.core.adapter.TargetImageGridAdapter;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.Tfile;
import com.catsic.core.custom.ImagePopupWindows;
import com.catsic.core.service.FileService;
import com.catsic.core.tools.ActionBarManager;
import com.catsic.core.tools.AlbumHelper;
import com.catsic.core.tools.DateUtil;
import com.catsic.core.tools.FileUtils;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.MediaUtil;
import com.catsic.core.tools.ObjectUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.ToastUtil;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 桥梁检测
 * 完成对编辑过的桥梁在编辑
 */
public class QljcJEditeQlActivity extends Base2Activity {
    //当前已选图片适配器
    public TargetImageGridAdapter adapter;

    /**
     * 照片文件绝对路径
     **/
    public String imageFileName = "", xmid = "", crowid = "";

    /**
     * 编辑选择的图片
     **/
    public final int RESULT_DEL_IMAGES = 1234;
    @Bind(R.id.tv_qlmc)
    TextView tvQlmc;
    @Bind(R.id.tv_qlms)
    TextView tvQlms;
    @Bind(R.id.qljc_fzr)
    EditText qljcFzr;
    @Bind(R.id.qljc_jlr)
    EditText qljcJlr;
    @Bind(R.id.qljc_qlmc)
    EditText qljcQlmc;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.ll_container)
    ListView llContainer;
    @Bind(R.id.gv_images)
    GridView gvImages;
    @Bind(R.id.btn_save)
    BootstrapButton btnSave;
    public QlcxListBean bean;
    private String mId;
    public MyAdapter myAdapter;
    private static String from;
    private int position;
    private QlAddCardBean qlAddCardBean;
    public static String uUid;
    private FinalDb db;
    private ArrayList<Tfile> tfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_qljc_jcql);
        ButterKnife.bind(this);
        ActionBarManager.initBackTitle(this, getActionBar(), "桥梁检测");
        adapter = new TargetImageGridAdapter(this);
        adapter.update();
        gvImages.setAdapter(adapter);
        //加载进度
        ProgressDialogUtil.show(this, getResources()
                .getString(R.string.loading), true);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                LogUtils.outString(position + "position");
                if (position == Bimp.bmp.size()) {//添加(点击最后一张图片 +号)
                    //生成照片文件名
                    imageFileName = MediaUtil.generatorFilePath();
                    new ImagePopupWindows(QljcJEditeQlActivity.this, PhotoAlbumActivity.class, gvImages, imageFileName);
                } else {//查看
                    Intent intent = new Intent(QljcJEditeQlActivity.this, PhotoActivity.class);
                    intent.putExtra("ID", position);
                    startActivityForResult(intent, RESULT_DEL_IMAGES);
                }
            }
        });
        mId = getIntent().getStringExtra("mId");
        from = getIntent().getStringExtra("form");
        position = getIntent().getIntExtra("position", 0);
        if (from.equals("桥梁编辑")) {

            if (QljcEditSwipeAdapter.listItems != null) {
                initViewFormEditql();
            }
        } else if (from.equals("桥梁卡片")) {
            //桥梁卡片进入
            uUid = UUID.randomUUID().toString();
            QljcUpdateAndDeleteService.savaNewQljcQlmx(this, mId, uUid, new SoapUtil.WebServiceCallBack() {
                @Override
                public void onSucced(String result) {
                    ProgressDialogUtil.dismiss();
                    if (result != null) {
                        qlAddCardBean = QljcAddListAdapter.listItems.get(position);
                        myAdapter = new MyAdapter(new ArrayList<QlcxListBean.QljcmxSetBean>());
                        llContainer.setAdapter(myAdapter);
                        crowid = uUid;
                        tvQlmc.setText(qlAddCardBean.getQlmc());
                        tvQlms.setText(qlAddCardBean.getQlcd() + "*" + qlAddCardBean.getQmqk() + "米 " + qlAddCardBean.getQlbm());
                    }
                }

                @Override
                public void onFailure(String result) {
                    ToastUtil.showShortToast(QljcJEditeQlActivity.this,"加载失败");
                    ProgressDialogUtil.dismiss();
                }
            });

        }
    }

    public void initViewFormEditql() {
        bean = QljcEditSwipeAdapter.listItems.get(position);
        crowid = bean.getCrowid();
        tvQlmc.setText(bean.getQlmc());
        tvQlms.setText(bean.getQlcd() + "*" + bean.getQmqk() + "米 " + bean.getQlbm());
        qljcJlr.setText(bean.getJlr());
        qljcFzr.setText(bean.getFzr());
        qljcQlmc.setText(bean.getJcrq());
        myAdapter = new MyAdapter(bean.getQljcmxSet());
        llContainer.setAdapter(myAdapter);
        new FileService(this).listQlTest("T_YH_QLGL_QLJC", bean.getCrowid());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_save, R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                ProgressDialogUtil.show(this, "正在更改，请稍后...", true);
                TQljc obj = new TQljc();
                if (from.equals("桥梁编辑")) {
                    obj.setParentCrowid(mId);
                    obj.setCrowid(bean.getCrowid());
                    obj.setJlr(qljcJlr.getText().toString());
                    obj.setFzr(qljcFzr.getText().toString());
                    obj.setJcrq(qljcQlmc.getText().toString());
                } else {
                    obj.setParentCrowid(mId);
                    obj.setCrowid(uUid);
                    obj.setJlr(qljcJlr.getText().toString());
                    obj.setFzr(qljcFzr.getText().toString());
                    obj.setJcrq(qljcQlmc.getText().toString());
                }
                LogUtils.outString(obj.getCrowid());
                saveIconInfo(obj);
                QljcUpdateAndDeleteService.upDatas(obj, new SoapUtil.WebServiceCallBack() {
                    @Override
                    public void onSucced(String result) {
                        ProgressDialogUtil.dismiss();
                        LogUtils.outString(" QljcUpdateAndDeleteService.upDatas" + result);
                        if (from.equals("桥梁编辑")) {
                            finish();
                            bean.setJlr(qljcJlr.getText().toString());
                            bean.setFzr(qljcFzr.getText().toString());
                            bean.setJcrq(qljcQlmc.getText().toString());
                            //initViewFormEditql();

                        } else {

                            finish();
                            if (QljcAddListActivity.qljcAddListActivity != null) {
                                QljcAddListActivity.qljcAddListActivity.finish();
                            }
                            startActivity(new Intent(getBaseContext(), QljcEditListActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(String result) {
                        ProgressDialogUtil.dismiss();
                        Toast.makeText(QljcJEditeQlActivity.this, "提交失败，请检查网络。", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.iv_add:
                new PartPopupwindows(this, ivAdd, mId, from);
                Toast.makeText(this, "iv_add", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 拍照,从相册中选择
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MediaUtil.RESULT_CAPTURE_IMAGE://拍照
                if (resultCode == RESULT_OK) {
                    if (Bimp.drr.size() < AppConstants.IMAGES_CNT) {
                        Bimp.drr.add(AppConstants.IMAGES_BASEPATH + imageFileName);
                        adapter.update();
                    }
                    //update 重新构建相簿
                    AlbumHelper.getHelper().hasBuildImagesBucketList = false;
                }
                break;
            case MediaUtil.REQUEST_PHOTO://从相册中选择图片
                adapter.update();
                break;
            case RESULT_DEL_IMAGES://已选择图片编辑
                adapter.update();
                break;
        }
    }


    public class MyAdapter extends BaseAdapter {

        public List<QlcxListBean.QljcmxSetBean> mData;

        public MyAdapter(List<QlcxListBean.QljcmxSetBean> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;

            if (view == null) {
                view = View.inflate(QljcJEditeQlActivity.this, R.layout.qljc_edit_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            holder = (ViewHolder) view.getTag();
            final QlcxListBean.QljcmxSetBean qljcmxSetBean = mData.get(i);
            holder.tvJy.setText(qljcmxSetBean.getBycsyj());
            holder.tvMc.setText(qljcmxSetBean.getQslx());
            holder.tvXmms.setText(qljcmxSetBean.getQslx() + "," + qljcmxSetBean.getQsfw());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    QljcUpdateAndDeleteService.deleteQljcmx(QljcJEditeQlActivity.this, i, qljcmxSetBean.getCrowid(), "deleteQljcmx");
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_mc)
        TextView tvMc;
        @Bind(R.id.tv_xmms)
        TextView tvXmms;
        @Bind(R.id.tv_jy)
        TextView tvJy;
        @Bind(R.id.delete)
        ImageView delete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }

    /**
     * 录入图片信息
     *
     * @param obj
     */
    public void saveIconInfo(TQljc obj) {
        db = FinalDb.create(this, AppConstants.DB_NAME);
        db.deleteByWhere(Tfile.class, " relationId='" + obj.getCrowid() + "'");
        List<String> filePaths = Bimp.drr;
        List<Tfile> files = new ArrayList<>();
        for (String filePath : filePaths) {
            File f = new File(filePath);

            Tfile file = new Tfile();
            //file.setFileId(UUID.randomUUID().toString());
            //filePath处理
            file.setFilePath(FileUtils.absolutePathToFilePath(f.getAbsolutePath(), "T_YH_QLGL_QLJC"));
            file.setFileSize(f.length() + "");

            String fileType = "";
            String fileName = f.getName();
            if (ObjectUtils.isNotEmpty(fileName) && (fileName.indexOf(".") > 0)) {
                fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
            file.setFileType(fileType);
            file.setFileTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            file.setRelationId(obj.getCrowid());
            file.setGroupId("T_YH_QLGL_QLJC");
            file.setFileName(fileName);
            //服务端处理
            String content = FileUtils.fileToBase64(filePath);
            file.setContent(content);
            files.add(file);
            db.save(file);
        }
        obj.setFiles(files);
        //clear
    }

    @Override
    public void onBackPressed() {
        //清空已经选择的图片
        Bimp.reset();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.reset();
    }
}
