package com.bytrustu.study.model;

public class GroupFileDTO {
  private int num;
  private int gNum;
  private String userID;
  private String content;
  private String gFile;
  private String gDate;
  
  public GroupFileDTO() {
  }
  
  public GroupFileDTO(int num, int gNum, String userID, String content, String gFile, String gDate) {
    super();
    this.num = num;
    this.gNum = gNum;
    this.userID = userID;
    this.content = content;
    this.gFile = gFile;
    this.gDate = gDate;
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
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getgFile() {
    return gFile;
  }
  
  public void setgFile(String gFile) {
    this.gFile = gFile;
  }
  
  public String getgDate() {
    return gDate;
  }
  
  public void setgDate(String gDate) {
    this.gDate = gDate;
  }
  
}
