package com.et.secondworld.widget.db.XCCacheManager;

/**
 * Created by az on 2017/5/6.
 */

import java.io.Closeable;
import java.io.IOException;

/** 关闭Closeable对象工具方法https://github.com/jczmdeveloper/XCCacheManager
 * Created by caizhiming on 2015/12/3.
 */
public final class CloseUtils {
    private CloseUtils(){

    }
    /**
     * 关闭Closeable对象
     */
    public static void closeCloseable(Closeable closeable){
        if(null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}