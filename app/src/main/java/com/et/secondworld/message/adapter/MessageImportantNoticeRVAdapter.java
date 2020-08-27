package com.et.secondworld.message.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.et.secondworld.R;
import com.et.secondworld.bean.GetImportantNoticeBean;
import com.et.secondworld.message.MessageImportantNoticeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class MessageImportantNoticeRVAdapter extends RecyclerView.Adapter<MessageImportantNoticeRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private ArrayList<Object> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(ArrayList<Object>  list) {
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
    public void addData(int position, ArrayList<Object>  list) {
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
        return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_important_notice_rv_item, parent, false));
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
        @BindView(R.id.tv_message_important_notice_rv_item_time)
        TextView tvMessageImportantNoticeRVItemTime;
        @BindView(R.id.tv_message_important_notice_rv_item_title)
        TextView tvMessageImportantNoticeRVItemTitle;
        @BindView(R.id.tv_message_important_notice_rv_item_content)
        TextView tvMessageImportantNoticeRVItemContent;
        @BindView(R.id.rly_message_important_notice_rv_item)
        RelativeLayout rlyMessageImportantNoticeRVItem;
        @BindView(R.id.rv_message_important_notice)
        RecyclerView rvMessageImportantNotice;
        private long clickTime = 0;
        @OnClick(R.id.rly_message_important_notice_rv_item)
        public void rlyMessageImportantNoticeRVItemOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                /*String articleAccount = dataList.get(pos).getArticleaccount();
                String articleid = dataList.get(pos).getArticleid();
                String title = dataList.get(pos).getTitle();
                Intent intent = new Intent(v.getContext(), MessageImportantNoticeDetailActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);

                v.getContext().startActivity(intent);*/
            }
        }
        MessageImportantNoticeSecondRVAdapter rvAdapter;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            v = view;

        }



        void setData(Object data,int position) {
            pos = position;
           /* String time = dataList.get(position).getTime();
            String title = dataList.get(position).getTitle();
            tvMessageImportantNoticeRVItemTitle.setText("公告");
            tvMessageImportantNoticeRVItemTime.setText(time);
            tvMessageImportantNoticeRVItemContent.setText(title);*/
            List<GetImportantNoticeBean.ListBean> listBeans = (ArrayList<GetImportantNoticeBean.ListBean>)dataList.get(position);
            rlyMessageImportantNoticeRVItem.setVisibility(View.GONE);
            rvMessageImportantNotice.setVisibility(View.VISIBLE);
            rvAdapter = new MessageImportantNoticeSecondRVAdapter();
            Log.d("aa11",listBeans.size()+" "+position);
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 4;i++){
//            dataList.add("");
//        }
            rvMessageImportantNotice.setLayoutManager(new LinearLayoutManager(v.getContext()));
            rvMessageImportantNotice.setAdapter(rvAdapter);
            rvAdapter.replaceAll(listBeans);

        }
    }


}
