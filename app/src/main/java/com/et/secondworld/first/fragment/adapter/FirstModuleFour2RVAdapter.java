package com.et.secondworld.first.fragment.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.CircleImgBean;
import com.et.secondworld.bean.GetFourBean;
import com.et.secondworld.bean.GetTradeBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.network.TradeNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MessageQueryDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.imageview.CircleImageView;
import com.et.secondworld.widget.viewpage.ImageInCycleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

import static com.et.secondworld.application.MyApplication.instance;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class FirstModuleFour2RVAdapter extends RecyclerView.Adapter<FirstModuleFour2RVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetFourBean.ListBean>  dataList ;
    private Boolean isNoMoreData = false;
    public void isNoMoreData(Boolean isNoMoreData){
        this.isNoMoreData = isNoMoreData;
    }
    public int isSixOne =0;

    public FirstModuleFour2RVAdapter(List<GetFourBean.ListBean> dataList1 ){
        dataList = dataList1;
    }
    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetFourBean.ListBean> list) {
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
    public void addData(int position, List<GetFourBean.ListBean> list) {
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
//        if(pos == 0){
//            return new FirstModuleFour2RVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_first_module_four_rv_header, parent, false));
//        }else {
            return new FirstModuleFour2RVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_first_module_four_rv_item2, parent, false));
