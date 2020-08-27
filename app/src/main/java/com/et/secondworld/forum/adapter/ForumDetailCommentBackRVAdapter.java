package com.et.secondworld.forum.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.R;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetCommentBackBean;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.network.PraiseArticleNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.DeleteReportDialog;
import com.et.secondworld.widget.emoji.EmojiUtil;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/7
 **/
public class ForumDetailCommentBackRVAdapter extends RecyclerView.Adapter<ForumDetailCommentBackRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetCommentBackBean.BacklistBean> dataList ;
    private EditText editText;
    private Activity activity;
    private String toaccountid ;
    private String tonick;
    private String articleid ;
    private String commentid ;
    private Map<String,Object> map;
    public boolean isOpen = false;
    private String commentAccount ="";
    public ForumDetailCommentBackRVAdapter(List<GetCommentBackBean.BacklistBean> dataList1, EditText editText1,Activity activity1,Map<String,Object> map1,String articleid1,String commentid1,String commentAccount1){
        dataList = dataList1;
        this.editText = editText1;
        activity = activity1;
        map = map1;
        articleid = articleid1;
        commentid = commentid1;
        commentAccount = commentAccount1;
    }

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetCommentBackBean.BacklistBean> list) {
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
    public void addData(int position, List<GetCommentBackBean.BacklistBean> list) {
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
        return new ForumDetailCommentBackRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_reply_all_comments_rv_item, parent, false));
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
//        int good = 0;
        int isPraise = 0;
        int praisecount = 0;
        @BindView(R.id.civ_popup_reply_all_comments_rv_item_head)
        CircleImageView civPopupReplyAllCommentsRVItemHead;
        @OnClick(R.id.civ_popup_reply_all_comments_rv_item_head)
        public void  civPopupReplyAllCommentsRVItemHeadOnclick(){
            String rule = dataList.get(pos).getRule();
            String accountid = dataList.get(pos).getAccountid();
            if(rule!= null && rule.equals("normal")){
                Intent intent = new Intent(v.getContext(), VisitOthersActivity.class);
                intent.putExtra("articleaccount",accountid);
                v.getContext().startActivity(intent);
            }

            if(rule!= null && rule.equals("shop")){
                Intent intent = new Intent(v.getContext(), VisitOthersShopActivity.class);
                intent.putExtra("shopid",accountid);
                v.getContext().startActivity(intent);
            }
        }
        @BindView(R.id.lly_popup_reply_all_comments_rv_item)
        LinearLayout llyPopupReplyAllCommentsRVItem;
       /* @OnClick(R.id.lly_popup_reply_all_comments_rv_item)
        public void llyPopupReplyAllCommentsRVItemOnclick(){
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            map.put("toaccountid",dataList.get(pos).getAccountid());
            map.put("tonick", dataList.get(pos).getNick());
            editText.setHint("回复 @"+map.get("tonick")+":");
        }*/
        @OnClick(R.id.lly_popup_reply_all_comments_rv_item)
        public void llyPopupReplyAllCommentsRVItemOnLongclick(){

            if (editText == null){
                return;
            }
            String backid = dataList.get(pos).getCommentbackid();

            String content = dataList.get(pos).getContent();
//            String toaccount = dataList.get(pos).getToaccountid();

            String nick = dataList.get(pos).getNick();
//            String tonick = dataList.get(position).getTonick();
//            String content = dataList.get(position).getContent();
//            String time = dataList.get(position).getTime();
//            String toaccount = dataList.get(position).getToaccountid();
            String account = dataList.get(pos).getAccountid();
//            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) ((Activity) v.getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
//            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View view = ((Activity) v.getContext()).getWindow().peekDecorView();

            if(imm.isActive(editText)){
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                isOpen = true;
                editText.clearFocus();
                civPopupReplyAllCommentsRVItemHead.setFocusable(true);
                civPopupReplyAllCommentsRVItemHead.setFocusableInTouchMode(true);
                civPopupReplyAllCommentsRVItemHead.requestFocus();
            }else {
                isOpen = false;
            }
            if (null != view) {
                if (isOpen) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    isOpen = false;
                } else {
                    DeleteReportDialog praiseDialog = new DeleteReportDialog(v.getContext()).Build.setCallBackListener(new DeleteReportDialog.OnFinishClickListener() {
                        @Override
                        public void isBack(Boolean isBack) {
                            if (isBack) {
                                if (editText == null) {
                                    return;
                                }
                                isOpen = true;
//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);
                                editText.setFocusable(true);
                                editText.setFocusableInTouchMode(true);
                                editText.requestFocus();
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                map.put("toaccountid",dataList.get(pos).getAccountid());
                                map.put("tonick", dataList.get(pos).getNick());
                                editText.setHint("回复 @"+map.get("tonick")+":");
                            } else {
                                dataList.remove(pos);
                                notifyDataSetChanged();
                            }
                        }
                    }).build(v.getContext(), content, account, nick, backid,1);
                    praiseDialog.show();
                }
            }




           /* XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String backAccount = dataList.get(pos).getAccountid();
            String backid = dataList.get(pos).getCommentbackid();
            if(account.equals(backAccount)) {
                new AlertDialog.Builder(activity).setTitle("确定删除评论吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Log.d("comment111","this is 确定");
                                //点击确定触发的事件
                                ArticleNetWork articleNetWork = new ArticleNetWork();
                                Map<String,Object> parammap = new HashMap<>();
                                parammap.put("backid",backid);
                                articleNetWork.deleteCommentBackToNet(parammap, new Observer<BaseBean>() {
                                    @Override
                                    public void onCompleted() {
//                                        Log.d("comment111","this is onCompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
//                                        Log.d("comment111","this is onCompleted"+e);
                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
//                                        Log.d("comment111","this is onCompleted"+baseBean.getIssuccess());
                                        if(baseBean.getIssuccess().equals("1")) {
                                            Toast.makeText(v.getContext(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                                            dataList.remove(pos);
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击取消触发的事件
                            }
                        }).show();
            }*/
        }
        @BindView(R.id.tv_popup_reply_all_comments_rv_item_nick)
        TextView tvPopupReplyAllCommentsRVItemNick;
        @BindView(R.id.iv_popup_reply_all_comments_rv_item_arrow)
        ImageView ivPopupReplyAllCommentsRVItemArrow;
        @BindView(R.id.tv_popup_reply_all_comments_rv_item_tonick)
        TextView tvPopupReplyAllCommentsRVItemToNick;
        @BindView(R.id.tv_popup_reply_all_comments_rv_item_content)
        TextView tvPopupReplyAllCommentsRVItemContent;
        @BindView(R.id.tv_popup_reply_all_comments_rv_item_time)
        TextView tvPopupReplyAllCommentsRVItemTime;
        @BindView(R.id.tv_popup_reply_all_comments_rv_item_good)
        TextView tvPopupReplyAllCommentsRVItemGood;
        @BindView(R.id.ib_popup_reply_all_comments_rv_item_good)
        ImageButton ibPopupReplyAllCommentsRVItemGood;
        @OnClick(R.id.lly_popup_reply_all_comments_rv_item_good)
        public void ibPopupReplyAllCommentsRVItemGoodOnclick(){
            commentZan();
        }
        @BindView(R.id.ib_popup_reply_all_comments_rv_item_comment_back)
        ImageButton ibPopupReplyAllCommentsRVItemCommentBack;
        @OnClick(R.id.ib_popup_reply_all_comments_rv_item_comment_back)
        public void ibPopupReplyAllCommentsRVItemCommentBackOnclick(){
//            Toast.makeText(v.getContext(),"this is popup",Toast.LENGTH_SHORT).show();
            //强制弹出软键盘https://www.jb51.net/article/78588.htm

        }

        private void commentZan(){
            Map<String,Object> map= new HashMap<>();
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            map.put("articleid",articleid);
            map.put("account",account);
            map.put("commentid",commentid);
            map.put("commentbackid",dataList.get(pos).getCommentbackid());
            PraiseArticleNetWork praiseArticleNetWork = new PraiseArticleNetWork();
            praiseArticleNetWork.addCancelZanCommentBackToNet(map, new Observer<AddCancelZanBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(AddCancelZanBean addCancelZanBean) {
                    if(addCancelZanBean.getIssuccess().equals("1")){
                        ibPopupReplyAllCommentsRVItemGood.setBackgroundResource(R.mipmap.zandown);
                        praisecount++;
                        isPraise++;
                        tvPopupReplyAllCommentsRVItemGood.setText(praisecount+"");
                        dataList.get(pos).setIspraise(1);
                    }
                    if(addCancelZanBean.getIssuccess().equals("2")){
                        ibPopupReplyAllCommentsRVItemGood.setBackgroundResource(R.mipmap.zan);
                        if(praisecount >0) {
                            praisecount--;
                        }
                        isPraise = 0;
                        tvPopupReplyAllCommentsRVItemGood.setText(praisecount+"");
                        dataList.get(pos).setIspraise(0);
                    }
                    dataList.get(pos).setPraisecount(praisecount);
                }
            });
        }
        int pos = 0;
        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v= view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
            String head = dataList.get(position).getHead();
            String nick = dataList.get(position).getNick();
            String tonick = dataList.get(position).getTonick();
            String content = dataList.get(position).getContent();
            String time = dataList.get(position).getTime();
            String toaccount = dataList.get(position).getToaccountid();
            String account = dataList.get(position).getAccountid();
             praisecount = dataList.get(position).getPraisecount();
             isPraise = dataList.get(position).getIspraise();
