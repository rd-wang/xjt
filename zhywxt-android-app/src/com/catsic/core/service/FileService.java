package com.catsic.core.service;

import android.content.Context;

import com.catsic.biz.js.activity.ZlxcActivity;
import com.catsic.biz.js.activity.ZlxcRespActivity;
import com.catsic.biz.yh.activity.QljcJEditeQlActivity;
import com.catsic.biz.yh.activity.YhXcjlViewActivity;
import com.catsic.core.AppConstants;
import com.catsic.core.AppUrls;
import com.catsic.core.activity.PhotoViewActivity;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.Tfile;
import com.catsic.core.service.base.BaseService;
import com.catsic.core.tools.FileUtils;
import com.catsic.core.tools.GsonUtils;
import com.catsic.core.tools.LogUtils;
import com.catsic.core.tools.ProgressDialogUtil;
import com.catsic.core.tools.SoapUtil;
import com.catsic.core.tools.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxianling
 * @Description: 文件Service
 * @date 2014年11月15日 下午2:26:06
 */
public class FileService extends BaseService {

    public FileService(Context context) {
        super(context);
    }

    public List<Tfile> getLocalFileList(String relationId) {
        return db.findAllByWhere(Tfile.class, " relationId='" + relationId + "'");
    }

    public List<Tfile> getLocalFileList(String groupId, String relationId) {
        return db.findAllByWhere(Tfile.class, " groupId='" + groupId + "' and relationId='" + relationId + "'");
    }

    public List<Tfile> getLocalFileList(String groupId, String relationId, String orderBy) {
        return db.findAllByWhere(Tfile.class, " groupId='" + groupId + "' and relationId='" + relationId + "'", orderBy);
    }

    /**
     * @param @param groupId
     * @param @param relationId
     * @return void
     * @throws
     * @Title: getFileList
     * @Description: TODO
     */
    public void list(final String groupId, String relationId) {
        //1.1本地是否存在文件
        List<Tfile> fileList = getLocalFileList(groupId, relationId);
        LogUtils.outString("查询本地db" + fileList.size());
        if (fileList != null && fileList.size() > 0) {
            LogUtils.outString("本地db存在");
            listHandler(groupId, fileList);
        } else {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("groupId", groupId);
            paramMap.put("relationId", relationId);

            SoapUtil.callService(AppUrls.getServiceURL(AppUrls.COMMON_FILE_URL), AppUrls.COMMON_FILE_NAMESPACE, "list", paramMap, new SoapUtil.WebServiceCallBack() {
                @Override
                public void onSucced(String result) {
                    LogUtils.outString(result + "result");
                    ProgressDialogUtil.dismiss();
                    if (result != null) {
                        List<Tfile> list = new Gson().fromJson(result, new TypeToken<List<Tfile>>() {
                        }.getType());
                        listHandler(groupId, list);
                    }
                }

                @Override
                public void onFailure(String result) {
                    ProgressDialogUtil.dismiss();
                    ToastUtil.showShortToast(context, "加载失败");
                }
            });
        }
    }

    private void listHandler(final String groupId, List<Tfile> list) {
        //File
        ProgressDialogUtil.dismiss();
        List<File> files = new ArrayList<File>();
        for (Tfile tfile : list) {
            //保存文件至本地
            File file = FileUtils.base64ToFile(tfile.getContent(), tfile.getFileName());
            if (file != null) {
                tfile.setFilePath(file.getPath());
            } else {
                //待处理
            }
            tfile.setContent("");
            files.add(file);

        }
        //养护巡查明细
        if (context instanceof YhXcjlViewActivity) {
            YhXcjlViewActivity activity = (YhXcjlViewActivity) context;
            activity.adapter.fileList = list;
            activity.adapter.notifyDataSetChanged();
            //质量巡查反馈
        } else if (context instanceof ZlxcRespActivity) {
            ZlxcRespActivity activity = (ZlxcRespActivity) context;
            //初始化巡查图片
            if ("T_JS_ZLGL_ZLXC".equals(groupId)) {
                //压缩图片
                activity.xc_adapter.oper = AppConstants.VIEW;
                activity.xc_adapter.fileList = list;
                activity.xc_adapter.notifyDataSetChanged();
                //初始化编辑页面反馈图片
            } else if ("T_JS_ZLGL_ZLXCRESP".equals(groupId)) {
                for (File file : files) {
                    Bimp.drr.add(file.getPath());
                }
                //压缩图片
                activity.adapter.update();
                activity.adapter.fileList = list;
                activity.adapter.notifyDataSetChanged();

            }
        }
        //质量巡查
        else if (context instanceof ZlxcActivity) {
            ZlxcActivity activity = (ZlxcActivity) context;
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        Bimp.drr.add(file.getPath());
                    }
                }
            }
            //压缩图片
            activity.adapter.update();
            activity.adapter.fileList = list;
            activity.adapter.notifyDataSetChanged();
            //图片查看
        } else if (context instanceof PhotoViewActivity) {
            PhotoViewActivity activity = (PhotoViewActivity) context;
            activity.initData(list);
            activity.adapter.notifyDataSetChanged();
        } else if (context instanceof QljcJEditeQlActivity) {
            QljcJEditeQlActivity activity = (QljcJEditeQlActivity) context;
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        Bimp.drr.add(file.getPath());
                    }
                }
            }
            //压缩图片
            activity.adapter.update();
            activity.adapter.fileList = list;
            activity.adapter.notifyDataSetChanged();
        }
    }

    /**
     * @param @param tfile    设定文件
     * @return void    返回类型
     * @throws
     * @Title: upload
     * @Description: 文件上传
     */
    public void upload(Tfile tfile) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("jsonStr", GsonUtils.toJson(tfile));
        SoapUtil.callService(AppUrls.getServiceURL(AppUrls.COMMON_FILE_URL), AppUrls.COMMON_FILE_NAMESPACE, "upload", paramMap, new SoapUtil.WebServiceCallBack() {
            @Override
            public void onSucced(String result) {
                ProgressDialogUtil.dismiss();
            }

            @Override
            public void onFailure(String result) {
                ProgressDialogUtil.dismiss();
                //ToastUtil.showShortToast(context, "加载失败");
            }
        });
    }

    public void listQlTest(final String groupId, String relationId) {
        //1.1本地是否存在文件
        List<Tfile> fileList = getLocalFileList(groupId, relationId);
        LogUtils.outString("查询本地db" + fileList.size());
        if (fileList != null && fileList.size() > 100) {
            LogUtils.outString("本地db存在");
            listHandler(groupId, fileList);
        } else {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("groupId", groupId);
            paramMap.put("relationId", relationId);
            //文件服务器：AppUrls.getServiceURL(AppUrls.COMMON_FILE_URL)  暂时用测试版
            //           AppUrls.COMMON_FILE_NAMESPACE 命名空间
            SoapUtil.callService(AppUrls.TESTFileServerUrl, AppUrls.TESTFileSpaceName, "list", paramMap, new SoapUtil.WebServiceCallBack() {
                @Override
                public void onSucced(String result) {
                    LogUtils.outString(result + "result");
                    ProgressDialogUtil.dismiss();
                    if (result != null) {
                        List<Tfile> list = new Gson().fromJson(result, new TypeToken<List<Tfile>>() {
                        }.getType());
                        listHandler(groupId, list);
                    }
                }

                @Override
                public void onFailure(String result) {
                    ProgressDialogUtil.dismiss();
                    ToastUtil.showShortToast(context, "加载失败");
                }
            });
        }
    }
}
