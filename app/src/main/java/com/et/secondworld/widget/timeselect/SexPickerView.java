package com.et.secondworld.widget.timeselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.et.secondworld.R;
import com.et.secondworld.widget.timeselect.view.BasePickerView;
import com.et.secondworld.widget.timeselect.view.WheelSex;


/**
 * Created by Sai on 15/11/22.
 */
public class SexPickerView extends BasePickerView implements View.OnClickListener {

    WheelSex wheelSex;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnSexSelectListener sexSelectListener;
    private boolean cancelable;//是否能取消
    //构造方法
    public SexPickerView(Builder builder) {
        super(builder.context);
        this.cancelable = builder.cancelable;
        initView(builder.context);
    }
    //建造器
    public static class Builder {
        private boolean cancelable = true;//是否能取消
        private Context context;
        //Required
        public Builder(Context context) {
            this.context = context;
        }
        public Builder setOutSideCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }
        public SexPickerView build() {
            return new SexPickerView(this);
        }
    }


    //Required
    private ViewGroup rootView;//附加View 的 根View
    public void initView(Context context) {


        LayoutInflater.from(context).inflate(R.layout.pickerview_sex, contentContainer);
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
        final View sexpickerview = findViewById(R.id.sexpicker);
        wheelSex = new WheelSex(sexpickerview);

        setCancelable(cancelable);
        wheelSex.setPicker();

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


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (sexSelectListener != null) {
                //                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
//                    Toast.makeText(v.getContext(),"time:"+wheelTime.getTime(),Toast.LENGTH_LONG).show();
//                String date = wheelTime.getTime().substring(0, wheelTime.getTime().indexOf(" "));
                String date = wheelSex.getItem();
                sexSelectListener.onTimeSelect(date);
            }
            dismiss();
            return;
        }
    }

    public interface OnSexSelectListener {
        public void onTimeSelect(String date);
    }

    public void setOnSexSelectListener(OnSexSelectListener sexSelectListener) {
        this.sexSelectListener = sexSelectListener;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }
}
