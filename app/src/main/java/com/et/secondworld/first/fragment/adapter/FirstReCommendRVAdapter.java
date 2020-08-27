package com.et.secondworld.first.fragment.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetRecommendBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.imageview.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class FirstReCommendRVAdapter extends RecyclerView.Adapter<FirstReCommendRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetRecommendBean.ListBean> dataList ;

    public FirstReCommendRVAdapter(List<GetRecommendBean.ListBean> dataList1){
        dataList = dataList1;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetRecommendBean.ListBean> list) {
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
    public void addData(int position, List<GetRecommendBean.ListBean> list) {
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
        return new FirstReCommendRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_first_recommend_rv_item, parent, false));
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
        private int pos = 0;
       @BindView(R.id.lly_first_recommend_rv_item)
        LinearLayout llyFirstRecommendRVItem;
       @OnClick(R.id.lly_first_recommend_rv_item)
       public void llyFirstRecommendRVItemOnclick(){
           String articleAccount = dataList.get(pos).getAccountid();
           String articleid = dataList.get(pos).getArticleid();
           String title = dataList.get(pos).getTitle();
           String model = dataList.get(pos).getModules();
           if(articleAccount == null){
               articleAccount = "";
           }
           if(articleid == null){
               articleid = "";
           }
           if(title == null){
               title = "";
           }

           if(model == null){
               model = "";
           }
           if(model.equals("M3")) {
               Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
               intent.putExtra("articleAccount", articleAccount);
               intent.putExtra("articleid", articleid);

               intent.putExtra("title", title);
               v.getContext().startActivity(intent);
           }else if(model.equals("M4")){
               Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
               intent.putExtra("articleAccount",articleAccount);
               intent.putExtra("articleid",articleid);

               intent.putExtra("title",title);

               v.getContext().startActivity(intent);
           }else if(model.equals("M1")||model.equals("M2")){
               Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
               intent.putExtra("articleAccount",articleAccount);
               intent.putExtra("articleid",articleid);

               intent.putExtra("title",title);

               v.getContext().startActivity(intent);
           }
       }
       @BindView(R.id.tv_first_recommend_rv_item_title)
        TextView tvFirstRecommendRVItemTitle;
       @BindView(R.id.tv_first_recommend_rv_item_content)
        TextView tvFirstRecommendRVItemContent;
       @BindView(R.id.rly_first_recommend_rv_item_content)
       RelativeLayout rlyFirstRecommendRVItemContent;
        @BindView(R.id.riv_first_recommend_rv_item_img)
        RoundImageView rivFirstRecommendRVItemImg;

        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String title = dataList.get(position).getTitle();
            String content = dataList.get(position).getContent();
            Object img = dataList.get(position).getImageone();
            if(title != null){
                tvFirstRecommendRVItemTitle.setText(title);
            }
            if(img != null && !img.toString().isEmpty()){
                ImageLoader.getInstance().displayImage(img.toString(),rivFirstRecommendRVItemImg, ImageLoaderUtils.options);
                rivFirstRecommendRVItemImg.setVisibility(View.VISIBLE);
                rlyFirstRecommendRVItemContent.setVisibility(View.GONE);
            }else{
                if(content != null){
                    if(content.length() > 100){
                        content = content.substring(0, 100);
                    }
                    tvFirstRecommendRVItemContent.setText(content);
                }
                rivFirstRecommendRVItemImg.setVisibility(View.GONE);
                rlyFirstRecommendRVItemContent.setVisibility(View.VISIBLE);
            }

        }
    }


}
