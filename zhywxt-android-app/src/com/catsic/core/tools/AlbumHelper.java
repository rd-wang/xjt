package com.catsic.core.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;

import com.catsic.core.bean.ImageBucket;
import com.catsic.core.bean.ImageItem;

/**  
  * @Description: 照片集帮助类 
  * 
  1、MediaStore.Audio.Mdia.EXTERNAL_CONTENT_URI：存储在外部设备上的音频文件
  2、MediaStore.Audio.Mdia.INTERNAL_CONTENT_URI：存储在手机内部上的音频文件
  3、MediaStore.Images.Mdia.EXTERNAL_CONTENT_URI：存储在外部设备上的图片文件
  4、MediaStore.Images.Mdia.INTERNAL_CONTENT_URI：存储在内部设备上的图片文件
  5、MediaStore.Video.Mdia.EXTERNAL_CONTENT_URI：存储在外部设备上的音频文件
  6、MediaStore.Video.Mdia.INTERNAL_CONTENT_URI：存储在内部设备上的音频文件
  * @author wuxianling  
  * @date 2014年8月22日 下午3:36:33    
  */ 
public class AlbumHelper {
	final String TAG = getClass().getSimpleName();
	private Context context;
	private ContentResolver cr;

	// 缩略图列表
	HashMap<String, String> thumbnailList = new HashMap<String, String>();
	// 专辑列表
	List<HashMap<String, String>> albumList = new ArrayList<HashMap<String, String>>();
	HashMap<String, ImageBucket> bucketList = new HashMap<String, ImageBucket>();

	private static AlbumHelper instance;

	private AlbumHelper() {
	}

