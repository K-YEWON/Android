package com.example.schedule;

public class RecyclerViewItem {
    private String mImagName;
    private String mMainText;
    private String mSubText;
    
    public void setImgName(String imgName) {
        this.mImagName = imgName;
    }

    public void setMainText(String mainText) {
        this.mMainText = mainText;
    }

    public void setSubText(String subText) {
        this.mSubText = subText;
    }

    public String getMainText() {
        return mMainText;
    }

    public String getSubText() {
        return mSubText;
    }
}