//             good = dataList.get(position).getGood();
            if(head != null){
                Glide.with(v).load(head).apply(new RequestOptions().fallback(R.mipmap.headimg).error(R.mipmap.headimg)).into(civPopupReplyAllCommentsRVItemHead);
//                ImageLoader.getInstance().displayImage(head,civPopupReplyAllCommentsRVItemHead, ImageLoaderUtils.options);
            }
            if(nick != null){
                tvPopupReplyAllCommentsRVItemNick.setText(nick);
            }
//            Log.d("commentback11","toaccount:"+toaccount);
//            Log.d("commentback12","account:"+account);
//            Log.d("commentback13","commentaccount:"+commentAccount);
            if(commentAccount != null && toaccount != null) {
                if (commentAccount.equals(toaccount)) {
                    ivPopupReplyAllCommentsRVItemArrow.setVisibility(View.GONE);
                    tvPopupReplyAllCommentsRVItemToNick.setVisibility(View.GONE);
                }else {
                    ivPopupReplyAllCommentsRVItemArrow.setVisibility(View.VISIBLE);
                    tvPopupReplyAllCommentsRVItemToNick.setVisibility(View.VISIBLE);
                }
            }
            if(tonick != null){
                tvPopupReplyAllCommentsRVItemToNick.setText(tonick);
            }
            if(content != null){
                try {
                    tvPopupReplyAllCommentsRVItemContent.setText("");
                    EmojiUtil.handlerEmojiText(tvPopupReplyAllCommentsRVItemContent, content, v.getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                tvPopupReplyAllCommentsRVItemContent.setText(content);
            }
            if(time != null){
                tvPopupReplyAllCommentsRVItemTime.setText(time);
            }
            tvPopupReplyAllCommentsRVItemGood.setText(praisecount+"");
            if(isPraise >0){
                ibPopupReplyAllCommentsRVItemGood.setBackgroundResource(R.mipmap.zandown);
            }else {
                ibPopupReplyAllCommentsRVItemGood.setBackgroundResource(R.mipmap.zan);
            }
        }
    }


}
