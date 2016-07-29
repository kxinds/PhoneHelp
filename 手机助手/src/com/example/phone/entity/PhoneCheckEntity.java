package com.example.phone.entity;

import android.graphics.drawable.Drawable;

public class PhoneCheckEntity {
  private Drawable img;
  private String   info;
  private String   info2;
public Drawable getImg() {
	return img;
}
public void setImg(Drawable img) {
	this.img = img;
}
public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public String getInfo2() {
	return info2;
}
public void setInfo2(String info2) {
	this.info2 = info2;
}
public PhoneCheckEntity(Drawable img, String info, String info2) {
	super();
	this.img = img;
	this.info = info;
	this.info2 = info2;
}
public PhoneCheckEntity() {
	super();
	// TODO Auto-generated constructor stub
}
  
  

}
