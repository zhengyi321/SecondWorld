package com.et.secondworld.message.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.VisitOthersActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetCommentAndAt;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumDetailTwoActivity;
import com.et.secondworld.message.MessageCommentAndAtBackActivity;
import com.et.secondworld.message.MessageCommentBackActivity;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.emoji.EmojiUtil;
import com.et.secondworld.widget.imageview.CircleImageView;

import java.io.IOException;
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
public class MessageGetGoodRVAdapter extends RecyclerView.Adapter<MessageGetGoodRVAdapter.OneViewHolder> {
    //    private ArrayList<Integer> dataList = new ArrayList<>();
    private List<GetCommentAndAt.ListBean> dataList = new ArrayList<>();

    //    ArrayList<String> list 中String改成int 就是本地图片
    public void replaceAll(List<GetCommentAndAt.ListBean> list) {
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
    public void addData(int position, List<GetCommentAndAt.ListBean> list) {
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
        return new MessageGetGoodRVAdapter.OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_get_good_rv_item, parent, false));
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
        private String time = "";
        private String nick = "";
        private String head = "";
        private String type = "";
        private String title = "";
        private String content = "";
        private String imgone = "";
        int pos = 0;
        @BindView(R.id.tv_comment_and_at_back)
        TextView tvCommentAndAtBack;
        @BindView(R.id.tv_comment_and_at_back_c)
        TextView tvCommentAndAtBackC;
        @BindView(R.id.tv_comment_and_at_mine_a)
        TextView tvCommentAndAtMineA;
        @BindView(R.id.tv_comment_and_at_mine_c)
        TextView tvCommentAndAtMineC;
        @BindView(R.id.tv_comment_and_at_mine)
        TextView tvCommentAndAtMine;
        @BindView(R.id.rly_comment_and_at_back_mine)
        RelativeLayout rlyCommentAndAtBackMine;
        @BindView(R.id.rly_comment_and_at_mine)
        RelativeLayout rlyCommentAndAtMine;
        @BindView(R.id.rly_comment_and_at_back)
        RelativeLayout rlyCommentAndAtBack;

        @OnClick(R.id.rly_comment_and_at_back)
        public void rlyCommentAndAtBackOnclick(){
            String toaccountid = dataList.get(pos).getAccountid();
            String articleid = dataList.get(pos).getArticleid();
            String commentid= dataList.get(pos).getCommentid();
            String modules = dataList.get(pos).getArticlemodules();

            int status = dataList.get(pos).getArticlestatus();
            int commentstatus = dataList.get(pos).getCommentstatus();
            int backstatus = dataList.get(pos).getBackstatus();
            if(backstatus == 1){
                Toast.makeText(v.getContext(),"此评论已消失",Toast.LENGTH_SHORT).show();
                return;
            }
            if(commentstatus == 1){
                Toast.makeText(v.getContext(),"此评论已消失",Toast.LENGTH_SHORT).show();
                return;
            }
            if(status == 1){
                Toast.makeText(v.getContext(),"此文章已消失",Toast.LENGTH_SHORT).show();
                return;
            }
            if(modules == null){
                return;
            }
            if(type != null){
                if(type.equals("comment")){
                    Intent intent= new Intent(v.getContext(), MessageCommentAndAtBackActivity.class);
                    intent.putExtra("articleid",articleid);
                    intent.putExtra("commentid",commentid);
                    intent.putExtra("toaccountid",toaccountid);
                    intent.putExtra("type","backcomment");
//                    intent.putExtra("type","articlecomment");
                    intent.putExtra("tonick",nick);
                    v.getContext().startActivity(intent);
                }
                if(type.equals("commentback")){
                    Intent intent= new Intent(v.getContext(), MessageCommentAndAtBackActivity.class);
                    intent.putExtra("articleid",articleid);
                    intent.putExtra("commentid",commentid);
                    intent.putExtra("toaccountid",toaccountid);
                    intent.putExtra("type","backcomment");
                    intent.putExtra("tonick",nick);
                    v.getContext().startActivity(intent);
                }
            }
        }

