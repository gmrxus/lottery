package com.zzrh.lottery.entity;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class BtnImgEntity {
  private Drawable image;
  private String string;

  public BtnImgEntity() {
  }

  public BtnImgEntity(Drawable image, String string) {
    this.image = image;
    this.string = string;

  }

  public Drawable getImage() {
    return image;
  }

  public void setImage(Drawable image) {
    this.image = image;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }
}