//        }
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



        @BindView(R.id.iv_first_module_four_rv_item_hot)
        ImageView ivFirstModuleFourRVItemHot;
        @BindView(R.id.lly_first_module_four_chance_rv_item1)
        LinearLayout llyFirstModuleFourChanceRVItem1;
        @BindView(R.id.iv_first_module_four_chance_rv_item1_img1)
        ImageView ivFirstModuleFourChanceRVItem1Img1;
        @BindView(R.id.iv_first_module_four_chance_rv_item1_img2)
        ImageView ivFirstModuleFourChanceRVItem1Img2;
        @BindView(R.id.iv_first_module_four_chance_rv_item1_img3)
        ImageView ivFirstModuleFourChanceRVItem1Img3;
        @BindView(R.id.iv_first_module_four_chance_rv_item1_img4)
        ImageView ivFirstModuleFourChanceRVItem1Img4;
        @BindView(R.id.iv_first_module_four_chance_rv_item1_img5)
        ImageView ivFirstModuleFourChanceRVItem1Img5;
        @BindView(R.id.iv_first_module_four_chance_rv_item1_img6)
        ImageView ivFirstModuleFourChanceRVItem1Img6;


        @BindView(R.id.lly_first_module_four_chance_rv_item2)
        LinearLayout llyFirstModuleFourChanceRVItem2;
        @BindView(R.id.iv_first_module_four_chance_rv_item2_img1)
        ImageView ivFirstModuleFourChanceRVItem2Img1;
        @BindView(R.id.iv_first_module_four_chance_rv_item2_img2)
        ImageView ivFirstModuleFourChanceRVItem2Img2;
        @BindView(R.id.iv_first_module_four_chance_rv_item2_img3)
        ImageView ivFirstModuleFourChanceRVItem2Img3;
        @BindView(R.id.iv_first_module_four_chance_rv_item2_img4)
        ImageView ivFirstModuleFourChanceRVItem2Img4;
        @BindView(R.id.iv_first_module_four_chance_rv_item2_img5)
        ImageView ivFirstModuleFourChanceRVItem2Img5;
        @BindView(R.id.iv_first_module_four_chance_rv_item2_img6)
        ImageView ivFirstModuleFourChanceRVItem2Img6;


        @BindView(R.id.lly_first_module_four_chance_rv_item3)
        LinearLayout llyFirstModuleFourChanceRVItem3;
        @BindView(R.id.iv_first_module_four_chance_rv_item3_img1)
        ImageView ivFirstModuleFourChanceRVItem3Img1;
        @BindView(R.id.iv_first_module_four_chance_rv_item3_img2)
        ImageView ivFirstModuleFourChanceRVItem3Img2;
        @BindView(R.id.iv_first_module_four_chance_rv_item3_img3)
        ImageView ivFirstModuleFourChanceRVItem3Img3;



        @BindView(R.id.lly_first_module_four_chance_rv_item4)
        LinearLayout llyFirstModuleFourChanceRVItem4;
        @BindView(R.id.iv_first_module_four_chance_rv_item4_img1)
        ImageView ivFirstModuleFourChanceRVItem4Img1;


        @BindView(R.id.lly_first_module_four_chance_rv_item5)
        LinearLayout llyFirstModuleFourChanceRVItem5;
        @BindView(R.id.iv_first_module_four_chance_rv_item5_img1)
        ImageView ivFirstModuleFourChanceRVItem5Img1;
        @BindView(R.id.iv_first_module_four_chance_rv_item5_img2)
        ImageView ivFirstModuleFourChanceRVItem5Img2;


        @BindView(R.id.lly_first_module_four_chance_rv_item6)
        LinearLayout llyFirstModuleFourChanceRVItem6;

        @BindView(R.id.iv_first_module_four_chance_rv_item6_img1)
        ImageView ivFirstModuleFourChanceRVItem6Img1;
        @BindView(R.id.iv_first_module_four_chance_rv_item6_img2)
        ImageView ivFirstModuleFourChanceRVItem6Img2;
        @BindView(R.id.iv_first_module_four_chance_rv_item6_img3)
        ImageView ivFirstModuleFourChanceRVItem6Img3;
        @BindView(R.id.iv_first_module_four_chance_rv_item6_img4)
        ImageView ivFirstModuleFourChanceRVItem6Img4;


        @BindView(R.id.lly_first_module_four_chance_rv_item7)
        LinearLayout llyFirstModuleFourChanceRVItem7;

        @BindView(R.id.iv_first_module_four_chance_rv_item7_img1)
        ImageView ivFirstModuleFourChanceRVItem7Img1;
        @BindView(R.id.iv_first_module_four_chance_rv_item7_img2)
        ImageView ivFirstModuleFourChanceRVItem7Img2;
        @BindView(R.id.iv_first_module_four_chance_rv_item7_img3)
        ImageView ivFirstModuleFourChanceRVItem7Img3;
        @BindView(R.id.iv_first_module_four_chance_rv_item7_img4)
        ImageView ivFirstModuleFourChanceRVItem7Img4;
        @BindView(R.id.iv_first_module_four_chance_rv_item7_img5)
        ImageView ivFirstModuleFourChanceRVItem7Img5;


        @BindView(R.id.tv_first_module_four_rv_item_brows)
        TextView tvFirstModuleFourRVItemBrows;
        @BindView(R.id.tv_first_module_four_rv_item_repost)
        TextView tvFirstModuleFourRVItemRepost;
        @BindView(R.id.tv_first_module_four_rv_item_comments)
        TextView tvFirstModuleFourRVItemComments;
        @BindView(R.id.tv_first_module_four_rv_item_praise)
        TextView tvFirstModuleFourRVItemPraise;
        @BindView(R.id.iv_first_module_four_rv_item_praise)
        ImageView ivFirstModuleFourRVItemPraise;









        @BindView(R.id.lly_first_module_four_head)
        LinearLayout llyFirstModuleFourHead;

        @BindView(R.id.sp_first_module_four_head)
        Spinner spFirstModuleFourHead;
        @BindView(R.id.icv_first_module_four)
        ImageInCycleView icvFirstModuleFour;

