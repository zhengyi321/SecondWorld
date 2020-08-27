package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.CancelGuanZhuBean;
import com.et.secondworld.bean.GetGuanZhuBean;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MineGuanZhuRVAdapter extends RecyclerView.Adapter<MineGuanZhuRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetGuanZhuBean.ListBean> dataList ;
    private int type = 0;
    private String guanzhuType = "";

    public MineGuanZhuRVAdapter(List<GetGuanZhuBean.ListBean> dataList1,String guanzhuType){
        dataList = dataList1;
        this.guanzhuType = guanzhuType;

    }
    public void setType(int type1){
        type = type1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetGuanZhuBean.ListBean> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 插入数据使用notifyItemInserted，如果要使用插入动画，必须使用notifyItemInserted
     * 才会有效果。即便不需要使用插入动画，也建议使用notifyItemInserted方式添加数据，
     * 不然容易出现闪动和间距错乱的问题
     * */
    public void addData(int position, List<GetGuanZhuBean.ListBean> list) {
        dataList.addAll(position,list);
        notifyItemInserted(position);
    }

    //移除数据使用notifyItemRemoved
    public void removeData(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public OneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MineGuanZhuRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mine_guanzhu_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OneViewHolder holder, int position) {
        holder.setData(dataList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }



    public class OneViewHolder extends RecyclerView.ViewHolder {
        //        private ImageView ivImage;
        public View v;
        int pos = 0;
        @BindView(R.id.tv_mine_guanzhu_rv_item_nick)
        TextView tvMineGuanZhuRVItemNick;
        @BindView(R.id.tv_mine_guanzhu_rv_item_personnalnote)
        TextView tvMineGuanZhuRVItemPersonnalnote;
        @BindView(R.id.tv_mine_guanzhu_rv_item_guanzhu)
        TextView tvMineGuanZhuRVItemGuanZhu;
        @BindView(R.id.civ_mine_guanzhu_rv_item_head)
        CircleImageView civMineGuanZhuRVItemHead;
        private long clickTime = 0;
        @OnClick(R.id.civ_mine_guanzhu_rv_item_head)
        public void civMineGuanZhuRVItemHeadOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                String articleAccount = dataList.get(pos).getAccountid();
                if (type == 2) {
                    Intent intent = new Intent(v.getContext(), VisitOthersShopActivity.class);
                    intent.putExtra("shopid", articleAccount);
                    v.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), VisitOthersActivity.class);
                    intent.putExtra("articleaccount", articleAccount);
                    v.getContext().startActivity(intent);
                }
            }
        }
        @BindView(R.id.rly_mine_guanzhu_rv_item_guanzhu)
        RelativeLayout rlyMineGuanZhuRVItemGuanZhu;
        @OnClick(R.id.rly_mine_guanzhu_rv_item_guanzhu)
        public void rlyMineGuanZhuRVItemGuanZhuOnclick(){
            if(tvMineGuanZhuRVItemGuanZhu.getText().toString().equals("已关注")||tvMineGuanZhuRVItemGuanZhu.getText().toString().equals("互相关注")){
                QueryCancelDialog queryCancelDialog = new QueryCancelDialog(v.getContext()).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                    @Override
                    public void isQuery(boolean isQuery) {
                        if(isQuery) {
                            guanzhuSubmit();
                        }
                    }
                }).build(v.getContext(),"确定取消关注吗");
                queryCancelDialog.show();
            }else {
                guanzhuSubmit();
            }
        }

        private void guanzhuSubmit(){

            String account = dataList.get(pos).getAccountid();
            String followid = dataList.get(pos).getFollowid();
            int isFriends = dataList.get(pos).getIsfriends();
            int isFans = dataList.get(pos).getIsfans();
            Map<String,String> map = new HashMap<>();
            GuanZhuFansNetWork guanZhuFansNetWork= new GuanZhuFansNetWork();
            map.put("account",account);
            map.put("followid",followid);
           /* if(isFans == 0){
                map.put("type","1");
            }
            if(isFans == 1){
                map.put("type","0");
            }*/
            guanZhuFansNetWork.cancelGuanZhuToNet(map, new Observer<CancelGuanZhuBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(CancelGuanZhuBean cancelGuanZhuBean) {
                    if(cancelGuanZhuBean.getIssuccess().equals("1")) {
                        if(isFans == 1) {
//                            if (isFriends == 0) {
                            rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvMineGuanZhuRVItemGuanZhu.setText("互相关注");
                            tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.GRAY);

//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//                                rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
//                                tvMineGuanZhuRVItemGuanZhu.setText("关注");
//                                tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
//
//                                dataList.get(pos).setIsfans(0);
//                                dataList.get(pos).setIsfriends(0);
//                            }
                        }
                        if(isFans == 0) {
//                            if (isFriends == 0) {
                            rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvMineGuanZhuRVItemGuanZhu.setText("已关注");
                            tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.GRAY);

//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//
//
//                            }
                        }
//                        notifyDataSetChanged();
                    }else {
                        rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                        tvMineGuanZhuRVItemGuanZhu.setText("关注");
//                        tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                        tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.WHITE);

//                            dataList.get(pos).setIsfans(0);
//                        dataList.get(pos).setIsfriends(0);
                    }
                    Toast.makeText(v.getContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            if(guanzhuType != null) {
                if (guanzhuType.equals("others")) {
                    rlyMineGuanZhuRVItemGuanZhu.setVisibility(View.GONE);
                }
            }
            pos = position;
            String nick = dataList.get(position).getNick();
            String personnalNote = dataList.get(position).getPersonalnote();
            String head = dataList.get(position).getHead();
            int isFriends = dataList.get(position).getIsfriends();
            int isFans = dataList.get(pos).getIsfans();
            int status = dataList.get(pos).getStatus();
            if(nick != null){
                tvMineGuanZhuRVItemNick.setText(nick);
//                Log.d("guanzhubeanposition",position+"");
            }else {
                tvMineGuanZhuRVItemNick.setText("");
            }
            if(personnalNote != null){
                tvMineGuanZhuRVItemPersonnalnote.setText(personnalNote);
//                Log.d("guanzhubean",personnalNote);
            }else {
                tvMineGuanZhuRVItemPersonnalnote.setText("");
            }
            if(type == 2) {
                if (status == 0) {
                    tvMineGuanZhuRVItemNick.setText("已注销");
                    civMineGuanZhuRVItemHead.setEnabled(false);
                }
            }
//            Log.d("guanzhubean",nick);
//            Log.d("guanzhubean",personnalNote);
//            if(head != null){
//                ImageLoader.getInstance().displayImage(head,civMineGuanZhuRVItemHead, ImageLoaderUtils.options);
//            }else {
//                ImageLoader.getInstance().displayImage("",civMineGuanZhuRVItemHead, ImageLoaderUtils.options);
//            }

            Glide.with(v.getContext()).load(head)
                    .apply(new RequestOptions().fallback(R.mipmap.imghead).error(R.mipmap.imghead).circleCrop())
                    .into(civMineGuanZhuRVItemHead);
            if(isFans == 0){
                rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                tvMineGuanZhuRVItemGuanZhu.setText("已关注");
                tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.GRAY);
            }else {
                rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                tvMineGuanZhuRVItemGuanZhu.setText("互相关注");
                tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.GRAY);
            }
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            if(account.equals(dataList.get(position).getAccountid())){
                rlyMineGuanZhuRVItemGuanZhu.setVisibility(View.GONE);
            }

        }
    }


}
