package com.et.secondworld.huanxin.dbhuanxin;


import com.et.secondworld.application.MyApplication;

public class PrefUtils extends PreferencesUtils {

    // 这里的MainApplication、BaseApplication替换成自己项目的Application子类实例即可
	public static final MyApplication mAppContext = (MyApplication)MyApplication.getContext();

/*
************************** 用户登录信息 **********************************************
*/


	public static void setUserId(long userId) {
		putLong(mAppContext, SharePrefConstant.UserId,
				userId);
	}

	public static long getUserId() {
		return getLong(mAppContext, SharePrefConstant.UserId);
	}

	public static void setUserPic(String UserPic) {
		putString(mAppContext, SharePrefConstant.UserPic,
				UserPic);
	}

	public static String getUserPic() {
		return getString(mAppContext, SharePrefConstant.UserPic);
	}

	public static void setUserEmail(String email) {
		putString(mAppContext, SharePrefConstant.UserEmail, email);
	}

	public static String getUserEmail() {
		return getString(mAppContext, SharePrefConstant.UserEmail);
	}

	public static void setUserName(String name) {
		putString(mAppContext, SharePrefConstant.UserName, name);
	}

	public static String getUserName() {
		return getString(mAppContext, SharePrefConstant.UserName);
	}

	public static void setUserChatId(String chatId) {
		putString(mAppContext, SharePrefConstant.UserChatId,
				chatId);
	}

	public static String getUserChatId() {
		return getString(mAppContext,
				SharePrefConstant.UserChatId);
	}

	public static void setUserChatPwd(String chatId) {
		putString(mAppContext, SharePrefConstant.UserChatPwd,
				chatId);
	}

	public static String getUserChatPwd() {
		return getString(mAppContext,
				SharePrefConstant.UserChatPwd);
	}
/*
*
	 * 用户是否已经登录
	 * @return 如果已经登录，则返回true
	 */

	public static boolean isLogin() {
		long userId = getUserId();
		String userName = getUserName();
		return userId > 0 && !StringUtils.isNullOrEmpty(userName);
	}

	public static void clearUserInfo(){
		setUserId(0);
		setUserPic("");
		setUserName("");
		setUserChatId("");
		setUserChatPwd("");
	}
/*
************************** 用户登录信息************end **********************************

*/

}