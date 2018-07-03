package com.beeshop.beeshop.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.beeshop.beeshop.R;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/22 下午2:50
 * Description :
 */
public class SelectPicImageView extends AppCompatImageView{

    private List<LocalMedia> mSelectionMedia;
    private SelectPicClickCallBack mSelectPicClickCallBack;
    private boolean isAddPic = true;

    public void setSelectPicClickCallBack(SelectPicClickCallBack selectPicClickCallBack) {
        this.mSelectPicClickCallBack = selectPicClickCallBack;
    }

    public SelectPicImageView(Context context) {
        super(context);
        init();
    }

    public SelectPicImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectPicImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectPicClickCallBack != null) {
                    mSelectPicClickCallBack.onClick(isAddPic);
                }
            }
        });
        this.setScaleType(ScaleType.CENTER_CROP);
    }

    public List<LocalMedia> getLocalMediaList() {
        return this.mSelectionMedia;
    }

    public String getPicPath() {
        if (mSelectionMedia != null && mSelectionMedia.size() > 0) {
            return mSelectionMedia.get(0).getCompressPath();
        } else {
            return "";
        }

    }

    public void setAddView() {
        isAddPic = true;
        Glide.with(this).load(R.drawable.add_image).into(this);
    }

    public void setPic(List<LocalMedia> localMediaList) {
        if (localMediaList.size() > 0) {
            this.mSelectionMedia = localMediaList;
            LocalMedia localMedia = localMediaList.get(0);
            isAddPic = false;
            Glide.with(this).load(localMedia.getPath()).into(this);
        }

    }

    public void setPic(String path) {
        this.mSelectionMedia = new ArrayList<>();
        LocalMedia localMedia = new LocalMedia();
        localMedia.setPath(path);
        mSelectionMedia.add(localMedia);
        isAddPic = false;
        Glide.with(this).load(localMedia.getPath()).into(this);

    }

    public interface SelectPicClickCallBack{
        void onClick(boolean isAddPic);
    }
}
