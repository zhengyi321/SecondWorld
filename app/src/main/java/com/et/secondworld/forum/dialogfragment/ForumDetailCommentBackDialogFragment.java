package com.et.secondworld.forum.dialogfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.DialogFragment;

import com.et.secondworld.R;
import com.et.secondworld.widget.emoji.FaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/21
 **/
public class ForumDetailCommentBackDialogFragment extends DialogFragment {
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
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popup_reply_all_comments, container);
         init();

        return view;
    }

    private void init(){
        ButterKnife.bind(this,view);
        FaceFragment faceFragment = FaceFragment.Instance();
//        view.getContext().getSupportFragmentManager().beginTransaction().add(R.id.fg_popup_reply_all_comments,faceFragment).commit();
    }
}
