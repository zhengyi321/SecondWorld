package com.et.secondworld.huanxin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.et.secondworld.R;
import com.et.secondworld.huanxin.widget.EaseConversationList;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * conversation list fragment
 *
 */
public class EaseConversationListFragment extends EaseBaseFragment {
    private final static int MSG_REFRESH = 2;
    protected EditText query;
    protected ImageButton clearSearch;
    protected boolean hidden;
    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    protected EaseConversationList conversationListView;
    protected FrameLayout errorItemContainer;
    private int unReadCountTotal = 0;
    protected boolean isConflict;
    protected RelativeLayout rlyRuleChange;
    protected TextView tvRuleChange;
    private boolean isShop = false;
    private long clickTime = 0;
    protected EMConversationListener convListener = new EMConversationListener(){

        @Override
        public void onCoversationUpdate() {
            refresh();
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_conversation_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        conversationListView = (EaseConversationList) getView().findViewById(R.id.list);
        query = (EditText) getView().findViewById(R.id.query);
        rlyRuleChange = (RelativeLayout) getView().findViewById(R.id.rly_chat_rule_change);
        tvRuleChange = (TextView) getView().findViewById(R.id.tv_chat_rule_change);
        // button to clear content in search bar
        clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);
        errorItemContainer = (FrameLayout) getView().findViewById(R.id.fl_error_item);
    }

    @Override
    protected void setUpView() {
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        conversationList.addAll(loadConversationList());
        Log.d("aaaaaaaaaaaaaa",""+conversationList.size());
        conversationListView.init(conversationList);
        if(shopid == null || shopid.isEmpty()){
            rlyRuleChange.setVisibility(View.GONE);
        }else {
            rlyRuleChange.setVisibility(View.VISIBLE);
        }

        if(listItemClickListener != null){
            conversationListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMConversation conversation = conversationListView.getItem(position);
                    listItemClickListener.onListItemClicked(conversation);
                }
            });
        }

        EMClient.getInstance().addConnectionListener(connectionListener);

        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conversationListView.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        clearSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
                hideSoftKeyboard();
            }
        });

        conversationListView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        rlyRuleChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis() - clickTime > 2000) {

                    clickTime = System.currentTimeMillis();
                    if (isShop) {
                        rlyRuleChange.setBackgroundResource(R.mipmap.changeshop);
//                    tvRuleChange.setText("个人");
                        EMClient.getInstance().logout(true);
                        EMClient.getInstance().login(account, "11", new EMCallBack() {//回调
                            @Override
                            public void onSuccess() {
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();
//                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//                Toast.makeText(getApplicationContext(),"登录聊天服务器成功this is list:"+conversations.size(),Toast.LENGTH_LONG).show();
                                Log.d("rlyRuleChange11", "登录聊天服务器成功！");
                                refresh();
//                Toast.makeText(getContext(),"登录成功",Toast.LENGTH_LONG).show();
                /*try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.d("zzzzzzzz", "好友数！"+usernames.size()+usernames.get(0));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }*/
                                isShop = false;
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }

                            @Override
                            public void onError(int code, String message) {
                                Log.d("main", "登录聊天服务器失败！");
                            }
                        });
                    } else {
                        rlyRuleChange.setBackgroundResource(R.mipmap.changeperson);
//                    tvRuleChange.setText("店铺");
                        EMClient.getInstance().logout(true);
                        EMClient.getInstance().login(shopid, "11", new EMCallBack() {//回调
                            @Override
                            public void onSuccess() {
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();
//                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//                Toast.makeText(getApplicationContext(),"登录聊天服务器成功this is list:"+conversations.size(),Toast.LENGTH_LONG).show();
                                Log.d("rlyRuleChange1112", "登录聊天服务器成功！");
                                refresh();
//                Toast.makeText(getContext(),"登录成功",Toast.LENGTH_LONG).show();
                /*try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.d("zzzzzzzz", "好友数！"+usernames.size()+usernames.get(0));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }*/
                                isShop = true;
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }

                            @Override
                            public void onError(int code, String message) {
                                Log.d("main", "登录聊天服务器失败！");
                            }
                        });
                    }
                }
            }
        });
    }


    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };
    private EaseConversationListItemClickListener listItemClickListener;

    protected Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;

                case MSG_REFRESH:
                {


                    /*  Toast.makeText(context1,"send count:"+count,3000).show();*/

                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    conversationListView.refresh();
                    /*     String count = conversationListView.adapter.getUnReadCount();*/
                    /*Toast.makeText(getContext(),"count:"+unReadCountTotal,3000).show();*/
                    Intent intent=new Intent("unReadMessage");
                    intent.putExtra("count",""+unReadCountTotal);
                    getContext().sendBroadcast(intent);
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * connected to server
     */
    protected void onConnectionConnected(){
        errorItemContainer.setVisibility(View.GONE);
    }

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected(){
        errorItemContainer.setVisibility(View.VISIBLE);
    }


    /**
     * refresh ui
     */
    public void refresh() {
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    /**
     * load conversation list
     *
     * @return
    +    */
    protected List<EMConversation> loadConversationList(){
        // get all conversations
        unReadCountTotal = 0;
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//        Toast.makeText(getActivity(),"this is list:"+conversations.size(),Toast.LENGTH_LONG).show();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                int count = conversation.getUnreadMsgCount();
                unReadCountTotal += count;
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));

                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isConflict){
            outState.putBoolean("isConflict", true);
        }
    }

    public interface EaseConversationListItemClickListener {
        /**
         * click event for conversation list
         * @param conversation -- clicked item
         */
        void onListItemClicked(EMConversation conversation);
    }

    /**
     * set conversation list item click listener
     * @param listItemClickListener
     */
    public void setConversationListItemClickListener(EaseConversationListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }

}
