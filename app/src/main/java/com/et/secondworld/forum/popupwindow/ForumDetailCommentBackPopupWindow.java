package com.et.secondworld.forum.popupwindow;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.et.secondworld.R;
//import com.zhyan.secondworld.huanxin.EaseConstant;
//import com.zhyan.secondworld.huanxin.ui.ChatActivity;
import com.et.secondworld.widget.emoji.Emoji;
import com.et.secondworld.widget.emoji.FaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/21
 *
 *
 * //            WindowUtils windowUtils = new WindowUtils((Activity) v.getContext());
 * //            ForumDetailCommentBackPopupWindow popupWindow = new ForumDetailCommentBackPopupWindow(v.getContext());
 * //            popupWindow .setOutsideTouchable(true);
 * //            popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
 * //            popupWindow.setHeight((int)windowUtils.getWindowHeight()-200);
 * //            popupWindow.setAnimationStyle(R.style.pop_anim);
 * //            popupWindow.setFocusable(true);
 * //            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
 * ////            popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
 * //            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
 * //            popupWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
 **/
public class ForumDetailCommentBackPopupWindow extends PopupWindow implements FaceFragment.OnEmojiClickListener{
    private View mPopView;
    ForumDetailCommentBackPopupWindow forumDetailCommentBackPopupWindow;
    @BindView(R.id.et_popup_reply_all_comments_huifu)
    EditText etPopupReplyAllCommentsHuiFu;
    @BindView(R.id.fg_popup_reply_all_comments)
    FrameLayout fgPopupReplyAllComments;
    @BindView(R.id.rly_popup_reply_all_comments_fabu)
    RelativeLayout rlyPopupReplyAllCommentsFaBu;
    @OnClick(R.id.rly_popup_reply_all_comments_fabu)
    public void rlyPopupReplyAllCommentsFaBuOnclick(){
//        mPopView.getContext().startActivity(new Intent(mPopView.getContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "aa"));
    }
    public ForumDetailCommentBackPopupWindow(Context context) {
        super(context);
        init(context);
        setPopupWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_reply_all_comments, null);
        ButterKnife.bind(this,mPopView);
        etPopupReplyAllCommentsHuiFu.requestFocus();
        FaceFragment faceFragment = FaceFragment.Instance();
//        fgPopupReplyAllComments.setVisibility(View.GONE);
        ((FragmentActivity)mPopView.getContext()).getSupportFragmentManager().beginTransaction().add(R.id.fg_popup_reply_all_comments,faceFragment).commit();

    }
    private void setPopupWindow() {

        this.setContentView(mPopView);// 设置View
    }


    @Override
    public void onEmojiDelete() {
        Toast.makeText(mPopView.getContext(),"this is emoji",Toast.LENGTH_SHORT).show();
        String text = etPopupReplyAllCommentsHuiFu.getText().toString();
        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(text.length() - 1, text.length()))) {
            int index = text.lastIndexOf("[");
            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                etPopupReplyAllCommentsHuiFu.onKeyDown(KeyEvent.KEYCODE_DEL, event);
                displayTextView();
                return;
            }
            etPopupReplyAllCommentsHuiFu.getText().delete(index, text.length());
            displayTextView();
            return;
        }
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        etPopupReplyAllCommentsHuiFu.onKeyDown(KeyEvent.KEYCODE_DEL, event);
        displayTextView();
    }
    private void displayTextView() {
       /* try {
            EmojiUtil.handlerEmojiText(textView, etPopupReplyAllCommentsHuiFu.getText().toString(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    @Override
    public void onEmojiClick(Emoji emoji) {
        Toast.makeText(mPopView.getContext(),"this is emoji",Toast.LENGTH_SHORT).show();
        if (emoji != null) {
            int index = etPopupReplyAllCommentsHuiFu.getSelectionStart();
            Editable editable = etPopupReplyAllCommentsHuiFu.getEditableText();
            if (index < 0) {
                editable.append(emoji.getContent());
            } else {
                editable.insert(index, emoji.getContent());
            }
        }
        displayTextView();
    }
}