/*
        @BindView(R.id.tv_first_module_four_rv_item_nums)
        TextView tvFirstModuleRVItemNums;*/
        @BindView(R.id.tv_first_module_four_rv_item_title)
        TextView tvFirstModuleRVItemTitle;
        @BindView(R.id.tv_first_module_four_rv_item_account)
        TextView tvFirstModuleRVItemAccount;
        @BindView(R.id.civ_first_module_four_rv_item_head)
        CircleImageView civFirstModuleFourRVItemHead;

        @BindView(R.id.tv_first_module_four_rv_item_readers)
        TextView tvFirstModuleRVItemReaders;
        @BindView(R.id.tv_first_module_four_rv_item_content)
        TextView tvFirstModuleRVItemContent;
        /*@BindView(R.id.iv_first_module_four_rv_item)
        ImageView ivFirstModuleFourRVItem;*/
        @BindView(R.id.tv_first_module_four_rv_item_local)
        TextView tvFirstModuleFourRVItemLocal;
      /*  @BindView(R.id.lly_splitview)
        LinearLayout llySplitView;*/
        private int pos = 0;
        @BindView(R.id.iv_first_module_four_rv_item_title_logo)
        ImageView ivFirstModuleFourRVItemTitleLogo;
        @BindView(R.id.lly_first_module_four_rv_item)
        LinearLayout llyFirstModuleRVItem;
        private long clickTime = 0;
        @OnClick(R.id.lly_first_module_four_rv_item)
        public void llyFirstModuleRVItemOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {
                checkLogin();
                clickTime = System.currentTimeMillis();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if(account == null || account.isEmpty() || (role != null && role.equals("00"))) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent);

                    return;
                }
                String articleAccount = dataList.get(pos).getAccountid();
                String articleid = dataList.get(pos).getArticleid();
                String title = dataList.get(pos).getTitle();
                String model = dataList.get(pos).getModules();
                if (articleAccount == null) {
                    articleAccount = "";
                }
                if (articleid == null) {
                    articleid = "";
                }
                if (title == null) {
                    title = "";
                }
                if (model == null) {
                    model = "";
                }
                if (model.equals("M3")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);
                    intent.putExtra("type", 1);
                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                } else if (model.equals("M4")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);

                    intent.putExtra("title", title);

                    v.getContext().startActivity(intent);
                } else if (model.equals("M1") || model.equals("M2")) {
                    Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("articleid", articleid);
                    intent.putExtra("type", 1);
                    intent.putExtra("title", title);

                    v.getContext().startActivity(intent);
                }
            }
        }
        private void checkLogin(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String tel = xcCacheManager.readCache(xcCacheSaveName.userTel);
            String uuuid = UniversalID.getUniversalID(v.getContext());
            RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
            Map<String,Object> map = new HashMap<>();
            map.put("tel",tel);
            map.put("uuuid",uuuid.trim());
            registerLoginNetWork.checkLoginFromNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    if(baseBean.getIssuccess().equals("1")){
                        MessageQueryDialog queryCancelDialog = new MessageQueryDialog(v.getContext()).Build.setCallBackListener(new MessageQueryDialog.OnFinishClickListener() {
                            @Override
                            public void isQuery(boolean isQuery) {
                                xcCacheManager.writeCache(xcCacheSaveName.account,"");
                                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                v.getContext().startActivity(intent);
//                            MainActivity.this.finish();
                            }
                        }).build(v.getContext(),"您的账号("+baseBean.getMsg()+")在其它地方登陆,请确认是否是本人操作,如若不是请及时修改密码!");
                        queryCancelDialog.show();


                    }
                }
            });
        }
        public void llyForumOneItemOnclick(){
//            Toast.makeText(v.getContext(),"this is one"+pos,Toast.LENGTH_LONG).show();

        }
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
//            if(pos != 0) {
                ButterKnife.bind(this, view);
