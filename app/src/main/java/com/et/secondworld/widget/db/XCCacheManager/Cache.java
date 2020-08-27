package com.et.secondworld.widget.db.XCCacheManager;

/**
 * Created by az on 2017/5/6.
 */
/**https://github.com/jczmdeveloper/XCCacheManager
 * Created by caizhiming on 2015/12/4.
 */
public interface Cache {
    String get(final String key);
    void put(final String key, final String value);
    boolean remove(final String key);
}