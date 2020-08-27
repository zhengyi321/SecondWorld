package com.et.secondworld.widget.timeselect.view;

import android.content.Context;
import android.view.View;

import com.et.secondworld.R;
import com.et.secondworld.widget.timeselect.TimePickerView;
import com.et.secondworld.widget.timeselect.adapter.SexWheelAdapter;
import com.et.secondworld.widget.timeselect.lib.WheelView;


public class WheelSex {
   private View view;

    private WheelView wn_sex;



    public WheelSex(View view) {
        super();
        this.view = view;

        setView(view);
    }

    public WheelSex(View view, TimePickerView.Type type) {
        super();
        this.view = view;

        setView(view);
    }



    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker() {
        // 添加大小月月份并将其转换为list,方便之后的判断


        Context context = view.getContext();



        wn_sex = (WheelView) view.findViewById(R.id.sex);
        wn_sex.setAdapter(new SexWheelAdapter());
        wn_sex.setCyclic(false);






    }



    public String getItem() {
        StringBuffer sb = new StringBuffer();
        sb.append(wn_sex.getCurrentItem() );
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


}