        @BindView(R.id.rly_comment_and_at_article)
        RelativeLayout rlyCommentAndAtArticle;
        @BindView(R.id.rly_comment_and_at_title)
        RelativeLayout rlyCommentAndAtTitle;
        @BindView(R.id.iv_comment_and_at_img)
        ImageView ivCommentAndAtImg;
        @BindView(R.id.tv_comment_and_at_time)
        TextView tvCommentAndAtTime;
        @BindView(R.id.tv_comment_and_at_nick)
        TextView tvCommentAndAtNick;
        @BindView(R.id.tv_comment_and_at_at)
        TextView tvCommentAndAtAt;
        @BindView(R.id.tv_comment_and_at_title)
        TextView tvCommentAndAtTitle;
        @BindView(R.id.tv_comment_and_at_content)
        TextView tvCommentAndAtContent;
        @BindView(R.id.tv_comment_and_at_parise)
        TextView tvCommentAndAtParise;
        @BindView(R.id.tv_comment_and_at_comments)
        TextView tvCommentAndAtComments;
        @BindView(R.id.civ_comment_and_at_head)
        CircleImageView civCommentAndAtHead;
        @OnClick(R.id.civ_comment_and_at_head)
        public void civCommentAndAtHeadOnclick(){
            String articleAccount = dataList.get(pos).getAccountid();
            Intent intent = new Intent(v.getContext(), VisitOthersActivity.class);
            intent.putExtra("articleaccount",articleAccount);
            v.getContext().startActivity(intent);
        }
        @BindView(R.id.lly_comment_and_at)
        LinearLayout llyCommentAndAt;
        @OnClick(R.id.lly_comment_and_at)
        public void llyCommentAndAtOnclick(){
            String articleid = dataList.get(pos).getArticleid();
            int praiseCount = dataList.get(pos).getGood();
            int isPraise = dataList.get(pos).getIspraised();
            String commentid = dataList.get(pos).getCommentid();
            String commentaccount = dataList.get(pos).getCommentAccount();
            String modules = dataList.get(pos).getArticlemodules();
            String articleAccount = dataList.get(pos).getArticleaccount();
            String commentnick = dataList.get(pos).getCommentnick();
            String commenthead = dataList.get(pos).getCommentlogo();
            int status = dataList.get(pos).getArticlestatus();
            int commentstatus = dataList.get(pos).getCommentstatus();
            if(commentstatus == 1){
                Toast.makeText(v.getContext(),"此评论已消失",Toast.LENGTH_SHORT).show();
                return;
            }
            if(status == 1){
                Toast.makeText(v.getContext(),"此文章已消失",Toast.LENGTH_SHORT).show();
                return;
            }
            if(type != null ){
                if(type.equals("commentback")) {
                    Intent intent = new Intent(v.getContext(), MessageCommentBackActivity.class);
                    intent.putExtra("head", commenthead);
                    intent.putExtra("nick", commentnick);
                    intent.putExtra("content", content);
                    intent.putExtra("articleid", articleid);
                    intent.putExtra("time", time);
                    intent.putExtra("good", praiseCount);
                    intent.putExtra("isPraise", isPraise);
//            intent.putExtra("bad",bad);
                    intent.putExtra("commentid", commentid);
                    intent.putExtra("commentAccount", commentaccount);
                    intent.putExtra("modules", modules);
                    intent.putExtra("articleAccount", articleAccount);
                    intent.putExtra("title", title);
                    v.getContext().startActivity(intent);
                }
                if(type.equals("article")||type.equals("comment")){

                    if(modules == null){
//                        Toast.makeText(v.getContext(),"此文章已消失",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(modules.equals("M4")){
                        Intent intent = new Intent(v.getContext(), ForumDetailTwoActivity.class);
                        intent.putExtra("articleAccount",articleAccount);
                        intent.putExtra("articleid",articleid);

                        intent.putExtra("title",title);
//                        Log.d("commentandat11",articleAccount+ " "+articleid+" "+title);
                        v.getContext().startActivity(intent);
                    }
                    else if(modules.equals("M3")) {
                        Intent intent = new Intent(v.getContext(), ForumDetailThreeActivity.class);
                        intent.putExtra("articleAccount", articleAccount);
                        intent.putExtra("articleid", articleid);

                        intent.putExtra("title", title);
                        v.getContext().startActivity(intent);
                    }else if(modules.equals("M1")||modules.equals("M2")){
                        Intent intent = new Intent(v.getContext(), ForumDetailOneActivity.class);
                        intent.putExtra("articleAccount", articleAccount);
                        intent.putExtra("articleid", articleid);

                        intent.putExtra("title", title);
                        v.getContext().startActivity(intent);
                    }
                }
            }
        }

