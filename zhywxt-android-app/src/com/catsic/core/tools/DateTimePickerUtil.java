package com.catsic.core.tools;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.catsic.R;

/**  
  * @Description: DateTimePickerUtil 
  * @author wuxianling  
  * @date 2014年8月22日 下午1:01:35    
  */ 
public class DateTimePickerUtil {
	
	/**  
	  * @Title: showDateTimePicker  
	  * @Description: 弹出日期时间框 
	  * @param @param context
	  * @param @param editText     
	  * @return void   
	  * @throws  
	  */ 
	public static void showDateTimePicker(Context context,final EditText editText) {
		//阻止软键盘弹出
		editText.setInputType(InputType.TYPE_NULL);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View  view = View.inflate(context,R.layout.common_date_time_dialog , null);
		builder.setView(view);
		
		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
		final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
		
		String currentDate = editText.getText().toString().trim();
		Calendar calendar = Calendar.getInstance();
		if (currentDate!=null && !currentDate.equals("")) {
			calendar.setTime(DateUtil.parse(currentDate, DateUtil.PATTERN_DATETIME));
		}else{
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH),null);
		
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
		
		
		builder
		.setTitle("选择时间")
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				  Calendar calendar = Calendar.getInstance();
				  calendar.setTimeInMillis(System.currentTimeMillis());
				  
				  String dateTime = datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth()
						+" "+timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute()+":"+calendar.get(Calendar.SECOND);
				  
				  editText.setText(dateTime);
			}
		}).setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		Dialog dialog = builder.create();
		dialog.show();
	}
	
	/**
	  * @Title: showDatePicker
	  * @Description: 弹出日期框
	  * @param @param context
	  * @param @param editText    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	public static void showDatePicker(Context context,final EditText editText) {
		//阻止软键盘弹出
		editText.setInputType(InputType.TYPE_NULL);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View  view = View.inflate(context,R.layout.common_date_dialog , null);
		builder.setView(view);
		
		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
		
		String currentDate = editText.getText().toString().trim();
		Calendar calendar = Calendar.getInstance();
		if (currentDate!=null && !currentDate.equals("")) {
			calendar.setTime(DateUtil.parse(currentDate, DateUtil.PATTERN_DATE));
		}else{
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		
		datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH),null);
		
		builder
		.setTitle("选择时间")
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				
				String dateTime = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
				editText.setText(dateTime);
			}
		}).setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		Dialog dialog = builder.create();
		dialog.show();
	}

}
