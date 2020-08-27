package com.et.secondworld.widget.timeselect.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.et.secondworld.R;
import com.et.secondworld.widget.timeselect.MorningAfterNoonPickerView;
import com.et.secondworld.widget.timeselect.adapter.NumericWheelAdapter;
import com.et.secondworld.widget.timeselect.lib.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class WheelMorningAfternoon {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;

    private WheelView wv_hours1,wv_hours2;
    private WheelView wv_mins1,wv_mins2;

    private MorningAfterNoonPickerView.Type type;



    public WheelMorningAfternoon(View view) {
        super();
        this.view = view;
        type = MorningAfterNoonPickerView.Type.MORNING_HOUR_MIN;
        setView(view);
    }

    public WheelMorningAfternoon(View view, MorningAfterNoonPickerView.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

   /* public void setPicker(int year, int month, int day) {
        this.setPicker( 0, 0);
    }*/

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker( int h, int m) {

        Context context = view.getContext();




        wv_hours1 = (WheelView) view.findViewById(R.id.hour1);

//        wv_hours1.setLabel(":");// 添加文字
        wv_hours1.setCurrentItem(h);

        wv_hours2 = (WheelView) view.findViewById(R.id.hour2);

//        wv_hours2.setLabel(":");// 添加文字
        wv_hours2.setCurrentItem(h);

        wv_mins1 = (WheelView) view.findViewById(R.id.min1);
        NumericWheelAdapter numericWheelAdapterMin = new NumericWheelAdapter(0, 59);
        numericWheelAdapterMin.setHourMinType();
        wv_mins1.setAdapter(numericWheelAdapterMin);
//        wv_mins1.setLabel("~");// 添加文字
        wv_mins1.setCurrentItem(m);
        wv_mins2 = (WheelView) view.findViewById(R.id.min2);
        wv_mins2.setAdapter(numericWheelAdapterMin);
//        wv_mins.setLabel("~");// 添加文字
        wv_mins2.setCurrentItem(m);




        /*wv_day.setOnItemSelectedListener(wheelListener_month);*/

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 5;
        switch (type) {
            case MORNING_HOUR_MIN:
                textSize = textSize * 4;
                NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(0, 12);
                numericWheelAdapter.setHourMinType();
                wv_hours1.setAdapter(numericWheelAdapter);
                wv_hours2.setAdapter(numericWheelAdapter);
                break;
            case AFTERNOON_HOUR_MIN:
                textSize = textSize * 4;
                NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(13, 23);
                numericWheelAdapter1.setHourMinType();
                wv_hours1.setAdapter(numericWheelAdapter1);
                wv_hours2.setAdapter(numericWheelAdapter1);
                break;

        }
        setTextSize(textSize);

    }

    /**
     * 设置时间字体大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {

        wv_hours1.setTextSize(textSize);
        wv_mins1.setTextSize(textSize);
        wv_hours2.setTextSize(textSize);
        wv_mins2.setTextSize(textSize);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {

        wv_hours1.setCyclic(cyclic);
        wv_mins1.setCyclic(cyclic);
        wv_hours2.setCyclic(cyclic);
        wv_mins2.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        String hour1 = wv_hours1.getCurrentItem()+"";
        String min1 = wv_mins1.getCurrentItem()+"";
        String hour2 = wv_hours2.getCurrentItem()+"";
        String min2= wv_mins2.getCurrentItem()+"";
        Log.d("hour1",hour1+" min:"+min1);
        if(hour1.length() == 1){
            hour1 = "0"+hour1;
        }
        if(min1.length() == 1){
            min1 = "0"+min1;
        }
        if(hour2.length() == 1){
            hour2 = "0"+hour2;
        }
        if(min2.length() == 1){
            min2 = "0"+min2;
        }
        sb.append(hour1)
                .append(":")
                .append(min1).append("~")
                .append(hour2)
                .append(":")
                .append(min2);
                /*.append(wv_hours.getCurrentItem()).append(":")
                .append(wv_mins.getCurrentItem());*/
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


}