	public static AlbumHelper getHelper() {
		if (instance == null) {
			instance = new AlbumHelper();
		}
		return instance;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
			cr = context.getContentResolver();
		}
	}

	/**
	 * Thumbnails.EXTERNAL_CONTENT_URI
	 * 得到缩略图
	 */
	private void getThumbnail(Uri uri) {
		//content://media/external/images/media
		String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID,Thumbnails.DATA };
		Cursor cursor = cr.query(uri, projection,null, null, null);
		getThumbnailColumnData(cursor);
	}

	/**
	 * 从数据库中得到缩略图
	 * 
	 * @param cur
	 */
	private void getThumbnailColumnData(Cursor cur) {
		if (cur.moveToFirst()) {
			int _id;
			int image_id;
			String image_path;
			int _idColumn = cur.getColumnIndex(Thumbnails._ID);
			int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
			int dataColumn = cur.getColumnIndex(Thumbnails.DATA);

			do {
				// Get the field values
				_id = cur.getInt(_idColumn);
				image_id = cur.getInt(image_idColumn);
				image_path = cur.getString(dataColumn);

				thumbnailList.put("" + image_id, image_path);
			} while (cur.moveToNext());
		}
	}

	/**
	 * Albums.EXTERNAL_CONTENT_URI
	 * 得到原图
	 */
	void getAlbum(Uri uri) {
		String[] projection = { Albums._ID, Albums.ALBUM, Albums.ALBUM_ART,Albums.ALBUM_KEY, Albums.ARTIST, Albums.NUMBER_OF_SONGS };
		Cursor cursor = cr.query(uri, projection, null,null, null);
		getAlbumColumnData(cursor);

	}

	/**
	 * 从本地数据库中得到原图
	 * 
	 * @param cur
	 */
	private void getAlbumColumnData(Cursor cur) {
		if (cur.moveToFirst()) {
			int _id;
			String album;
			String albumArt;
			String albumKey;
			String artist;
			int numOfSongs;

			int _idColumn = cur.getColumnIndex(Albums._ID);
			int albumColumn = cur.getColumnIndex(Albums.ALBUM);
			int albumArtColumn = cur.getColumnIndex(Albums.ALBUM_ART);
			int albumKeyColumn = cur.getColumnIndex(Albums.ALBUM_KEY);
			int artistColumn = cur.getColumnIndex(Albums.ARTIST);
			int numOfSongsColumn = cur.getColumnIndex(Albums.NUMBER_OF_SONGS);

			do {
				// Get the field values
				_id = cur.getInt(_idColumn);
				album = cur.getString(albumColumn);
				albumArt = cur.getString(albumArtColumn);
				albumKey = cur.getString(albumKeyColumn);
				artist = cur.getString(artistColumn);
				numOfSongs = cur.getInt(numOfSongsColumn);

				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("_id", _id + "");
				hash.put("album", album);
				hash.put("albumArt", albumArt);
				hash.put("albumKey", albumKey);
				hash.put("artist", artist);
				hash.put("numOfSongs", numOfSongs + "");
				albumList.add(hash);
			} while (cur.moveToNext());

		}
	}

	/**
	 * 是否创建了图片集
	 */
	public boolean hasBuildImagesBucketList = false;

	/**
	 * 得到图片集
	 */
	void buildImagesBucketList() {
		long startTime = System.currentTimeMillis();

		// 构造缩略图索引
		getThumbnail(Thumbnails.INTERNAL_CONTENT_URI);
		getThumbnail(Thumbnails.EXTERNAL_CONTENT_URI);

		// 构造相册索引
		String columns[] = new String[] { Media._ID, Media.BUCKET_ID,
				Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,
				Media.SIZE, Media.BUCKET_DISPLAY_NAME };
		// 得到一个游标
		//Media.EXTERNAL_CONTENT_URI
		Uri uri = Media.INTERNAL_CONTENT_URI;
		Cursor cur = cr.query(uri, columns, null, null,Media.DATE_MODIFIED+" DESC");
		getImagesColumnData(cur);
		
		uri = Media.EXTERNAL_CONTENT_URI;
		cur = cr.query(uri, columns, null, null, Media.DATE_MODIFIED+" DESC");
		getImagesColumnData(cur);

		hasBuildImagesBucketList = true;
		long endTime = System.currentTimeMillis();
		Log.d(TAG, "use time: " + (endTime - startTime) + " ms");
	}
	
	private void getImagesColumnData(Cursor cur){
		if (cur.moveToFirst()) {
			// 获取指定列的索引
			int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
			int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
			int photoNameIndex = cur.getColumnIndexOrThrow(Media.DISPLAY_NAME);
			int photoTitleIndex = cur.getColumnIndexOrThrow(Media.TITLE);
			int photoSizeIndex = cur.getColumnIndexOrThrow(Media.SIZE);
			int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
			int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
			int picasaIdIndex = cur.getColumnIndexOrThrow(Media.PICASA_ID);
			// 获取图片总数
			int totalNum = cur.getCount();

			do {
				String _id = cur.getString(photoIDIndex);
				String name = cur.getString(photoNameIndex);
				String path = cur.getString(photoPathIndex);
				String title = cur.getString(photoTitleIndex);
				String size = cur.getString(photoSizeIndex);
				String bucketName = cur.getString(bucketDisplayNameIndex);
				String bucketId = cur.getString(bucketIdIndex);
				String picasaId = cur.getString(picasaIdIndex);

				ImageBucket bucket = bucketList.get(bucketId);
				if (bucket == null) {
					bucket = new ImageBucket();
					bucketList.put(bucketId, bucket);
					bucket.imageList = new ArrayList<ImageItem>();
					bucket.bucketName = bucketName;
				}
				bucket.count++;
				ImageItem imageItem = new ImageItem();
				imageItem.imageId = _id;
				imageItem.imagePath = path;
				imageItem.thumbnailPath = thumbnailList.get(_id);
				bucket.imageList.add(imageItem);

			} while (cur.moveToNext());
		}

	}

	/**
	 * 得到图片集
	 * 
	 * @param refresh
	 * @return
	 */
	public List<ImageBucket> getImagesBucketList(boolean refresh) {
		if (refresh || (!refresh && !hasBuildImagesBucketList)) {
			thumbnailList.clear();
			albumList.clear();
			bucketList.clear();
			
			buildImagesBucketList();
		}
		List<ImageBucket> tmpList = new ArrayList<ImageBucket>();
		Iterator<Entry<String, ImageBucket>> itr = bucketList.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, ImageBucket> entry = (Map.Entry<String, ImageBucket>) itr.next();
			tmpList.add(entry.getValue());
		}
		return tmpList;
	}

	/**
	 * 得到原始图像路径
	 * 
	 * @param image_id
	 * @return
	 */
	String getOriginalImagePath(String image_id) {
		String path = null;
		Log.i(TAG, "---(^o^)----" + image_id);
		String[] projection = { Media._ID, Media.DATA };
		Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, projection,
				Media._ID + "=" + image_id, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			path = cursor.getString(cursor.getColumnIndex(Media.DATA));

		}
		return path;
	}
	
	public void insert(Uri uri,ContentValues contentValues){
		cr.insert(uri, contentValues);
	}

}
