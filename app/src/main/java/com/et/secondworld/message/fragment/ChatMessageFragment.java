package com.et.secondworld.message.fragment;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.hyphenate.EMClientListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.et.secondworld.R;
import com.et.secondworld.huanxin.EaseConstant;
import com.et.secondworld.huanxin.ui.ChatActivity;
import com.et.secondworld.huanxin.ui.EaseConversationListFragment;

import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by az on 2017/4/26.
 */

public class ChatMessageFragment extends EaseConversationListFragment {




    @BindView(R.id.lly_main_chatmessage_content_systeminfo)
    LinearLayout llyMainChatMessageContentSystemInfo;
     @OnClick(R.id.lly_main_chatmessage_content_systeminfo)
    public void llyMainChatMessageContentSystemInfoOnclick(){
         /*Intent sys=new Intent(getActivity(),SystemInfoActivity.class);
         startActivity(sys);*/

     }
 private ChatMessageFragmentController chatMessageFragmentController;
     @Override
     protected void initView() {
         super.initView();
         //add HeaderView

         View view=View.inflate(getActivity(), R.layout.fragment_main_chatmessage_lly,null);

         conversationListView.addHeaderView(view);

         init(view);
     }



    private void init(View view){
        ButterKnife.bind(this,view);
//        initController(view);
//        EMClient.getInstance().addClientListener(clientListener);
//        EMClient.getInstance().chatManager().addMessageListener(messageListener);
//            Toast.makeText(getActivity(),"zz",Toast.LENGTH_LONG).show();
//            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//            Log.d("zzzzzzzz", "好友数！"+usernames.size()+usernames.get(0));
//            Toast.makeText(getActivity(),"22zz"+usernames.size(),Toast.LENGTH_LONG).show();
//            String currUsername = EMClient.getInstance().getCurrentUser();
//            Toast.makeText(getActivity(),"22zz:"+currUsername,Toast.LENGTH_LONG).show();

       /* addFragment(view);*/
    }
    private void initController(View view){
        chatMessageFragmentController = new ChatMessageFragmentController(view);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //拿到好友列表
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//                    Toast.makeText(getActivity(),"22zz"+usernames.size(),Toast.LENGTH_LONG).show();
//                    Log.d("zzzzzzzz", "好友数！"+usernames.size()+usernames.get(0));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
//                Log.e("TAG", usernames.toString());
            }
        }.start();
    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
//            Toast.makeText(getContext(), "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
//            if (success) {
//                refreshUIWithMessage();
//            }
        }
    };




    EMMessageListener messageListener = new EMMessageListener() {

/*    *
     * 接受消息*/


    @Override
    public void onMessageReceived(List<EMMessage> messages) {
//        Toast.makeText(getContext(),"this is new message",Toast.LENGTH_SHORT).show();
//        refresh();//开启所有的refresh就能聊天
        /*
        for (EMMessage emMessage : messages) {

            try {

                refresh();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getActivity());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                //获取消息扩展
                String HXid = emMessage.getStringAttribute(SharePrefConstant.ChatUserId);
                String nickname = emMessage.getStringAttribute(SharePrefConstant.ChatUserNick);
                String avater  =emMessage.getStringAttribute(SharePrefConstant.ChatUserPic);
                //存入本地数据库
                UserInfoCacheSvc.createOrUpdate(HXid, nickname, avater);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }

        }*/
    }

    @Override
    public void onMessageRead(List<EMMessage> arg0) {
//        Toast.makeText(getContext(),"this is new message",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessageDelivered(List<EMMessage> arg0) {
//        Toast.makeText(getContext(),"this is new message",Toast.LENGTH_SHORT).show();
    }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {
//            Toast.makeText(getContext(),"this is new message",Toast.LENGTH_SHORT).show();
//            refresh();//开启所有的refresh就能聊天
//            for (EMMessage emMessage : list) {
//
//                try {
//
//                    refresh();
//                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getActivity());
//                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//                    //获取消息扩展
//                    String HXid = emMessage.getStringAttribute(SharePrefConstant.ChatUserId);
//                    String nickname = emMessage.getStringAttribute(SharePrefConstant.ChatUserNick);
//                    String avater  =emMessage.getStringAttribute(SharePrefConstant.ChatUserPic);
//                    //存入本地数据库
//                    UserInfoCacheSvc.createOrUpdate(HXid, nickname, avater);
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }

//            }
        }

        @Override
    public void onMessageChanged(EMMessage arg0, Object arg1) {
//            Toast.makeText(getContext(),"this is new message",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> arg0) {
//        Toast.makeText(getContext(),"this is new message",Toast.LENGTH_SHORT).show();
//        refresh();//开启所有的refresh就能聊天
        /*for (EMMessage emMessage : arg0) {

            try {

                refresh();
//                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getActivity());
//                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                //获取消息扩展
                String HXid = emMessage.getStringAttribute(SharePrefConstant.ChatUserId);
                String nickname = emMessage.getStringAttribute(SharePrefConstant.ChatUserNick);
                String avater  =emMessage.getStringAttribute(SharePrefConstant.ChatUserPic);
                //存入本地数据库
                UserInfoCacheSvc.createOrUpdate(HXid, nickname, avater);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }

        }*/
    }
};


    public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    };
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position-1);
        if (tobeDeleteCons == null) {
            return true;
        }

        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
//	            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
//	            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        return true;
    }
    @Override
    protected void setUpView() {
        super.setUpView();
        //item点击事件
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                EMConversation conversation = conversationListView.getItem(position-1);
//                EMConversation conversation = conversationListView.getItem(position);
                if(conversation != null){
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
                }
            }
        });
        registerForContextMenu(conversationListView);

    }


    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(),"this is new onResume",Toast.LENGTH_SHORT).show();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);

    }
    @Override
    public void onStop() {

        super.onStop();
    }
    @Override
    public void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        super.onDestroy();
    }
}
