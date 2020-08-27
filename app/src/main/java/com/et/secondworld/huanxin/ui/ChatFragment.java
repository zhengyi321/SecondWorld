package com.et.secondworld.huanxin.ui;

import android.view.View;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.et.secondworld.huanxin.dbhuanxin.PrefUtils;
import com.et.secondworld.huanxin.dbhuanxin.SharePrefConstant;
import com.et.secondworld.huanxin.widget.chatrow.EaseCustomChatRowProvider;
import com.et.secondworld.huanxin.widget.presenter.EaseChatRowPresenter;

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {
	@Override
	protected void initView() {
		super.initView();


	}
	@Override
	protected void setUpView() {
		/*setChatFragmentListener(this);*/

		super.setUpView();
	}


/**
	 * 设置消息扩展
	 *
	 */

	@Override
	public void onSetMessageAttributes(EMMessage message) {
		onSetUserInfoAttributes(message);
	}
/*
*
	 * 设置昵称头像扩展消息
	 * @param message 消息体

*/

	public void onSetUserInfoAttributes(EMMessage message){
		message.setAttribute(SharePrefConstant.ChatUserId, PrefUtils.getUserChatId());
		message.setAttribute(SharePrefConstant.ChatUserNick, PrefUtils.getUserName());
		message.setAttribute(SharePrefConstant.ChatUserPic, PrefUtils.getUserPic());

	}





	@Override
	public void onEnterToChatDetails() {

	}
/**
	 * 头像点击事件
	 */

	@Override
	public void onAvatarClick(String username) {

	}
/*
*
	 * 头像长按事件

*/

	@Override
	public void onAvatarLongClick(String username) {

	}
/**
	 * 消息框点击事件
	 * 这里 默认是不做覆盖的，这里也不需要覆盖，不管怎么样
	 * 这个 false最好不要改
	*/

	@Override
	public boolean onMessageBubbleClick(EMMessage message) {
		return false;
	}
/**
	 * 消息框长按事件
	 */

	@Override
	public void onMessageBubbleLongClick(EMMessage message) {

	}
/**
	 * 聊天下面的图片  相机，视频，音频通话菜单按钮
	 */

	@Override
	public boolean onExtendMenuItemClick(int itemId, View view) {
		return false;
	}
/*
*
	 * 扩展消息提供者

*/

	@Override
	public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
		return customChatRowProvider;
	}

	EaseCustomChatRowProvider customChatRowProvider = new EaseCustomChatRowProvider() {

		@Override
		public int getCustomChatRowTypeCount() {
			return 0;
		}

		@Override
		public int getCustomChatRowType(EMMessage message) {
			return 0;
		}

		@Override
		public EaseChatRowPresenter getCustomChatRow(EMMessage message, int position,
													 BaseAdapter adapter) {
			return null;
		}
	};
	protected void registerExtendMenuItem() {
		super.registerExtendMenuItem();
	};




}