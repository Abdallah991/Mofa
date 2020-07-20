package com.fathom.mofa.DataModels;

import android.graphics.Bitmap;

public class CarPhotosDataModel {

    private Bitmap photoLeftSide;
    private Bitmap photoRightSide;
    private Bitmap photoFrontSide;
    private Bitmap photoBackSide;

    public Bitmap getPhotoLeftSide() {
        return photoLeftSide;
    }

    public void setPhotoLeftSide(Bitmap photoLeftSide) {
        this.photoLeftSide = photoLeftSide;
    }

    public Bitmap getPhotoRightSide() {
        return photoRightSide;
    }

    public void setPhotoRightSide(Bitmap photoRightSide) {
        this.photoRightSide = photoRightSide;
    }

    public Bitmap getPhotoFrontSide() {
        return photoFrontSide;
    }

    public void setPhotoFrontSide(Bitmap photoFrontSide) {
        this.photoFrontSide = photoFrontSide;
    }

    public Bitmap getPhotoBackSide() {
        return photoBackSide;
    }

    public void setPhotoBackSide(Bitmap photoBackSide) {
        this.photoBackSide = photoBackSide;
    }
}