//            }
        }



        void setData(Object data,int position) {
           /* if(isNoMoreData){
                if(position == dataList.size() -1){
                    llyFirstModuleRVItem.setVisibility(View.GONE);
                    llySplitView.setVisibility(View.VISIBLE);
                }
//                return;

            }else {
                llyFirstModuleRVItem.setVisibility(View.VISIBLE);
                llySplitView.setVisibility(View.GONE);
            }*/
            pos = position;
           /* if(position == 0){
                return;
            }
*/
           if(position == 0){
               llyFirstModuleFourHead.setVisibility(View.VISIBLE);
               llyFirstModuleRVItem.setVisibility(View.GONE);

               TradeNetWork tradeNetWork = new TradeNetWork();
               Map<String,Object> map = new HashMap<>();
               tradeNetWork.getTradeFromNet(map, new Observer<GetTradeBean>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onNext(GetTradeBean getTradeBean) {
                       List<GetTradeBean.ListBean> listBeanList = getTradeBean.getList();
                       String[] spinnerItems = new String[listBeanList.size()];
                       for(int i=0;i<listBeanList.size();i++){
                           spinnerItems[i] = listBeanList.get(i).getTrade();
                       }
//                final String[] spinnerItems = {"全部", "关注的店铺"};
                       ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(instance,
                               R.layout.simple_spinner_item, spinnerItems);
                       //下拉的样式res
                       spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                       //绑定 Adapter到控件
                       spFirstModuleFourHead.setAdapter(spinnerAdapter);
                       spFirstModuleFourHead.setSelection(0, true);
                   }
               });
               List<Integer> localList = new ArrayList<>();
////               localList.add(R.mipmap.forumad1);
////               localList.add(R.mipmap.forumad1);
////               localList.add(R.mipmap.forumad1);
//               List<String> urlList = new ArrayList<>();
//               urlList.add("https://n.sinaimg.cn/sinakd202057s/656/w991h465/20200507/acd6-iteyfww2260558.jpg");
//               urlList.add("https://n.sinaimg.cn/sinakd202057s/656/w991h465/20200507/acd6-iteyfww2260558.jpg");
//               urlList.add("https://n.sinaimg.cn/sinakd202057s/656/w991h465/20200507/acd6-iteyfww2260558.jpg");
               ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
               XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
               XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
               String prov = xcCacheManager.readCache(xcCacheSaveName.forumProv);
               map.put("province",prov+",");

//               Map<String,String> map = new HashMap<>();
               forumPostNetWork.getBusinessCircleImgFromNet(map,new Observer<CircleImgBean>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onNext(CircleImgBean circleImgBean) {
                       if (circleImgBean.getList().size() > 0){
                           icvFirstModuleFour.setImageResources(circleImgBean.getList(), null, localList, imageCycleViewListener);
                        }
                   }

               });
//               icvFirstModuleFour.setImageResources(urlList, null, localList, imageCycleViewListener);
               return;
           }else {
               llyFirstModuleFourHead.setVisibility(View.GONE);
               llyFirstModuleRVItem.setVisibility(View.VISIBLE);
           }
            int isfire = dataList.get(position).getIsfire();
            if(isfire == 1){
                ivFirstModuleFourRVItemHot.setVisibility(View.VISIBLE);
            }else {
                ivFirstModuleFourRVItemHot.setVisibility(View.GONE);
            }
            String title = dataList.get(position).getTitle();
            String content = dataList.get(position).getContent();
            String head = dataList.get(position).getLogo();
            String nick = dataList.get(position).getNick();
            int readers = dataList.get(position).getReaders();
            int good = dataList.get(position).getGood();
            int comments = dataList.get(position).getComments();
            int repost = dataList.get(position).getRepost();
            int ispraised = dataList.get(position).getIspraised();
            int istop = dataList.get(position).getIstop();
            if(position == 0){
                if(istop == 1){
                    ivFirstModuleFourRVItemTitleLogo.setVisibility(View.VISIBLE);
                }else {
                    ivFirstModuleFourRVItemTitleLogo.setVisibility(View.GONE);
                }
            }
            if(position == 1){
                if(istop == 1){
                    ivFirstModuleFourRVItemTitleLogo.setVisibility(View.VISIBLE);
                }else {
                    ivFirstModuleFourRVItemTitleLogo.setVisibility(View.GONE);
                }
            }
            if(position == 2){
                if(istop == 1){
                    ivFirstModuleFourRVItemTitleLogo.setVisibility(View.VISIBLE);
                }else {
                    ivFirstModuleFourRVItemTitleLogo.setVisibility(View.GONE);
                }
            }
            tvFirstModuleFourRVItemBrows.setText(readers+"");
            tvFirstModuleFourRVItemPraise.setText(good+"");
            tvFirstModuleFourRVItemComments.setText(comments+"");
            tvFirstModuleFourRVItemRepost.setText(repost+"");
            if(ispraised == 1){
                ivFirstModuleFourRVItemPraise.setBackgroundResource(R.mipmap.praised);
            }else {
                ivFirstModuleFourRVItemPraise.setBackgroundResource(R.mipmap.praiseicon);
            }
            List<GetFourBean.ListBean.ImglistBean> imgList = new ArrayList<>();
            imgList.clear();
            if(dataList.get(position).getImglist() != null) {
                imgList.addAll(dataList.get(position).getImglist());
            }
            if(imgList != null) {
                for (int i = 0; i < imgList.size(); ) {
                    if (imgList.get(i).getImg().isEmpty()) {
                        imgList.remove(i);
                        continue;
                    }
                    i++;
                }
            }