        //        LinearLayout llyItemWaterFall;
        public OneViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this,view);

        }



        void setData(Object data,int position) {
            pos = position;
             time = dataList.get(position).getCommentandattime();
//             int length = time.length();
//             if(time != null && length > 2) {
//                 time = time.substring(2);
//             }
             nick = dataList.get(position).getNick();
             head = dataList.get(position).getHead();
             type = dataList.get(position).getType();
             title = dataList.get(position).getTitle();
             content = dataList.get(position).getCommentcontent();
            imgone = dataList.get(position).getImgone();
            String backContent = dataList.get(position).getBackcontent();
            String mineContent = dataList.get(position).getMinecontent();
//            for(int i =0;i<5;i++){
//                backContent += backContent;
//                mineContent += mineContent;
//            }
            String articleContent = dataList.get(position).getArticlecontent();
//            if(articleContent != null && articleContent.length() > 10) {
//                articleContent = articleContent.substring(0, 10);
//            }
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(v.getContext());
            String accountNick = xcCacheManager.readCache(xcCacheSaveName.userName);
            tvCommentAndAtTime.setText(time);
            tvCommentAndAtNick.setText(nick);
            Glide.with(v.getContext()).load(head)
                    .apply(new RequestOptions().circleCrop().fallback(R.mipmap.imghead)
                            .error(R.mipmap.imghead))
                    .into(civCommentAndAtHead);
//            ImageLoader.getInstance().displayImage(head,civCommentAndAtHead, ImageLoaderUtils.options);
            if(imgone == null || imgone.isEmpty()){
                ivCommentAndAtImg.setVisibility(View.GONE);
            }else {
                ivCommentAndAtImg.setVisibility(View.VISIBLE);
            }
            if(type != null ){
                if(type.equals("article")){
                    tvCommentAndAtAt.setText("在文章中@了你");
                    tvCommentAndAtAt.setVisibility(View.VISIBLE);
                    tvCommentAndAtTitle.setText(title);
                    tvCommentAndAtContent.setText(articleContent);
                    Glide.with(v.getContext()).load(imgone)
                            .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead))
                            .into(ivCommentAndAtImg);
//                    ImageLoader.getInstance().displayImage(imgone,ivCommentAndAtImg,ImageLoaderUtils.options);

                    rlyCommentAndAtBack.setVisibility(View.GONE);
                    rlyCommentAndAtMine.setVisibility(View.GONE);
                    rlyCommentAndAtBackMine.setVisibility(View.GONE);
                    rlyCommentAndAtArticle.setBackgroundColor(Color.WHITE);
                    rlyCommentAndAtTitle.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
                if(type.equals("comment")){
                    tvCommentAndAtAt.setText("评论了你的文章");
                    tvCommentAndAtAt.setVisibility(View.GONE);
                    tvCommentAndAtBack.setVisibility(View.VISIBLE);
                    tvCommentAndAtMine.setVisibility(View.GONE);
                    tvCommentAndAtTitle.setText(title);
                    tvCommentAndAtContent.setText(articleContent);
                    Glide.with(v.getContext()).load(imgone)
                            .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead))
                            .into(ivCommentAndAtImg);
