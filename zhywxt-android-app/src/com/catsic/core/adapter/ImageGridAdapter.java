package com.catsic.core.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.AppConstants;
import com.catsic.core.bean.Bimp;
import com.catsic.core.bean.BitmapCache;
import com.catsic.core.bean.BitmapCache.ImageCallback;
import com.catsic.core.bean.ImageItem;

/**  
  * @Description: 相册图片 
  * @author wuxianling  
  * @date 2014年8月22日 下午2:53:17    
  */ 
public class ImageGridAdapter extends BaseAdapter {

	final String TAG = getClass().getSimpleName();
	
	private Activity activity;
	private List<ImageItem> dataList;
	/**选中图片的路径**/
	public  Map<String, String> map = new HashMap<String, String>();
	private BitmapCache cache;
	private TextCallback textcallback = null;
	private Handler mHandler;
	/**选择图片总数**/
	private int selectTotal = 0;
	
	/**回调，显示图片**/
	ImageCallback callback = new ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	
	/**  
	  * @Description: 监听当前选中图片
	  * @author wuxianling  
	  * @date 2014年8月22日 下午3:40:08    
	  */ 
	public static interface TextCallback {
		public void onListen(int count);
	}

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	public ImageGridAdapter(Activity act, List<ImageItem> list, Handler mHandler) {
		this.activity = act;
		dataList = list;
		cache = new BitmapCache();
		this.mHandler = mHandler;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView text;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(activity, R.layout.common_item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView.findViewById(R.id.isselected);
			holder.text = (TextView) convertView.findViewById(R.id.item_image_grid_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final ImageItem item = dataList.get(position);

		holder.iv.setTag(item.imagePath);
		//显示缩略图
		cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath,callback);
		if (item.isSelected) {
			holder.selected.setImageResource(R.drawable.icon_data_select);  
			holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
		} else {
			holder.selected.setImageResource(-1);
			holder.text.setBackgroundColor(0x00000000);
		}
		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = dataList.get(position).imagePath;
				/**小于最大图片支持数**/
				if ((Bimp.drr.size() + selectTotal) < AppConstants.IMAGES_CNT) {
					item.isSelected = !item.isSelected;
					/**选中**/
					if (item.isSelected) {
						holder.selected.setImageResource(R.drawable.icon_data_select);
						holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
						
						/**选中图片个数递增**/
						selectTotal++;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						/**记录选中图片路径**/
						map.put(path, path);

					} else if (!item.isSelected) {/**取消**/
						holder.selected.setImageResource(-1);
						holder.text.setBackgroundColor(0x00000000);
						/**取消选中，递减**/
						selectTotal--;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						/**移除 选中图片路径**/
						map.remove(path);
					}
				/**超过最大图片支持数**/
				} else if ((Bimp.drr.size() + selectTotal) >= AppConstants.IMAGES_CNT) {
					/**取消操作**/
					if (item.isSelected == true) {
						item.isSelected = !item.isSelected;
						holder.selected.setImageResource(-1);
						selectTotal--;
						map.remove(path);
					/**通知用户，超过最大图片支持数**/
					} else {
						Message message = Message.obtain(mHandler, 0);
						message.sendToTarget();
					}
				}
			}
		});
		return convertView;
	}
}
