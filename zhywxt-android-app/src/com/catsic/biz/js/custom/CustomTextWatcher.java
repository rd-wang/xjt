
package com.catsic.biz.js.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.catsic.biz.js.activity.ZljcYsdTabActivity;

/**
  * @ClassName: CustomTextWatcher
  * @Description: EditText 文本内容监听
  * @author Comsys-wuxianling
  * @date 2015年5月19日 上午10:26:07
  */
public class CustomTextWatcher implements TextWatcher{
	
	private Context context;
	private EditText editText;
	private String type;
	private String param;
	
	public CustomTextWatcher(Context context,EditText editText,String type,String param){
		this.context = context;
		this.type = type;
		this.editText = editText;
		this.param = param;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if ("zljcYsd".equals(type)) {
			ZljcYsdTabActivity activity = (ZljcYsdTabActivity) context;
			activity.funComp(param);
		}
		
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		
		
	}

}
