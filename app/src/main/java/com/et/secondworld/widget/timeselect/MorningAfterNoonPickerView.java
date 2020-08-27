package com.et.secondworld.widget.timeselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.et.secondworld.R;
import com.et.secondworld.widget.timeselect.view.BasePickerView;
import com.et.secondworld.widget.timeselect.view.WheelMorningAfternoon;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by Sai on 15/11/22.
 */
public class MorningAfterNoonPickerView extends BasePickerView implements View.OnClickListener {
    public enum Type {
        MORNING_HOUR_MIN , AFTERNOON_HOUR_MIN
    }// 选择模式，年月日时分，年月日，年月日时 , 时分，月日时分 ,年月

    WheelMorningAfternoon wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;
    //Required

    public MorningAfterNoonPickerView(Context context, Type type) {
        super(context);
        setCancelable(true);//点击外面取消
        LayoutInflater.from(context).inflate(R.layout.pickerview_time_morning_afternoon, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.lly_morning_afternoon_timepicker);
        wheelTime = new WheelMorningAfternoon(timepickerview, type);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY)-2;
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker( hours, minute);

    }



    /**
     * 设置选中时间
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(hours, minute);
    }

//    /**
//     * 指定选中的时间，显示选择器
//     *
//     * @param date
//     */
//    public void show(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        if (date == null)
//            calendar.setTimeInMillis(System.currentTimeMillis());
//        else
//            calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        wheelTime.setPicker(year, month, day, hours, minute);
//        show();
//    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    /**
     * 设置文字大小
     * @param textSize
     */
    public void setTextSize(float textSize){
        wheelTime.setTextSize(textSize);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
//                                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
//                    Toast.makeText(v.getContext(),"time:"+wheelTime.getTime(),Toast.LENGTH_LONG).show();
//                String date = wheelTime.getTime().substring(0, wheelTime.getTime().indexOf(" "));
                String date = wheelTime.getTime();
                timeSelectListener.onTimeSelect(date);
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        public void onTimeSelect(String date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }
}
