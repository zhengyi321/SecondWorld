package com.et.secondworld.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;


import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.et.secondworld.utils.GlobalParams;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.et.secondworld.MainActivity;
import com.et.secondworld.R;
import com.et.secondworld.huanxin.EaseUI;

import java.util.Iterator;
import java.util.List;


/**
 *非常好用的插件http://blog.csdn.net/liang5630/article/details/46366901/
 */
public class MyApplication extends Application {

    public static  MyApplication instance;

    public static final String APP_ID = "cee3a98135"; // TODO 替换成bugly上注册的appid
//    public static final String APP_CHANNEL = "DEBUG"; // TODO 自定义渠道
    private static final String TAG = "OnUILifecycleListener";
    @Override
    public void onCreate() {
        this.instance = this;

        super.onCreate();
        GlobalParams.mApplication = this;
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        // 图片缓存初始化
        ImageLoaderConfiguration configuration= ImageLoaderConfiguration.createDefault(this);
        // setImageLoader(configuration);
        ImageLoader.getInstance().init(configuration);
        initYouMengShare();
        initHuanXin();
        initBugly();

    }

    private void initYouMengShare(){
        UMConfigure.init(this,"5eaa8d66dbc2ec0856ab1cdc"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wxf0dc1844747169f6", "12345678901234yangyangyangetkeji");
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        /*  最新的版本需要加上这个回调地址，可以在微博开放平台申请的应用获取，必须要有*/
        /*  Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";*/
        //QQ
        PlatformConfig.setQQZone("101876523", "b78ee66a4313034d19e4026cea38abee");
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static  MyApplication getContext() {

        return instance;
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }



    private void initHuanXin(){
//        appContext = this;
       /* EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        EaseUI.getInstance().init(this, null);*/
//         EMClient.getInstance().setDebugMode(true);
      /*  int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(getContext().getPackageName())) {
//            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
//...
//初始化
        EMClient.getInstance().init(getContext(), options);
        EaseUI.getInstance().init(this, null);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        DemoHelper.getInstance().init(getContext());*/
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        EaseUI.getInstance().init(this, null);
        /* EMClient.getInstance().setDebugMode(true);*/

    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private void initBugly(){

        /**** Beta高级设置*****/
        /**
         * true表示app启动自动初始化升级模块；
         * false不好自动初始化
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false
         * 在后面某个时刻手动调用
         */
        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        Beta.autoCheckUpgrade = true;

        /**
         * 设置升级周期为60s（默认检查周期为0s），60s内SDK不重复向后天请求策略
         */
        Beta.initDelay = 1 * 1000;

        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源；
         */
        Beta.largeIconId = R.mipmap.ic_launcher;

        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;


        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.ic_launcher;

        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = false;

        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);
        //监听安装包下载状态
        Beta.downloadListener = new DownloadListener() {
            @Override
            public void onReceive(DownloadTask downloadTask) {
                Log.d(TAG,"downloadListener receive apk file");
            }

            @Override
            public void onCompleted(DownloadTask downloadTask) {
                Log.d(TAG,"downloadListener download apk file success");
            }

            @Override
            public void onFailed(DownloadTask downloadTask, int i, String s) {
                Log.d(TAG,"downloadListener download apk file fail");
            }
        };

        //监听APP升级状态
        Beta.upgradeStateListener = new UpgradeStateListener(){
            @Override
            public void onUpgradeFailed(boolean b) {
                Log.d(TAG,"upgradeStateListener upgrade fail");
            }

            @Override
            public void onUpgradeSuccess(boolean b) {
                Log.d(TAG,"upgradeStateListener upgrade success");
            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                Log.d(TAG,"upgradeStateListener upgrade has no new version");
            }

            @Override
            public void onUpgrading(boolean b) {
                Log.d(TAG,"upgradeStateListener upgrading");
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                Log.d(TAG,"upgradeStateListener download apk file success");
            }
        };
        Bugly.init(getApplicationContext(), APP_ID, true);
    }
}

