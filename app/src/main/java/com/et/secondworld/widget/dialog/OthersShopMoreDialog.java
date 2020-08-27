package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.et.secondworld.R;
import com.et.secondworld.huanxin.Constant;
import com.et.secondworld.huanxin.ui.ChatActivity;
import com.et.secondworld.mine.setting.MineSettingReportActivity;
import com.et.secondworld.utils.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/28
 **/
public class OthersShopMoreDialog extends Dialog {

    Context context;
    private Activity activity;
    private String tel = "";
    private String id = "";
//    public interface OnFinishClickListener{
//        public void getMaps(Map<Object, Object> map);
//    }
//    public interface OnDataListClickListener{
//        public void getData(ArrayList<JsonBean> dataList);
//    }
//    private OnFinishClickListener onFinishClickListener;
//    private OnDataListClickListener onDataListClickListener;
    public OthersShopMoreDialog(Context context1, String id1,String tel1) {
        super(context1);
        context = context1;
        id = id1;
        tel = tel1;
//        init(context);
//        setPopupWindow();
    }
    public OthersShopMoreDialog(Context context1, int themeResId) {
        super(context1, themeResId);
        this.context = context1;
    }
    public Builder Build = new Builder(context);
    //用Builder模式来构造Dialog
    public  class Builder {
        private Context mContext;
        public Builder(Context mContext) {
            this.mContext = mContext;
        }

         OthersShopMoreDialog othersShopMoreDialog;

      /*  @BindView(R.id.tv_mine_shop_loc)
        TextView tvMineShopLoc;*/
        @BindView(R.id.rly_others_shop_more_tel_total)
        RelativeLayout rlyOthersShopMoreTelTotal;
        @OnClick(R.id.rly_others_shop_more_tel)
        public void ivOthersShopMoreTelOnclick(){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + tel);
            intent.setData(data);
            context.startActivity(intent);
        }
        @OnClick(R.id.rly_others_shop_more_report)
        public void rlyOthersShopMoreReportOnclick(){
            Intent intent = new Intent(context, MineSettingReportActivity.class);
            intent.putExtra("reportid", id);
            context.startActivity(intent);
        }
        @OnClick(R.id.rly_others_shop_more_message)
        public void rlyOthersShopMoreMessageOnclick(){
            Toast.makeText(getContext(),"暂不开放,敬请期待！",Toast.LENGTH_SHORT).show();
            return;
            /*
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra(Constant.EXTRA_USER_ID, id);
            context.startActivity(intent);*/
        }
        @OnClick(R.id.rly_others_shop_more_ok)
        public void rlyOthersShopMoreOKOnclick(){
            othersShopMoreDialog.dismiss();
        }
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public OthersShopMoreDialog build(Context context) {
            activity = (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            othersShopMoreDialog = new OthersShopMoreDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            othersShopMoreDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_others_shop_more, null);
            ButterKnife.bind(this,view);
            othersShopMoreDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = othersShopMoreDialog.getWindow().getAttributes();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
//
//
//            tvMineShopLoc.setText(loc);
            if(tel == null || tel.isEmpty()){
                rlyOthersShopMoreTelTotal.setVisibility(View.GONE);
            }
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
//            Log.d("width",""+windowUtils.getWindowWidth());
            othersShopMoreDialog.getWindow().setAttributes(layoutParams);


            othersShopMoreDialog.setContentView(view);


            return othersShopMoreDialog;
        }

    }





}