//                    ImageLoader.getInstance().displayImage(imgone,ivCommentAndAtImg,ImageLoaderUtils.options);
                    rlyCommentAndAtMine.setVisibility(View.GONE);
//                    tvCommentAndAtBackC.setText(accountNick);
//                    String back="回复<font color='#2374ff'>@"+accountNick+" :</font>";
                    try {
                        if(content != null) {
                            tvCommentAndAtBack.setText("");
//                            tvCommentAndAtBack.setText(content);
//                            tvCommentAndAtBack.setText(Html.fromHtml(back));
                            EmojiUtil.handlerEmojiText(tvCommentAndAtBack, content, v.getContext());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    tvCommentAndAtBack.setText(content);
//                    rlyCommentAndAtBack.setVisibility(View.GONE);
                    rlyCommentAndAtBack.setVisibility(View.VISIBLE);
                    rlyCommentAndAtArticle.setBackgroundColor(Color.WHITE);
                    rlyCommentAndAtTitle.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
                if(type.equals("commentback")){

                    tvCommentAndAtAt.setText("回复了你的评论");
                    tvCommentAndAtAt.setVisibility(View.GONE);
                    tvCommentAndAtMine.setVisibility(View.VISIBLE);
                    tvCommentAndAtBack.setVisibility(View.VISIBLE);
                    rlyCommentAndAtMine.setVisibility(View.VISIBLE);

                    tvCommentAndAtTitle.setText(title);
                    tvCommentAndAtContent.setText(articleContent);
                    Glide.with(v.getContext()).load(imgone)
                            .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead))
                            .into(ivCommentAndAtImg);
//                    ImageLoader.getInstance().displayImage(imgone,ivCommentAndAtImg,ImageLoaderUtils.options);
                    rlyCommentAndAtBack.setVisibility(View.VISIBLE);
                    rlyCommentAndAtBackMine.setVisibility(View.VISIBLE);
//                    tvCommentAndAtBackC.setText(" @"+accountNick+":");

                    String back="回复<font color='#5ac6de'>@"+accountNick+" :</font>";
                    String mine="<font color='#5ac6de'>@"+accountNick+"</font>回复<font color='#5ac6de'>@"+nick+":</font>";
//                    tvCommentAndAtMineA.setText("@"+accountNick);
//                    tvCommentAndAtMineC.setText(" @"+nick+":");
                    try {
                        if(backContent == null){
                            tvCommentAndAtBack.setVisibility(View.GONE);
                            return;
                        }
                        tvCommentAndAtBack.setText("");
                        tvCommentAndAtBack.setText(Html.fromHtml(back));
                        EmojiUtil.handlerEmojiText(tvCommentAndAtBack, backContent, v.getContext());
                        if(mineContent == null){
                        String commentnick = dataList.get(position).getCommentnick();
                        String mine11="<font color='#5ac6de'>@"+commentnick+"</font>回复<font color='#5ac6de'>@"+nick+":</font>";
                            tvCommentAndAtMine.setText(Html.fromHtml(mine11));
//                        EmojiUtil.handlerEmojiText(tvCommentAndAtBack, backContent, v.getContext());
                            if(content != null) {
                                EmojiUtil.handlerEmojiText(tvCommentAndAtMine, content, v.getContext());
                            }
//                            tvCommentAndAtMine.setVisibility(View.GONE);
                            return;
                        }
//                        tvCommentAndAtBack.setText("");
                        tvCommentAndAtMine.setText("");

                        tvCommentAndAtMine.setText(Html.fromHtml(mine));
//                        EmojiUtil.handlerEmojiText(tvCommentAndAtBack, backContent, v.getContext());
                        EmojiUtil.handlerEmojiText(tvCommentAndAtMine, mineContent, v.getContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    tvCommentAndAtBack.setText("回复 @"+accountNick+":"+);
                }
            }

        }
    }


}
