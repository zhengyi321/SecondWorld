package com.et.secondworld.fragment.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.et.secondworld.BaseFragment;
import com.et.secondworld.R;
//import com.zhyan.secondworld.huanxin.domain.EaseUser;
//import com.zhyan.secondworld.huanxin.ui.ContactListFragment;
//import com.zhyan.secondworld.huanxin.ui.ConversationListFragment;
//import com.zhyan.secondworld.huanxin.ui.DemoHelper;
//import com.zhyan.secondworld.huanxin.ui.EaseContactListFragment;
//import com.zhyan.secondworld.message.fragment.ChatMessageFragment;
import com.et.secondworld.message.fragment.ChatMessageFragment;
import com.et.secondworld.message.fragment.MessageGetGoodFragment;
import com.et.secondworld.message.fragment.MessageImportantNoticeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/8
 **/
public class MessageFragment extends BaseFragment {
    public static MessageFragment newInstance() {


        MessageFragment firstFragment = new MessageFragment();

        // Get arguments passed in, if any
        Bundle args = firstFragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        // Add parameters to the argument bundle
        args.putInt("aa", 0);
        firstFragment.setArguments(args);

        return firstFragment;
    }
    public MessageFragment(){
        super();
    }
    private ArrayList<Fragment> list_fragment = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
    private MessageImportantNoticeFragment messageImportantNoticeFragment;
//    private MessagePrivateLetterFragment messagePrivateLetterFragment;
    private ChatMessageFragment chatMessageFragment;
//    public ConversationListFragment chatMessageFragment;
//    private EaseContactListFragment easeContactListFragment;
//    private ContactListFragment contactListFragment;
    private MessageGetGoodFragment messageGetGoodFragment;
    @BindView(R.id.tly_message)
    TabLayout tlyMessage;
    @BindView(R.id.vp_message)
    ViewPager vpMessage;
    View view;
    private MessageFragmentPageAdapter messageFragmentPageAdapter;

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    @Override
    public void initView() {
        if (view != null) {
            ButterKnife.bind(this, view);
        }
        initTab();
        initHuanXin();

    }

    private void initTab() {
//        vpFansFollowGoods = view.findViewById(R.id.vp_fans_follow_goods);
        messageImportantNoticeFragment = new MessageImportantNoticeFragment();
//        messagePrivateLetterFragment = new MessagePrivateLetterFragment();
        chatMessageFragment = new ChatMessageFragment();
//        easeContactListFragment = new EaseContactListFragment();
//        contactListFragment = new ContactListFragment();
        messageGetGoodFragment = new MessageGetGoodFragment();
//        getContact();

        list_fragment.add(messageImportantNoticeFragment);
        list_fragment.add(chatMessageFragment);
        list_fragment.add(messageGetGoodFragment);
//        list_fragment.add(new FirstPageFragment());
//        list_fragment.add(allOrderFragment);


        list_title.add("重要公告");
        list_title.add("私信");
        list_title.add("评论");
        messageFragmentPageAdapter = new MessageFragmentPageAdapter(getActivity().getSupportFragmentManager(), list_fragment, list_title);
        if (vpMessage == null) {
            Toast.makeText(view.getContext(), "this is null", Toast.LENGTH_SHORT).show();
            return;
        }
        vpMessage.setAdapter(messageFragmentPageAdapter);
        vpMessage.setOffscreenPageLimit(3);
        tlyMessage.setupWithViewPager(vpMessage);
        tlyMessage.setTabMode(tlyMessage.MODE_FIXED);
        vpMessage.setCurrentItem(2);
    }

    private void initHuanXin() {


    }
/*

    private void getContact() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Map<String, EaseUser> contactsMap = new HashMap<>();
                    //拿到好友列表
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for(String name: usernames){
                        EaseUser user = new EaseUser(name);
                        contactsMap.put("user",user);
                    }
                    easeContactListFragment.setContactsMap(contactsMap);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

            }
        };
    }
*/

    public void onDestroyView() {
        super.onDestroyView();
        tlyMessage = null;
        vpMessage = null;
        view = null;
    }


}
