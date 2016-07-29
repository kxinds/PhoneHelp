package com.example.phone.entity;

import android.R.integer;

public class TelManagerEntity {
   private int  id;
   private String name;
   private String phone;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public TelManagerEntity() {
	super();
	// TODO Auto-generated constructor stub
}
public TelManagerEntity(int id, String name, String phone) {
	super();
	this.id = id;
	this.name = name;
	this.phone = phone;
}

   
}
