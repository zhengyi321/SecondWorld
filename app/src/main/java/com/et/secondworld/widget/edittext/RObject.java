package com.et.secondworld.widget.edittext;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/27
 **/
@SuppressLint("ParcelCreator")
public class RObject implements Parcelable {
    private String objectRule = "@";// 匹配规则
    private String objectText;// 高亮文本
    private String id;

    // 其他属性...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getObjectRule() {
        return objectRule;
    }

    public void setObjectRule(String objectRule) {
        this.objectRule = objectRule;
    }

    public String getObjectText() {
        return objectText;
    }

    public void setObjectText(String objectText) {
        this.objectText = objectText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectRule);
        dest.writeString(this.objectText);
        dest.writeString(this.id);
    }
    public RObject() {
    }
    protected RObject(Parcel in) {
        this.objectRule = in.readString();
        this.objectText = in.readString();
        this.id = in.readString();
    }
    public static final Creator<RObject> CREATOR = new Creator<RObject>() {

        @Override
        public RObject[] newArray(int size) {
            return new RObject[size];
        }

        @Override
        public RObject createFromParcel(Parcel source) {
            return new RObject(source);
        }
    };
}
