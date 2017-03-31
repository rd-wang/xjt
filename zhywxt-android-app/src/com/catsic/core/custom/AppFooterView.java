package com.catsic.core.custom;

import java.util.EventListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.catsic.R;

/**
 * @Description: App Footer View
 * @author wuxianling
 * @date 2014年7月2日 下午5:43:14
 */
public class AppFooterView extends LinearLayout {
	private Context mContext;
	private OnMenuItemClickListener mListener1 = null;
	private OnMenuItemClickListener mListener2 = null;
	private OnMenuItemClickListener mListener3 = null;
	private OnMenuItemClickListener mListener4 = null;
	private ImageView mMenu1;
	private ImageView mMenu2;
	private ImageView mMenu3;
	private ImageView mMenu4;

	public AppFooterView(Context paramContext) {
		this(paramContext, null);
	}

	public AppFooterView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.mContext = paramContext;
	}

	private void setMenuItem1Image(final int paramInt1, final int paramInt2) {
		if (this.mMenu1 == null)
			return;
		this.mMenu1.setImageResource(paramInt1);
		//触屏操作(按住或松开)
		this.mMenu1.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view,MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN://按下
						AppFooterView.this.mMenu1.setImageResource(paramInt2);
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						AppFooterView.this.mMenu1.setImageResource(paramInt1);
						break;
					default:
						break;
				}
				return false;
			}
		});
	}

	private void setMenuItem2Image(final int paramInt1, final int paramInt2) {
		if (this.mMenu2 == null)
			return;
		this.mMenu2.setImageResource(paramInt1);
		//触屏操作(按住或松开)
		this.mMenu2.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view,MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN://按下
						AppFooterView.this.mMenu2.setImageResource(paramInt2);
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						AppFooterView.this.mMenu2.setImageResource(paramInt1);
						break;
					default:
						break;
				}
				return false;
			}
		});
	}

	private void setMenuItem3Image(final int paramInt1, final int paramInt2) {
		if (this.mMenu3 == null)
			return;
		this.mMenu3.setImageResource(paramInt1);
		//触屏操作(按住或松开)
		this.mMenu3.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view,MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN://按下
						AppFooterView.this.mMenu3.setImageResource(paramInt2);
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						AppFooterView.this.mMenu3.setImageResource(paramInt1);
						break;
					default:
						break;
				}
				return false;
			}
		});
	}

	private void setMenuItem4Image(final int paramInt1, final int paramInt2) {
		if (this.mMenu4 == null)
			return;
		this.mMenu4.setImageResource(paramInt1);
		//触屏操作(按住或松开)
		this.mMenu4.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view,MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN://按下
						AppFooterView.this.mMenu4.setImageResource(paramInt2);
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						AppFooterView.this.mMenu4.setImageResource(paramInt1);
						break;
					default:
						break;
				}
				return false;
			}
		});
	}

	private void setOnMenuItem1ClickListener(
			OnMenuItemClickListener paramOnMenuItemClickListener) {
		this.mListener1 = paramOnMenuItemClickListener;
	}

	private void setOnMenuItem2ClickListener(
			OnMenuItemClickListener paramOnMenuItemClickListener) {
		this.mListener2 = paramOnMenuItemClickListener;
	}

	private void setOnMenuItem3ClickListener(
			OnMenuItemClickListener paramOnMenuItemClickListener) {
		this.mListener3 = paramOnMenuItemClickListener;
	}

	private void setOnMenuItem4ClickListener(
			OnMenuItemClickListener paramOnMenuItemClickListener) {
		this.mListener4 = paramOnMenuItemClickListener;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		this.mMenu1 = ((ImageView) findViewById(R.id.appfooter_foot1));
		this.mMenu2 = ((ImageView) findViewById(R.id.appfooter_foot2));
		this.mMenu3 = ((ImageView) findViewById(R.id.appfooter_foot3));
		this.mMenu4 = ((ImageView) findViewById(R.id.appfooter_foot4));
		this.mMenu1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (AppFooterView.this.mListener1 != null)
					AppFooterView.this.mListener1.onMenuItemClicked();
			}
		});
		this.mMenu2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (AppFooterView.this.mListener2 != null)
					AppFooterView.this.mListener2.onMenuItemClicked();
			}
		});
		this.mMenu3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (AppFooterView.this.mListener3 != null)
					AppFooterView.this.mListener3.onMenuItemClicked();
			}
		});
		this.mMenu4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (AppFooterView.this.mListener4 != null)
					AppFooterView.this.mListener4.onMenuItemClicked();
			}
		});
	}

	/**  
	  * @Title: setMenuItem  
	  * @Description: 设置图标，初始化事件 
	  * @param @param paramInt
	  * @param @param paramFooterMenuItem     
	  * @return void   
	  * @throws  
	  */ 
	public void setMenuItem(int paramInt, FooterMenuItem paramFooterMenuItem) {
		switch (paramInt) {
		case 0:
			setMenuItem1Image(paramFooterMenuItem.getImgNormal(),paramFooterMenuItem.getImgPress());
			setOnMenuItem1ClickListener(paramFooterMenuItem.getListener());
			break;
		case 1:
			setMenuItem2Image(paramFooterMenuItem.getImgNormal(),paramFooterMenuItem.getImgPress());
			setOnMenuItem2ClickListener(paramFooterMenuItem.getListener());
			break;
		case 2:
			setMenuItem3Image(paramFooterMenuItem.getImgNormal(),paramFooterMenuItem.getImgPress());
			setOnMenuItem3ClickListener(paramFooterMenuItem.getListener());
			break;
		case 3:
			setMenuItem4Image(paramFooterMenuItem.getImgNormal(),paramFooterMenuItem.getImgPress());
			setOnMenuItem4ClickListener(paramFooterMenuItem.getListener());
			break;
		default:
			break;
		}
	}

	public static abstract interface OnMenuItemClickListener extends EventListener {
		public abstract void onMenuItemClicked();
	}

}