//            String imgOne = dataList.get(position).getImageone();
//            String imgTwo = dataList.get(position).getImagetwo();
//            String imgThree = dataList.get(position).getImagethree();
            String local = dataList.get(position).getLocal();
            if(local.indexOf("市")>=0) {
                local = local.substring(0, local.indexOf("市"));
            }
            tvFirstModuleFourRVItemLocal.setText(local);
//            ivFirstModuleFourRVItem.setVisibility(View.VISIBLE);
//            tvFirstModuleRVItemContent.setVisibility(View.GONE);
            tvFirstModuleRVItemContent.setText(content);
            tvFirstModuleRVItemTitle.setText(title);
//            if(imgOne != null && !imgOne.isEmpty()){
//                Glide.with(v.getContext()).load(imgOne)
//                        .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                        .into(ivFirstModuleFourRVItem);
//            }else {
//
//                if(imgTwo != null && !imgTwo.isEmpty()){
//                    Glide.with(v.getContext()).load(imgTwo)
//                            .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                            .into(ivFirstModuleFourRVItem);
//                }else {
//                    if(imgThree != null && !imgThree.isEmpty()){
//                        Glide.with(v.getContext()).load(imgThree)
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
//                                .into(ivFirstModuleFourRVItem);
//                    }else {
//                        ivFirstModuleFourRVItem.setVisibility(View.GONE);
//                        tvFirstModuleRVItemContent.setVisibility(View.VISIBLE);
//                        tvFirstModuleRVItemContent.setText(content);
//
//                    }
//                }
//            }
            if(imgList != null){
                switch (imgList.size()){
                    case 0:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        break;
                    case 1:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.VISIBLE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem4Img1);

                        break;
                    case 2:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.VISIBLE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem5Img1);
                        Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem5Img2);
                        break;
                    case 3:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.VISIBLE);
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem3Img1);
                        Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem3Img2);
                        Glide.with(v.getContext()).load(imgList.get(2).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem3Img3);

                        break;
                    case 4:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.VISIBLE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem6Img1);
                        Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem6Img2);
                        Glide.with(v.getContext()).load(imgList.get(2).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem6Img3);
                        Glide.with(v.getContext()).load(imgList.get(3).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem6Img4);
                        break;
                    case 5:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.VISIBLE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem7Img1);
                        Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem7Img2);
                        Glide.with(v.getContext()).load(imgList.get(2).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem7Img3);
                        Glide.with(v.getContext()).load(imgList.get(3).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem7Img4);
                        Glide.with(v.getContext()).load(imgList.get(4).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem7Img5);
                        break;

                    case 6:
                        llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                        if(isSixOne % 2 == 0){

                            llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                            llyFirstModuleFourChanceRVItem1.setVisibility(View.VISIBLE);
                            Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem1Img1);
                            Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem1Img2);
                            Glide.with(v.getContext()).load(imgList.get(2).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem1Img3);
                            Glide.with(v.getContext()).load(imgList.get(3).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem1Img4);
                            Glide.with(v.getContext()).load(imgList.get(4).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem1Img5);
                            Glide.with(v.getContext()).load(imgList.get(5).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem1Img6);

                        }
                        if(isSixOne % 2 == 1){
                            llyFirstModuleFourChanceRVItem2.setVisibility(View.VISIBLE);
                            llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                            Glide.with(v.getContext()).load(imgList.get(0).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem2Img1);
                            Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem2Img2);
                            Glide.with(v.getContext()).load(imgList.get(2).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem2Img3);
                            Glide.with(v.getContext()).load(imgList.get(3).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem2Img4);
                            Glide.with(v.getContext()).load(imgList.get(4).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem2Img5);
                            Glide.with(v.getContext()).load(imgList.get(5).getImg())
                                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                            .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                    .into(ivFirstModuleFourChanceRVItem2Img6);

                        }
                        isSixOne++;

                        break;
                }

                if(imgList.size() > 6){
                    llyFirstModuleFourChanceRVItem7.setVisibility(View.GONE);
                    llyFirstModuleFourChanceRVItem6.setVisibility(View.GONE);
                    llyFirstModuleFourChanceRVItem5.setVisibility(View.GONE);
                    llyFirstModuleFourChanceRVItem4.setVisibility(View.GONE);
                    llyFirstModuleFourChanceRVItem3.setVisibility(View.GONE);
                    if(isSixOne % 2 == 0){

                        llyFirstModuleFourChanceRVItem2.setVisibility(View.GONE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.VISIBLE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
                                .into(ivFirstModuleFourChanceRVItem1Img1);
                        Glide.with(v.getContext()).load(imgList.get(1).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem1Img2);
                        Glide.with(v.getContext()).load(imgList.get(2).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem1Img3);
                        Glide.with(v.getContext()).load(imgList.get(3).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem1Img4);
                        Glide.with(v.getContext()).load(imgList.get(4).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem1Img5);
                        Glide.with(v.getContext()).load(imgList.get(5).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem1Img6);

                    }
                    if(isSixOne % 2 == 1){
                        llyFirstModuleFourChanceRVItem2.setVisibility(View.VISIBLE);
                        llyFirstModuleFourChanceRVItem1.setVisibility(View.GONE);
                        Glide.with(v.getContext()).load(imgList.get(0).getImg())
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
                                .into(ivFirstModuleFourChanceRVItem2Img1);
                        Glide.with(v.getContext()).load(imgList.get(1).getImg())
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
                                .into(ivFirstModuleFourChanceRVItem2Img2);
                        Glide.with(v.getContext()).load(imgList.get(2).getImg())
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
                                .into(ivFirstModuleFourChanceRVItem2Img3);
                        Glide.with(v.getContext()).load(imgList.get(3).getImg())
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
                                .into(ivFirstModuleFourChanceRVItem2Img4);
                        Glide.with(v.getContext()).load(imgList.get(4).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem2Img5);
                        Glide.with(v.getContext()).load(imgList.get(5).getImg())
                                .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                        .error(R.mipmap.imghead))
//                                .apply(new RequestOptions().fallback(R.mipmap.forumad3))
                                .into(ivFirstModuleFourChanceRVItem2Img6);
                    }
                    isSixOne++;

                }
            }




            Glide.with(v.getContext()).load(head)
                    .apply(new RequestOptions().fallback(R.mipmap.imghead)
                            .error(R.mipmap.imghead))
                    .into(civFirstModuleFourRVItemHead);
//            tvFirstModuleRVItemReaders.setText(readers);
            tvFirstModuleRVItemAccount.setText(nick);
        }



        private ImageInCycleView.ImageCycleViewListener imageCycleViewListener=new ImageInCycleView.ImageCycleViewListener() {
            @Override
            public void displayImageURL(String imageURL, ImageView imageView) {
                Glide.with(v.getContext()).load(imageURL)
                        .apply(new RequestOptions().fallback(R.mipmap.imghead))
                        .into(imageView);
//            ImageLoader.getInstance().displayImage(imageURL,imageView,ImageLoaderUtils.options);
            }

            @Override
            public void displayImageLocal(int mipmap, ImageView imageView) {
//            Log.d("zzzz111",mipmap+"");
                imageView.setImageResource(R.mipmap.imghead);
            }

            @Override
            public void onImageClick(ArrayList<String> urlList, ArrayList<Integer> localList, int postion, View imageView) {

            }


        };
    }


}
