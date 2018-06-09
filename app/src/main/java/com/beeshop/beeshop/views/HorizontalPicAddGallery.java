package com.beeshop.beeshop.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.utils.DensityUtil;
import com.beeshop.beeshop.utils.ViewUtil;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/7 下午5:51
 * Description :
 */
public class HorizontalPicAddGallery extends RecyclerView {

    private Context mContext;
    private PicAdapter mPicAdapter;
    private List<UploadPicEnrity> mPicList = new ArrayList<>();
    private PicAddListener mPicAddListener;

    public void setPicAddListener(PicAddListener picAddListener) {
        this.mPicAddListener = picAddListener;
    }

    public HorizontalPicAddGallery(Context context) {
        super(context);
        initView(context);
    }

    public HorizontalPicAddGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HorizontalPicAddGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(linearLayoutManager);

        setAddPic();
        mPicAdapter = new PicAdapter(mPicList, mContext);
        this.setAdapter(mPicAdapter);
    }

    /**
     * 添加默认图片
     */
    private void setAddPic() {
        UploadPicEnrity uploadPicEnrity = new UploadPicEnrity();
        uploadPicEnrity.isAddPic = true;
        uploadPicEnrity.progress = -1;
        uploadPicEnrity.isDelete = 0;
        uploadPicEnrity.isFailed = 0;
        mPicList.add(uploadPicEnrity);
    }

    public List<UploadPicEnrity> getPicList() {
        return mPicList;
    }

    public void setPic(UploadPicEnrity uploadPicEnrity) {
        if (mPicAdapter != null) {
            mPicAdapter.setPic(uploadPicEnrity);
        }
    }

    public void setPicList(List<UploadPicEnrity> list) {
        mPicList.clear();
        setAddPic();
        if (mPicAdapter != null) {
            mPicAdapter.setPicList(list);
        }
    }

    public void deletePic(int position) {
        if (mPicAdapter != null) {
            mPicAdapter.deletePic(position);
        }
    }

    public void refreshGallery() {
        mPicAdapter.notifyDataSetChanged();
    }

    public List<LocalMedia> getChoosedPic() {
        List<LocalMedia> localMediaList = new ArrayList<>();
        for (int i = 0; i < mPicList.size() - 1; i++) {
            UploadPicEnrity uploadPicEnrity = mPicList.get(i);
            localMediaList.add(uploadPicEnrity.localMedia);
        }
        return localMediaList;
    }

    class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicViewHolder> {

        private List<UploadPicEnrity> picList;
        private Context paContext;

        public PicAdapter(List<UploadPicEnrity> picList, Context paContext) {
            this.picList = picList;
            this.paContext = paContext;
        }

        public void setPicList(List<UploadPicEnrity> list) {
            for (UploadPicEnrity uploadPicEnrity : list) {
                picList.add(0, uploadPicEnrity);
            }
            this.notifyDataSetChanged();
        }

        public void setPic(UploadPicEnrity uploadPicEnrity) {
            picList.add(0, uploadPicEnrity);
            this.notifyDataSetChanged();
        }

        public void deletePic(int position) {
            picList.remove(position);
            this.notifyDataSetChanged();
        }

        @Override
        public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_status, parent, false);
            return new PicViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PicViewHolder holder, final int position) {
            final UploadPicEnrity uploadPicEnrity = picList.get(position);
            if (uploadPicEnrity.isDelete == 0) {
                holder.iv_delete.setVisibility(GONE);
            } else {
                holder.iv_delete.setVisibility(VISIBLE);
            }
            if (uploadPicEnrity.isFailed == 0) {
                holder.iv_retry.setVisibility(GONE);
            } else {
                holder.iv_retry.setVisibility(VISIBLE);
            }
            if (uploadPicEnrity.progress > 0 && uploadPicEnrity.progress < 100) {
                holder.tv_upload_progress.setVisibility(VISIBLE);
                holder.tv_upload_progress.setText(uploadPicEnrity.progress + "%");
            } else {
                holder.tv_upload_progress.setVisibility(GONE);
            }
            if (uploadPicEnrity.isAddPic) {
                Glide.with(paContext).load(R.drawable.add_image).into(holder.iv_pic);
            } else {
                Glide.with(paContext).load(uploadPicEnrity.path).into(holder.iv_pic);
            }

            holder.iv_pic.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPicAddListener != null) {
                        if (uploadPicEnrity.isAddPic) {
                            mPicAddListener.addPic();
                        } else {
                            mPicAddListener.picClicked(position);
                        }
                    }
                }
            });

            holder.iv_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPicAddListener != null) {
                        mPicAddListener.deletePic(position);
                    }
                    deletePic(position);
                }
            });

            holder.iv_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPicAddListener != null) {
                        mPicAddListener.retryUploadPic(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return picList.size();
        }

        class PicViewHolder extends RecyclerView.ViewHolder {
            public ImageView iv_pic;
            public TextView tv_upload_progress;
            public ImageView iv_delete;
            public ImageView iv_retry;

            public PicViewHolder(View itemView) {
                super(itemView);
                iv_pic = itemView.findViewById(R.id.iv_pic);
                iv_delete = itemView.findViewById(R.id.iv_delete);
                tv_upload_progress = itemView.findViewById(R.id.tv_upload_progress);
                iv_retry = itemView.findViewById(R.id.iv_retry);
            }
        }
    }

    public static class UploadPicEnrity {
        public int progress;
        public String path;
        public int isFailed; // 0成功  1失败
        public int isDelete; // 0不删除  1删除
        public boolean isAddPic; // 是否是默认添加图片
        public LocalMedia localMedia;
    }

    public interface PicAddListener {
        void deletePic(int position);

        void retryUploadPic(int position);

        void addPic();

        void picClicked(int position);
    }

}
