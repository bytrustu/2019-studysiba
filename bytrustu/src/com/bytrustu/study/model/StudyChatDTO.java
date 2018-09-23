package com.bytrustu.study.model;

public class StudyChatDTO {
  private int chatID;
  private int chatNum;
  private String userID;
  private String chatContent;
  private String chatTime;
  
  public StudyChatDTO() {
  }
  
  public StudyChatDTO(int chatID, int chatNum, String userID, String chatContent, String chatTime) {
    super();
    this.chatID = chatID;
    this.chatNum = chatNum;
    this.userID = userID;
    this.chatContent = chatContent;
    this.chatTime = chatTime;
  }
  
  public int getChatID() {
    return chatID;
  }
  
  public void setChatID(int chatID) {
    this.chatID = chatID;
  }
  
  public int getChatNum() {
    return chatNum;
  }
  
  public void setChatNum(int chatNum) {
    this.chatNum = chatNum;
  }
  
  public String getUserID() {
    return userID;
  }
  
  public void setUserID(String userID) {
    this.userID = userID;
  }
  
  public String getChatContent() {
    return chatContent;
  }
  
  public void setChatContent(String chatContent) {
    this.chatContent = chatContent;
  }
  
  public String getChatTime() {
    return chatTime;
  }
  
  public void setChatTime(String chatTime) {
    this.chatTime = chatTime;
  }
  
}
