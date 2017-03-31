package com.catsic.core.custom;

import com.catsic.R;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @Description: 定义App Header
 * @author wuxianling
 * @date 2014年7月2日 下午5:24:28
 */
public class AppHeaderView extends LinearLayout {

	private Context mContext;
	private Marquee mText;

	public AppHeaderView(Context paramContext) {
		this(paramContext, null);
	}

	public AppHeaderView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.mContext = paramContext;
	}

	public String getText() {
		return this.mText.getText().toString();
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		this.mText = ((Marquee) findViewById(R.id.appheader_txt));
	}

	public void setText(String paramString) {
		this.mText.setText(paramString);
		if (this.mText.getText().length() > 10) {
			this.mText.setTransformationMethod(SingleLineTransformationMethod
					.getInstance());
			this.mText.setSingleLine(true);
			this.mText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
			this.mText.setMarqueeRepeatLimit(-1);
			this.mText.setFocusable(true);
			this.mText.setFocusableInTouchMode(true);
		}
	}

}
