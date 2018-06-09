package com.beeshop.beeshop.model;

import com.beeshop.beeshop.views.HorizontalPicAddGallery;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : cooper
 * Time :  2018/6/8 下午5:30
 * Description :
 */
public class TransformUtil {

    public static List<HorizontalPicAddGallery.UploadPicEnrity> transUploadPicEnrity(List<LocalMedia> localMediaList) {
        List<HorizontalPicAddGallery.UploadPicEnrity> list = new ArrayList<>();
        for (LocalMedia localMedia : localMediaList) {
            HorizontalPicAddGallery.UploadPicEnrity uploadPicEnrity = new HorizontalPicAddGallery.UploadPicEnrity();
            uploadPicEnrity.path = localMedia.getPath();
            uploadPicEnrity.isDelete = 1;
            uploadPicEnrity.isFailed = 0;
            uploadPicEnrity.progress = 0;
            uploadPicEnrity.isAddPic = false;
            uploadPicEnrity.localMedia = localMedia;
            list.add(uploadPicEnrity);
        }

        return list;
    }
}
