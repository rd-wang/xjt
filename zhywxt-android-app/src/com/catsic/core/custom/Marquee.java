package com.catsic.core.custom;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class Marquee extends TextView {

	public Marquee(Context paramContext) {
		super(paramContext);
	}

	public Marquee(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public Marquee(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public boolean isFocused() {
		return true;
	}

	protected void onFocusChanged(boolean paramBoolean, int paramInt,
			Rect paramRect) {
	}

}
