package com.catsic.core.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catsic.R;
import com.catsic.core.bean.BitmapCache;
import com.catsic.core.bean.BitmapCache.ImageCallback;
import com.catsic.core.bean.ImageBucket;

/**  
  * @Description: 照片集适配器 
  * @author wuxianling  
  * @date 2014年8月22日 下午2:46:10    
  */ 
public class ImageBucketAdapter extends BaseAdapter {
	final String TAG = getClass().getSimpleName();

	Activity act;
	
	List<ImageBucket> dataList;
	BitmapCache cache;
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

	public ImageBucketAdapter(Activity act, List<ImageBucket> list) {
		this.act = act;
		dataList = list;
		cache =  new BitmapCache();
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
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView name;
		private TextView count;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup arg2) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(act, R.layout.common_item_image_bucket, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView.findViewById(R.id.isselected);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ImageBucket item = dataList.get(i);
		holder.count.setText("" + item.count);
		holder.name.setText(item.bucketName);
		holder.selected.setVisibility(View.GONE);
		if (item.imageList != null && item.imageList.size() > 0) {
			String thumbPath = item.imageList.get(0).thumbnailPath;
			String sourcePath = item.imageList.get(0).imagePath;
			holder.iv.setTag(sourcePath);
			cache.displayBmp(holder.iv, thumbPath, sourcePath, callback);
		} else {
			holder.iv.setImageBitmap(null);
			Log.e(TAG, "no images in bucket " + item.bucketName);
		}
		return convertView;
	}

}
