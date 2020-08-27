package com.et.secondworld.huanxin.widget.emojicon;

import com.et.secondworld.huanxin.domain.EaseEmojicon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class EaseEmojiconMenuBase extends LinearLayout{
    protected EaseEmojiconMenuListener listener;
    
    public EaseEmojiconMenuBase(Context context) {
        super(context);
    }
    
    @SuppressLint("NewApi")
    public EaseEmojiconMenuBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public EaseEmojiconMenuBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    
    /**
     * set emojicon menu listener
     * @param listener
     */
    public void setEmojiconMenuListener(EaseEmojiconMenuListener listener){
        this.listener = listener;
    }
    
    public interface EaseEmojiconMenuListener{
        /**
         * on emojicon clicked
         * @param emojicon
         */
        void onExpressionClicked(EaseEmojicon emojicon);
        /**
         * on delete image clicked
         */
        void onDeleteImageClicked();
    }
}