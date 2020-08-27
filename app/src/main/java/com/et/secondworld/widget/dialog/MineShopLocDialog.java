package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.et.secondworld.R;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.utils.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/28
 **/
public class MineShopLocDialog extends Dialog {

    Context context;
    private Activity activity;
    private String loc = "";
    private String street = "";
//    public interface OnFinishClickListener{
//        public void getMaps(Map<Object, Object> map);
//    }
//    public interface OnDataListClickListener{
//        public void getData(ArrayList<JsonBean> dataList);
//    }
//    private OnFinishClickListener onFinishClickListener;
//    private OnDataListClickListener onDataListClickListener;
    public MineShopLocDialog(Context context1,String loc1,String street1) {
        super(context1);
        context = context1;
        loc = loc1;
        street = street1;
//        init(context);
//        setPopupWindow();
    }
    public MineShopLocDialog(Context context1, int themeResId) {
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

         MineShopLocDialog locDialog;

        @BindView(R.id.tv_mine_shop_loc)
        TextView tvMineShopLoc;
        private long clickTime = 0;
        @OnClick(R.id.tv_mine_shop_loc)
        public void tvMineShopLocOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(context, TecentMapViewActivity.class);
                intent.putExtra("addr",street);
                context.startActivity(intent);
                dismiss();
            }

        }
        @OnClick(R.id.rly_mine_shop_loc_ok)
        public void rlyMineShopLocOKOnclick(){
            locDialog.dismiss();
        }
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public MineShopLocDialog build(Context context) {
            activity = (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            locDialog = new MineShopLocDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            locDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_mine_shop_loc, null);
            ButterKnife.bind(this,view);
            locDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = locDialog.getWindow().getAttributes();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
//
//
            tvMineShopLoc.setText(loc);
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
//            Log.d("width",""+windowUtils.getWindowWidth());
            locDialog.getWindow().setAttributes(layoutParams);


            locDialog.setContentView(view);


            return locDialog;
        }

    }





}
