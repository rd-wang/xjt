package com.catsic.core.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.Tfile;
import com.catsic.core.tools.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
  * @Description: 目标显示图片GridAdapter
  * @author wuxianling
  * @date 2014年8月22日 下午1:15:38
  */
public class TargetImageGridAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater; // 视图容器
	private int selectedPosition = -1;// 选中的位置
	public List<Tfile> fileList;
	public String oper;
	private boolean shape;

	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public TargetImageGridAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public TargetImageGridAdapter(Context context,String oper) {
		this.context = context;
		this.oper = oper;
		inflater = LayoutInflater.from(context);
	}

	public TargetImageGridAdapter(Context context,List<Tfile> list,String oper) {
		this.context = context;
		this.fileList = list;
		this.oper = oper;
		inflater = LayoutInflater.from(context);
	}

	public void update() {
		loading();
	}

	public int getCount() {
		//view
		if (AppConstants.VIEW.equals(oper)) {
			return fileList == null || fileList.size() == 0 ? 0: fileList.size();
		}else{
			return (Bimp.bmp.size() + 1);
		}
	}

	public Object getItem(int arg0) {

		return null;
	}

	public long getItemId(int arg0) {

		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.common_item_published_grid,parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.item_grid_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//默认显示 + 图片
		if (position == Bimp.bmp.size() && (!AppConstants.VIEW.equals(oper))) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_addpic_unfocused));
			//图片超过图片总数限制的时候，则不显示
			if (position == AppConstants.IMAGES_CNT) {
				holder.image.setVisibility(View.GONE);
			}
		}else if(AppConstants.VIEW.equals(oper)){
			try {
				if (fileList!=null && fileList.size()>0) {
					File file = new File(fileList.get(position).getFilePath());
					Bitmap bitmap = null;
					//如果SD卡中存在，就显示，如果不存在 就从服务器段下载
					if (file.exists()) {
						FileInputStream fis = new FileInputStream(file);
						bitmap = BitmapFactory.decodeStream(fis);
						fis.close();
					}else{
						//--待完成
					}
					holder.image.setImageBitmap(bitmap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			holder.image.setImageBitmap(Bimp.bmp.get(position));
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					//更新GridView显示最新选择的图片
					notifyDataSetChanged();
					break;
				}
			super.handleMessage(msg);
		}
	};

	/**
	  * @Title: loading
	  * @Description: 加载图片
	  * @param
	  * @return void
	  * @throws
	  */
	public void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.drr.size()) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						try {
							String path = Bimp.drr.get(Bimp.max);
							//压缩图片
							Bitmap bm = Bimp.revitionImageSize(path);
							Bimp.bmp.add(bm);

							String picName = path.substring(path.lastIndexOf("/") + 1,path.lastIndexOf("."));
							//图片保存
							FileUtils.saveBitmap(bm, "" + picName);
							Bimp.max += 1;

							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

}