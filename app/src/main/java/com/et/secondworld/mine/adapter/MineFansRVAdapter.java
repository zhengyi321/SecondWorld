package com.et.secondworld.mine.adapter;

import android.content.Intent;
import android.graphics.Color;
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
import com.et.secondworld.bean.AddFansBean;
import com.et.secondworld.bean.GetFansBean;
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
public class MineFansRVAdapter extends RecyclerView.Adapter<MineFansRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetFansBean.ListBean> dataList ;
    private String type = "";
    public MineFansRVAdapter(List<GetFansBean.ListBean> dataList1,String type1){
        dataList = dataList1;
        type = type1;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetFansBean.ListBean> list) {
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
    public void addData(int position, List<GetFansBean.ListBean> list) {
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
        return new MineFansRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mine_fans_rv_item, parent, false));
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
        @BindView(R.id.tv_mine_fans_rv_item_nick)
        TextView tvMineFansRVItemNick;
        @BindView(R.id.tv_mine_fans_rv_item_personnalnote)
        TextView tvMineFansRVItemPersonnalnote;
        @BindView(R.id.tv_mine_fans_rv_item_guanzhu)
        TextView tvMineFansRVItemGuanZhu;
        @BindView(R.id.civ_mine_fans_rv_item_head)
        CircleImageView civMineFansRVItemHead;
        private long clickTime = 0;
        @OnClick(R.id.civ_mine_fans_rv_item_head)
        public void civMineFansRVItemHeadOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                String followid = dataList.get(pos).getFollowid();
                Intent intent = new Intent(v.getContext(), VisitOthersActivity.class);
                intent.putExtra("articleaccount", followid);
                v.getContext().startActivity(intent);
            }
        }
        @BindView(R.id.rly_mine_fans_rv_item_guanzhu)
        RelativeLayout rlyMineFansRVItemGuanZhu;
        @OnClick(R.id.rly_mine_fans_rv_item_guanzhu)
        public void rlyMineFansRVItemGuanZhuOnclick(){
            if(tvMineFansRVItemGuanZhu.getText().toString().equals("已关注")||tvMineFansRVItemGuanZhu.getText().toString().equals("互相关注")){
                QueryCancelDialog queryCancelDialog = new QueryCancelDialog(v.getContext()).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                    @Override
                    public void isQuery(boolean isQuery) {
                        if(isQuery) {
                            guanzhuSubmit();
                        }
                    }
                }).build(v.getContext(),"确定取消关注吗?");
                queryCancelDialog.show();
            }else {
                guanzhuSubmit();
            }
        }

        private void guanzhuSubmit(){

            String account = dataList.get(pos).getAccountid();
            String followid = dataList.get(pos).getFollowid();
            int isFriends = dataList.get(pos).getIsfriends();
            int isGuanzhu = dataList.get(pos).getIsguanzhu();
            Map<String,String> map = new HashMap<>();
            GuanZhuFansNetWork guanZhuFansNetWork= new GuanZhuFansNetWork();
            map.put("account",account);
            map.put("followid",followid);
           /* if(isFriends == 0){
                map.put("type","1");
            }
            if(isFriends == 1){
                map.put("type","0");
            }*/
            guanZhuFansNetWork.addCancelFansToNet(map, new Observer<AddFansBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(AddFansBean addFansBean) {
                    if(addFansBean.getIssuccess().equals("1")){
//                        if(isGuanzhu == 0){
                        rlyMineFansRVItemGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                        tvMineFansRVItemGuanZhu.setText("互相关注");
                        tvMineFansRVItemGuanZhu.setTextColor(Color.GRAY);

//                            dataList.get(pos).setIsfriends(1);

//                        }
//                        if(isGuanzhu == 1){


//                            dataList.get(pos).setIsfriends(0);

//                        }

//                        notifyDataSetChanged();
                    }else {
                        rlyMineFansRVItemGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                        tvMineFansRVItemGuanZhu.setText("关注");
                        tvMineFansRVItemGuanZhu.setTextColor(Color.WHITE);
//                        tvMineFansRVItemGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                    }
                    Toast.makeText(v.getContext(),addFansBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
            });

        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v= view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String nick = dataList.get(position).getNick();
            String personnalNote = dataList.get(position).getPersonalnote();
            String head = dataList.get(position).getHead();
            int isFriends = dataList.get(position).getIsfriends();
            int isguanzhu = dataList.get(pos).getIsguanzhu();
            if(type != null && type.equals("shop")){
                rlyMineFansRVItemGuanZhu.setVisibility(View.GONE);
            }
            if(nick != null){
                tvMineFansRVItemNick.setText(nick);
            }
            if(personnalNote != null){
                tvMineFansRVItemPersonnalnote.setText(personnalNote);
            }
            if(head != null){
                Glide.with(v.getContext()).load(head)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead).error(R.mipmap.imghead).circleCrop())
                        .into(civMineFansRVItemHead);
//                ImageLoader.getInstance().displayImage(head,civMineFansRVItemHead, ImageLoaderUtils.options);
            }
            if(isguanzhu == 0){
                rlyMineFansRVItemGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                tvMineFansRVItemGuanZhu.setText("关注");
                tvMineFansRVItemGuanZhu.setTextColor(Color.WHITE);
//                tvMineFansRVItemGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
            }else {
                rlyMineFansRVItemGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                tvMineFansRVItemGuanZhu.setText("互相关注");
                tvMineFansRVItemGuanZhu.setTextColor(Color.GRAY);
            }
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            if(account.equals(dataList.get(position).getFollowid())){
                rlyMineFansRVItemGuanZhu.setVisibility(View.GONE);
            }

        }
    }


}
