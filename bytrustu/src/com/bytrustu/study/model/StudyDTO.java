package com.bytrustu.study.model;

import java.util.ArrayList;

public class StudyDTO {
  private int num;
  private int gNum;
  private String userID;
  private String groupName;
  private String divide;
  private String title;
  private String content;
  private String lat;
  private String lng;
  private String area;
  private String address;
  private String period;
  private String stime;
  private String sfile;
  private int person;
  private String date;
  private ArrayList<String> listFile;
  private int memberCount;
  private String userFile;
  
  public StudyDTO() {
  }
  
  public StudyDTO(int num, int gNum, String userID, String groupName, String divide, String title, String content, String lat, String lng, String area, String address, String period, String stime, String sfile, int person, String date) {
    super();
    this.num = num;
    this.gNum = gNum;
    this.userID = userID;
    this.groupName = groupName;
    this.divide = divide;
    this.title = title;
    this.content = content;
    this.lat = lat;
    this.lng = lng;
    this.area = area;
    this.address = address;
    this.period = period;
    this.stime = stime;
    this.sfile = sfile;
    this.person = person;
    this.date = date;
  }
  
  public int getNum() {
    return num;
  }
  
  public void setNum(int num) {
    this.num = num;
  }
  
  public int getgNum() {
    return gNum;
  }
  
  public void setgNum(int gNum) {
    this.gNum = gNum;
  }
  
  public String getUserID() {
    return userID;
  }
  
  public void setUserID(String userID) {
    this.userID = userID;
  }
  
  public String getGroupName() {
    return groupName;
  }
  
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
  
  public String getDivide() {
    return divide;
  }
  
  public void setDivide(String divide) {
    this.divide = divide;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getLat() {
    return lat;
  }
  
  public void setLat(String lat) {
    this.lat = lat;
  }
  
  public String getLng() {
    return lng;
  }
  
  public void setLng(String lng) {
    this.lng = lng;
  }
  
  public String getArea() {
    return area;
  }
  
  public void setArea(String area) {
    this.area = area;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public String getPeriod() {
    return period;
  }
  
  public void setPeriod(String period) {
    this.period = period;
  }
  
  public String getStime() {
    return stime;
  }
  
  public void setStime(String stime) {
    this.stime = stime;
  }
  
  public String getSfile() {
    return sfile;
  }
  
  public void setSfile(String sfile) {
    this.sfile = sfile;
  }
  
  public int getPerson() {
    return person;
  }
  
  public void setPerson(int person) {
    this.person = person;
  }
  
  public String getDate() {
    return date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }

  public ArrayList<String> getListFile() {
    return listFile;
  }

  public void setListFile(ArrayList<String> listFile) {
    this.listFile = listFile;
  }

  public int getMemberCount() {
    return memberCount;
  }

  public void setMemberCount(int memberCount) {
    this.memberCount = memberCount;
  }

  public String getUserFile() {
    return userFile;
  }

  public void setUserFile(String userFile) {
    this.userFile = userFile;
  }
  
  
}
